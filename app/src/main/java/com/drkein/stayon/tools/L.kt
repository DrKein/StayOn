package com.drkein.stayon.tools

import android.util.Log
import com.drkein.stayon.BuildConfig

/**
 * Created by kein on 2017. 1. 14..
 */

object L {

    @JvmStatic
    fun d(TAG: String, msg: String) {
        if (BuildConfig.DEBUG) Log.d(TAG, msg)
    }

}
