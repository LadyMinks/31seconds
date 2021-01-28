package nl.ladyminks.thirtyoneseconds.logic.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

/**
 * In order to start a new Game Activity, Game needs to be given to an Intent. This is why Game
 * needs to be Serializable (and therefore the "connecting classes" need to be serializable too).
 *
 * @author Minka Firth
 */
public class Category implements Serializable {
    private final String name;
    //    private final int icon;
    private final List<String> questions;

    @JsonCreator
    public Category(
            @JsonProperty("name") String name,
            @JsonProperty("questions") List<String> questions) {
        this.name = name;
        this.questions = questions;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("questions")
    public List<String> getQuestions() {
        return questions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return name.equals(category.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
