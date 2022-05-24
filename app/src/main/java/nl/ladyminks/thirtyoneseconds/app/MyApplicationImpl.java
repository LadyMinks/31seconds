package nl.ladyminks.thirtyoneseconds.app;

import android.app.Application;

import nl.ladyminks.thirtyoneseconds.presentation.MyApplication;
import nl.ladyminks.thirtyoneseconds.presentation.PresenterFactory;
import timber.log.Timber;

/**
 * @author Minka Firth asdasdasdasdasd
 */
public class MyApplicationImpl extends Application implements MyApplication {

    private final PresenterFactory presenterFactory = PresenterFactoryImpl.createFactory(this);

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
    }

    @Override
    public PresenterFactory getPresenterFactory() {
        return presenterFactory;
    }
}
