package finalproject;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.io.InputStream;

public class StarterApp extends Application {

    @Override
    public void start(Stage stage) {
        // screen size
        Rectangle2D screen = Screen.getPrimary().getVisualBounds();
        double sw = screen.getWidth();
        double sh = screen.getHeight();

        // title
        Label title = new Label("RoseGuessr");
        title.setLayoutX(175);
        title.setLayoutY(10);

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


        // background
        Rectangle back1 = new Rectangle(sw,sh,Color.rgb(128,0,0));
        back1.setOpacity(0.25);

        // layout
        Pane menu = new Pane(title, start, marker);
        menu.setPadding(new Insets(20));

        Pane game = new Pane(back1, marker, map, confirm, location);

        // window

        Scene mainMenu = new Scene(menu, sw, sh);

        Scene gameMenu = new Scene(game, sw, sh);

        stage.setTitle("RoseGuessr");
        stage.setScene(mainMenu);
        stage.show();

        // start
        start.setOnAction(event -> {
            stage.setScene(gameMenu);
            GameRound round = new GameRound();
            location.setImage(round.pickRandomLocation().getImage());
        });

        // cursor
        gameMenu.setOnMouseClicked(event -> {
            double x = event.getX();
            double y = event.getY();
            if(x > 1107 && y > 675 && y < 965){
                marker.setLayoutX(x-14);
                marker.setLayoutY(y-24);
                InputStream stream2 = getClass().getResourceAsStream("/images/BlackMarker.png");
                Image image2 = new Image(stream2);
                marker.setImage(image2);
                marker.toFront();
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
