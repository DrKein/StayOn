package com.drkein.stayon.act

import android.app.Activity
import android.os.Bundle
import android.os.Handler

/**
 * @author kein
 */
class EmptyActivity : Activity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Handler().postDelayed({ finish() }, 500)
    }

}