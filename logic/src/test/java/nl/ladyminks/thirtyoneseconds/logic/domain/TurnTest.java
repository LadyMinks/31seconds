package nl.ladyminks.thirtyoneseconds.logic.domain;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author Minka Firth
 */
public class TurnTest {

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

    private Category makeCat(String categoryName, String... words) {
        List<String> wordsList = Arrays.asList(words);
        return new Category(categoryName, wordsList);
    }

    @Test
    public void getScoreForTurn_OneTeamFivePoints_MustReturnFivePoints() {
        List<Category> categories = new ArrayList<>();
        categories.add(makeCat("cat1", "asd"));
        Team team1 = new Team("Team1", categories, "Team1");

        Turn turn = makeTurn(team1, 5, 0);

        int expected = 5;
        int actual = turn.getScoreForTurn();
        assertEquals(expected, actual);
    }

    @Test
    public void getScoreForTurn_TwoTeamsFivePoints_MustReturnDifferentScores() {
        List<Category> categories = new ArrayList<>();
        categories.add(makeCat("cat1", "asd"));
        Team team1 = new Team("Team1", categories, "Team1");
        Team team2 = new Team("Team2", categories, "Team2");

        Turn turn = makeTurn(team1, 5, 0);
        Turn turn2 = makeTurn(team2, 3, 2);

        assertEquals(5, turn.getScoreForTurn());
        assertEquals(3, turn2.getScoreForTurn());
    }

    @Test
    public void getMissedQuestionsForTurn_OneTeamThreeMissed_MustReturnThreeMissed() {
        List<Category> categories = new ArrayList<>();
        categories.add(makeCat("cat1", "asd"));
        Team team1 = new Team("Team1", categories, "Team1");

        Turn turn = makeTurn(team1, 2, 3);

        assertEquals(3, turn.getMissedQuestionsForTurn());
    }

    @Test
    public void getMissedQuestionsForTurn_TwoTeamsThreeMissed_MustReturnDifferentScores(){
        List<Category> categories = new ArrayList<>();
        categories.add(makeCat("cat1", "asd"));
        Team team1 = new Team("Team1", categories, "Team1");
        Team team2 = new Team("Team2", categories, "Team2");

        Turn turn = makeTurn(team1, 0, 5);
        Turn turn2 = makeTurn(team2, 2, 3);

        assertEquals(5, turn.getMissedQuestionsForTurn());
        assertEquals(3, turn2.getMissedQuestionsForTurn());
    }
}
