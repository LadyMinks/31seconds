package nl.ladyminks.thirtyoneseconds.logic.factories;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import nl.ladyminks.thirtyoneseconds.logic.domain.Category;
import nl.ladyminks.thirtyoneseconds.logic.domain.Game;
import nl.ladyminks.thirtyoneseconds.logic.domain.Question;
import nl.ladyminks.thirtyoneseconds.logic.domain.Team;
import nl.ladyminks.thirtyoneseconds.logic.domain.Turn;

/**
 * @author Minka Firth
 */
public class TurnFactory {

    private final Random random;

    public TurnFactory() {
        this(new Random());
    }

    public TurnFactory(Random random) {
        this.random = random;
    }

    /**
     * Creates a new Turn with 5 questions.
     *
     * @param playingTeam
     *         The Team that will play this Turn.
     * @return a new Turn.
     */
    public Turn generateTurn(Team playingTeam, Game game) {
        List<Question> questions = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            questions.add(generateQuestion(playingTeam, game));
        }
        return new Turn(playingTeam, questions, System.currentTimeMillis());
    }

    /**
     * Generates a question by randomly picking a Category from the List of playingCategories. Then
     * randomly selects a word from that category.
     *
     * @param playingTeam
     *         needs the playing Team to determine what Categories to use.
     * @return a new Question.
     */
    Question generateQuestion(Team playingTeam, Game game) {

        for (int i = 0; i < 100; i++) {

            List<Category> categories = playingTeam.getPlayingCategories();
            Category category = categories.get(random.nextInt(categories.size()));

            List<String> questions = category.getQuestions();
            String question = questions.get(random.nextInt(questions.size()));

            if (!game.isQuestionUsed(question)) {
                game.addQuestionToUsed(question);
                return new Question(question, category, false);
            }
        }
        throw new IllegalStateException("stuck in loop looking for unused questions.");


    }
}
