package nl.ladyminks.thirtyoneseconds.presentation.pages.customgame;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.material.snackbar.Snackbar;

import java.util.Arrays;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import nl.ladyminks.thirtyoneseconds.logic.domain.Game;
import nl.ladyminks.thirtyoneseconds.logic.domain.Statistics;
import nl.ladyminks.thirtyoneseconds.logic.domain.StopCondition;
import nl.ladyminks.thirtyoneseconds.logic.domain.Team;
import nl.ladyminks.thirtyoneseconds.logic.screens.customGame.CustomGamePresenter;
import nl.ladyminks.thirtyoneseconds.logic.screens.customGame.CustomGameView;
import nl.ladyminks.thirtyoneseconds.presentation.MyApplication;
import nl.ladyminks.thirtyoneseconds.presentation.pages.game.GameActivity;
import nl.ladyminks.thirtyoneseconds.presentation.pages.teammanager.TeamAdapter;
import nl.minka.ap2thirtyseconds.R;

public class CustomGameActivity extends AppCompatActivity implements CustomGameView {

    private CustomGamePresenter presenter;
    private TextView tvStopConditionUnit;
    private Spinner spnrStopConditionType;
    private TeamAdapter teamAdapter;
    private RecyclerView rvTeamList;
    private Button btnManageTeams;
    private ImageButton btnPlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_game);

        MyApplication myApplication = (MyApplication) getApplicationContext();
        this.presenter = myApplication.getPresenterFactory().createCustomGamePresenter(this);

        tvStopConditionUnit = findViewById(R.id.tvStopConditionUnit);
        tvStopConditionUnit.setOnClickListener(view -> new MaterialDialog.Builder(this)
                .title(R.string.input)
                .inputType(InputType.TYPE_CLASS_NUMBER)
                .input(R.string.input_hint, R.string.input_prefill, (dialog, input) -> {
                    try {
                        int intInput = Integer.parseInt(input.toString());
                        if (intInput < 5 || intInput > 60) {
                            Snackbar.make(view, "Changed invalid number to fit within the valid range of 5 to 60.", Snackbar.LENGTH_LONG).show();
                        }
                        tvStopConditionUnit.setText(String.valueOf(Math.max(Math.min(intInput, 60), 5)));
                    } catch (NumberFormatException e) {
                        Snackbar.make(view, "Please select a number.", Snackbar.LENGTH_LONG).show();
                    }
                }).show());

        spnrStopConditionType = findViewById(R.id.spnrConditionType);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.stop_condition_type_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnrStopConditionType.setAdapter(adapter);
        spnrStopConditionType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               presenter.saveStopCondition(StopCondition.Type.values()[position]);
            } // to close the onItemSelected

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        rvTeamList = findViewById(R.id.rvTeamList);
        rvTeamList.setLayoutManager(new LinearLayoutManager((this)));
        teamAdapter = new TeamAdapter(this);
        rvTeamList.setAdapter(teamAdapter);

        btnManageTeams = findViewById(R.id.btnManageTeams);
        btnManageTeams.setOnClickListener(view -> presenter.manageTeams());

        btnPlay = findViewById(R.id.btnPlay);
        btnPlay.setOnClickListener(view -> presenter.startCustomGame(Integer.parseInt(tvStopConditionUnit.getText().toString())));

        presenter.onCreate();
    }

    @Override
    public void makeTeamSelectionDialog(List<String> teamNames, List<Integer> selectedTeamIndexes) {
        new MaterialDialog.Builder(this)
                .title(R.string.manage_teams)
                .items(teamNames)
                .itemsCallbackMultiChoice(selectedTeamIndexes.toArray(new Integer[selectedTeamIndexes.size()]), (dialog, which, text) -> {
                    presenter.updateSelectedList(Arrays.asList(which));

                    /**
                     * If you use alwaysCallMultiChoiceCallback(), which is discussed below,
                     * returning false here won't allow the newly selected check box to actually be selected
                     * (or the newly unselected check box to be unchecked).
                     * See the limited multi choice dialog example in the sample project for details.
                     **/
                    return true;
                })
                .positiveText("OK")
                .show();
    }

    @Override
    public void displayTeams(List<Team> teamList, List<Statistics> statistics) {
        teamAdapter.setItems(teamList, statistics);
    }

    @Override
    public void startGame(Game game) {
        final Intent gameIntent = new Intent(CustomGameActivity.this, GameActivity.class);
        gameIntent.putExtra("game", game);
        startActivity(gameIntent);
    }
}

