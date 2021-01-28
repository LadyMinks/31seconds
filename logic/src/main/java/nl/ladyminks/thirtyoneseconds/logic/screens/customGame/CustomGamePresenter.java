package nl.ladyminks.thirtyoneseconds.logic.screens.customGame;

import java.util.ArrayList;
import java.util.List;

import nl.ladyminks.thirtyoneseconds.logic.domain.Game;
import nl.ladyminks.thirtyoneseconds.logic.domain.Statistics;
import nl.ladyminks.thirtyoneseconds.logic.domain.StopCondition;
import nl.ladyminks.thirtyoneseconds.logic.domain.Team;
import nl.ladyminks.thirtyoneseconds.logic.factories.GameFactory;
import nl.ladyminks.thirtyoneseconds.logic.factories.StatisticsFactory;
import nl.ladyminks.thirtyoneseconds.logic.repositories.TeamsRepository;

/**
 * @author Minka Firth
 */
public class CustomGamePresenter {

    private final CustomGameView customGameView;
    private final TeamsRepository teamsRepository;
    private final StatisticsFactory statisticsFactory;
    private final List<Integer> selectedTeamIndexes = new ArrayList<>();
    private final List<Team> teams = new ArrayList<>();
    private final GameFactory gameFactory;
    private StopCondition.Type stopConditionType;

    public CustomGamePresenter(CustomGameView customGameView, TeamsRepository teamsRepository, StatisticsFactory statisticsFactory, GameFactory gameFactory) {
        this.customGameView = customGameView;
        this.teamsRepository = teamsRepository;
        this.statisticsFactory = statisticsFactory;
        this.gameFactory = gameFactory;
    }

    public void onCreate() {
        teams.addAll(teamsRepository.loadTeams());
        if (teams.size() >= 2) {
            selectedTeamIndexes.add(0);
            selectedTeamIndexes.add(1);
        }
        decideAndDisplayPlayingTeams();
    }

    public void manageTeams() {
        List<String> teamNames = new ArrayList<>();
        for (int i = 0; i < teams.size(); i++) {
            teamNames.add(teams.get(i).getName());
        }
        customGameView.makeTeamSelectionDialog(teamNames, selectedTeamIndexes);
    }

    public void updateSelectedList(List<Integer> which) {
        selectedTeamIndexes.clear();
        selectedTeamIndexes.addAll(which);
        decideAndDisplayPlayingTeams();
    }

    private void decideAndDisplayPlayingTeams() {
        List<Team> playingTeams = new ArrayList<>();
        List<Statistics> statisticsForTeam = new ArrayList<>();
        for (int i = 0; i < selectedTeamIndexes.size(); i++) {
            playingTeams.add(teams.get(selectedTeamIndexes.get(i)));
            for (int j = 0; j < playingTeams.size(); j++) {
                Statistics statistics = statisticsFactory.getStatisticsForTeam(playingTeams.get(i).getTeamID());
                statisticsForTeam.add(statistics);
            }
        }
        customGameView.displayTeams(playingTeams, statisticsForTeam);
    }

    public void startCustomGame(int stopConditionUnit) {
        List<Team> playingTeams = new ArrayList<>();
        for (int i = 0; i < selectedTeamIndexes.size(); i++) {
            playingTeams.add(teams.get(selectedTeamIndexes.get(i)));
        }
        Game customGame = gameFactory.createCustomGame(playingTeams, new StopCondition(stopConditionType, stopConditionUnit));
        customGameView.startGame(customGame);
    }

    public void saveStopCondition(StopCondition.Type stopConditionType) {
        this.stopConditionType = stopConditionType;
    }

}
