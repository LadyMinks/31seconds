package nl.ladyminks.thirtyoneseconds.presentation.pages.statistics;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import nl.ladyminks.thirtyoneseconds.logic.domain.Statistics;
import nl.ladyminks.thirtyoneseconds.logic.screens.statistics.StatisticsPresenter;
import nl.ladyminks.thirtyoneseconds.logic.screens.statistics.StatisticsView;
import nl.ladyminks.thirtyoneseconds.presentation.MyApplication;
import nl.ladyminks.thirtyoneseconds.presentation.ui.StatisticTypeAndUnit;
import nl.minka.ap2thirtyseconds.R;

/**
 * @author Minka Firth
 */
public class StatisticsActivity extends AppCompatActivity implements StatisticsView {

    private StatisticsPresenter presenter;
    //    private List<StatisticTypeAndUnit> statViews = new ArrayList<>();
    private StatisticTypeAndUnit tvStatsWins;
    private StatisticTypeAndUnit tvStatsDraws;
    private StatisticTypeAndUnit tvStatsLosses;
    private StatisticTypeAndUnit tvStatsMostPoints;
    private StatisticTypeAndUnit tvStatsBestRatio;
    private StatisticTypeAndUnit tvStatsWorstRatio;
    private StatisticTypeAndUnit tvStatsRivalTeam;
    private StatisticTypeAndUnit tvStatsTotalPoints;
    private StatisticTypeAndUnit tvStatsPlayedTime;
    private StatisticTypeAndUnit tvStatsCategoriesWithPoints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        Intent intent = getIntent();
        String teamID = intent.getStringExtra("teamID");

        MyApplication myApplication = (MyApplication) getApplicationContext();
        this.presenter = myApplication.getPresenterFactory().createStatisticsPresenter(this, teamID);

        this.tvStatsWins = findViewById(R.id.tvStatsWins);
        this.tvStatsDraws = findViewById(R.id.tvStatsDraws);
        this.tvStatsLosses = findViewById(R.id.tvStatsLosses);
        this.tvStatsMostPoints = findViewById(R.id.tvStatsMostPoints);
        this.tvStatsBestRatio = findViewById(R.id.tvStatsBestRatio);
        this.tvStatsWorstRatio = findViewById(R.id.tvStatsWorstRatio);
        this.tvStatsRivalTeam = findViewById(R.id.tvStatsRivalTeam);
        this.tvStatsTotalPoints = findViewById(R.id.tvStatsTotalPoints);
        this.tvStatsPlayedTime = findViewById(R.id.tvStatsPlayedTime);
        this.tvStatsCategoriesWithPoints = findViewById(R.id.tvCategoriesWithPoints);

        presenter.onCreate();

    }

    @Override
    public void displayStatistics(Statistics statistics) {
        tvStatsWins.setCvStatistics(getString(R.string.wins), String.valueOf(statistics.getWins()));
        tvStatsDraws.setCvStatistics(getString(R.string.draws), String.valueOf(statistics.getDraws()));
        tvStatsLosses.setCvStatistics(getString(R.string.losses), String.valueOf(statistics.getLosses()));
        tvStatsMostPoints.setCvStatistics(getString(R.string.most_points), statistics.getMostPoints() == null ? "n.a." : statistics.getMostPoints().getName());
        tvStatsBestRatio.setCvStatistics(getString(R.string.best_ratio),statistics.getBestRatio() == null ? "n.a." : statistics.getBestRatio().getName());
        tvStatsWorstRatio.setCvStatistics(getString(R.string.worst_ratio),statistics.getWorstRatio() == null ? "n.a." : statistics.getWorstRatio().getName());
        tvStatsRivalTeam.setCvStatistics(getString(R.string.rival_team),statistics.getRivalTeam() == null ? "n.a." :  String.valueOf(statistics.getRivalTeam()));
        tvStatsTotalPoints.setCvStatistics(getString(R.string.total_points), String.valueOf(statistics.getTotalPointsEver()));
        tvStatsPlayedTime.setCvStatistics(getString(R.string.played_time), String.valueOf(statistics.getPlayedTime()));
    }
}