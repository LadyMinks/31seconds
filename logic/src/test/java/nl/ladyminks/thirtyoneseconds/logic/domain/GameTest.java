package nl.ladyminks.thirtyoneseconds.logic.domain;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nl.ladyminks.thirtyoneseconds.logic.factories.TurnFactory;

import static org.junit.Assert.assertEquals;

/**
 * @author Minka Firth
 */
public class GameTest {

    private nl.ladyminks.thirtyoneseconds.logic.domain.Game makeGame(nl.ladyminks.thirtyoneseconds.logic.domain.Team... teams) {
        List<nl.ladyminks.thirtyoneseconds.logic.domain.Team> teamList = Arrays.asList(teams);
        return new nl.ladyminks.thirtyoneseconds.logic.domain.Game(0, teamList, 0, new nl.ladyminks.thirtyoneseconds.logic.domain.StopCondition(StopCondition.Type.POINTS_REACHED, 10), new GameLog());
    }

    private nl.ladyminks.thirtyoneseconds.logic.domain.Team makeTeam(nl.ladyminks.thirtyoneseconds.logic.domain.Category... categories) {
        List<nl.ladyminks.thirtyoneseconds.logic.domain.Category> categoriesList = Arrays.asList(categories);
        return new nl.ladyminks.thirtyoneseconds.logic.domain.Team("teamName", categoriesList, "teamID");
    }

    private nl.ladyminks.thirtyoneseconds.logic.domain.Category makeCat(String categoryName, String... words) {
        List<String> wordsList = Arrays.asList(words);
        return new nl.ladyminks.thirtyoneseconds.logic.domain.Category(categoryName, wordsList);
    }

    private Turn makeTurn(nl.ladyminks.thirtyoneseconds.logic.domain.Team playingTeam, int amountOfCorrect, int amountOfIncorrect) {
        List<nl.ladyminks.thirtyoneseconds.logic.domain.Question> questions = new ArrayList<>();
        for (int i = 0; i < amountOfCorrect; i++) {
            questions.add(new nl.ladyminks.thirtyoneseconds.logic.domain.Question(null, null, true));
        }
        for (int i = 0; i < amountOfIncorrect; i++) {
            questions.add(new Question(null, null, false));
        }
        return new Turn(playingTeam, questions, 0);
    }

    @Test
    public void decidePlayingTeam_NoTurnsPlayedThreeTeamsPlaying_MustReturnFirstTeam() {
        List<nl.ladyminks.thirtyoneseconds.logic.domain.Category> categories = new ArrayList<>();
        categories.add(makeCat("cat1", "asd"));
        nl.ladyminks.thirtyoneseconds.logic.domain.Team team1 = new nl.ladyminks.thirtyoneseconds.logic.domain.Team("Team1", categories, "Team1");
        nl.ladyminks.thirtyoneseconds.logic.domain.Team team2 = new nl.ladyminks.thirtyoneseconds.logic.domain.Team("Team2", categories, "Team2");
        nl.ladyminks.thirtyoneseconds.logic.domain.Team team3 = new nl.ladyminks.thirtyoneseconds.logic.domain.Team("Team3", categories, "Team3");
        nl.ladyminks.thirtyoneseconds.logic.domain.Game game = makeGame(team1, team2, team3);

        nl.ladyminks.thirtyoneseconds.logic.domain.Team expected = team1;
        nl.ladyminks.thirtyoneseconds.logic.domain.Team actual = game.decidePlayingTeam();

        assertEquals(expected, actual);
    }

