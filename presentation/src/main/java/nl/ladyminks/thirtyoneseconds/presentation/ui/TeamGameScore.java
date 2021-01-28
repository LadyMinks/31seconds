package nl.ladyminks.thirtyoneseconds.presentation.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import nl.minka.ap2thirtyseconds.R;
import nl.ladyminks.thirtyoneseconds.logic.domain.Team;

/**
 * a custom view that displays the team scores in between rounds.
 *
 * @author Minka Firth
 */
public class TeamGameScore extends FrameLayout {

    private TextView teamName;
    private TextView teamScore;

    public TeamGameScore(@NonNull Context context) {
        super(context);
        initialise(context, null, 0, 0);

    }

    public TeamGameScore(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TeamGameScore(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public TeamGameScore(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void initialise(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.cv_team_game_score, this);
        this.teamName = findViewById(R.id.tvTeamName);
        this.teamScore = findViewById(R.id.tvTeamScore);
    }

    public void setTeamGameScore(Team team, int score){
        teamName.setText(team.getName());
        teamScore.setText(String.valueOf(score));
    }
}
