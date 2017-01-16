package com.drkein.stayon.act;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

/**
 * @author kein
 *
 */
public class EmptyActivity extends Activity {
    private static final String TAG = EmptyActivity.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               finish();
            }
        }, 500);
    }


}