    @Test
    public void decidePlayingTeam_FourTurnsThreeTeamsPlaying_MustReturnRightOrder() {
        List<nl.ladyminks.thirtyoneseconds.logic.domain.Category> categories = new ArrayList<>();
        categories.add(makeCat("cat1", "A", "B", "C", "D", "E"));
        nl.ladyminks.thirtyoneseconds.logic.domain.Team team1 = new nl.ladyminks.thirtyoneseconds.logic.domain.Team("Team1", categories, "Team1");
        nl.ladyminks.thirtyoneseconds.logic.domain.Team team2 = new nl.ladyminks.thirtyoneseconds.logic.domain.Team("Team2", categories, "Team2");
        nl.ladyminks.thirtyoneseconds.logic.domain.Team team3 = new nl.ladyminks.thirtyoneseconds.logic.domain.Team("Team3", categories, "Team3");

        nl.ladyminks.thirtyoneseconds.logic.domain.Game game = makeGame(team1, team2, team3);
        TurnFactory turnFactory = new TurnFactory();
        Turn turn = turnFactory.generateTurn(team1, game);

        assertEquals(team1, game.decidePlayingTeam());
        game.getGameLog().addTurn(turn);

        assertEquals(team2, game.decidePlayingTeam());
        game.getGameLog().addTurn(turn);

        assertEquals(team3, game.decidePlayingTeam());
        game.getGameLog().addTurn(turn);

        assertEquals(team1, game.decidePlayingTeam());
    }

    @Test
    public void addTurnWithScore_AddTwoPoints_MustReturnTwoPoints() {
        List<nl.ladyminks.thirtyoneseconds.logic.domain.Category> categories = new ArrayList<>();
        categories.add(makeCat("cat1", "asd"));
        nl.ladyminks.thirtyoneseconds.logic.domain.Team team1 = new nl.ladyminks.thirtyoneseconds.logic.domain.Team("Team1", categories, "Team1");
        nl.ladyminks.thirtyoneseconds.logic.domain.Team team2 = new nl.ladyminks.thirtyoneseconds.logic.domain.Team("Team2", categories, "Team2");

        nl.ladyminks.thirtyoneseconds.logic.domain.Game game = makeGame(team1, team2);
        game.initialiseScoresToZero();

        Turn turn = makeTurn(team1, 2, 3);
        game.addTurnWithScore(turn);

        int expected = 2;
        int actual = game.getScore(team1);
        assertEquals(expected, actual);
    }

    @Test
    public void addTurnsWithScore_FourTurnsTwoPoints_MustReturnFourPoints() {
        List<nl.ladyminks.thirtyoneseconds.logic.domain.Category> categories = new ArrayList<>();
        categories.add(makeCat("cat1", "A"));
        nl.ladyminks.thirtyoneseconds.logic.domain.Team team1 = new nl.ladyminks.thirtyoneseconds.logic.domain.Team("Team1", categories, "Team1");
        nl.ladyminks.thirtyoneseconds.logic.domain.Team team2 = new nl.ladyminks.thirtyoneseconds.logic.domain.Team("Team2", categories, "Team2");

        nl.ladyminks.thirtyoneseconds.logic.domain.Game game = makeGame(team1, team2);
        game.initialiseScoresToZero();

        Turn turn = makeTurn(team1, 2, 3);
        Turn turn2 = makeTurn(team2, 2, 3);
        game.addTurnWithScore(turn);

        assertEquals(2, game.getScore(team1));
        game.addTurnWithScore(turn);

        assertEquals(0, game.getScore(team2));
        game.addTurnWithScore(turn2);

        assertEquals(4, game.getScore(team1));
    }

    @Test
    public void isStopConditionMet_TwoTeamsTwoPointsPerTurn_MustReturnFalse() {
        List<nl.ladyminks.thirtyoneseconds.logic.domain.Category> categories = new ArrayList<>();
        categories.add(makeCat("cat1", "asd"));
        nl.ladyminks.thirtyoneseconds.logic.domain.Team team1 = new nl.ladyminks.thirtyoneseconds.logic.domain.Team("Team1", categories, "Team1");
        nl.ladyminks.thirtyoneseconds.logic.domain.Team team2 = new nl.ladyminks.thirtyoneseconds.logic.domain.Team("Team2", categories, "Team2");

        nl.ladyminks.thirtyoneseconds.logic.domain.Game game = makeGame(team1, team2);
        game.initialiseScoresToZero();

        Turn turn = makeTurn(team1, 2, 3);
        Turn turn2 = makeTurn(team2, 2, 3);
        game.addTurnWithScore(turn);
        game.addTurnWithScore(turn2);

        assertEquals(false, game.isStopConditionMet());
    }

