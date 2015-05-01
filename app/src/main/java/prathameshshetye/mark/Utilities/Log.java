package prathameshshetye.mark.Utilities;

/**
 * Created by p.shetye on 5/1/15.
 */
public class Log {
    public static final String LOG_TAG = "Marker";

    public static void LogThis(String s) {
        android.util.Log.d(LOG_TAG, s);
    }

    public static void InfoLog(String s) {
        android.util.Log.i(LOG_TAG, s);
    }

    public static void ErrorLog(String s) {
        android.util.Log.i(LOG_TAG, s);
    }
}
