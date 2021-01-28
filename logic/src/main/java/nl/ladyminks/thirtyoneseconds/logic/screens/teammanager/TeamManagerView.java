package nl.ladyminks.thirtyoneseconds.logic.screens.teammanager;

import java.util.List;

import nl.ladyminks.thirtyoneseconds.logic.domain.Statistics;
import nl.ladyminks.thirtyoneseconds.logic.domain.Team;

/**
 * @author Minka Firth
 */
public interface TeamManagerView {

    /**
     * Displays the Teams on the screen.
     * @param teamList the List of Teams that exist.
     */
    void displayTeams(List<Team> teamList, List<Statistics> statistics);

}