    @Test
    public void isStopConditionMet_TwoTeamsFivePointsPerTurn_MustReturnTrue() {
        List<nl.ladyminks.thirtyoneseconds.logic.domain.Category> categories = new ArrayList<>();
        categories.add(makeCat("cat1", "asd"));
        nl.ladyminks.thirtyoneseconds.logic.domain.Team team1 = new nl.ladyminks.thirtyoneseconds.logic.domain.Team("Team1", categories, "Team1");
        nl.ladyminks.thirtyoneseconds.logic.domain.Team team2 = new nl.ladyminks.thirtyoneseconds.logic.domain.Team("Team2", categories, "Team2");

        nl.ladyminks.thirtyoneseconds.logic.domain.Game game = makeGame(team1, team2);
        game.initialiseScoresToZero();

        Turn turn = makeTurn(team1, 5, 0);
        Turn turn2 = makeTurn(team2, 5, 0);
        game.addTurnWithScore(turn);
        game.addTurnWithScore(turn2);
        game.addTurnWithScore(turn);

        assertEquals(true, game.isStopConditionMet());

    }

    @Test
    public void getHighScore_TwoTurnsTwoQuestions_MustReturnTwoPoints(){
        List<nl.ladyminks.thirtyoneseconds.logic.domain.Category> categories = new ArrayList<>();
        categories.add(makeCat("cat1", "asd"));
        nl.ladyminks.thirtyoneseconds.logic.domain.Team team1 = new nl.ladyminks.thirtyoneseconds.logic.domain.Team("Team1", categories, "Team1");
        nl.ladyminks.thirtyoneseconds.logic.domain.Team team2 = new nl.ladyminks.thirtyoneseconds.logic.domain.Team("Team2", categories, "Team2");

        nl.ladyminks.thirtyoneseconds.logic.domain.Game game = makeGame(team1, team2);
        game.initialiseScoresToZero();

        Turn turn = makeTurn(team1, 2, 3);
        Turn turn2 = makeTurn(team2, 2, 3);
        game.addTurnWithScore(turn);
        game.addTurnWithScore(turn2);

        int expected = 2;
        int actual = game.getHighScore();
        assertEquals(expected, actual);
    }

    @Test
    public void getHighScore_ThreeTurnsFiveQuestions_MustReturnTenPoints(){
        List<nl.ladyminks.thirtyoneseconds.logic.domain.Category> categories = new ArrayList<>();
        categories.add(makeCat("cat1", "asd"));
        nl.ladyminks.thirtyoneseconds.logic.domain.Team team1 = new nl.ladyminks.thirtyoneseconds.logic.domain.Team("Team1", categories, "Team1");
        nl.ladyminks.thirtyoneseconds.logic.domain.Team team2 = new nl.ladyminks.thirtyoneseconds.logic.domain.Team("Team2", categories, "Team2");

        nl.ladyminks.thirtyoneseconds.logic.domain.Game game = makeGame(team1, team2);
        game.initialiseScoresToZero();

        Turn turn = makeTurn(team1, 5, 0);
        Turn turn2 = makeTurn(team2, 2, 3);
        game.addTurnWithScore(turn);
        game.addTurnWithScore(turn2);
        game.addTurnWithScore(turn);

        int expected = 10;
        int actual = game.getHighScore();
        assertEquals(expected, actual);
    }

