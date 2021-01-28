package nl.ladyminks.thirtyoneseconds.logic.factories;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import nl.ladyminks.thirtyoneseconds.logic.RandomMock;
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
public class TurnFactoryTest {

    private Category makeCat(String categoryName, String... words) {
        List<String> wordsList = Arrays.asList(words);
        return new Category(categoryName, wordsList);
    }

    private Team makeTeam(Category... categories) {
        List<Category> categoriesList = Arrays.asList(categories);
        return new Team("teamName", categoriesList, "teamID");
    }

    private Game makeGame(Team... teams) {
        List<Team> teamList = Arrays.asList(teams);
        return new Game(0, teamList, 0, new StopCondition(StopCondition.Type.POINTS_REACHED, 10), new GameLog());
    }

    @Test
    public void generateTurn_OneCategoryOneWord_UsesThatWordFiveTimes() {
        TurnFactory turnFactory = new TurnFactory();
        Category category = makeCat("category", "word1", "word2", "word3", "word4", "word5");
        Team team = makeTeam(category);
        List<Question> questions = new ArrayList<>();
        questions.add(new Question ("word1", category, false));
        questions.add(new Question ("word2", category, false));
        questions.add(new Question ("word3", category, false));
        questions.add(new Question ("word4", category, false));
        questions.add(new Question ("word5", category, false));

        Turn expected = new Turn(team, questions, System.currentTimeMillis());
        Game game = makeGame(team);

        Turn actual = turnFactory.generateTurn(team, game);

        assertEquals(expected.getPlayingTeam(), actual.getPlayingTeam());
        assertEquals(new HashSet<>(expected.getQuestions()), new HashSet<>(actual.getQuestions()));
    }

    @Test
    public void generateQuestion_OnlyOneCatOneWord_MustUseThatWord() {
        TurnFactory turnFactory = new TurnFactory();
        Category category = makeCat("category", "word");
        Team team = makeTeam(category);
        Game game = makeGame(team);

        Question expected = new Question("word", category, false);
        Question actual = turnFactory.generateQuestion(team, game);

        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getCategory(), actual.getCategory());
        assertEquals(expected.isCorrect(), actual.isCorrect());
    }

    @Test
    public void generateQuestion_MultipleCatsMultipleWords_MustReturnCertainWord() {
        TurnFactory turnFactory = new TurnFactory(RandomMock.thatReturns(1, 0));
        Category categoryA = makeCat("categoryA", "A1", "A2", "A3");
        Category categoryB = makeCat("categoryB", "B1", "B2", "B3");
        Category categoryC = makeCat("categoryC", "C1", "C2", "C3");
        Team team = makeTeam(categoryA, categoryB, categoryC);

        Game game = makeGame(team);

        Question expected = new Question("B1", categoryB, false);
        Question actual = turnFactory.generateQuestion(team, game);

        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getCategory(), actual.getCategory());
        assertEquals(expected.isCorrect(), actual.isCorrect());
    }


    //TODO: when application checks if word has been used -> unit test.
}
