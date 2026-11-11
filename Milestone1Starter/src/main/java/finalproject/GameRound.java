package finalproject;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import java.io.InputStream;
import java.util.ArrayList;

public class GameRound {
    private static int LOCATION_NUM = 30;
    private ArrayList<Location> locations = new ArrayList<>();
    private int roundNumber;
    private int pointsEarned;
    private int locationNum;
    private boolean roundEnded = true;
    Rectangle2D screen = Screen.getPrimary().getVisualBounds();

    public GameRound(ArrayList<Location> locations) {
        this.locations = locations;
        this.roundNumber = 1;
        this.pointsEarned = 0;
        this.locationNum = 1;
        this.roundEnded = false;
        
    }

    public Location nextLocation(){
        locationNum+=1;
        if(locationNum >3){
            roundNumber +=1;
            locationNum=1;
        }
        if(roundNumber >3){
            roundEnded = true;
        }
        return pickRandomLocation();
    }

    public Location pickRandomLocation() {
        int num = (int)(Math.random() * locations.size());
        Location tempLocation = locations.get(num);
        locations.remove(num);
        return tempLocation;
    }




    public int getPointsEarned() {
        return this.pointsEarned;
    }

    public void setRoundNumber(int num) {
        this.roundNumber = num;
    }

    public int getRoundNumber() {
        return this.roundNumber;
    }

    public int getLocationNum(){
        return locationNum;
    }

    public void setPointsEarned(int num) {
        this.pointsEarned = num;
    }

    public boolean isRoundEnded() {
        return roundEnded;
    }
}
