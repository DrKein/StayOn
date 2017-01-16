package com.drkein.stayon.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.text.TextUtils;

import com.drkein.stayon.act.EmptyActivity;
import com.drkein.stayon.service.WakeLockService;
import com.drkein.stayon.tools.L;

import java.text.SimpleDateFormat;
import java.util.Date;

import static android.content.Intent.ACTION_POWER_CONNECTED;

public class PowerConnectionReceiver extends BroadcastReceiver {
    private static final String TAG = PowerConnectionReceiver.class.getSimpleName();

	@Override
	public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        L.d(TAG, "onReceive() : " + action);
		if(!TextUtils.isEmpty(action)) {
            processAction(context, action);
        }

		if(isNeedToShowEmptyActivity(context)) {
			Intent i = new Intent(context, EmptyActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(i);
		}
	}

    private void processAction(Context context, String action) {
        if(action.equals(ACTION_POWER_CONNECTED)) {
            IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
            Intent batteryStatus = context.registerReceiver(null, intentFilter);
            int charger = batteryStatus.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);

            if((charger == BatteryManager.BATTERY_PLUGGED_USB)) {
                sendStartService(context);
                return;
            }
        }

        sendStopService(context);
    }

    private void sendStartService(Context context) {
        L.d(TAG, "sendStartService() ");
        Intent intent = new Intent(context, WakeLockService.class);
        intent.putExtra("ACTION", WakeLockService.ACTION_START);
        context.startService(intent);
    }

    private void sendStopService(Context context) {
        L.d(TAG, "sendStopService() ");
        Intent intent = new Intent(context, WakeLockService.class);
        intent.putExtra("ACTION", WakeLockService.ACTION_STOP);
        context.startService(intent);
    }

    /** To avoid battery saver */
	private boolean isNeedToShowEmptyActivity(Context ctx) {
		String lastRunDate = ctx.getSharedPreferences(TAG, Context.MODE_PRIVATE).getString("lastRunDate", "");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String today = sdf.format(new Date());
		if(lastRunDate.equals(today)) {
			return false;
		} else {
			ctx.getSharedPreferences(TAG, Context.MODE_PRIVATE).edit().putString("lastRunDate", today).apply();
			return true;
		}
	}

}
