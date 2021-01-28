package nl.ladyminks.thirtyoneseconds.presentation.pages.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import nl.ladyminks.thirtyoneseconds.presentation.MyApplication;
import nl.minka.ap2thirtyseconds.R;
import nl.ladyminks.thirtyoneseconds.presentation.pages.game.GameActivity;
import nl.ladyminks.thirtyoneseconds.presentation.pages.customgame.CustomGameActivity;
import nl.ladyminks.thirtyoneseconds.presentation.pages.teammanager.TeamManagerActivity;
import nl.ladyminks.thirtyoneseconds.logic.domain.Game;
import nl.ladyminks.thirtyoneseconds.logic.screens.home.HomePresenter;
import nl.ladyminks.thirtyoneseconds.logic.screens.home.HomeView;

public class HomeActivity extends AppCompatActivity implements HomeView {

    private TextView tvName;
    private Button btnQuickStart2;
    private Button btnQuickStart3;
    private Button btnQuickStart4;
    private CardView cvTeams;
    private TextView tvFirstTeam;
    private TextView tvSecondName;
    private TextView tvThirdName;
    private Button btnMoreTeams;
    private Button btnNewGame;
    private HomePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        MyApplication myApplication = (MyApplication) getApplicationContext();
        this.presenter = myApplication.getPresenterFactory().createHomePresenter(this);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> Snackbar.make(view, "This Button shows statistics", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());

        tvName = findViewById(R.id.tvName);
        btnQuickStart2 = findViewById(R.id.btnQuickStart2);
        btnQuickStart2.setOnClickListener(view -> presenter.startQuickGame(2));

        btnQuickStart3 = findViewById(R.id.btnQuickStart3);
        btnQuickStart3.setOnClickListener(view -> presenter.startQuickGame(3));

        btnQuickStart4 = findViewById(R.id.btnQuickStart4);
        btnQuickStart4.setOnClickListener(view -> presenter.startQuickGame(4));

        cvTeams = findViewById(R.id.cvTeams);

        tvFirstTeam = findViewById(R.id.tvFirstTeam);
        tvSecondName = findViewById(R.id.tvSecondTeam);
        tvThirdName = findViewById(R.id.tvThirdTeam);

        btnMoreTeams = findViewById(R.id.btnManageTeams);
        btnMoreTeams.setOnClickListener(view -> {
            Intent teamManagerIntent = new Intent(HomeActivity.this, TeamManagerActivity.class);
            startActivity(teamManagerIntent);
        });
        btnNewGame = findViewById(R.id.btnNewGame);
        btnNewGame.setOnClickListener(view -> {
            Intent newGameIntent = new Intent(HomeActivity.this, CustomGameActivity.class);
            startActivity(newGameIntent);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void startGame(Game game) {
        final Intent gameIntent = new Intent(HomeActivity.this, GameActivity.class);
        gameIntent.putExtra("game", game);
        startActivity(gameIntent);
    }
}
