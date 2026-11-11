package finalproject;

public class PlayerGuess implements Scorable {
//    Location correct;
    double correct_x;
    double correct_y;
    double guess_x;
    double guess_y;

    public PlayerGuess(double correctX, double correctY, double guess_x, double guess_y){
        this.correct_x = correctX;
        this.correct_y = correctY;
        this.guess_x = guess_x;
        this.guess_y = guess_y;
    }
    @Override
    public int pointTotal (){
        return 6700-((int)Math.sqrt(Math.pow((guess_x-correct_x), 2) + Math.pow((guess_y-correct_y), 2)))*10;
    }
    public int distance (){
        return ((int)Math.sqrt(Math.pow((guess_x-correct_x), 2) + Math.pow((guess_y-correct_y), 2)))*10;
    }
}
