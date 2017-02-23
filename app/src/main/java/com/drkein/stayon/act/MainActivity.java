package com.drkein.stayon.act;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.CustomEvent;
import com.drkein.stayon.BuildConfig;
import com.drkein.stayon.R;
import com.drkein.stayon.service.WakeLockService;
import com.drkein.stayon.tools.L;
import com.drkein.stayon.tools.Pref;

/**
 * @author kein
 *
 */
public class MainActivity extends Activity {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        Switch manualSwitch = (Switch)findViewById(R.id.manualSwitch);
        manualSwitch.setChecked(Pref.getServiceRunning(this));
        manualSwitch.setOnCheckedChangeListener(mSwitchChangedListener);

        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = registerReceiver(null, intentFilter);
        int charger = batteryStatus.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
        if((charger == BatteryManager.BATTERY_PLUGGED_USB)) {
            sendStartService();
        }

        ((TextView)findViewById(R.id.tvVersion)).setText("v"+ BuildConfig.VERSION_NAME);
    }

    private CompoundButton.OnCheckedChangeListener mSwitchChangedListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            L.d(TAG, "onCheckedChanged() : " + b);
            if(b) {
                sendStartService();
            } else {
                sendStopService();
            }
        }
    };

    private void sendStartService() {
        L.d(TAG, "sendStartService() ");
        Intent intent = new Intent(this, WakeLockService.class);
        intent.putExtra("ACTION", WakeLockService.ACTION_START);
        startService(intent);

        Answers.getInstance().logCustom(new CustomEvent("Switch").putCustomAttribute("click", "StartService"));
    }

    private void sendStopService() {
        L.d(TAG, "sendStopService() ");
        Intent intent = new Intent(this, WakeLockService.class);
        intent.putExtra("ACTION", WakeLockService.ACTION_STOP);
        startService(intent);

        Answers.getInstance().logCustom(new CustomEvent("Switch").putCustomAttribute("click", "StopService"));
    }

    @Override
    protected void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver, new IntentFilter("ServiceReceiver"));
    }

    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mReceiver);
    }

    BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            L.d(TAG, "onReceive() : " + intent.getBooleanExtra("running", false));
            Switch manualSwitch = (Switch)findViewById(R.id.manualSwitch);
            manualSwitch.setOnCheckedChangeListener(null);
            manualSwitch.setChecked(intent.getBooleanExtra("running", false));
            manualSwitch.setOnCheckedChangeListener(mSwitchChangedListener);
        }
    };
}