package nl.ladyminks.thirtyoneseconds.logic.factories;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.ladyminks.thirtyoneseconds.logic.domain.Category;
import nl.ladyminks.thirtyoneseconds.logic.domain.GameLog;
import nl.ladyminks.thirtyoneseconds.logic.domain.PointsRatio;
import nl.ladyminks.thirtyoneseconds.logic.domain.Question;
import nl.ladyminks.thirtyoneseconds.logic.domain.Statistics;
import nl.ladyminks.thirtyoneseconds.logic.domain.Turn;
import nl.ladyminks.thirtyoneseconds.logic.repositories.GameLogRepository;

/**
 * @author Minka Firth
 */
public class StatisticsFactory {

    private final GameLogRepository gameLogRepository;

    public StatisticsFactory(GameLogRepository gameLogRepository) {
        this.gameLogRepository = gameLogRepository;
    }

    private enum CategoryType {
        BEST_CATEGORY,
        BEST_CATEGORY_RATIO,
        WORST_CATEGORY_RATIO;

        public boolean currentIsBetterThanTheBestSoFar(PointsRatio bestSoFar, PointsRatio current) {
            switch (this) {
                case BEST_CATEGORY:
                    return bestSoFar.getWins() < current.getWins();
                case BEST_CATEGORY_RATIO:
                    return bestSoFar.getWinPercentage() < current.getWinPercentage();
                case WORST_CATEGORY_RATIO:
                    return bestSoFar.getWinPercentage() > current.getWinPercentage();
                default:
                    throw new RuntimeException("unsupported object type");
            }
        }
    }

    public Statistics getStatisticsForTeam(String teamID) {
        Map<Category, PointsRatio> catMap = fillMap(teamID);
        List<GameLog> gameLogs = gameLogRepository.loadGames();
        int totalPointsEver = 0;
        int missedQuestions = 0;
        for (int i = 0; i < gameLogs.size(); i++) {
            List<Turn> turns = gameLogs.get(i).getTurns();
            for (int j = 0; j < turns.size(); j++) {
                if (turns.get(j).getPlayingTeam().getTeamID().equals(teamID)) {
                    totalPointsEver += turns.get(j).getScoreForTurn();
                    missedQuestions += turns.get(j).getMissedQuestionsForTurn();
                }
            }
        }
        return new Statistics(calculateWins(teamID), 0, calculateLosses(teamID),
                getCategoryStatistics(catMap, CategoryType.BEST_CATEGORY), getCategoryStatistics(catMap, CategoryType.BEST_CATEGORY_RATIO), getCategoryStatistics(catMap, CategoryType.WORST_CATEGORY_RATIO),
                null, totalPointsEver, missedQuestions, 0, catMap);
    }

    private Category getCategoryStatistics(Map<Category, PointsRatio> catMap, CategoryType categoryType) {
        Category bestCategorySoFar = null;
        for (Category current : catMap.keySet()) {
            if (bestCategorySoFar == null || categoryType.currentIsBetterThanTheBestSoFar(catMap.get(bestCategorySoFar), catMap.get(current))) {
                bestCategorySoFar = current;
            }
        }
        return bestCategorySoFar;
    }

    private Map<Category, PointsRatio> fillMap(String teamID) {
        List<GameLog> gameLogs = gameLogRepository.loadGames();
        Map<Category, PointsRatio> catMap = new HashMap<>();
        for (int i = 0; i < gameLogs.size(); i++) {
            List<Turn> turns = gameLogs.get(i).getTurns();

            for (int j = 0; j < turns.size(); j++) {
                if (turns.get(j).getPlayingTeam().getTeamID().equals(teamID)) {
                    List<Question> questions = turns.get(j).getQuestions();

                    for (int k = 0; k < questions.size(); k++) {
                        Category category = questions.get(k).getCategory();
                        if (!catMap.containsKey(category)) {
                            catMap.put(category, new PointsRatio());
                        }
                        if (questions.get(k).isCorrect()) {
                            catMap.get(category).incrementWins();
                        } else {
                            catMap.get(category).incrementLosses();
                        }
                    }
                }
            }
        }
        return catMap;
    }

    private int calculateWins(String teamID) {
        int wins = 0;
        List<GameLog> gameLogs = gameLogRepository.loadGames();
        for (int i = 0; i < gameLogs.size(); i++) {
            if (gameLogs.get(i).decideWinningTeam().getTeamID().equals(teamID)) {
                wins++;
            }
        }
        return wins;
    }


    private int calculateLosses(String teamID) {
        int losses = 0;
        List<GameLog> gameLogs = gameLogRepository.loadGames();
        for (int i = 0; i < gameLogs.size(); i++) {
            if (gameLogs.get(i).isTeamInGame(teamID)){
                if(!gameLogs.get(i).decideWinningTeam().getTeamID().equals(teamID)){
                    losses++;
                }
            }
        }
        return losses;
    }
}