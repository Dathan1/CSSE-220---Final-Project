package finalproject;

import javafx.application.Application;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.transform.Scale;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class StarterApp extends Application {
    public void start(Stage stage){
        Image mapImage = new Image(getClass().getResourceAsStream("/images/CampusMap.png"));
        double mapWidth = 600;
        double mapHeight = mapWidth * mapImage.getHeight() / mapImage.getWidth();
        String locationData;
        try (InputStream stream = getClass().getResourceAsStream("/LocationData.txt")) {
            if (stream == null) {
                throw new IOException("Could not find /LocationData.txt on the classpath.");
            }
            locationData = new String(stream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new UncheckedIOException("Could not read location data.", e);
        }

        String[] locationLines = locationData.split("\\R");
        final ArrayList<Location> locations = new ArrayList<>();
        for (int i = 1; i <= 30; i++) {
            String line = locationLines[i - 1].trim();
            if (line.isEmpty() || line.equalsIgnoreCase("Not within bounds")) {
                continue;
            }

            String[] coordinates = line.split(",");
            if (coordinates.length != 2) {
                throw new IllegalStateException("Invalid location data on line " + i + ": " + line);
            }

            double x;
            double y;
            try {
                x = Double.parseDouble(coordinates[0].trim());
                y = Double.parseDouble(coordinates[1].trim());
            } catch (NumberFormatException e) {
                throw new IllegalStateException();
            }
            if (!Double.isFinite(x) || !Double.isFinite(y) || x < 0 || x > 1 || y < 0 || y > 1) {
                throw new IllegalStateException();
            }

            InputStream imageStream = getClass().getResourceAsStream(
                    "/images/locations/Location" + i + ".jpeg");
            if (imageStream == null) {
                throw new IllegalStateException();
            }
            try (imageStream) {
                locations.add(new Location(new Image(imageStream), mapWidth * x, mapHeight * y));
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }
        AtomicReference<Boolean> confirmClicked = new AtomicReference<>(false);
        // screen size
        int highestRound = 0;
        int closestGuess = 6400;
        int totalPoints = 0;
        double markerX=0;
        double markerY=0;
        AtomicReference<GameRound> round = new AtomicReference<>(new GameRound(locations));
        AtomicReference<Location> current = new AtomicReference<>();
        current.set(round.get().pickRandomLocation());

        Rectangle2D screen = Screen.getPrimary().getVisualBounds();
        double sw = screen.getWidth();
        double sh = screen.getHeight();

        // title
        Label title = new Label("RoseGuessr");
        title.setLayoutX(175);
        title.setLayoutY(10);
        title.setFont(new Font(30));

        Label pointsNum = new Label("0");
        pointsNum.setLayoutX(1400);
        pointsNum.setLayoutY(400);
        pointsNum.setFont(new Font(30));

        Label pointsString = new Label("Points: ");
        pointsString.setLayoutX(1300);
        pointsString.setLayoutY(400);
        pointsString.setFont(new Font(30));

        Label roundString = new Label("Round Num: ");
        roundString.setLayoutX(1300);
        roundString.setLayoutY(300);
        roundString.setFont(new Font(30));

        Label roundNum = new Label("0");
        roundNum.setLayoutX(1500);
        roundNum.setLayoutY(300);
        roundNum.setFont(new Font(30));

        Label locationString = new Label("Location Num: ");
        locationString.setLayoutX(1300);
        locationString.setLayoutY(350);
        locationString.setFont(new Font(30));

        Label locationNum = new Label("0");
        locationNum.setLayoutX(1500);
        locationNum.setLayoutY(350);
        locationNum.setFont(new Font(30));

        Label statusLabel = new Label("");
        statusLabel.setLayoutX(10);
        statusLabel.setLayoutY(30);

        Label HighestRoundScore = new Label("Highest Round Score: " + highestRound);
        HighestRoundScore.setFont(new Font(30));

        Label ClosestSingleGuess = new Label("Closest Guess: " + closestGuess + " meters");
        ClosestSingleGuess.setFont(new Font(30));

        Label TotalPoints = new Label("Total Points: " + totalPoints);
        TotalPoints.setFont(new Font(30));

        // buttons
        Button start = new Button("Start");
        start.setLayoutX(210);
        start.setLayoutY(110);

        Button menuButton = new Button("Menu");
        menuButton.setPrefSize(200,50);
        menuButton.setLayoutY(0);
        menuButton.setLayoutX(sw-200);

        Button finalMenuButton = new Button("Menu");
        finalMenuButton.setPrefSize(200, 50);
        finalMenuButton.setLayoutX((sw/2)-100);
        finalMenuButton.setLayoutY(sh - 500);

        Button confirm = new Button("Confirm");
        confirm.setPrefSize(200,50);
        confirm.setLayoutX(sw-525);
        confirm.setLayoutY(sh-100);

        Button next = new Button("Next");
        next.setPrefSize(200,50);
        next.setLayoutX(sw-275);
        next.setLayoutY(sh-100);

        Button submit = new Button("Submit");
        submit.setLayoutX(210);
        submit.setLayoutY(110);

        // image
        ImageView marker = new ImageView( new Image(getClass().getResourceAsStream("/images/BlackMarker.png")));
        marker.setFitHeight(30);
        marker.setFitWidth(30);
        marker.setVisible(false);
        marker.toFront();//pushes marker to front to ensure in front of mouse

        ImageView correctMarker = new ImageView(new Image(getClass().getResourceAsStream("/images/RedMarker.png")));
        correctMarker.setFitWidth(30);
        correctMarker.setFitHeight(30);
        correctMarker.setVisible(false);
        correctMarker.setLayoutY(current.get().getY() - correctMarker.getFitHeight());
        correctMarker.setLayoutX(current.get().getX() - correctMarker.getFitWidth() / 2);
        marker.toFront();

        ImageView location = new ImageView();
        location.setFitWidth(1050);
        location.setFitHeight(1000);
        location.setLayoutY(25);
        location.setLayoutX(25);

        ImageView map = new ImageView();
        map.setFitHeight(mapHeight);
        map.setFitWidth(mapWidth);
        map.setPreserveRatio(true);
        map.setLayoutX(0);
        map.setLayoutY(0);
        map.setImage(mapImage);

        Line guessLine = new Line();
        guessLine.setVisible(false);
        guessLine.setStroke(Color.BLUE);
        guessLine.setStrokeWidth(1);

        // background
        Rectangle back1 = new Rectangle(sw,sh,Color.rgb(128,0,0));
        back1.setOpacity(0.25);

        // layout
//        StackPane mapPane  = new StackPane(map);
//        mapPane.setAlignment(map, Pos.BOTTOM_CENTER);
        Group mapContent = new Group(map, marker, correctMarker, guessLine);
        Scale mapScale = new Scale(1, 1, 0, 0);
        mapContent.getTransforms().add(mapScale);
        Group scrollContent = new Group(mapContent);
        ScrollPane mapZoom = new ScrollPane(scrollContent);
        mapZoom.setPannable(true);
        mapZoom.toFront();
        mapZoom.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        mapZoom.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        mapZoom.setPrefSize(mapWidth, mapHeight);
        mapZoom.setLayoutX(sw - mapWidth);
        mapZoom.setLayoutY(sh - mapHeight - 100);

        Pane menu = new Pane(title, start);
        menu.setPadding(new Insets(20));

        Pane game = new Pane(back1, confirm, location, pointsNum, pointsString,locationString,locationNum,roundString,roundNum, next, mapZoom, menuButton);
        //temporarily moving marker, correctMarker, and guessLine to mapContent
        // window

        Pane score = new Pane(finalMenuButton);
        VBox scores = new VBox(TotalPoints, HighestRoundScore, ClosestSingleGuess);
        scores.setAlignment(Pos.BASELINE_CENTER);
        scores.prefWidthProperty().bind(score.widthProperty());
        score.getChildren().add(scores);
//add things such as total points, highest round score, highest single guess,

        Scene mainMenu = new Scene(menu, sw, sh);

        Scene gameMenu = new Scene(game, sw, sh);

        Scene ScoreMenu = new Scene(score,sw,sh);


        stage.setTitle("RoseGuessr");
        stage.setScene(mainMenu);
        //stage.setFullScreen(true);
        //stage.setFullScreenExitHint("");
        stage.show();

        // start
        start.setOnAction(event -> {
            stage.setScene(gameMenu);
            //stage.setFullScreen(true);
            location.setImage(current.get().getImage());
            pointsNum.setText(String.valueOf((Integer.valueOf(round.get().getPointsEarned()))));
            locationNum.setText(String.valueOf((Integer.valueOf(round.get().getLocationNum()))));
            roundNum.setText(String.valueOf((Integer.valueOf(round.get().getRoundNumber()))));

        });

        confirm.setOnAction(event -> {
            System.out.println(mapHeight);
            if(marker.getLayoutY()!=0 && marker.getLayoutX()!=0 && !confirmClicked.get()) {
                correctMarker.setVisible(true);
                correctMarker.toFront();
                confirmClicked.set(true);
                guessLine.setStartX(marker.getLayoutX() + marker.getFitWidth() / 2);
                guessLine.setStartY(marker.getLayoutY() + marker.getFitHeight());
                guessLine.setEndX(correctMarker.getLayoutX()+correctMarker.getFitWidth()/2);
                guessLine.setEndY(correctMarker.getLayoutY()+correctMarker.getFitHeight());
                guessLine.setVisible(true);
                PlayerGuess guess = new PlayerGuess(
                        guessLine.getEndX(),
                        guessLine.getEndY(),
                        marker.getLayoutX() + marker.getFitWidth() / 2,
                        marker.getLayoutY() + marker.getFitHeight());
                round.get().setPointsEarned(round.get().getPointsEarned()+ guess.pointTotal());
                pointsNum.setText(String.valueOf((Integer.valueOf(round.get().getPointsEarned()))));
                mapScale.setX(1);
                mapScale.setY(1);
                mapZoom.setHvalue(mapZoom.getHmin());
                mapZoom.setVvalue(mapZoom.getVmin());
            }
        });

        next.setOnAction(event -> {
             if (confirmClicked.get()==true){
                marker.setLayoutX(0);
                marker.setLayoutY(0);
                marker.setVisible(false);
                current.set(round.get().nextLocation());
                location.setImage(current.get().getImage());
                confirmClicked.set(false);
                correctMarker.setVisible(false);
                correctMarker.setLayoutY(current.get().getY() - correctMarker.getFitHeight());
                correctMarker.setLayoutX(current.get().getX() - correctMarker.getFitWidth() / 2);
                guessLine.setVisible(false);
                locationNum.setText(String.valueOf(round.get().getLocationNum()));
                roundNum.setText(String.valueOf(round.get().getRoundNumber()));
                mapScale.setX(1);
                mapScale.setY(1);
                mapZoom.setHvalue(mapZoom.getHmin());
                mapZoom.setVvalue(mapZoom.getVmin());
            }
            if(round.get().isRoundEnded()){
                stage.setScene(ScoreMenu);
                //stage.setFullScreen(true);
            }

        });
        // cursor
        gameMenu.setOnMouseClicked(event -> {
            if(event.getButton()== MouseButton.PRIMARY) {
                Point2D mapPoint = mapContent.sceneToLocal(event.getSceneX(), event.getSceneY());
                if (map.getBoundsInParent().contains(mapPoint) && !confirmClicked.get()) {
                    marker.setVisible(true);
                    marker.setLayoutX(mapPoint.getX() - marker.getFitWidth() / 2);
                    marker.setLayoutY(mapPoint.getY() - marker.getFitHeight());
                    marker.toFront();
                }
            }
        });

        menuButton.setOnAction(event -> {
            stage.setScene(mainMenu);
           // stage.setFullScreen(true);
            round.set(new GameRound(locations));
        });

        finalMenuButton.setOnAction(event -> {
            stage.setScene(mainMenu);
            // stage.setFullScreen(true);
            round.set(new GameRound(locations));
        });


        mapZoom.addEventFilter(ScrollEvent.SCROLL, event -> {
            double zoomFactor = event.getDeltaY() > 0 ? 1.1 : 1 / 1.1;
            double oldScale = mapScale.getX();
            double newScale = Math.clamp(oldScale * zoomFactor, 1, 4.0);
            if (newScale == oldScale) {
                event.consume();
                return;
            }

            Point2D anchor = mapContent.sceneToLocal(event.getSceneX(), event.getSceneY());
            mapScale.setX(newScale);
            mapScale.setY(newScale);
            mapZoom.applyCss();
            mapZoom.layout();

            Point2D movedAnchor = mapContent.localToScene(anchor);
            double scrollDeltaX = movedAnchor.getX() - event.getSceneX();
            double scrollDeltaY = movedAnchor.getY() - event.getSceneY();
            Bounds contentBounds = scrollContent.getBoundsInLocal();
            Bounds viewport = mapZoom.getViewportBounds();
            double maxScrollX = Math.max(0, contentBounds.getWidth() - viewport.getWidth());
            double maxScrollY = Math.max(0, contentBounds.getHeight() - viewport.getHeight());
            double hRange = mapZoom.getHmax() - mapZoom.getHmin();
            double vRange = mapZoom.getVmax() - mapZoom.getVmin();
            double currentScrollX = hRange == 0 ? 0
                    : (mapZoom.getHvalue() - mapZoom.getHmin()) / hRange * maxScrollX;
            double currentScrollY = vRange == 0 ? 0
                    : (mapZoom.getVvalue() - mapZoom.getVmin()) / vRange * maxScrollY;
            mapZoom.setHvalue(mapZoom.getHmin() + Math.clamp((currentScrollX + scrollDeltaX)
                    / Math.max(1, maxScrollX), 0, 1) * hRange);
            mapZoom.setVvalue(mapZoom.getVmin() + Math.clamp((currentScrollY + scrollDeltaY)
                    / Math.max(1, maxScrollY), 0, 1) * vRange);
            event.consume();
        });


    }

    public static void main(String[] args) {
        launch(args);
    }
}
