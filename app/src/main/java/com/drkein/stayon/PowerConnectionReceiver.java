package com.drkein.stayon;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

public class PowerConnectionReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		if(!TextUtils.isEmpty(action)) {
			Setting.setStayOn(context);
		}
	}


}
