package nl.ladyminks.thirtyoneseconds.logic;

import java.util.ArrayList;
import java.util.List;

import nl.ladyminks.thirtyoneseconds.logic.domain.Team;
import nl.ladyminks.thirtyoneseconds.logic.repositories.TeamsRepository;

/**
 * @author Minka Firth
 */
public class TeamsRepositoryMock implements TeamsRepository {

    private final List<Team> mockTeamList = new ArrayList<>();

    @Override
    public void storeTeam(Team team) {
        mockTeamList.add(team);
    }

    @Override
    public List<Team> loadTeams() {
        return mockTeamList;
    }

    @Override
    public Team loadTeam(String teamID) {
        return null;
    }
}
