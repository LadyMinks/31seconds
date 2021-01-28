package nl.ladyminks.thirtyoneseconds.logic.domain;

/**
 * @author Minka Firth
 */
public class PointsRatio {

    private int wins = 0;
    private int losses = 0;

    public int getWins() {
        return wins;
    }

    public int getLosses() {
        return losses;
    }

    public float getWinPercentage() {
        return wins * (float) 100 / (wins + losses);
    }

    public void incrementWins(){
        wins++;
    }

    public  void incrementLosses(){
        losses++;
    }
}
