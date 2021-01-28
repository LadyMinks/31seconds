package nl.ladyminks.thirtyoneseconds.presentation.pages.game;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import nl.minka.ap2thirtyseconds.R;
import nl.ladyminks.thirtyoneseconds.presentation.pages.home.HomeActivity;
import nl.ladyminks.thirtyoneseconds.presentation.ui.TeamGameScore;
import nl.ladyminks.thirtyoneseconds.logic.domain.Team;
import nl.ladyminks.thirtyoneseconds.logic.screens.game.GamePresenter;
import nl.ladyminks.thirtyoneseconds.logic.screens.game.ResultsPage;

/**
 * @author Minka Firth
 */
public class ResultsPageImpl extends FrameLayout implements ResultsPage {

    private LinearLayout scoreBoard;
    private GamePresenter gamePresenter;
    private TextView tvCongratulations;
    private TextView tvWinningTeam;
    private ImageButton btnHome;

    public ResultsPageImpl(@NonNull Context context) {
        super(context);
        initialise(context, null, 0, 0);
    }

    public ResultsPageImpl(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialise(context, attrs, 0, 0);
    }

    public ResultsPageImpl(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialise(context, attrs, defStyleAttr, 0);
    }

    public ResultsPageImpl(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initialise(context, attrs, defStyleAttr, defStyleRes);
    }

    private void initialise(final Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.page_results, this);
        this.scoreBoard = findViewById(R.id.scoreBoard);
        this.tvCongratulations = findViewById(R.id.tvCongratulations);
        this.tvWinningTeam = findViewById(R.id.tvWinningTeam);
        this.btnHome = findViewById(R.id.btnHome);
        btnHome.setOnClickListener(view -> {
            Intent homeIntent = new Intent(context, HomeActivity.class);
            context.startActivity(homeIntent);
        });
    }

    public void setGamePresenter(GamePresenter gamePresenter) {
        this.gamePresenter = gamePresenter;
    }

    @Override
    public void prepareScoreBoard(int amountOfTeams) {
        for (int i = 0; i < amountOfTeams; i++) {
            TeamGameScore teamGameScore = new TeamGameScore(getContext());
            scoreBoard.addView(teamGameScore);
        }
    }

    @Override
    public void displayTeamWithScore(int index, Team team, int score) {
        TeamGameScore teamGameScore = (TeamGameScore) scoreBoard.getChildAt(index);
        teamGameScore.setTeamGameScore(team, score);
    }

    @Override
    public void displayWinningTeam(Team team) {
        tvWinningTeam.setText(team.getName());

    }
}
