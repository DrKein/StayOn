package com.drkein.stayon;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PowerConnectionReceiver extends BroadcastReceiver {
    private static final String TAG = PowerConnectionReceiver.class.getSimpleName();

	@Override
	public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();
        L.d(TAG, "onReceive() : " + action);
		if(!TextUtils.isEmpty(action)) {
			PlugMonitor.changed(context);
        }

		if(isNeedToShowEmptyActivity(context)) {
			Intent i = new Intent(context, EmptyActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(i);
		}
	}

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
