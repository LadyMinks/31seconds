package nl.ladyminks.thirtyoneseconds.logic.screens.game;

import nl.ladyminks.thirtyoneseconds.logic.domain.Team;

/**
 * @author Minka Firth
 */
public interface ScoreBoard {
    /**
     * Makes a score board view for each playing Team.
     *
     * @param amountOfTeams
     *         the amount of Teams that are playing.
     */
    void prepareScoreBoard(int amountOfTeams);

    /**
     * Displays the given Team with its score on the screen.
     *
     * @param index
     *         index of the The order of the score board views.
     * @param team
     *         the Team on that score board view.
     * @param score
     *         the Score on that score board view.
     */
    void displayTeamWithScore(int index, Team team, int score);

}
