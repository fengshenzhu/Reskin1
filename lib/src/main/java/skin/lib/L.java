package skin.lib;

import android.util.Log;

/**
 * 换肤库调试Log
 * <p/>
 * Created by fengshzh on 16/2/18.
 */
public class L {
    private static boolean debug = false;

    private L() {
    }

    public static void debug(boolean debug) {
        L.debug = debug;
    }

    public static void d(String tag, String msg) {
        if (debug)
            Log.d(tag, msg);
    }
    public static void e(String tag, String msg) {
        if (debug)
            Log.e(tag, msg);
    }
}
