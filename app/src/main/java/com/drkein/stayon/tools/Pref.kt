package com.drkein.stayon.tools

import android.content.Context

/**
 * Created by kein on 2017. 2. 22..
 */

object Pref {
    private val TAG = Pref::class.java.simpleName
    @JvmStatic
    fun setServiceRunning(ctx: Context, running: Boolean) {
        ctx.getSharedPreferences(TAG, Context.MODE_PRIVATE).edit().putBoolean("running", running)
            .apply()
    }
    @JvmStatic
    fun getServiceRunning(ctx: Context): Boolean {
        return ctx.getSharedPreferences(TAG, Context.MODE_PRIVATE).getBoolean("running", false)
    }
}
