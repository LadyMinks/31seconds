package nl.ladyminks.thirtyoneseconds.presentation.pages.game;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import nl.ladyminks.thirtyoneseconds.presentation.MyApplication;
import nl.minka.ap2thirtyseconds.R;
import nl.ladyminks.thirtyoneseconds.logic.domain.Game;
import nl.ladyminks.thirtyoneseconds.logic.screens.game.GamePresenter;
import nl.ladyminks.thirtyoneseconds.logic.screens.game.GameView;

/**
 * @author Minka Firth
 */
public class GameActivity extends AppCompatActivity implements GameView {

    private GamePresenter presenter;
    private TurnPageImpl turnPageImpl;
    private ReadyPageImpl readyPageImpl;
    private ResultsPageImpl resultsPageImpl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // intent: game given by activity
        final Intent intent = getIntent();
        final Game game = (Game) intent.getSerializableExtra("game");

        turnPageImpl = findViewById(R.id.turnPageImpl);
        readyPageImpl = findViewById(R.id.readyPageImpl);
        resultsPageImpl = findViewById(R.id.resultsPageImpl);

        //give game to presenter
        MyApplication myApplication = (MyApplication) getApplicationContext();
        this.presenter = myApplication.getPresenterFactory().createGamePresenter(this, readyPageImpl, turnPageImpl, resultsPageImpl, game);

        presenter.onCreate();

        turnPageImpl.setGamePresenter(presenter);
        readyPageImpl.setGamePresenter(presenter);
        resultsPageImpl.setGamePresenter(presenter);

    }

    @Override
    public void showPage(Page page) {
        readyPageImpl.setVisibility(page == Page.READY_PAGE ? View.VISIBLE : View.GONE);
        turnPageImpl.setVisibility(page == Page.TURN_PAGE ? View.VISIBLE : View.GONE);
        resultsPageImpl.setVisibility(page == Page.RESULTS_PAGE ? View.VISIBLE : View.GONE);
    }
}
