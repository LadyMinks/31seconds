package nl.ladyminks.thirtyoneseconds.logic.repositories;

import java.util.List;

import nl.ladyminks.thirtyoneseconds.logic.domain.GameLog;

/**
 * @author Minka Firth
 */
public interface GameLogRepository {

    void storeGameLog(GameLog gamelog);

    List<GameLog> loadGames();

    GameLog loadGameLog(String gameLogID);
}
