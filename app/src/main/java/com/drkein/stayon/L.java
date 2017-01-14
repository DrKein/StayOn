package com.drkein.stayon;

import android.util.Log;

/**
 * Created by kein on 2017. 1. 14..
 */

public class L {
    public static void d(String TAG, String msg) {
        if (BuildConfig.DEBUG) Log.d(TAG, msg);
    }
}
