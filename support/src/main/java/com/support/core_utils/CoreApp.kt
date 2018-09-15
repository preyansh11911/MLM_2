package com.support.core_utils

import android.annotation.SuppressLint
import android.app.Application
import android.provider.Settings

class CoreApp : Application() {

    companion object {
        var instance: CoreApp? = null
        var deviceId: String = ""
    }

    @SuppressLint("HardwareIds")
    override fun onCreate() {
        super.onCreate()
        instance = this
        deviceId = Settings.Secure.getString(this.contentResolver, Settings.Secure.ANDROID_ID)
    }

}