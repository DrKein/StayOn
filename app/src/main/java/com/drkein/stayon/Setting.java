package com.drkein.stayon;


import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Build;
import android.provider.Settings;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

/**
 * Created by kein on 2016. 8. 17..
 */
public class Setting {
    private static final String TAG = "Setting";

    public static int getSavedTimeout(Context ctx) {
        return ctx.getSharedPreferences(TAG, Context.MODE_PRIVATE).getInt("lastValue", 0);
    }

    public static void setTimeout(Context ctx, int timeMillis) {
        ctx.getSharedPreferences(TAG, Context.MODE_PRIVATE).edit().putInt("lastValue", timeMillis).apply();
    }


    public static void setStayOn(Context ctx) {
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = ctx.registerReceiver(null, ifilter);
        int charger = batteryStatus.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);

        boolean enabled = (charger == BatteryManager.BATTERY_PLUGGED_USB);
        int timeout=0;
        if(enabled) {

            try {
                int lastValue = Settings.System.getInt(ctx.getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT);
                ctx.getSharedPreferences(TAG, Context.MODE_PRIVATE).edit().putInt("lastValue", lastValue).apply();
            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
            }

            timeout = (int)TimeUnit.MILLISECONDS.convert(30, TimeUnit.MINUTES);
            showToast(ctx, "화면꺼짐 시간 설정 : 30분");
        } else {
            timeout = ctx.getSharedPreferences(TAG, Context.MODE_PRIVATE).getInt("lastValue", 10000);
            int t = (timeout/1000);
            String msg = "화면꺼짐 시간 설정 : " + t + "초";
            if(t>60) {
                t = t/60;
                msg = "화면꺼짐 시간 설정 : " + t + "분";
            }
            showToast(ctx, msg);
        }
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(Settings.System.canWrite(ctx)) {
                Settings.System.putInt(ctx.getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT, timeout);
            } else {
                showToast(ctx, ctx.getString(R.string.no_permission));
            }
        } else {
            Settings.System.putInt(ctx.getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT, timeout);
        }
    }

    private static void showToast(Context ctx, String msg) {
        Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();
    }

}
