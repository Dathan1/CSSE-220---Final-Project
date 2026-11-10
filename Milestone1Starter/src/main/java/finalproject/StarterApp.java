package finalproject;

import javafx.application.Application;
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
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class StarterApp extends Application {
    public void start(Stage stage) {
        final ArrayList<Location> locations = new ArrayList<>();
        for (int i = 1; i <= 30; i++) {
            InputStream stream = getClass().getResourceAsStream("/images/locations/Location" + i + ".jpeg");
            Image image = new Image(stream);
            locations.add(new Location(image,  400, 200));
        }


        AtomicReference<Boolean> confirmClicked = new AtomicReference<>(false);
        // screen size
        int highestRound = 0;
        int closestGuess = 6400;
        int totalPoints = 0;
        double markerX=0;
        double markerY=0;
        AtomicReference<GameRound> round = new AtomicReference<>(new GameRound(locations));
        Location current;
        current = round.get().pickRandomLocation();

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


        // buttons
        Button start = new Button("Start");
        start.setLayoutX(210);
        start.setLayoutY(110);
//        start.setOnAction(event -> {
//            statusLabel.setText("");
//            statusLabel.setLayoutX(10);
//            statusLabel.setLayoutY(30);
//        });
        Button menuButton = new Button("Menu");
        menuButton.setPrefSize(200,50);
        menuButton.setLayoutY(0);
        menuButton.setLayoutX(sw-200);

        Button finalMenuButton = new Button("Menu");
        finalMenuButton.setPrefSize(200, 50);
        finalMenuButton.setLayoutX(sw - 200);
        finalMenuButton.setLayoutY(0);

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
        correctMarker.setLayoutY(current.getY());
        correctMarker.setLayoutX(current.getX());
        marker.toFront();

        ImageView location = new ImageView();
        location.setFitWidth(1050);
        location.setFitHeight(1000);
        location.setLayoutY(25);
        location.setLayoutX(25);

        ImageView map = new ImageView();
        map.setFitHeight(300);
        map.setFitWidth(600);
        map.setLayoutX(0);
        map.setLayoutY(0);
        InputStream stream = getClass().getResourceAsStream("/images/CampusMap.png");
        Image image = new Image(stream);
        map.setImage(image);

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
        Group scrollContent = new Group(mapContent);
        ScrollPane mapZoom = new ScrollPane(scrollContent);
        mapZoom.setPannable(true);
        mapZoom.toFront();
        mapZoom.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        mapZoom.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        mapZoom.setPrefSize(600,300);
        mapZoom.setLayoutX(sw - 600);
        mapZoom.setLayoutY(sh - 400);

        Pane menu = new Pane(title, start);
        menu.setPadding(new Insets(20));

        Pane game = new Pane(back1, confirm, location, pointsNum, pointsString,locationString,locationNum,roundString,roundNum, next, mapZoom, menuButton);
        //temporarily moving marker, correctMarker, and guessLine to mapContent
        // window

        Label HighestRoundScore = new Label("Highest Round Score: " + highestRound);
        HighestRoundScore.setFont(new Font(30));

        Label ClosestSingleGuess = new Label("Closest Guess: " + closestGuess + " meters");
        ClosestSingleGuess.setFont(new Font(30));

        Label TotalPoints = new Label("Total Points: " + totalPoints);
        TotalPoints.setFont(new Font(30));

        Pane score = new Pane(finalMenuButton);
        VBox scores = new VBox(TotalPoints, HighestRoundScore, ClosestSingleGuess);
        scores.setAlignment(Pos.BASELINE_CENTER);
        //scores.prefHeightProperty().bind(score.heightProperty());
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
            location.setImage(current.getImage());
            pointsNum.setText(String.valueOf((Integer.valueOf(round.get().getPointsEarned()))));
            locationNum.setText(String.valueOf((Integer.valueOf(round.get().getLocationNum()))));
            roundNum.setText(String.valueOf((Integer.valueOf(round.get().getRoundNumber()))));

        });

        confirm.setOnAction(event -> {
            if(marker.getLayoutY()!=0 && marker.getLayoutX()!=0 && !confirmClicked.get()) {
                correctMarker.setVisible(true);
                correctMarker.toFront();
                confirmClicked.set(true);
                guessLine.setStartX(marker.getLayoutX() + marker.getFitWidth() / 2);
                guessLine.setStartY(marker.getLayoutY() + marker.getFitHeight());
                guessLine.setEndX(correctMarker.getLayoutX()+correctMarker.getFitWidth()/2);
                guessLine.setEndY(correctMarker.getLayoutY()+correctMarker.getFitHeight());
                guessLine.setVisible(true);
                PlayerGuess guess = new PlayerGuess(guessLine.getEndX(), guessLine.getEndY(), marker.getLayoutX(), marker.getLayoutY());
                round.get().setPointsEarned(round.get().getPointsEarned()+ guess.pointTotal());
                pointsNum.setText(String.valueOf((Integer.valueOf(round.get().getPointsEarned()))));
                mapContent.setScaleX(1); mapContent.setScaleY(1);
            }
        });

        next.setOnAction(event -> {
             if (confirmClicked.get()==true){
                marker.setLayoutX(0);
                marker.setLayoutY(0);
                marker.setVisible(false);
                location.setImage(round.get().nextLocation().getImage());
                confirmClicked.set(false);
                correctMarker.setVisible(false);
                guessLine.setVisible(false);
                locationNum.setText(String.valueOf(round.get().getLocationNum()));
                roundNum.setText(String.valueOf(round.get().getRoundNumber()));
                mapContent.setScaleX(1); mapContent.setScaleY(1);
            }
            if(round.get().isRoundEnded()){
                stage.setScene(ScoreMenu);
                //stage.setFullScreen(true);
            }

        });
        // cursor
        gameMenu.setOnMouseClicked(event -> {
            Point2D mapPoint = mapContent.sceneToLocal(event.getSceneX(), event.getSceneY());
            if(map.getBoundsInParent().contains(mapPoint) && !confirmClicked.get()){
                marker.setVisible(true);
                marker.setLayoutX(mapPoint.getX() - marker.getFitWidth() / 2);
                marker.setLayoutY(mapPoint.getY() - marker.getFitHeight());
                marker.toFront();
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

        //currently does not work if zoomed in too much

//        mapZoom.setOnScroll(event -> {
//            double zoomFactor = event.getDeltaY() > 0 ? 1.1 : 1 / 1.1;
//            double newScale = Math.clamp(mapContent.getScaleX() * zoomFactor, 1, 4.0);
//            mapContent.setScaleX(newScale);
//            mapContent.setScaleY(newScale);
//            event.consume();
//        });

        mapZoom.addEventFilter(ScrollEvent.SCROLL, event -> {
            double zoomFactor = event.getDeltaY() > 0 ? 1.1 : 1 / 1.1;
            double newScale = Math.clamp(mapContent.getScaleX() * zoomFactor, 1, 4.0);
            mapContent.setScaleX(newScale);
            mapContent.setScaleY(newScale);
            event.consume();
        });


    }

    public static void main(String[] args) {
        launch(args);
    }
}
