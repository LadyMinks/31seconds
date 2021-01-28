package nl.ladyminks.thirtyoneseconds.logic.screens.teammanager;

import java.util.ArrayList;
import java.util.List;

import nl.ladyminks.thirtyoneseconds.logic.domain.Statistics;
import nl.ladyminks.thirtyoneseconds.logic.domain.Team;
import nl.ladyminks.thirtyoneseconds.logic.factories.StatisticsFactory;
import nl.ladyminks.thirtyoneseconds.logic.repositories.TeamsRepository;

/**
 * @author Minka Firth
 */
public class TeamManagerPresenter {

    private final TeamManagerView teamManagerView;
    private final StatisticsFactory statisticsFactory;
    private final TeamsRepository teamsRepository;

    public TeamManagerPresenter(TeamManagerView teamManagerView, StatisticsFactory statisticsFactory, TeamsRepository teamsRepository) {
        this.teamManagerView = teamManagerView;
        this.statisticsFactory = statisticsFactory;
        this.teamsRepository = teamsRepository;
    }

    public void onCreate(){
    }

    public void onResume(){
        List<Team> teams = teamsRepository.loadTeams();
        List<Statistics> statisticsForTeam = new ArrayList<>();
        for (int i = 0; i < teams.size(); i++) {
            Statistics statistics = statisticsFactory.getStatisticsForTeam(teams.get(i).getTeamID());
                // TODO: You could add statistics as a field to Team. (Making it @JsonIgnore would prevent it from being saved in a JSON file) Then you don't need two lists, but just one.
                statisticsForTeam.add(statistics);
            }
        teamManagerView.displayTeams(teams, statisticsForTeam);
        }
    }

