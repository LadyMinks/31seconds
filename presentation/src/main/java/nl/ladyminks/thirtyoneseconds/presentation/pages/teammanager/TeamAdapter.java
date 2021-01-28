package nl.ladyminks.thirtyoneseconds.presentation.pages.teammanager;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import nl.ladyminks.thirtyoneseconds.logic.domain.Statistics;
import nl.ladyminks.thirtyoneseconds.logic.domain.Team;
import nl.ladyminks.thirtyoneseconds.presentation.pages.editteam.EditTeamActivity;
import nl.ladyminks.thirtyoneseconds.presentation.pages.statistics.StatisticsActivity;
import nl.ladyminks.thirtyoneseconds.presentation.ui.StatisticTypeAndUnit;
import nl.minka.ap2thirtyseconds.R;

/**
 * @author Minka Firth
 */
public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.ViewHolder> {

    private final List<Team> teams = new ArrayList<>();
    private final List<Statistics> statisticsList = new ArrayList<>();
    private final Context context;

    public TeamAdapter(Context context) {
        this.context = context;
    }

    // inflates the row layout from xml when needed
    @Override
    public TeamAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.row_team, parent, false);
        return new TeamAdapter.ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(TeamAdapter.ViewHolder holder, int position) {
        final Team team = teams.get(position);
        final Statistics statistics = statisticsList.get(position);
        holder.bind(team, statistics);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return teams.size();
    }

    public void setItems(List<Team> teams, List<Statistics> statistics) {
        this.teams.clear();
        this.teams.addAll(teams);
        this.statisticsList.clear();
        this.statisticsList.addAll(statistics);
        notifyDataSetChanged();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvTeam;
        private final StatisticTypeAndUnit tvStatsWins;
        private final StatisticTypeAndUnit tvStatsLosses;
        private final StatisticTypeAndUnit tvStatsBestCategory;
        private final ImageButton btnEditTeam;
        private final ImageButton btnStatistics;

        ViewHolder(View itemView) {
            super(itemView);
            tvTeam = itemView.findViewById(R.id.tvTeam);
            tvStatsWins = itemView.findViewById(R.id.tvStatsWins);
            tvStatsLosses = itemView.findViewById(R.id.tvStatsLosses);
            tvStatsBestCategory = itemView.findViewById(R.id.tvStatsBestCategory);
            btnEditTeam = itemView.findViewById(R.id.btnEditTeam);
            btnStatistics = itemView.findViewById(R.id.btnStatistics);
        }

        private void bind(final Team team, Statistics statistics) {
            tvTeam.setText(team.getName());


            tvStatsWins.setCvStatistics("Wins: ", String.valueOf(statistics.getWins()));
            tvStatsLosses.setCvStatistics("Losses: ", String.valueOf(statistics.getLosses()));
            tvStatsBestCategory.setCvStatistics("Best Category: ", statistics.getMostPoints() == null ? "n.a." : statistics.getMostPoints().getName());

            btnEditTeam.setOnClickListener(view -> {
                Intent editTeamIntent = new Intent(context, EditTeamActivity.class);
                editTeamIntent.putExtra("teamID", team.getTeamID());
                context.startActivity(editTeamIntent);
            });

            btnStatistics.setOnClickListener(view -> {
                Intent statisticsIntent = new Intent(context, StatisticsActivity.class);
                statisticsIntent.putExtra("teamID", team.getTeamID());
                context.startActivity(statisticsIntent);
            });
        }

    }
}


