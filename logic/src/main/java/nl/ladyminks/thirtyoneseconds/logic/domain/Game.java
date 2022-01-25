package nl.ladyminks.thirtyoneseconds.logic.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * In order to start a new Game Activity, Game needs to be given to an Intent. This is why Game
 * needs to be Serializable.
 *
 * @author Minka Firth
 */
public class Game implements Serializable {
    private final long gameStartTime;
    private final List<Team> teams;
    private final int turnDuration;
    private final StopCondition stopCondition;
    private final GameLog gameLog;
    private final Map<Team, Integer> scoresPerTeam = new HashMap<>();
    private final Set<String> usedQuestions = new HashSet<>();

    // change map team to string teamID?

    public Game(long gameStartTime, List<Team> teams, int turnDuration, StopCondition stopCondition, GameLog gameLog) {
        this.gameStartTime = gameStartTime;
        this.teams = teams;
        this.turnDuration = turnDuration;
        this.stopCondition = stopCondition;
        this.gameLog = gameLog;
    }

    public long getGameStartTime() {
        return gameStartTime;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public int getTurnDuration() {
        return turnDuration;
    }

    public StopCondition getStopCondition() {
        return stopCondition;
    }

    public GameLog getGameLog() {
        return gameLog;
    }

    /**
     * Decides playing Team.
     *
     * @return The playing Team : the Team whose Turn it is.
     */
    public Team decidePlayingTeam() {
        int teamIndex = gameLog.howManyTurnsPlayed() % teams.size();
        return teams.get(teamIndex);
    }

    /**
     * Sets the scores to zero at the start of a game.
     */
    public void initialiseScoresToZero() {
        for (int i = 0; i < teams.size(); i++) {
            scoresPerTeam.put(teams.get(i), 0);
        }
    }

    public void addQuestionToUsed(String question){
        usedQuestions.add(question);
    }

    @JsonIgnore
    public boolean isQuestionUsed(String newQuestion){
        return usedQuestions.contains(newQuestion);
    }

    @JsonIgnore
    public int getScore(Team team) {
        return scoresPerTeam.get(team);
    }

    /**
     * Adds a Turn to the List of turns with the score for that Turn.
     *
     * @param turn
     *         : needs a Turn to add to the List.
     */
    public void addTurnWithScore(Turn turn) {
        if (!scoresPerTeam.containsKey(turn.getPlayingTeam())) {
            throw new IllegalStateException("The scores map is empty, you should call initialiseScoresToZero on Game before you start using it.");
        }
        getGameLog().addTurn(turn);
        int score = scoresPerTeam.get(turn.getPlayingTeam()) + turn.getScoreForTurn();
        scoresPerTeam.put(turn.getPlayingTeam(), score);
    }

    /**
     * Checks if the stopCondition is met, using isConditionMet.
     *
     * @return either condition is met or not.
     */
    @JsonIgnore
    public boolean isStopConditionMet() {
        int highScore = getHighScore();
        int roundsPlayed = gameLog.howManyTurnsPlayed() / teams.size();
        return stopCondition.isConditionMet(gameStartTime, roundsPlayed, highScore);
    }

    /**
     * @return The highest score obtained by a Team thus far.
     */
    @JsonIgnore
    int getHighScore() {
        int highScore = 0;
        Collection<Integer> scores = scoresPerTeam.values();
        for (Integer score : scores) {
            if (score > highScore) {
                highScore = score;
            }
        }
        return highScore;
    }

    /**
     * Takes the List of Teams and makes a new List that is sorted.
     *
     * @return List of Teams : a new Sorted List.
     */
    @JsonIgnore
    public List<Team> getSortedList() {
        List<Team> sortedTeams = new ArrayList<>(teams);
        Collections.sort(sortedTeams, (team1, team2) -> {
            if (scoresPerTeam.get(team1) > scoresPerTeam.get(team2)) {
                return -1;
            } else if (scoresPerTeam.get(team1) < scoresPerTeam.get(team2)) {
                return 1;
            } else {
                return 0;
            }
        });
        return sortedTeams;
    }

}

