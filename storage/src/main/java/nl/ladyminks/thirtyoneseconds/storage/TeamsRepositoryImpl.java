package nl.ladyminks.thirtyoneseconds.storage;

import android.content.Context;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import nl.ladyminks.thirtyoneseconds.logic.domain.Team;
import nl.ladyminks.thirtyoneseconds.logic.repositories.TeamsRepository;
import timber.log.Timber;

/**
 * @author Minka Firth
 */
public class TeamsRepositoryImpl implements TeamsRepository {

    private static final String FILE_NAME = "TeamManager";
    private final ObjectMapper mapper = new ObjectMapper();
    private final Context context;

    public TeamsRepositoryImpl(Context context) {
        this.context = context;
    }

    @Override
    public void storeTeam(Team team) {
        try {
            final TeamCache teamCache = loadTeamCache();
            teamCache.addToTeamList(team);
            String jsonString = mapper.writeValueAsString(teamCache);
            Timber.d("storeTeam: %s", jsonString);
            try (FileOutputStream fos = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE)) {
                fos.write(jsonString.getBytes());
            } catch (FileNotFoundException e) {
                Timber.e(e, "failed to store team.");
            } catch (IOException e) {
                Timber.e(e, "failed to store team.");
            }
        } catch (JsonProcessingException e) {
            Timber.e(e, "failed to process json");
        }
    }

    @Override
    public List<Team> loadTeams() {
        return loadTeamCache().getTeamList();
    }

    @Override
    public Team loadTeam(String teamID) {
        return loadTeamCache().getTeam(teamID);
    }

    /**
     * Loads the TeamCache.
     *
     * @return the TeamCache.
     */
    private TeamCache loadTeamCache() {
        try {
            FileInputStream fis = context.openFileInput(FILE_NAME);
            InputStreamReader inputStreamReader =
                    new InputStreamReader(fis, StandardCharsets.UTF_8);
            StringBuilder stringBuilder = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
                String line = reader.readLine();
                while (line != null) {
                    stringBuilder.append(line).append('\n');
                    line = reader.readLine();
                }
            }
            String jsonString = stringBuilder.toString();
            TeamCache teamCache = mapper.readValue(jsonString, TeamCache.class);
            return teamCache;
        } catch (IOException e) {
            Timber.w(e);
        }
        return new TeamCache(new ArrayList<>());
    }


}
