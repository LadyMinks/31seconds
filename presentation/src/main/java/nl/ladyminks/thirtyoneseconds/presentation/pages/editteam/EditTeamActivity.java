package nl.ladyminks.thirtyoneseconds.presentation.pages.editteam;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.widget.ImageButton;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Set;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import nl.ladyminks.thirtyoneseconds.presentation.MyApplication;
import nl.minka.ap2thirtyseconds.R;
import nl.ladyminks.thirtyoneseconds.logic.domain.Category;
import nl.ladyminks.thirtyoneseconds.logic.screens.editteam.EditTeamPresenter;
import nl.ladyminks.thirtyoneseconds.logic.screens.editteam.EditTeamView;

public class EditTeamActivity extends AppCompatActivity implements EditTeamView {

    private EditTeamPresenter presenter;
    private TextView tvEditTeamName;
    private ImageButton btnEditTeamName;
    private ImageButton btnAutoGenerateAgain;
    private RecyclerView rvCategories;
    private CategoryAdapter categoryAdapter;
    private FloatingActionButton fabSaveTeam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_team);

        final Intent intent = getIntent();
        final String teamID = intent.getStringExtra("teamID");

        MyApplication myApplication = (MyApplication) getApplicationContext();
        this.presenter = myApplication.getPresenterFactory().createEditTeamPresenter(this, teamID);

        tvEditTeamName = findViewById(R.id.tvEditTeamName);
        btnEditTeamName = findViewById(R.id.btnEditTeamName);
        btnEditTeamName.setOnClickListener(view -> new MaterialDialog.Builder(EditTeamActivity.this)
                .title(R.string.edit_team)
//                        .content(R.string.input_number)
                .inputType(InputType.TYPE_CLASS_TEXT)
                .input(R.string.random_team, R.string.input_prefill2, (dialog, input) -> tvEditTeamName.setText(input))
                .show());
        btnAutoGenerateAgain = findViewById(R.id.btnAutoGenerateAgain);
        btnAutoGenerateAgain.setOnClickListener(view -> presenter.generateAgain());

        fabSaveTeam = findViewById(R.id.fabSaveTeam);
        fabSaveTeam.setOnClickListener(view -> {
            presenter.saveTeam(tvEditTeamName.getText().toString(), categoryAdapter.getSelectedCategories());
            finish();
        });

        rvCategories = findViewById(R.id.rvCategories);
        rvCategories.setLayoutManager(new LinearLayoutManager(this));
        categoryAdapter = new CategoryAdapter(this);
        rvCategories.setAdapter(categoryAdapter);

        presenter.onCreate();

    }

    @Override
    public void displayName(String name) {
        tvEditTeamName.setText(name);
    }

    @Override
    public void displayCategories(List<Category> allCategories, Set<Category> playingCategories) {
        categoryAdapter.initialiseItems(allCategories, playingCategories);
    }
}