    @Test
    public void getSortedList_TeamOneIsLeading_MustReturnFirstTeam(){
        List<nl.ladyminks.thirtyoneseconds.logic.domain.Category> categories = new ArrayList<>();
        categories.add(makeCat("cat1", "asd"));
        nl.ladyminks.thirtyoneseconds.logic.domain.Team team1 = new nl.ladyminks.thirtyoneseconds.logic.domain.Team("Team1", categories, "Team1");
        nl.ladyminks.thirtyoneseconds.logic.domain.Team team2 = new nl.ladyminks.thirtyoneseconds.logic.domain.Team("Team2", categories, "Team2");

        nl.ladyminks.thirtyoneseconds.logic.domain.Game game = makeGame(team1, team2);
        game.initialiseScoresToZero();

        Turn turn = makeTurn(team1, 5, 0);
        Turn turn2 = makeTurn(team2, 2, 3);
        game.addTurnWithScore(turn);
        game.addTurnWithScore(turn2);

        nl.ladyminks.thirtyoneseconds.logic.domain.Team expected = team1;
        nl.ladyminks.thirtyoneseconds.logic.domain.Team actual = game.getSortedList().get(0);
        assertEquals(expected, actual);
    }

    @Test
    public void getSortedList_TwoTeamsFirstTeamLeading_MustReturnRightOrder(){
        List<nl.ladyminks.thirtyoneseconds.logic.domain.Category> categories = new ArrayList<>();
        categories.add(makeCat("cat1", "asd"));
        nl.ladyminks.thirtyoneseconds.logic.domain.Team team1 = new nl.ladyminks.thirtyoneseconds.logic.domain.Team("Team1", categories, "Team1");
        nl.ladyminks.thirtyoneseconds.logic.domain.Team team2 = new nl.ladyminks.thirtyoneseconds.logic.domain.Team("Team2", categories, "Team2");

        nl.ladyminks.thirtyoneseconds.logic.domain.Game game = makeGame(team1, team2);
        game.initialiseScoresToZero();

        Turn turn = makeTurn(team1, 5, 0);
        Turn turn2 = makeTurn(team2, 2, 3);
        game.addTurnWithScore(turn);
        game.addTurnWithScore(turn2);

        List<nl.ladyminks.thirtyoneseconds.logic.domain.Team> expected = new ArrayList<>();
        expected.add(team1);
        expected.add(team2);
        List<nl.ladyminks.thirtyoneseconds.logic.domain.Team> actual = game.getSortedList();
        assertEquals(expected, actual);
    }


    @Test
    public void getSortedList_ThreeTeamsSecondTeamLeading_MustReturnRightOrder(){
        List<Category> categories = new ArrayList<>();
        categories.add(makeCat("cat1", "asd"));
        nl.ladyminks.thirtyoneseconds.logic.domain.Team team1 = new nl.ladyminks.thirtyoneseconds.logic.domain.Team("Team1", categories, "Team1");
        nl.ladyminks.thirtyoneseconds.logic.domain.Team team2 = new nl.ladyminks.thirtyoneseconds.logic.domain.Team("Team2", categories, "Team2");
        nl.ladyminks.thirtyoneseconds.logic.domain.Team team3 = new nl.ladyminks.thirtyoneseconds.logic.domain.Team("Team3", categories, "Team3");

        Game game = makeGame(team1, team2, team3);
        game.initialiseScoresToZero();

        Turn turn = makeTurn(team1, 3, 2);
        Turn turn2 = makeTurn(team2, 4, 1);
        Turn turn3 = makeTurn(team3, 2, 3);

        game.addTurnWithScore(turn);
        game.addTurnWithScore(turn2);
        game.addTurnWithScore(turn3);

        List<nl.ladyminks.thirtyoneseconds.logic.domain.Team> expected = new ArrayList<>();
        expected.add(team2);
        expected.add(team1);
        expected.add(team3);
        List<Team> actual = game.getSortedList();
        assertEquals(expected, actual);
    }
}
