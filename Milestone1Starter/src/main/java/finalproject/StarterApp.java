package finalproject;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.InputStream;

/**
 * Starter JavaFX app for CSSE220 Milestone 1.
 * Replace this simple window with the first version of your project.
 */
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

        // TODO 3: Arrange the title, message, and control in a clean layout.
        Pane root = new Pane( titleLabel, statusLabel, actionButton, marker);
        root.setPadding(new Insets(20));

        // TODO 4: Set the window title and size.
        Scene scene = new Scene(root, 420, 220);
        stage.setTitle("CSSE220 Team Starter");
        stage.setScene(scene);
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

        // TODO 5: Replace this starter view with your real app design.
    }

    public static void main(String[] args) {
        launch(args);
    }
}

