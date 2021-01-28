package nl.ladyminks.thirtyoneseconds.logic.factories;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nl.ladyminks.thirtyoneseconds.logic.CategoryRepositoryMock;
import nl.ladyminks.thirtyoneseconds.logic.TeamsRepositoryMock;
import nl.ladyminks.thirtyoneseconds.logic.domain.Category;
import nl.ladyminks.thirtyoneseconds.logic.domain.Game;
import nl.ladyminks.thirtyoneseconds.logic.domain.GameLog;
import nl.ladyminks.thirtyoneseconds.logic.domain.Question;
import nl.ladyminks.thirtyoneseconds.logic.domain.StopCondition;
import nl.ladyminks.thirtyoneseconds.logic.domain.Team;
import nl.ladyminks.thirtyoneseconds.logic.domain.Turn;

import static org.junit.Assert.assertEquals;

/**
 * @author Minka Firth
 */
public class GameFactoryTest {


    private Category makeCat(String categoryName, String... words) {
        List<String> wordsList = Arrays.asList(words);
        return new Category(categoryName, wordsList);
    }

    private Game makeGame(Team... teams) {
        List<Team> teamList = Arrays.asList(teams);
        return new Game(0, teamList, 0, new StopCondition(StopCondition.Type.POINTS_REACHED, 10), new GameLog());
    }

    private Team makeTeam(Category... categories) {
        List<Category> categoriesList = Arrays.asList(categories);
        return new Team("teamName", categoriesList, "teamID");
    }

    private Turn makeTurn(Team playingTeam, int amountOfCorrect, int amountOfIncorrect) {
        List<Question> questions = new ArrayList<>();
        for (int i = 0; i < amountOfCorrect; i++) {
            questions.add(new Question(null, null, true));
        }
        for (int i = 0; i < amountOfIncorrect; i++) {
            questions.add(new Question(null, null, false));
        }
        return new Turn(playingTeam, questions, 0);
    }

    @Test
    public void createQuickGame_TwoTeamsPlaying_MustReturnCorrectGame() {
        NameFactory nameFactory = NameFactory.makeHardCodedGenerator();
        CategoryRepositoryMock categoryRepositoryMock = new CategoryRepositoryMock();
        TeamsRepositoryMock teamsRepositoryMock = new TeamsRepositoryMock();
        GameFactory gameFactory = new GameFactory(nameFactory, categoryRepositoryMock, teamsRepositoryMock);

        Game actual = gameFactory.createQuickGame(2);

        assertEquals(2, teamsRepositoryMock.loadTeams().size());
        assertEquals(teamsRepositoryMock.loadTeams(), actual.getTeams());
        assertEquals(GameFactory.DEFAULT_STOP_CONDITION, actual.getStopCondition());
        assertEquals(GameFactory.DEFAULT_TURN_DURATION, actual.getTurnDuration());
    }

}
