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

        Button submit = new Button("Submit");
        submit.setLayoutX(210);
        submit.setLayoutY(110);

        // image
        ImageView marker = new ImageView();
        marker.setFitHeight(30);
        marker.setFitWidth(30);

        // background
        Rectangle back1 = new Rectangle(sw,sh,Color.rgb(128,0,0));
        back1.setOpacity(0.25);

        // layout
        Pane menu = new Pane(title, start, marker);
        menu.setPadding(new Insets(20));

        Pane game = new Pane(back1, submit, marker);

        // window

        Scene mainMenu = new Scene(menu, sw, sh);

        Scene gameMenu = new Scene(game, sw, sh);

        stage.setTitle("RosGuessr");
        stage.setScene(mainMenu);
        stage.show();

        // start
        start.setOnAction(event -> {
            stage.setScene(gameMenu);
        });

        // cursor
        gameMenu.setOnMouseClicked(event -> {
            double x = event.getX();
            double y = event.getY();
            marker.setLayoutX(x-14);
            marker.setLayoutY(y-24);
            InputStream stream = getClass().getResourceAsStream("/images/BlackMarker.png");
            Image image = new Image(stream);
            marker.setImage(image);
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
