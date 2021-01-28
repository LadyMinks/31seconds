package nl.ladyminks.thirtyoneseconds.logic.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

/** In order to start a new Game Activity, Game needs to be given to an Intent. This is why Game
 * needs to be Serializable(and therefore the "connecting classes" need to be serializable too).
 * @author Minka Firth
 */
public class Team implements Serializable {
    private final String name;
    private final List<Category> playingCategories;
    private final String teamID;

    @JsonCreator
    public Team(
            @JsonProperty("name") String name,
            @JsonProperty("playingCategories") List<Category> categories,
            @JsonProperty("teamID") String teamID) {
        this.name = name;
        this.playingCategories = categories;
        this.teamID = teamID;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("playingCategories")
    public List<Category> getPlayingCategories() {
        return playingCategories;
    }

    @JsonProperty("teamID")
    public String getTeamID() {
        return teamID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return teamID.equals(team.teamID);
    }

    @Override
    public int hashCode() {
        return teamID.hashCode();
    }
}