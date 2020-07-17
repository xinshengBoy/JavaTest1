package com.just.test.tools;

import android.util.Log;

public class MitakeLogger {

    public final static String tag = "Mitake";
    public static int debugLevel = 0;

    public static void log(String msg) {
        log(tag, msg);
    }

    public static void log(String tag, String msg) {
        if ((debugLevel & 2) > 0) {
            Log.d(tag, msg);
        }
    }

    public static void log(int logLevel, String msg) {
        log(logLevel, tag, msg);
    }

    public static void log(int logLevel, String tag, String msg) {
        if ((debugLevel & 2) > 0) {
            switch (logLevel) {
                case 0:
                    Log.v(tag, msg);
                    break;
                case 1:
                    Log.d(tag, msg);
                    break;
                case 2:
                    Log.i(tag, msg);
                    break;
                case 3:
                    Log.w(tag, msg);
                    break;
                case 4:
                    Log.e(tag, msg);
                    break;
                case 5:
                    Log.wtf(tag, msg);
                    break;
                default:
                    break;
            }

        }
    }
}
