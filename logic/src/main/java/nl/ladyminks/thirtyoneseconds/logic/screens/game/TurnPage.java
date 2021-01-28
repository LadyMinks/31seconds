package nl.ladyminks.thirtyoneseconds.logic.screens.game;

import java.util.List;

import nl.ladyminks.thirtyoneseconds.logic.domain.Question;


/**
 * @author Minka Firth
 */
public interface TurnPage {

    /**
     * Displays the Questions on the screen.
     *
     * @param questions
     */
    void displayQuestions(List<Question> questions);

    /**
     * Is called at the end of the Turn.
     *
     * @return List with question with their 'correct' field filled in, depending on whether the
     * Team guessed this Question correctly.
     */
    List<Question> getQuestionsWithCorrectness();

    /**
     * Schedules {@link GamePresenter#updateTimer()} to be called in a 100 milliseconds.
     */
    void scheduleTimer();

    /**
     * Displays how much time is left.
     *
     * @param timeLeft time left in milliseconds.
     */
    void displayTimeLeft(long timeLeft);
}
