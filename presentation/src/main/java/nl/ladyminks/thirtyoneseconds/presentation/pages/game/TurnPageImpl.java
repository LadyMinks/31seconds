package nl.ladyminks.thirtyoneseconds.presentation.pages.game;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import nl.minka.ap2thirtyseconds.R;
import nl.ladyminks.thirtyoneseconds.presentation.ui.SelectableQuestion;
import nl.ladyminks.thirtyoneseconds.presentation.utilities.ThreadUtils;
import nl.ladyminks.thirtyoneseconds.logic.domain.Question;
import nl.ladyminks.thirtyoneseconds.logic.screens.game.GamePresenter;
import nl.ladyminks.thirtyoneseconds.logic.screens.game.TurnPage;

/**
 * @author Minka Firth
 */
public class TurnPageImpl extends FrameLayout implements TurnPage {

    private TextView tvDisplaySecondsLeft;
    private final List<SelectableQuestion> selectableQuestions = new ArrayList<>();
    private ImageButton btnConfirm;
    private GamePresenter gamePresenter;

    public TurnPageImpl(@NonNull Context context) {
        super(context);
        initialise(context, null, 0, 0);
    }

    public TurnPageImpl(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialise(context, attrs, 0, 0);
    }

    public TurnPageImpl(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialise(context, attrs, defStyleAttr, 0);
    }

    public TurnPageImpl(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initialise(context, attrs, defStyleAttr, defStyleRes);
    }

    private void initialise(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.page_turn, this);

        selectableQuestions.add((SelectableQuestion) findViewById(R.id.qcFirstQuestion));
        selectableQuestions.add((SelectableQuestion) findViewById(R.id.qcSecondQuestion));
        selectableQuestions.add((SelectableQuestion) findViewById(R.id.qcThirdQuestion));
        selectableQuestions.add((SelectableQuestion) findViewById(R.id.qcFourthQuestion));
        selectableQuestions.add((SelectableQuestion) findViewById(R.id.qcFifthQuestion));

        this.tvDisplaySecondsLeft = findViewById(R.id.tvDisplaySecondsLeft);

        this.btnConfirm = findViewById(R.id.btnConfirm);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gamePresenter.stopTurn();
            }
        });
    }


    @Override
    public void displayQuestions(List<Question> questions) {
        for (int i = 0; i < questions.size(); i++) {
            selectableQuestions.get(i).setQuestion(questions.get(i));
        }
    }

    @Override
    public List<Question> getQuestionsWithCorrectness() {
        List<Question> usedQuestions= new ArrayList<>();
        for (int i = 0; i < selectableQuestions.size(); i++) {
            usedQuestions.add(selectableQuestions.get(i).getQuestion());
        }
        return usedQuestions;
    }

    @Override
    public void scheduleTimer() {
        ThreadUtils.postOnMainDelayed(100L, new Runnable() {
            @Override
            public void run() {
                gamePresenter.updateTimer();
            }
        });
    }

    @Override
    public void displayTimeLeft(long timeLeft) {
        //TODO: String res inst String
        tvDisplaySecondsLeft.setText("TIME LEFT: " + timeLeft/1000 + " SECONDS");
    }
    public void setGamePresenter(GamePresenter gamePresenter) {
        this.gamePresenter = gamePresenter;
    }
}
