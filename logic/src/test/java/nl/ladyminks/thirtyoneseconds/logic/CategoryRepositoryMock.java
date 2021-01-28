package nl.ladyminks.thirtyoneseconds.logic;

import java.util.List;

import nl.ladyminks.thirtyoneseconds.logic.domain.Category;
import nl.ladyminks.thirtyoneseconds.logic.repositories.CategoryRepository;

/**
 * @author Minka Firth
 */
public class CategoryRepositoryMock implements CategoryRepository {

    @Override
    public List<Category> loadCategories() {
        return null;
    }
}
