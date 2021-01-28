package nl.ladyminks.thirtyoneseconds.logic.domain;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author Minka Firth
 */
public class StopConditionTest {

    private Game makeGame(nl.ladyminks.thirtyoneseconds.logic.domain.Team... teams) {
        List<Team> teamList = Arrays.asList(teams);
        return new Game(0, teamList, 0, new StopCondition(StopCondition.Type.POINTS_REACHED, 10), new GameLog());
    }

    private Category makeCat(String categoryName, String... words) {
        List<String> wordsList = Arrays.asList(words);
        return new Category(categoryName, wordsList);
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
    public void isStopCondition_OneTeamFivePointsReached_MustReturnFalse() {
        StopCondition stopCondition = new StopCondition(StopCondition.Type.POINTS_REACHED, 10);
        List<Category> categories = new ArrayList<>();
        categories.add(makeCat("cat1", "asd"));
        Team team1 = new Team("Team1", categories, "Team1");
        Team team2 = new Team("Team2", categories, "Team2");

        Game game = makeGame(team1, team2);
        game.initialiseScoresToZero();

        Turn turn = makeTurn(team1, 5, 0);
        game.addTurnWithScore(turn);

        boolean expected = false;
        boolean actual = stopCondition.isConditionMet(0, 0, game.getHighScore());

        assertEquals(expected, actual);
    }

    @Test
    public void isStopCondition_TwoTeamEachTenPointsReached_MustReturnTrue() {
        StopCondition stopCondition = new StopCondition(StopCondition.Type.POINTS_REACHED, 10);
        List<Category> categories = new ArrayList<>();
        categories.add(makeCat("cat1", "asd"));
        Team team1 = new Team("Team1", categories, "Team1");
        Team team2 = new Team("Team2", categories, "Team2");

        Game game = makeGame(team1, team2);
        game.initialiseScoresToZero();

        Turn turn = makeTurn(team1, 5, 0);
        Turn turn2 = makeTurn(team1, 5, 0);

        game.addTurnWithScore(turn);
        game.addTurnWithScore(turn2);
        game.addTurnWithScore(turn);


        boolean expected = true;
        boolean actual = stopCondition.isConditionMet(0, 0, game.getHighScore());

        assertEquals(expected, actual);
    }

    @Test
    public void isStopCondition_TwoTeamsFourTwoRoundsPlayed_MustReturnFalse() {
        StopCondition stopCondition = new StopCondition(StopCondition.Type.ROUNDS_PLAYED, 2);
        List<Category> categories = new ArrayList<>();
        categories.add(makeCat("cat1", "asd"));
        List<Team>  teams = new ArrayList<>();
        Team team1 = new Team("Team1", categories, "Team1");
        Team team2 = new Team("Team2", categories, "Team2");
        teams.add(team1);
        teams.add(team2);

        Game game = makeGame(team1, team2);
        game.initialiseScoresToZero();

        Turn turn = makeTurn(team1, 5, 0);
        Turn turn2 = makeTurn(team1, 5, 0);

        game.addTurnWithScore(turn);
        game.addTurnWithScore(turn2);
        game.addTurnWithScore(turn);

        int roundsPlayed = game.getGameLog().howManyTurnsPlayed() / teams.size();

        boolean expected = false;
        boolean actual = stopCondition.isConditionMet(0, roundsPlayed, 0);

        assertEquals(expected, actual);
    }

    @Test
    public void isStopCondition_TwoTeamsFourTwoRoundsPlayed_MustReturnTrue() {
        StopCondition stopCondition = new StopCondition(StopCondition.Type.ROUNDS_PLAYED, 2);
        List<Category> categories = new ArrayList<>();
        categories.add(makeCat("cat1", "asd"));
        List<Team>  teams = new ArrayList<>();
        Team team1 = new Team("Team1", categories, "Team1");
        Team team2 = new Team("Team2", categories, "Team2");
        teams.add(team1);
        teams.add(team2);

        Game game = makeGame(team1, team2);
        game.initialiseScoresToZero();

        Turn turn = makeTurn(team1, 5, 0);
        Turn turn2 = makeTurn(team1, 5, 0);

        game.addTurnWithScore(turn);
        game.addTurnWithScore(turn2);
        game.addTurnWithScore(turn);
        game.addTurnWithScore(turn2);

        int roundsPlayed = game.getGameLog().howManyTurnsPlayed() / teams.size();

        boolean expected = true;
        boolean actual = stopCondition.isConditionMet(0, roundsPlayed, 0);

        assertEquals(expected, actual);
    }

}
