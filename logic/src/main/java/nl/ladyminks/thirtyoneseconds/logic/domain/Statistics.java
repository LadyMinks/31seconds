package nl.ladyminks.thirtyoneseconds.logic.domain;

import java.util.Map;

import androidx.annotation.Nullable;

/**
 * @author Minka Firth
 */
public class Statistics {

    private final int wins;
    private final int draws;
    private final int losses;
    private final Category mostPoints;
    private final Category bestRatio;
    private final Category worstRatio;
    private final nl.ladyminks.thirtyoneseconds.logic.domain.Team rivalTeam;
    private final int totalPointsEver;
    private final int missedQuestions;
    private final long playedTime;
    private final Map<Category, PointsRatio> CategoriesWithPoints;

    public Statistics(int wins, int draws, int losses, Category mostPoints, Category bestRatio, Category worstRatio, nl.ladyminks.thirtyoneseconds.logic.domain.Team rivalTeam, int totalPointsEver, int missedQuestions, long playedTime, Map<Category, PointsRatio> categoriesWithPoints) {
        this.wins = wins;
        this.draws = draws;
        this.losses = losses;
        this.mostPoints = mostPoints;
        this.bestRatio = bestRatio;
        this.worstRatio = worstRatio;
        this.rivalTeam = rivalTeam;
        this.totalPointsEver = totalPointsEver;
        this.missedQuestions = missedQuestions;
        this.playedTime = playedTime;
        CategoriesWithPoints = categoriesWithPoints;
    }

    public int getWins() {
        return wins;
    }

    public int getDraws() {
        return draws;
    }

    public int getLosses() {
        return losses;
    }

    @Nullable
    public Category getMostPoints() {
        return mostPoints;
    }

    @Nullable
    public Category getBestRatio() {
        return bestRatio;
    }

    @Nullable
    public Category getWorstRatio() {
        return worstRatio;
    }

    @Nullable
    public Team getRivalTeam() {
        return rivalTeam;
    }

    public int getTotalPointsEver() {
        return totalPointsEver;
    }

    public int getMissedQuestions() {
        return missedQuestions;
    }

    public long getPlayedTime() {
        return playedTime;
    }

    public Map<Category, PointsRatio> getCategoriesWithPoints() {
        return CategoriesWithPoints;
    }
}
