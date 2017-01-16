package com.drkein.stayon.tools;

import android.util.Log;

import com.drkein.stayon.BuildConfig;

/**
 * Created by kein on 2017. 1. 14..
 */

public class L {
    public static void d(String TAG, String msg) {
        if (BuildConfig.DEBUG) Log.d(TAG, msg);
    }
}
