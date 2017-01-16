package com.drkein.stayon.service;

import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.PowerManager;
import android.text.TextUtils;

import com.drkein.stayon.R;
import com.drkein.stayon.tools.L;

/**
 * Created by kein on 2017. 1. 16..
 */

public class WakeLockService extends Service {

    private static final String TAG = WakeLockService.class.getSimpleName();

    public static final String ACTION_STOP = "action_stop";
    public static final String ACTION_START = "action_start";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getStringExtra("ACTION");
        if(TextUtils.isEmpty(action) || action.equals(ACTION_STOP)) {
            stopWakeLock();
        } else {
            startWakeLock();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void stopWakeLock() {
        L.d(TAG, "stopWakeLock() ");
        releaseWakeLock();
        stopForeground(true);
        stopSelf();
    }

    private PowerManager.WakeLock mWakeLock;

    private void startWakeLock() {
        L.d(TAG, "startWakeLock() ");
        PowerManager pm = (PowerManager)getSystemService(Context.POWER_SERVICE);
        mWakeLock = pm.newWakeLock( PowerManager.ACQUIRE_CAUSES_WAKEUP
                        | PowerManager.SCREEN_DIM_WAKE_LOCK, "hello");
        mWakeLock.acquire();

        startForeground();
    }

    private void releaseWakeLock() {
        L.d(TAG, "releaseWakeLock() ");
        if(mWakeLock != null) {
            mWakeLock.release();
        }
    }

    private void startForeground() {
        Notification.Builder mBuilder =
                new Notification.Builder(this)
                        .setSmallIcon(R.drawable.ic_stat_noti)
                        .setContentTitle("Stay On")
                        .setContentText("Wake lock activated.");

        startForeground(100, mBuilder.build());
    }


}
