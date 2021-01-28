package nl.ladyminks.thirtyoneseconds.logic.factories;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import nl.ladyminks.thirtyoneseconds.logic.domain.Game;
import nl.ladyminks.thirtyoneseconds.logic.domain.GameLog;
import nl.ladyminks.thirtyoneseconds.logic.domain.StopCondition;
import nl.ladyminks.thirtyoneseconds.logic.domain.Team;
import nl.ladyminks.thirtyoneseconds.logic.repositories.CategoryRepository;
import nl.ladyminks.thirtyoneseconds.logic.repositories.TeamsRepository;

/**
 * @author Minka Firth
 */
public class GameFactory {

    static final StopCondition DEFAULT_STOP_CONDITION = new StopCondition(StopCondition.Type.POINTS_REACHED, 30);
    static final int DEFAULT_TURN_DURATION = 30;

    private final NameFactory nameFactory;
    private final CategoryRepository categoryRepository;
    private final TeamsRepository teamsRepository;

    public GameFactory(NameFactory nameFactory, CategoryRepository categoryRepository, TeamsRepository teamsRepository) {
        this.nameFactory = nameFactory;
        this.categoryRepository = categoryRepository;
        this.teamsRepository = teamsRepository;
    }

    /**
     * Creates a game with new teams and default settings. It also stores the team to the
     * TeamsRepository.
     *
     * @param amountOfTeams
     *         is specified by the user.
     * @return a new QuickGame.
     */
    public Game createQuickGame(int amountOfTeams) {
        List<Team> teamList = new ArrayList<>();
        for (int i = 0; i < amountOfTeams; i++) {
            Team quickTeam = new Team(nameFactory.generateName(), categoryRepository.loadCategories(), UUID.randomUUID().toString());
            teamsRepository.storeTeam(quickTeam);
            teamList.add(quickTeam);
        }
        return new Game(System.currentTimeMillis(), teamList, DEFAULT_TURN_DURATION, DEFAULT_STOP_CONDITION, new GameLog());
    }

    public Game createCustomGame(List<Team> teams, StopCondition stopCondition){
        return new Game(System.currentTimeMillis(),  teams, DEFAULT_TURN_DURATION, stopCondition, new GameLog());
    }
}
