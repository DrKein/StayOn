package com.drkein.stayon.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;

import com.drkein.stayon.R;
import com.drkein.stayon.act.MainActivity;
import com.drkein.stayon.tools.L;
import com.drkein.stayon.tools.Pref;

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
        if (TextUtils.isEmpty(action) || action.equals(ACTION_STOP)) {
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
        Pref.setServiceRunning(getApplicationContext(), false);

        sendLocalBroadCast(false);
    }

    private void sendLocalBroadCast(boolean running) {
        L.d(TAG, "sendLocalBroadCast() : " + running);
        Intent intent = new Intent("ServiceReceiver");
        intent.putExtra("running", running);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    private PowerManager.WakeLock mWakeLock;

    private void startWakeLock() {
        L.d(TAG, "startWakeLock() ");
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        if(mWakeLock == null) {
            mWakeLock = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_DIM_WAKE_LOCK, "hello");
        }
        if(!mWakeLock.isHeld()) {
            mWakeLock.acquire();
            startForeground();
        }

        Pref.setServiceRunning(getApplicationContext(), true);
        sendLocalBroadCast(true);
    }

    private void releaseWakeLock() {
        L.d(TAG, "releaseWakeLock() ");
        if (mWakeLock != null) {
            mWakeLock.release();
            mWakeLock = null;
        }
    }

    private void startForeground() {
        Notification.Builder mBuilder =
                new Notification.Builder(this)
                        .setSmallIcon(R.drawable.ic_stat_noti)
                        .setContentTitle("Stay On")
                        .setContentText("Wake lock activated.");

        PendingIntent intent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(intent);

        startForeground(100, mBuilder.build());
    }
}
