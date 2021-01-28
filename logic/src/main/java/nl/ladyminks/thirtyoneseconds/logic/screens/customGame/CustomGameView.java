package nl.ladyminks.thirtyoneseconds.logic.screens.customGame;

import java.util.List;

import nl.ladyminks.thirtyoneseconds.logic.domain.Game;
import nl.ladyminks.thirtyoneseconds.logic.domain.Statistics;
import nl.ladyminks.thirtyoneseconds.logic.domain.Team;

/**
 * @author Minka Firth
 */
public interface CustomGameView {

    void displayTeams(List<Team> teamList, List<Statistics> statistics);

    void makeTeamSelectionDialog(List<String> teamNames, List<Integer> selectedTeamIndexes);

    void startGame(Game game);

}
