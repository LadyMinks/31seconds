package nl.ladyminks.thirtyoneseconds.logic.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * In order to start a new Game Activity, Game needs to be given to an Intent. This is why Game
 * needs to be Serializable(and therefore the "connecting classes" need to be serializable too).
 *
 * @author Minka Firth
 */
public class GameLog implements Serializable {

    private final List<Turn> turns;
    private final String gameLogID;

    @JsonCreator
    public GameLog(
            @JsonProperty("listOfTurns")
                    List<Turn> turns,
            @JsonProperty("gameLogID")
                    String gameLogID) {
        this.turns = turns;
        this.gameLogID = gameLogID;
    }

    @JsonProperty("listOfTurns")
    public List<Turn> getTurns() {
        return turns;
    }

    public GameLog() {
        this.turns = new ArrayList<>();
        this.gameLogID = UUID.randomUUID().toString();
    }

    @JsonProperty("gameLogID")
    public String getGameLogID() {
        return gameLogID;
    }

    /**
     * adds a Turn to the list of Turns.
     *
     * @param turn
     */
    public void addTurn(Turn turn) {
        turns.add(turn);
    }

    /**
     * checks how many Turns are inside a list.
     *
     * @return
     */
    public int howManyTurnsPlayed() {
        return turns.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GameLog gameLog = (GameLog) o;

        if (!Objects.equals(turns, gameLog.turns)) return false;
        return Objects.equals(gameLogID, gameLog.gameLogID);
    }

    @Override
    public int hashCode() {
        int result = turns != null ? turns.hashCode() : 0;
        result = 31 * result + (gameLogID != null ? gameLogID.hashCode() : 0);
        return result;
    }

    public Team decideWinningTeam() {
        Map<Team, Integer> teamWithPoints = new HashMap<Team, Integer>();
        for (int i = 0; i < turns.size(); i++) {
            int points = 0;
            List<Question> questions = turns.get(i).getQuestions();
            if (!teamWithPoints.containsKey(turns.get(i).getPlayingTeam())) {
                teamWithPoints.put(turns.get(i).getPlayingTeam(), 0);
            }
            for (int j = 0; j < questions.size(); j++) {
                if (questions.get(j).isCorrect()) {
                    points++;
                }
            }
            int oldPoints = teamWithPoints.get(turns.get(i).getPlayingTeam());
            points += oldPoints;
            teamWithPoints.put(turns.get(i).getPlayingTeam(), points);
        }
        Team winningTeam = null;
        int maxValueInMap = (Collections.max(teamWithPoints.values()));
        for (Map.Entry<Team, Integer> entry : teamWithPoints.entrySet()) {
            if (entry.getValue() == maxValueInMap) {
                winningTeam = entry.getKey();
            }
        }
        return winningTeam;
    }

    public boolean isTeamInGame(String teamID) {
        List<Team> playingTeams = new ArrayList<>();
        boolean isTeamInGame = false;
        for (int i = 0; i < turns.size(); i++) {
            playingTeams.add(turns.get(i).getPlayingTeam());
        }
        for (int i = 0; i < playingTeams.size(); i++) {
            if (playingTeams.get(i).getTeamID().equals(teamID)) {
                isTeamInGame = true;
            }
        }
        return isTeamInGame;
    }
}
