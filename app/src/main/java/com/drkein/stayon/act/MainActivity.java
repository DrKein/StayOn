package com.drkein.stayon.act;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

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
        setContentView(R.layout.main);

        Switch manualSwitch = (Switch)findViewById(R.id.manualSwitch);
        manualSwitch.setChecked(Pref.getServiceRunning(this));
        manualSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    sendStartService();
                } else {
                    sendStopService();
                }
            }
        });

        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = registerReceiver(null, intentFilter);
        int charger = batteryStatus.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
        if((charger == BatteryManager.BATTERY_PLUGGED_USB)) {
            sendStartService();
            manualSwitch.setChecked(true);
        }
    }

    private void sendStartService() {
        L.d(TAG, "sendStartService() ");
        Intent intent = new Intent(this, WakeLockService.class);
        intent.putExtra("ACTION", WakeLockService.ACTION_START);
        startService(intent);
    }
    private void sendStopService() {
        L.d(TAG, "sendStopService() ");
        Intent intent = new Intent(this, WakeLockService.class);
        intent.putExtra("ACTION", WakeLockService.ACTION_STOP);
        startService(intent);
    }

}