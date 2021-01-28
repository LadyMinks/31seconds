package nl.ladyminks.thirtyoneseconds.presentation.pages.teammanager;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import nl.ladyminks.thirtyoneseconds.logic.domain.Statistics;
import nl.ladyminks.thirtyoneseconds.logic.domain.Team;
import nl.ladyminks.thirtyoneseconds.logic.screens.teammanager.TeamManagerPresenter;
import nl.ladyminks.thirtyoneseconds.logic.screens.teammanager.TeamManagerView;
import nl.ladyminks.thirtyoneseconds.presentation.MyApplication;
import nl.ladyminks.thirtyoneseconds.presentation.pages.editteam.EditTeamActivity;
import nl.minka.ap2thirtyseconds.R;

public class TeamManagerActivity extends AppCompatActivity implements TeamManagerView {

    private TeamManagerPresenter presenter;
    private RecyclerView rvTeamList;
    private TeamAdapter teamAdapter;
    private FloatingActionButton fabAddTeams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_manager);

        MyApplication myApplication = (MyApplication) getApplicationContext();
        this.presenter = myApplication.getPresenterFactory().createTeamManagerPresenter(this);

        rvTeamList = findViewById(R.id.rvTeamList);
        rvTeamList.setLayoutManager(new LinearLayoutManager((this)));
        teamAdapter = new TeamAdapter(this);
        rvTeamList.setAdapter(teamAdapter);

        fabAddTeams = findViewById(R.id.fabAddTeam);
        fabAddTeams.setOnClickListener(view -> {
            Intent editTeamIntent = new Intent(TeamManagerActivity.this, EditTeamActivity.class);
            startActivity(editTeamIntent);
        });

        presenter.onCreate();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    public void displayTeams(List<Team> teamList, List<Statistics> statistics) {
        teamAdapter.setItems(teamList, statistics);
    }
}
