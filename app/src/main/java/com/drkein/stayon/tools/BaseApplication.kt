package com.drkein.stayon.tools

import android.app.Application

import com.crashlytics.android.Crashlytics

import io.fabric.sdk.android.Fabric

/**
 * Created by kein on 2017. 2. 24..
 */

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Fabric.with(this, Crashlytics())
    }
}
