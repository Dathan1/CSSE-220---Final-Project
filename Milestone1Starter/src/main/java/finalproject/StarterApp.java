package finalproject;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.io.InputStream;

public class StarterApp extends Application {

    @Override
    public void start(Stage stage) {
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
        Label title = new Label("oseGuessr");
        title.setFont(new Font(100));
        title.setTranslateX(58);
        title.setTranslateY(-150);

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
        start.setPrefSize(200,50);
//        start.setOnAction(event -> {
//            statusLabel.setText("");
//            statusLabel.setLayoutX(10);
//            statusLabel.setLayoutY(30);
//        });

        Button confirm = new Button("Confirm");
        confirm.setPrefSize(200,50);
        confirm.setLayoutX(sw-400);
        confirm.setLayoutY(sh-100);

        Button submit = new Button("Submit");
        submit.setLayoutX(210);
        submit.setLayoutY(110);

        // image
        ImageView marker = new ImageView();
        marker.setFitHeight(30);
        marker.setFitWidth(30);

        ImageView location = new ImageView();
        location.setFitWidth(1050);
        location.setFitHeight(1000);
        location.setLayoutY(25);
        location.setLayoutX(25);

        ImageView map = new ImageView();
        map.setFitHeight(300);
        map.setFitWidth(600);
        map.setLayoutX(sw - 600);
        map.setLayoutY(sh-400);
        InputStream stream = getClass().getResourceAsStream("/images/CampusMap.png");
        Image image = new Image(stream);
        map.setImage(image);

        ImageView rose = new ImageView();
        rose.setTranslateY(-176);
        rose.setTranslateX(-232);
        InputStream logo = getClass().getResourceAsStream("/images/RoseHulmanLogo.png");
        Image r = new Image(logo);
        rose.setImage(r);

        // background
        Rectangle back1 = new Rectangle(sw,sh,Color.rgb(128,0,0));
        back1.setOpacity(0.25);

        // layout
        StackPane menu = new StackPane(title, start, rose);
        menu.setPadding(new Insets(20));

        Pane game = new Pane(back1, marker, map, confirm, location, pointsNum, pointsString);

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
            if(marker.getLayoutY()!=0 && marker.getLayoutX()!=0) {
                PlayerGuess guess = new PlayerGuess(current, marker.getLayoutX()-(sw-600), marker.getLayoutY()-(sh-400)); //fix math for finding point
                pointsNum.setText(String.valueOf((Integer.valueOf(pointsNum.getText()) + guess.pointTotal())));
                marker.setLayoutX(0);
                marker.setLayoutY(0);
                marker.setVisible(false);
                Location nextLocation = round.pickRandomLocation();
                location.setImage(nextLocation.getImage());
            }
        });

        // cursor
        gameMenu.setOnMouseClicked(event -> {
            double x = event.getX();//get mouse x and y
            double y = event.getY();
            if(x > 1107 && y > 675 && y < 965){ //if it's in the x/y of the map in bottom right
                marker.setVisible(true);
                marker.setLayoutX(x-14);//sets the marker visible and places the point on tip of mouse
                marker.setLayoutY(y-24);
                InputStream stream2 = getClass().getResourceAsStream("/images/BlackMarker.png");
                Image image2 = new Image(stream2);
                marker.setImage(image2);
                marker.toFront();//pushes marker to front to ensure in front of mouse
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
