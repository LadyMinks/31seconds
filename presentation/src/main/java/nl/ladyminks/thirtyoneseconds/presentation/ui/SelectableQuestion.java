package nl.ladyminks.thirtyoneseconds.presentation.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import nl.minka.ap2thirtyseconds.R;
import nl.ladyminks.thirtyoneseconds.logic.domain.Question;

/**
 * @author Minka Firth
 */

public class SelectableQuestion extends FrameLayout {

    private TextView tvQuestion;
    private Question question;
    private boolean correct = false;

    public SelectableQuestion(@NonNull Context context) {
        super(context);
        initialise(context, null, 0, 0);
    }

    public SelectableQuestion(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialise(context, attrs, 0, 0);
    }

    public SelectableQuestion(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialise(context, attrs, defStyleAttr, 0);
    }

    public SelectableQuestion(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initialise(context, attrs, defStyleAttr, defStyleRes);
    }

    private void initialise(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.cv_question, this);
        this.tvQuestion = findViewById(R.id.tvQuestion);
        this.setOnClickListener(view -> {
            correct = !correct;
            if (correct) {
                SelectableQuestion.this.setBackgroundResource(R.color.correct);
            } else {
                SelectableQuestion.this.setBackgroundResource(R.color.white);
            }
        });
    }

    public void setQuestion(Question question) {
        this.question = question;
        tvQuestion.setText(question.getName());
        correct = false;
        this.setBackgroundResource(R.color.white);
    }

    public Question getQuestion() {
        return new Question(question.getName(), question.getCategory(), correct);
    }

}
