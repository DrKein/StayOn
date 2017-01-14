package com.drkein.stayon;

import android.app.Activity;
import android.os.Bundle;

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

        PlugMonitor.changed(this);
    }

}