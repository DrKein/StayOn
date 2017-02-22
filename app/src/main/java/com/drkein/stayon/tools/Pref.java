package com.drkein.stayon.tools;

import android.content.Context;

/**
 * Created by kein on 2017. 2. 22..
 */

public class Pref {
    private static final String TAG = Pref.class.getSimpleName();
    public static void setServiceRunning(Context ctx, boolean running) {
        ctx.getSharedPreferences(TAG, Context.MODE_PRIVATE).edit().putBoolean("running", running).apply();
    }

    public static boolean getServiceRunning(Context ctx) {
        return ctx.getSharedPreferences(TAG, Context.MODE_PRIVATE).getBoolean("running", false);
    }
}
