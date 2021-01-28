package nl.ladyminks.thirtyoneseconds.app;

import android.content.Context;

import nl.ladyminks.thirtyoneseconds.logic.domain.Game;
import nl.ladyminks.thirtyoneseconds.logic.factories.GameFactory;
import nl.ladyminks.thirtyoneseconds.logic.factories.NameFactory;
import nl.ladyminks.thirtyoneseconds.logic.factories.StatisticsFactory;
import nl.ladyminks.thirtyoneseconds.logic.factories.TurnFactory;
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
import nl.ladyminks.thirtyoneseconds.presentation.PresenterFactory;
import nl.ladyminks.thirtyoneseconds.storage.CategoryRepositoryImpl;
import nl.ladyminks.thirtyoneseconds.storage.GameLogRepositoryImpl;
import nl.ladyminks.thirtyoneseconds.storage.TeamsRepositoryImpl;

/**
 * @author Minka Firth
 */
public class PresenterFactoryImpl implements PresenterFactory {

    static PresenterFactory createFactory(Context context) {
        NameFactory nameFactory = NameFactory.makeHardCodedGenerator();
        CategoryRepositoryImpl categoryRepositoryImpl = new CategoryRepositoryImpl(context);
        GameLogRepositoryImpl gameLogRepositoryImpl = new GameLogRepositoryImpl(context);
        TeamsRepositoryImpl teamsRepositoryImpl = new TeamsRepositoryImpl(context);
        GameFactory gameFactory = new GameFactory(nameFactory, categoryRepositoryImpl, teamsRepositoryImpl);
        StatisticsFactory statisticsFactory = new StatisticsFactory(gameLogRepositoryImpl);
        TurnFactory turnFactory = new TurnFactory();

        return new PresenterFactoryImpl(nameFactory, categoryRepositoryImpl, gameFactory, gameLogRepositoryImpl, teamsRepositoryImpl, statisticsFactory, turnFactory);
    }

    private final NameFactory nameFactory;
    private final CategoryRepositoryImpl categoryRepositoryImpl;
    private final GameFactory gameFactory;
    private final GameLogRepositoryImpl gameLogRepositoryImpl;
    private final TeamsRepositoryImpl teamsRepositoryImpl;
    private final StatisticsFactory statisticsFactory;
    private final TurnFactory turnFactory;

    public PresenterFactoryImpl(NameFactory nameFactory, CategoryRepositoryImpl categoryRepositoryImpl, GameFactory gameFactory, GameLogRepositoryImpl gameLogRepositoryImpl, TeamsRepositoryImpl teamsRepositoryImpl, StatisticsFactory statisticsFactory, TurnFactory turnFactory) {
        this.nameFactory = nameFactory;
        this.categoryRepositoryImpl = categoryRepositoryImpl;
        this.gameFactory = gameFactory;
        this.gameLogRepositoryImpl = gameLogRepositoryImpl;
        this.teamsRepositoryImpl = teamsRepositoryImpl;
        this.statisticsFactory = statisticsFactory;
        this.turnFactory = turnFactory;
    }

    @Override
    public CustomGamePresenter createCustomGamePresenter(CustomGameView customGameView) {
        return new CustomGamePresenter(customGameView, teamsRepositoryImpl, statisticsFactory, gameFactory);
    }

    @Override
    public EditTeamPresenter createEditTeamPresenter(EditTeamView editTeamView, String teamID) {
        return new EditTeamPresenter(editTeamView, nameFactory, categoryRepositoryImpl, teamsRepositoryImpl, teamID);
    }

    @Override
    public GamePresenter createGamePresenter(GameView gameView, ReadyPage readyPage, TurnPage turnPage, ResultsPage resultsPage, Game game) {
        return new GamePresenter(gameView, readyPage, turnPage, resultsPage, game, turnFactory, gameLogRepositoryImpl);
    }

    @Override
    public HomePresenter createHomePresenter(HomeView homeview) {
        return new HomePresenter(homeview, gameFactory);
    }

    @Override
    public StatisticsPresenter createStatisticsPresenter(StatisticsView statisticsView, String teamID) {
        return new StatisticsPresenter(statisticsFactory, statisticsView, gameLogRepositoryImpl, teamID);
    }

    @Override
    public TeamManagerPresenter createTeamManagerPresenter(TeamManagerView teamManagerView) {
        return new TeamManagerPresenter(teamManagerView, statisticsFactory, teamsRepositoryImpl);
    }

}
