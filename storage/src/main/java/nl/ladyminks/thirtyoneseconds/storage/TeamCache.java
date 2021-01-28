package nl.ladyminks.thirtyoneseconds.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import nl.ladyminks.thirtyoneseconds.logic.domain.Team;

/**
 * The object is in the JSON file.
 *
 * @author Minka Firth
 */
public class TeamCache {

    private final List<Team> teamList;

    @JsonCreator
    public TeamCache(
            @JsonProperty("teamList") List<Team> teamList) {
        this.teamList = teamList;
    }

    public void addToTeamList(Team team) {
        teamList.remove(team);
        teamList.add(team);
    }

    @JsonProperty("teamList")
    public List<Team> getTeamList() {
        return teamList;
    }

    public Team getTeam(String teamID) {
        for (Team team : teamList) {
            if (team.getTeamID().equals(teamID)) {
                return team;
            }
        }
        throw new IllegalArgumentException("no matching teamID found");
    }
}
