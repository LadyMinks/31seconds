package nl.ladyminks.thirtyoneseconds.logic.screens.home;

import nl.ladyminks.thirtyoneseconds.logic.domain.Game;

/**
 * @author Minka Firth
 */
public interface HomeView {

    /**
     * Whenever startGame is called, it will send an Intent to the game activity with an actual game.
     * @param game
     */
    void startGame(Game game);

}
