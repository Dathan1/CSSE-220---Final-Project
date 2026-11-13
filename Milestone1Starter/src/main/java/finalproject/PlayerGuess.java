package finalproject;

public class PlayerGuess implements Scorable {
    private static final int MAX_POINTS = 6700;
    private static final double DISTANCE_PENALTY = 5;

    private final double correctX;
    private final double correctY;
    private final double guessX;
    private final double guessY;
    private final double mapDiagonal;

    public PlayerGuess(double correctX, double correctY, double guessX, double guessY,
                       double mapWidth, double mapHeight) {
        this.correctX = correctX;
        this.correctY = correctY;
        this.guessX = guessX;
        this.guessY = guessY;
        this.mapDiagonal = Math.hypot(mapWidth, mapHeight);
    }

    @Override
    public int pointTotal() {
        double normalizedDistance = pixelDistance() / mapDiagonal;
        return (int) Math.round(MAX_POINTS * Math.exp(-DISTANCE_PENALTY * normalizedDistance));
    }

    public int distance() {
        return (int) Math.round(pixelDistance() * 10);
    }

    private double pixelDistance() {
        return Math.hypot(guessX - correctX, guessY - correctY);
    }
}
