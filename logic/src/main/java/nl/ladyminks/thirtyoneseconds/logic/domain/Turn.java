package nl.ladyminks.thirtyoneseconds.logic.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

/**
 * In order to start a new Game Activity, Game needs to be given to an Intent. This is why Game
 * needs to be Serializable(and therefore the "connecting classes" need to be serializable too).
 *
 * @author Minka Firth
 */
public class Turn implements Serializable {
    private final Team playingTeam;
    private final List<Question> questions;
    private final long turnStartTime;

    @JsonCreator
    public Turn(
            @JsonProperty("team") Team playingTeam,
            @JsonProperty("questions") List<Question> questions,
            @JsonProperty("startTime") long turnStartTime) {
        this.playingTeam = playingTeam;
        this.questions = questions;
        this.turnStartTime = turnStartTime;
    }

    @JsonProperty("team")
    public Team getPlayingTeam() {
        return playingTeam;
    }

    @JsonProperty("questions")
    public List<Question> getQuestions() {
        return questions;
    }

    @JsonProperty("startTime")
    public long getTurnStartTime() {
        return turnStartTime;
    }

    /**
     * Calculates the score for this turn. Score is the amount of correctly guessed Questions this
     * Turn.
     *
     * @return the score of this turn.
     */
    @JsonIgnore
    public int getScoreForTurn() {
        int score = 0;
        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            if (question.isCorrect()) {
                score++;
            }
        }
        return score;
    }

    @JsonIgnore
    public int getMissedQuestionsForTurn() {
        int missedQuestions = 0;
        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            if (!question.isCorrect()) {
                missedQuestions++;
            }
        }
        return missedQuestions;
    }
}
