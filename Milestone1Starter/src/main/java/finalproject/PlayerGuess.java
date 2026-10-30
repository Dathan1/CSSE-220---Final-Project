package finalproject;

public class PlayerGuess {
    Location correct;
    double guess_x;
    double guess_y;

    public PlayerGuess(Location correct, double guess_x, double guess_y){
        this.correct = correct;
        this.guess_x = guess_x;
        this.guess_y = guess_y;
    }

    public double pointTotal (){
        return 5000.0-Math.sqrt(Math.pow((guess_x-correct.getX()), 2) + Math.pow((guess_y-correct.getY()), 2));
    }
}
