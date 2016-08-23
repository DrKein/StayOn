package com.drkein.stayon;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;

/**
 * @author kein
 *
 */
public class MainActivity extends Activity {
    private static final String TAG = "MainActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        if(Setting.getSavedTimeout(this) == 0) {
            try {
                int lastValue = Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT);
                Setting.setTimeout(this, lastValue);
            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
            }
        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(!Settings.System.canWrite(this)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.dialog_need_permission_title);
                builder.setMessage(R.string.dialog_need_permission_message);
                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        showPermissionScreen();
                    }
                });
                builder.show();
            }
        }
    }

    private void showPermissionScreen() {
        Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_WRITE_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}