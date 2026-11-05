package finalproject;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicReference;

public class StarterApp extends Application {

    @Override
    public void start(Stage stage) {
        AtomicReference<Boolean> confirmClicked = new AtomicReference<>(false);
        // screen size
        double markerX=0;
        double markerY=0;
        GameRound round = new GameRound();
        Location current;
        current = round.pickRandomLocation();

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
        map.setLayoutX(sw - 600);
        map.setLayoutY(sh - 400);
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
        Pane menu = new Pane(title, start);
        menu.setPadding(new Insets(20));

        Pane game = new Pane(back1, marker, correctMarker, map, confirm, location, pointsNum, pointsString, next, guessLine);

        // window

        Scene mainMenu = new Scene(menu, sw, sh);

        Scene gameMenu = new Scene(game, sw, sh);

        stage.setTitle("RoseGuessr");
        stage.setScene(mainMenu);
        stage.show();

        // start
        start.setOnAction(event -> {
            stage.setScene(gameMenu);
            location.setImage(current.getImage());
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
                PlayerGuess guess = new PlayerGuess(guessLine.getEndX(), guessLine.getEndY(), marker.getLayoutX(), marker.getLayoutY()); //fix math for finding point
                pointsNum.setText(String.valueOf((Integer.valueOf(pointsNum.getText()) + guess.pointTotal())));
            }
        });

        next.setOnAction(event -> {
            if(confirmClicked.get()==true){
                marker.setLayoutX(0);
                marker.setLayoutY(0);
                marker.setVisible(false);
                Location nextLocation = round.pickRandomLocation();
                location.setImage(nextLocation.getImage());
                confirmClicked.set(false);
                correctMarker.setVisible(false);
                guessLine.setVisible(false);
            }
        });
        // cursor
        gameMenu.setOnMouseClicked(event -> {
            Point2D mouse = game.sceneToLocal(event.getSceneX(), event.getSceneY());
            double x = mouse.getX();
            double y = mouse.getY();
            if(x > 1107 && y > 675 && y < 965 && !confirmClicked.get()){ //if it's in the x/y of the map in bottom right
                marker.setVisible(true);
                marker.setLayoutX(x-marker.getFitWidth()/2);//sets the marker visible and places the point on tip of mouse
                marker.setLayoutY(y-marker.getFitHeight());
                marker.toFront();
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
