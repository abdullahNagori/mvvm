package com.example.abl.utils

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import javax.inject.Inject

class UtilHelper  @Inject constructor(private val context: Context) {
    @SuppressLint("HardwareIds")
    fun getDeviceId(): String {
        return Settings.Secure.getString(context.contentResolver,
            Settings.Secure.ANDROID_ID)
    }

}