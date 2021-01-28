package nl.ladyminks.thirtyoneseconds.logic.screens.statistics;

import nl.ladyminks.thirtyoneseconds.logic.factories.StatisticsFactory;
import nl.ladyminks.thirtyoneseconds.logic.repositories.GameLogRepository;

/**
 * @author Minka Firth
 */
public class StatisticsPresenter {

    private final StatisticsFactory statisticsFactory;
    private final StatisticsView statisticsView;
    private final GameLogRepository gameLogRepository;
    private final String teamID;

    public StatisticsPresenter(StatisticsFactory statisticsFactory, StatisticsView statisticsView, GameLogRepository gameLogRepository, String teamID) {
        this.statisticsFactory = statisticsFactory;
        this.statisticsView = statisticsView;
        this.gameLogRepository = gameLogRepository;
        this.teamID = teamID;
    }

    public void onCreate(){
    statisticsView.displayStatistics(statisticsFactory.getStatisticsForTeam(teamID));
    }
}
