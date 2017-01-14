package com.drkein.stayon;

import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.PowerManager;
import android.widget.Toast;


/**
 * Created by kein on 2017. 1. 14..
 */

public class PlugMonitor {
    private static final String TAG = PlugMonitor.class.getSimpleName();

    private static Context mCtx;
    private static PowerManager.WakeLock mWakeLock;

    public static void changed(Context ctx) {
        mCtx = ctx;
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = ctx.registerReceiver(null, intentFilter);
        int charger = batteryStatus.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);

        boolean enabled = (charger == BatteryManager.BATTERY_PLUGGED_USB);
        if(enabled) {
            setWakeLock();
            Toast.makeText(ctx, R.string.stay_on_enabled, Toast.LENGTH_LONG).show();
        } else {
            releaseWakeLock();
            Toast.makeText(ctx, R.string.stay_on_disabled, Toast.LENGTH_LONG).show();
        }
    }

    private static void setWakeLock() {
        L.d(TAG, "setWakeLock() ");
        if(mWakeLock == null) {
            PowerManager pm = (PowerManager)mCtx.getSystemService(Context.POWER_SERVICE);
            mWakeLock = pm.newWakeLock( PowerManager.ACQUIRE_CAUSES_WAKEUP
                    | PowerManager.SCREEN_DIM_WAKE_LOCK
                    , "hello");
        }

        mWakeLock.acquire();

        disableKeyguard();
    }

    private static void releaseWakeLock() {
        L.d(TAG, "releaseWakeLock() ");
        if(mWakeLock != null) {
            mWakeLock.release();
        }

        enableKeyguard();
    }


    private static void disableKeyguard() {
        KeyguardManager km = (KeyguardManager)mCtx.getSystemService(Context.KEYGUARD_SERVICE);
        if(km != null) {
            KeyguardManager.KeyguardLock kl = km.newKeyguardLock(Context.KEYGUARD_SERVICE);
            if(kl != null) {
                kl.disableKeyguard();
            }
        }
    }

    private static void enableKeyguard() {
        KeyguardManager km = (KeyguardManager)mCtx.getSystemService(Context.KEYGUARD_SERVICE);
        if(km != null) {
            KeyguardManager.KeyguardLock kl = km.newKeyguardLock(Context.KEYGUARD_SERVICE);
            if(kl != null) {
                kl.reenableKeyguard();
            }
        }
    }
}
