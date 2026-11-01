package finalproject;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Location {
    double x;
    double y;
    Image image;

    public Location(Image image, double x, double y){
        this.image = image;
        this.x = x;
        this.y = y;
    }

    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }

    public Image getImage(){
        return image;
    }

    @Override
    public String toString() {
        return "Location with image: " + this.getImage().toString() + " at x: " + this.getX() + ", y: " + this.getY();
    }
}
