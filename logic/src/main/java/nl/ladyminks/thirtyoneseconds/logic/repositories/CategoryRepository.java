package nl.ladyminks.thirtyoneseconds.logic.repositories;

import java.util.List;

import nl.ladyminks.thirtyoneseconds.logic.domain.Category;

/**
 * @author Minka Firth
 */
public interface CategoryRepository {

    /**
     * Loads all categories.
     *
     * @return List of Categories.
     */
    List<Category> loadCategories();
}
