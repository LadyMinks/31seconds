package nl.ladyminks.thirtyoneseconds.presentation.pages.game;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import nl.ladyminks.thirtyoneseconds.logic.domain.Team;
import nl.ladyminks.thirtyoneseconds.logic.screens.game.GamePresenter;
import nl.ladyminks.thirtyoneseconds.logic.screens.game.ReadyPage;
import nl.ladyminks.thirtyoneseconds.presentation.ui.TeamGameScore;
import nl.minka.ap2thirtyseconds.R;

/**
 * @author Minka Firth
 */
public class ReadyPageImpl extends FrameLayout implements ReadyPage {

    private LinearLayout scoreBoard;
    //    private List<TeamGameScore> teamGameScores;
    private TextView tvReady;
    private TextView tvPlayingTeam;
    private ImageButton btnIsReady;
    private TextView three;
    private GamePresenter gamePresenter;

    public ReadyPageImpl(@NonNull Context context) {
        super(context);
        initialise(context, null, 0, 0);
    }

    public ReadyPageImpl(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialise(context, attrs, 0, 0);
    }

    public ReadyPageImpl(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialise(context, attrs, defStyleAttr, 0);
    }

    public ReadyPageImpl(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initialise(context, attrs, defStyleAttr, defStyleRes);
    }

    private void initialise(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.page_ready, this);
        this.tvReady = findViewById(R.id.tvReady);
        this.tvPlayingTeam = findViewById(R.id.tvPlayingTeam);
        this.btnIsReady = findViewById(R.id.btnIsReady);
        this.scoreBoard = findViewById(R.id.scoreBoard);
        this.three = findViewById(R.id.three);
//        three.setVisibility();
        btnIsReady.setOnClickListener(view ->

                gamePresenter.startTurn());
    }

    @Override
    public void prepareScoreBoard(int amountOfTeams) {
        for (int i = 0; i < amountOfTeams; i++) {
            TeamGameScore teamGameScore = new TeamGameScore(getContext());
            scoreBoard.addView(teamGameScore);
        }
    }

    @Override
    public void displayPlayingTeam(Team playingTeam) {
        tvPlayingTeam.setText(playingTeam.getName());
    }

    @Override
    public void displayTeamWithScore(int index, Team team, int score) {
        TeamGameScore teamGameScore = (TeamGameScore) scoreBoard.getChildAt(index);
        teamGameScore.setTeamGameScore(team, score);
    }

    public void setGamePresenter(GamePresenter gamePresenter) {
        this.gamePresenter = gamePresenter;
    }


}
