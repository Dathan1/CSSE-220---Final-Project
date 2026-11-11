package finalproject;

import javafx.scene.image.Image;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.nio.file.Files;
import java.nio.file.Path;

public class GameRound {
    private static final int LOCATION_NUM = 30;
    private static ArrayList<Location> locations = new ArrayList<>();
    private int roundNumber;
    private int pointsEarned;

    public GameRound() {
        this.roundNumber = 1;
        this.pointsEarned = 0;

        if (locations.isEmpty()) {
            createLocations();
        }
        
    }

    private void createLocations() {
        //locations = new ArrayList<>(LOCATION_NUM);
        String locationData = "";
        int index = 0;
        try {
            locationData = Files.readString(Path.of("src/main/resources/LocationData.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 1; i <= LOCATION_NUM; i++) {
            if (locationData.charAt(index) == 'N') continue;
            double x = Double.parseDouble(locationData.substring(index, index + 6));
            index += 7;
            double y = Double.parseDouble(locationData.substring(index, index + 6));
            index += 8;
            InputStream stream = getClass().getResourceAsStream("/images/locations/Location" + i + ".jpeg");
            Image image = new Image(stream);
            //locations[i - 1] = new Location(image, 0, 0);   OLD ARRAY code
            locations.add(new Location(image, x, y));
        }
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

    public void setPointsEarned(int num) {
        this.pointsEarned = num;
    }


}
