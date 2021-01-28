package nl.ladyminks.thirtyoneseconds.presentation.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import nl.minka.ap2thirtyseconds.R;

/**
 * @author Minka Firth
 */
public class StatisticTypeAndUnit extends FrameLayout {

    private TextView tvStatisticsType;
    private TextView tvStatisticsUnit;

    public StatisticTypeAndUnit(@NonNull Context context) {
        super(context);
        initialise(context, null, 0, 0);

    }

    public StatisticTypeAndUnit(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialise(context, attrs, 0, 0);

    }

    public StatisticTypeAndUnit(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialise(context, attrs, defStyleAttr, 0);

    }

    public StatisticTypeAndUnit(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initialise(context, attrs, defStyleAttr, defStyleRes);

    }

    private void initialise(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.cv_stat_type_unit, this);
        this.tvStatisticsType = findViewById(R.id.tvStatsType);
        this.tvStatisticsUnit = findViewById(R.id.tvStatsUnit);
    }

    public void setCvStatistics(String statType, String statUnit) {
        tvStatisticsType.setText(statType);
        tvStatisticsUnit.setText(statUnit);
    }
}
