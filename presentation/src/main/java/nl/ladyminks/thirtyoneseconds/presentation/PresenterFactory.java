package nl.ladyminks.thirtyoneseconds.presentation;

import nl.ladyminks.thirtyoneseconds.logic.domain.Game;
import nl.ladyminks.thirtyoneseconds.logic.screens.customGame.CustomGamePresenter;
import nl.ladyminks.thirtyoneseconds.logic.screens.customGame.CustomGameView;
import nl.ladyminks.thirtyoneseconds.logic.screens.editteam.EditTeamPresenter;
import nl.ladyminks.thirtyoneseconds.logic.screens.editteam.EditTeamView;
import nl.ladyminks.thirtyoneseconds.logic.screens.game.GamePresenter;
import nl.ladyminks.thirtyoneseconds.logic.screens.game.GameView;
import nl.ladyminks.thirtyoneseconds.logic.screens.game.ReadyPage;
import nl.ladyminks.thirtyoneseconds.logic.screens.game.ResultsPage;
import nl.ladyminks.thirtyoneseconds.logic.screens.game.TurnPage;
import nl.ladyminks.thirtyoneseconds.logic.screens.home.HomePresenter;
import nl.ladyminks.thirtyoneseconds.logic.screens.home.HomeView;
import nl.ladyminks.thirtyoneseconds.logic.screens.statistics.StatisticsPresenter;
import nl.ladyminks.thirtyoneseconds.logic.screens.statistics.StatisticsView;
import nl.ladyminks.thirtyoneseconds.logic.screens.teammanager.TeamManagerPresenter;
import nl.ladyminks.thirtyoneseconds.logic.screens.teammanager.TeamManagerView;

/**
 * @author Minka Firth
 */
public interface PresenterFactory {

    CustomGamePresenter createCustomGamePresenter(CustomGameView customGameView);

    EditTeamPresenter createEditTeamPresenter(EditTeamView editTeamView, String teamID);

    GamePresenter createGamePresenter(GameView gameView, ReadyPage readyPage, TurnPage turnPage, ResultsPage resultsPage, Game game);

    HomePresenter createHomePresenter(HomeView homeview);

    StatisticsPresenter createStatisticsPresenter(StatisticsView statisticsView, String teamID);

    TeamManagerPresenter createTeamManagerPresenter(TeamManagerView teamManagerView);
}
