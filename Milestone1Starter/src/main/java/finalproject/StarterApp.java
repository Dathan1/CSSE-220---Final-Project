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
        // TODO 1: Choose a simple app title and create a small root layout.
        Label titleLabel = new Label("CSSE220 Final Project");
        Label statusLabel = new Label("Starter app is ready.");
        ImageView imageView = new ImageView();
        imageView.setFitHeight(30);
        imageView.setFitWidth(30);

        // TODO 2: Add your first interactive control (button, text field, etc.).
        Button actionButton = new Button("Tap to update status");
        actionButton.setOnAction(event -> {
            statusLabel.setText("Your team has launched the shared starter.");
            statusLabel.setLayoutX(10);
            statusLabel.setLayoutY(30);
        });

        // TODO 3: Arrange the title, message, and control in a clean layout.
        Pane root = new Pane( titleLabel, statusLabel, actionButton, imageView);
        root.setPadding(new Insets(20));

        // TODO 4: Set the window title and size.
        Scene scene = new Scene(root, 420, 220);
        stage.setTitle("HEEEEEELP! HEEELLLPP ME!");
        stage.setScene(scene);
        stage.show();

        scene.setOnMouseClicked(event -> {
            double x = event.getX();
            double y = event.getY();
            imageView.setLayoutX(x-14);
            imageView.setLayoutY(y-24);
            InputStream stream = getClass().getResourceAsStream("/images/BlackMarker.png");
            Image image = new Image(stream);
            imageView.setImage(image);
        });

        // TODO 5: Replace this starter view with your real app design.
    }

    public static void main(String[] args) {
        launch(args);
    }
}
