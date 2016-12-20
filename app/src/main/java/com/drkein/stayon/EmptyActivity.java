package com.drkein.stayon;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

/**
 * @author kein
 *
 */
public class EmptyActivity extends Activity {
    private static final String TAG = "EmptyActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_empty);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               finish();
            }
        }, 1000);
    }


}