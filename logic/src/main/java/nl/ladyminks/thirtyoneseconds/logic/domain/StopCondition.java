package nl.ladyminks.thirtyoneseconds.logic.domain;

import java.io.Serializable;

/** In order to start a new Game Activity, Game needs to be given to an Intent. This is why Game
 * needs to be Serializable (and therefore the "connecting classes" need to be serializable too).
 * @author Minka Firth
 */
public class StopCondition implements Serializable {

    private final Type type;
    private final int stopConditionUnit;

    public StopCondition(Type type, int stopConditionUnit) {
        this.type = type;
        this.stopConditionUnit = stopConditionUnit;
    }

    public enum Type {
        POINTS_REACHED,
        TIME_ELAPSED,
        ROUNDS_PLAYED
    }

    public Type getType() {
        return type;
    }

    /**
     * Checks if the condition is met, based on what Type has been specified.
     *
     * @param gameStartTime
     *         Timestamp from start of the game.
     * @param roundsPlayed
     *         Amount of Rounds that have been played.
     * @param score
     *         The score of a Team.
     * @return Condition is either met or not.
     */
    public boolean isConditionMet(long gameStartTime, int roundsPlayed, int score) {
        switch (type) {
            case TIME_ELAPSED:
                long targetTime = gameStartTime + stopConditionUnit * 1000 * 60;
                long now = System.currentTimeMillis();
                return now >= targetTime;
            case ROUNDS_PLAYED:
                return roundsPlayed >= stopConditionUnit;
            case POINTS_REACHED:
                return score >= stopConditionUnit;
            default:
                throw new IllegalStateException("wrong condition type");
        }
    }
}
