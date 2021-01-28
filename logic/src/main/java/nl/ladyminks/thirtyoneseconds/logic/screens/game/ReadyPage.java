package nl.ladyminks.thirtyoneseconds.logic.screens.game;

import nl.ladyminks.thirtyoneseconds.logic.domain.Team;

/**
 * @author Minka Firth
 */
public interface ReadyPage extends ScoreBoard {

    /**
     * Displays the playing Team on the screen.
     *
     * @param playingTeam
     *         the Team whose Turn it is.
     */
    void displayPlayingTeam(Team playingTeam);

}
