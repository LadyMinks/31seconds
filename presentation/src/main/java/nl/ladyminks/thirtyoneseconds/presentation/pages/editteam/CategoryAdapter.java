package nl.ladyminks.thirtyoneseconds.presentation.pages.editteam;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import androidx.recyclerview.widget.RecyclerView;
import nl.minka.ap2thirtyseconds.R;
import nl.ladyminks.thirtyoneseconds.logic.domain.Category;
import timber.log.Timber;

/**
 * the  Adapter object acts as a bridge between an RecyclerView and the data that has to "fill" it.
 * @author Minka Firth
 */


public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private final List<Category> categories = new ArrayList<>();
    private final Set<Category> selectedCategories = new HashSet<Category>();
    private LayoutInflater inflater;

    CategoryAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);
    }


    /**
     * actually inflates the row layout from xml when needed
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_category, parent, false);
        return new ViewHolder(view);
    }

    /**
     * binds the data to the TextView in each row, using the bind method (line 103)
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(CategoryAdapter.ViewHolder holder, int position) {
        Category category = categories.get(position);
        holder.bind(category);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    /**
     * When the adapter is first initialised, this method actually puts the data inside the Category
     * List or selected Category Set.
     *
     * @param categories
     *         List of all the categories.
     * @param selectedCategories
     *         Set of playing categories that are active for the team we are editing right now.
     */
    public void initialiseItems(List<Category> categories, Set<Category> selectedCategories) {
        this.categories.clear();
        this.categories.addAll(categories);
        this.selectedCategories.clear();
        this.selectedCategories.addAll(selectedCategories);
        notifyDataSetChanged();
    }

    public List<Category> getSelectedCategories() {
        return new ArrayList<>(selectedCategories);
    }

    /**
     * stores and recycles views as they are scrolled off screen
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        @SuppressLint("UseSwitchCompatOrMaterialCode") private final Switch tvCategory;
        ViewHolder(View itemView) {
            super(itemView);
            tvCategory = itemView.findViewById(R.id.switchCategory);
        }

        /**
         * Bind sets each item/row with a String, (the Name)
         * and with a setOnCheckedChangedListener, to decide if a switch has been "checked"
         * @param category Each of these Rows represents a category a Team can play with.
         */
        private void bind(final Category category) {
            tvCategory.setText(category.getName());

            tvCategory.setOnCheckedChangeListener(null);

            if (selectedCategories.contains(category)) {
                tvCategory.setChecked(true);
            } else {
                tvCategory.setChecked(false);
            }

            tvCategory.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Timber.e("Checked veranderd: %s", isChecked);
                    if (isChecked) {
                        selectedCategories.add(category);
                    } else {
                        selectedCategories.remove(category);
                    }
                }
            });

        }

    }


}
