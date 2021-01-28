package nl.ladyminks.thirtyoneseconds.presentation.utilities;

import android.os.Handler;
import android.os.Looper;

/**
 * @author Minka Firth
 */
public final class ThreadUtils {

    private static final Handler MAIN_HANDLER = new Handler(Looper.getMainLooper());

    /**
     * Runs the given Runnable on the main thread after the specified delay.
     *
     * @param delay    The time in milliseconds to wait until the runnable will be executed.
     * @param runnable The code to execute.
     */
    public static void postOnMainDelayed(long delay, Runnable runnable) {
        MAIN_HANDLER.postDelayed(runnable, delay);
    }

    private ThreadUtils() {
    }
}