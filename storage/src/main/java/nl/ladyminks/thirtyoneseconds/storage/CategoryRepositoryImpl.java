package nl.ladyminks.thirtyoneseconds.storage;

import android.content.Context;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nl.ladyminks.thirtyoneseconds.logic.domain.Category;
import nl.ladyminks.thirtyoneseconds.logic.repositories.CategoryRepository;
import timber.log.Timber;

/**
 * @author Minka Firth
 */
public class CategoryRepositoryImpl implements CategoryRepository {
    /**
     * Name of the directory in assets that contains all of the categories. Each file in this
     * directory should be a Json file containing a single category.
     */
    private static final String DIRECTORY_NAME = "categories";
    private final ObjectMapper mapper = new ObjectMapper();
    private final Context context;

    public CategoryRepositoryImpl(Context context) {
        this.context = context;
    }

    @Override
    public List<Category> loadCategories() {
        final List<Category> allCategories = new ArrayList<>();
        try {
            final String[] categoryFileNames = context.getAssets().list(DIRECTORY_NAME);
            Timber.e("categoryFileNames: %s", Arrays.toString(categoryFileNames));
            for (int i = 0; i < categoryFileNames.length; i++) {
                final String fileName = categoryFileNames[i];
                allCategories.add(loadCategory(fileName));
            }
        } catch (IOException e) {
            Timber.w(e);
        }
        return allCategories;
    }


    // Stringbuilder: reads JSON string -> java string
    // inputstreamreader: Stream: file lezen, lees je als een stream van bytes ->  leest file stream.
    // bufferedREader: stream of bytes -> buffered -> chunks of bytes at once.

    /**
     * @param fileName
     * @return
     */
    private Category loadCategory(String fileName) {
        try {
            final StringBuilder stringBuilder = new StringBuilder();
            final InputStreamReader inputStreamReader =
                    new InputStreamReader(context.getAssets().open(DIRECTORY_NAME + "/" + fileName));
            try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
                String line = reader.readLine();
                while (line != null) {
                    stringBuilder.append(line).append('\n');
                    line = reader.readLine();
                    //readLine: file = null : returns null
                }
            }

            // readValue: pakt json String en maakt er een category van, deserialisatie;
            final String jsonString = stringBuilder.toString();
            final Category category = mapper.readValue(jsonString, Category.class);
            return category;
        } catch (IOException e) {
            Timber.w(e);
            throw new RuntimeException("failed to load category " + DIRECTORY_NAME + "/" + fileName);
        }
    }

}
