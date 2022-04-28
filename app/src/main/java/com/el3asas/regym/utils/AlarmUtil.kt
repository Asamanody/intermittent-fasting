package com.el3asas.regym.utils

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_MUTABLE
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.AlarmManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import com.el3asas.regym.receivers.AlarmReceiver
import com.el3asas.regym.receivers.PlanReceiver
import com.el3asas.regym.receivers.WaterTimeReceiver

@SuppressLint("ServiceCast", "UnspecifiedImmutableFlag")
fun startAlarm(context: Context, alarmTime: Long, requestCode: Int) {
    val flag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
        FLAG_MUTABLE
    else
        FLAG_UPDATE_CURRENT
    val alarm: AlarmManager = getSystemService(context, AlarmManager::class.java) as AlarmManager
    val intent: Intent = if (requestCode == LONG_PLAN_ALARM_REQUEST_CODE)
        Intent(context, PlanReceiver::class.java)
    else
        Intent(context, AlarmReceiver::class.java)
    val pendingIntent =
        PendingIntent.getBroadcast(context, requestCode, intent, flag)
    AlarmManagerCompat.setAlarmClock(
        alarm, alarmTime,
        context.getOpenAlarmTabIntent(),
        pendingIntent
    )
}

@SuppressLint("UnspecifiedImmutableFlag")
fun endAlarm(context: Context, requestCode: Int) {
    val flag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
        FLAG_MUTABLE
    else
        FLAG_UPDATE_CURRENT
    val alarm: AlarmManager = getSystemService(context, AlarmManager::class.java) as AlarmManager
    val intent: Intent = if (requestCode == LONG_PLAN_ALARM_REQUEST_CODE)
        Intent(context, PlanReceiver::class.java)
    else
        Intent(context, AlarmReceiver::class.java)
    val pendingIntent =
        PendingIntent.getService(context, requestCode, intent, flag)

    alarm.cancel(pendingIntent)
}

@SuppressLint("ServiceCast", "UnspecifiedImmutableFlag")
fun startWaterAlarm(context: Context, alarmTime: Long) {
    val flag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
        FLAG_MUTABLE
    else
        FLAG_UPDATE_CURRENT
    val alarm: AlarmManager = getSystemService(context, AlarmManager::class.java) as AlarmManager
    val intent = Intent(context, WaterTimeReceiver::class.java)

    val pendingIntent =
        PendingIntent.getBroadcast(context, WATER_ALARM_REQUEST_CODE, intent, flag)

    AlarmManagerCompat.setAlarmClock(
        alarm,
        alarmTime,
        pendingIntent,
        pendingIntent
    )
}

@SuppressLint("UnspecifiedImmutableFlag")
fun endWaterAlarm(context: Context) {
    val flag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
        FLAG_MUTABLE
    else
        FLAG_UPDATE_CURRENT
    val alarm: AlarmManager = getSystemService(context, AlarmManager::class.java) as AlarmManager
    val intent = Intent(context, WaterTimeReceiver::class.java)

    val pendingIntent =
        PendingIntent.getBroadcast(context, WATER_ALARM_REQUEST_CODE, intent, flag)

    alarm.cancel(pendingIntent)
}

