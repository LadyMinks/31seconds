package nl.ladyminks.thirtyoneseconds.logic.screens.game;

import java.util.List;

import nl.ladyminks.thirtyoneseconds.logic.domain.Game;
import nl.ladyminks.thirtyoneseconds.logic.domain.Team;
import nl.ladyminks.thirtyoneseconds.logic.domain.Turn;
import nl.ladyminks.thirtyoneseconds.logic.factories.TurnFactory;
import nl.ladyminks.thirtyoneseconds.logic.repositories.GameLogRepository;

/**
 * @author Minka Firth
 */
public class GamePresenter {

    private final GameView gameView;
    private final ReadyPage readyPage;
    private final TurnPage turnPage;
    private final ResultsPage resultsPage;
    private final TurnFactory turnFactory;
    private final GameLogRepository gameLogRepository;
    private final Game game;
    private Turn turn;

    public GamePresenter(GameView gameView, ReadyPage readyPage, TurnPage turnPage, ResultsPage resultsPage, Game game, TurnFactory turnFactory, GameLogRepository gameLogRepository) {
        this.gameView = gameView;
        this.readyPage = readyPage;
        this.turnPage = turnPage;
        this.resultsPage = resultsPage;
        this.game = game;
        this.turnFactory = turnFactory;
        this.gameLogRepository = gameLogRepository;
    }

    /**
     * Creates the Presenter.
     */
    public void onCreate() {
        readyPage.prepareScoreBoard(game.getTeams().size());
        game.initialiseScoresToZero();
        prepareNextTurn();
    }

    /**
     * Starts a Turn, using the TurnGenerator.
     */
    public void startTurn() {
        gameView.showPage(GameView.Page.TURN_PAGE);
        turn = turnFactory.generateTurn(game.decidePlayingTeam(), game);
        turnPage.displayQuestions(turn.getQuestions());
        updateTimer();
    }

    /**
     * Terminates the rounds.
     */
    public void stopTurn() {
        Turn completedTurn = new Turn(turn.getPlayingTeam(), turnPage.getQuestionsWithCorrectness(), turn.getTurnStartTime());
        game.addTurnWithScore(completedTurn);
        turn = null;
        prepareNextTurn();
        final boolean isStopped = game.isStopConditionMet();
        if (isStopped) {
            stopGame();
        }
        System.out.println("Is the game stopped? " +
                (isStopped ? "YES!" : "No... :("));
    }

    /**
     * shows the ready page, and displays the right team that's playing.
     */
    private void prepareNextTurn() {
        gameView.showPage(GameView.Page.READY_PAGE);
        readyPage.displayPlayingTeam(game.decidePlayingTeam());
        List<Team> sortedTeams = game.getSortedList();
        for (int i = 0; i < sortedTeams.size(); i++) {
            readyPage.displayTeamWithScore(i, sortedTeams.get(i), game.getScore(sortedTeams.get(i)));
        }
    }

    /**
     * this method calculates the 30 seconds timer.
     */
    public void updateTimer() {
        if (turn == null) {
            return;
        }
        long now = System.currentTimeMillis();
        long targetTime = turn.getTurnStartTime() + 30000;
        long timeLeft = targetTime - now;
        turnPage.displayTimeLeft(timeLeft);
        if (timeLeft <= 0) {
            stopTurn();
        }
        turnPage.scheduleTimer();
    }

    /**
     * Terminates a game. Shows the Results page with winning Team.
     */
    public void stopGame() {
        gameView.showPage(GameView.Page.RESULTS_PAGE);
        resultsPage.prepareScoreBoard(game.getTeams().size());
        List<Team> sortedTeams = game.getSortedList();
        for (int i = 0; i < sortedTeams.size(); i++) {
            resultsPage.displayTeamWithScore(i, sortedTeams.get(i), game.getScore(sortedTeams.get(i)));
        }
        resultsPage.displayWinningTeam(sortedTeams.get(0));
        gameLogRepository.storeGameLog(game.getGameLog());
    }
}

