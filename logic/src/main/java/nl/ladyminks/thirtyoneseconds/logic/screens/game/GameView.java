package nl.ladyminks.thirtyoneseconds.logic.screens.game;

/**
 * @author Minka Firth
 */
public interface GameView {

    /**
     * Shows the given Page.
     *
     * @param page
     *         the Page that must become visible.
     */
    void showPage(Page page);

    enum Page {
        READY_PAGE,
        TURN_PAGE,
        RESULTS_PAGE
    }

}
