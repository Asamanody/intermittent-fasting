package com.el3asas.regym.utils

import android.app.Activity
import android.app.Application
import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager
import android.view.WindowManager
import com.el3asas.regym.R
import com.simplemobiletools.commons.extensions.*
import com.simplemobiletools.commons.helpers.BaseConfig
import com.simplemobiletools.commons.helpers.INVALID_NAVIGATION_BAR_COLOR

//fun Context.getSharedPrefs() = getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE)

val Context.baseConfig: BaseConfig get() = BaseConfig.newInstance(this)

fun Context.getLaunchIntent() = packageManager.getLaunchIntentForPackage(baseConfig.appId)

fun Application.appLaunched(appId: String) {
//    baseConfig.internalStoragePath = getInternalStoragePath()
//    updateSDCardPath()
    baseConfig.appId = appId

}