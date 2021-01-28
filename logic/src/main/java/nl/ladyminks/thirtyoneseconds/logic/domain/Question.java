package nl.ladyminks.thirtyoneseconds.logic.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * In order to start a new Game Activity, Game needs to be given to an Intent. This is why Game
 * needs to be Serializable(and therefore the "connecting classes" need to be serializable too).
 *
 * @author Minka Firth
 */
public class Question implements Serializable {
    private final String name;
    private final Category category;
    private final boolean correct;

    @JsonCreator
    public Question(
            @JsonProperty("name") String name,
            @JsonProperty("category") Category category,
            @JsonProperty("correct") boolean correct) {
        this.name = name;
        this.category = category;
        this.correct = correct;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("category")
    public Category getCategory() {
        return category;
    }

    @JsonProperty("correct")
    public boolean isCorrect() {
        return correct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Question question = (Question) o;

        if (correct != question.correct) return false;
        if (!name.equals(question.name)) return false;
        return category.equals(question.category);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + category.hashCode();
        result = 31 * result + (correct ? 1 : 0);
        return result;
    }
}


