package nl.ladyminks.thirtyoneseconds.logic.repositories;

import java.util.List;

import nl.ladyminks.thirtyoneseconds.logic.domain.Team;

/**
 * @author Minka Firth
 */
public interface TeamsRepository {

    /**
     * Stores a Team in a Json File.
     *
     * @param team
     *         : the Team that is being stored.
     */
    void storeTeam(Team team);

    /**
     * Loads a List of Teams from the Json File.
     *
     * @return List of the Teams in the Json file.
     */
    List<Team> loadTeams();

    /**
     * Loads an individual Team from the Json File.
     *
     * @param teamID
     *         : to find the Team.
     * @return Team : that matches the teamID.
     */
    Team loadTeam(String teamID);

}
