package com.just.test.tools;

/**
 * log打印机制
 */
public class Logger {
    private static final boolean DEBUG = true;
    /**
     * 0关闭，6打开
     */
    public static int level = 0;

    public static void log(String tag,String message) {
        if ((level & 2) > 0) {
            if (DEBUG) android.util.Log.d(tag, message);
        }
    }
}
