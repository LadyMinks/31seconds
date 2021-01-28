package nl.ladyminks.thirtyoneseconds.logic.screens.editteam;

import java.util.List;
import java.util.Set;

import nl.ladyminks.thirtyoneseconds.logic.domain.Category;

/**
 * @author Minka Firth
 */
public interface EditTeamView {
    /**
     * Puts the given name on the screen.
     *
     * @param name
     *         name of the Team that we are editing.
     */
    void displayName(String name);


    /**
     * Puts the Categories on the screen.
     *
     * @param allCategories
     *         List of all the categories.
     * @param playingCategories
     *         List of the categories this Team is playing with.
     */
    void displayCategories(List<Category> allCategories, Set<Category> playingCategories);
}
