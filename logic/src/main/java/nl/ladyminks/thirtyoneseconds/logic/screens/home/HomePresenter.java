package nl.ladyminks.thirtyoneseconds.logic.screens.home;

import nl.ladyminks.thirtyoneseconds.logic.domain.Game;
import nl.ladyminks.thirtyoneseconds.logic.factories.GameFactory;

/**
 * @author Minka Firth
 */
public class HomePresenter {

    private final HomeView homeView;
    private final GameFactory gameFactory;

    public HomePresenter(HomeView homeView, GameFactory gameFactory) {
        this.homeView = homeView;
        this.gameFactory = gameFactory;
    }

    /**
     * Starts a new Game.
     *
     * @param amountOfTeams
     *         specified by the user.
     */
    public void startQuickGame(int amountOfTeams) {
        Game quickGame = gameFactory.createQuickGame(amountOfTeams);
        homeView.startGame(quickGame);
    }

}
