package nl.ladyminks.thirtyoneseconds.storage;

import android.content.Context;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
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

import nl.ladyminks.thirtyoneseconds.logic.domain.GameLog;
import nl.ladyminks.thirtyoneseconds.logic.repositories.GameLogRepository;
import timber.log.Timber;

/**
 * @author Minka Firth
 */
public class GameLogRepositoryImpl implements GameLogRepository {

    private static final String FILE_NAME = "GameLog";
    private final ObjectMapper mapper = new ObjectMapper();
    private final Context context;

    public GameLogRepositoryImpl(Context context) {
        this.context = context;
    }

    @Override
    public void storeGameLog(GameLog gamelog) {
        try {
            List<GameLog> gameLogs = loadGameLogs();
            gameLogs.add(gamelog);
            String jsonString = mapper.writeValueAsString(gameLogs);
            Timber.d("storeGameLog: %s", jsonString);
            try (FileOutputStream fos = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE)) {
                fos.write(jsonString.getBytes());
            } catch (FileNotFoundException e) {
                Timber.e(e, "failed to store gameLog.");
            } catch (IOException e) {
                Timber.e(e, "failed to store gameLog.");
            }
        } catch (JsonProcessingException e) {
            Timber.e(e, "failed to process json");
        }
    }

    @Override
    public List<GameLog> loadGames() {
        return loadGameLogs();
    }

    @Override
    public GameLog loadGameLog(String gameLogID) {
        for (GameLog gameLog : loadGameLogs()) {
            if (gameLog.getGameLogID().equals(gameLogID)) {
                return gameLog;
            }
        }
        throw new IllegalArgumentException("no matching gameLogID found");
    }

    private List<GameLog> loadGameLogs() {
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
            Timber.e("jsonString: %s", jsonString);
            List<GameLog> gameLogs = mapper.readValue(jsonString,
                    new TypeReference<List<GameLog>>() {
                    });
            Timber.e("Gamelog size: %s", gameLogs.size());
            return gameLogs;
        } catch (IOException e) {
            Timber.w(e);
        }
        return new ArrayList<>();
    }
}
