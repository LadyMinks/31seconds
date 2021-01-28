package nl.ladyminks.thirtyoneseconds.logic.screens.game;

import nl.ladyminks.thirtyoneseconds.logic.domain.Team;

/**
 * @author Minka Firth
 */
public interface ResultsPage extends ScoreBoard {

    /**
     * Displays the winning Team on the screen.
     *
     * @param winningTeam
     *         the Team that has won.
     */
    void displayWinningTeam(Team winningTeam);
}
