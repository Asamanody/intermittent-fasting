package com.el3asas.regym.utils

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.RingtoneManager
import android.os.Build
import android.os.PowerManager
import androidx.core.app.NotificationCompat
import com.el3asas.regym.R
import com.el3asas.regym.receivers.HideAlarmReceiver
import com.el3asas.regym.ui.MainActivity


fun isOreoPlus() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O

@SuppressLint("UnspecifiedImmutableFlag")
fun Context.getOpenAlarmTabIntent(): PendingIntent {
    val intent = getLaunchIntent() ?: Intent(this, MainActivity::class.java)
    val flag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
        PendingIntent.FLAG_MUTABLE
    else
        PendingIntent.FLAG_UPDATE_CURRENT
    return PendingIntent.getActivity(
        this,
        MAIN_ACTIVITY_PENDING_INTENT,
        intent,
        flag
    )
}

@SuppressLint("UnspecifiedImmutableFlag")
fun Context.getHideAlarmPendingIntent(id: Int): PendingIntent? {
    val intent = Intent(this, HideAlarmReceiver::class.java)
    intent.putExtra("alarm_id", id)
    val flag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
        PendingIntent.FLAG_MUTABLE
    else
        PendingIntent.FLAG_UPDATE_CURRENT
    return PendingIntent.getBroadcast(this, id, intent, flag)
}

fun Context.hideNotification(id: Int) {
    val manager =
        applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    manager.cancel(id)
}

fun Context.isScreenOn() = (getSystemService(Context.POWER_SERVICE) as PowerManager).isScreenOn

@SuppressLint("NewApi")
fun Context.showAlarmNotification(
    notificationId: Int,
    channelName: String,
    title: String,
    content: String,
    pendingIntent: PendingIntent
): Notification {
    val alarmTone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)

    val channelId = "regymChannel"
    if (isOreoPlus()) {
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_ALARM)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .setLegacyStreamType(AudioManager.STREAM_ALARM)
            .setFlags(AudioAttributes.FLAG_AUDIBILITY_ENFORCED)
            .build()

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val importance = NotificationManager.IMPORTANCE_HIGH
        NotificationChannel(channelId, channelName, importance).apply {
            setBypassDnd(true)
            enableLights(true)
            enableVibration(true)
            setSound(alarmTone, audioAttributes)
            notificationManager.createNotificationChannel(this)
        }
    }

    val dismissIntent = getHideAlarmPendingIntent(notificationId)
    val builder = NotificationCompat.Builder(this)
        .setContentTitle(title)
        .setContentText(content)
        .setSmallIcon(R.drawable.ic_icon88)
        .setContentIntent(pendingIntent)
        .setPriority(Notification.PRIORITY_HIGH)
        .setDefaults(Notification.DEFAULT_LIGHTS)
        .setAutoCancel(true)
        .setChannelId(channelId)
        .addAction(R.drawable.ic_baseline_clear_24, getString(R.string.dismiss), dismissIntent)
        .setDeleteIntent(dismissIntent)

    builder.setVisibility(NotificationCompat.VISIBILITY_PUBLIC)

    // if (soundUri != SILENT) {
    builder.setSound(alarmTone, AudioManager.STREAM_ALARM)
    //}

    val vibrateArray = LongArray(2) { 500 }
    builder.setVibrate(vibrateArray)

    val notification = builder.build()
    notification.flags = notification.flags or Notification.FLAG_INSISTENT
    return notification
}

@SuppressLint("NewApi")
fun Context.showLongPlanNotification(
    channelName: String,
    title: String,
    content: String,
    pendingIntent: PendingIntent
): Notification {

    val channelId = "regymChannelLongPlan"
    if (isOreoPlus()) {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val importance = NotificationManager.IMPORTANCE_HIGH
        NotificationChannel(channelId, channelName, importance).apply {
            setBypassDnd(false)
            enableLights(false)
            enableVibration(true)
            notificationManager.createNotificationChannel(this)
        }
    }

    val builder = NotificationCompat.Builder(this)
        .setContentTitle(title)
        .setContentText(content)
        .setSmallIcon(R.drawable.ic_icon88)
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)
        .addAction(R.drawable.ic_icon88, getString(R.string.startLngPln), pendingIntent)
        .setChannelId(channelId)

    builder.setVisibility(NotificationCompat.VISIBILITY_PUBLIC)

    val vibrateArray = LongArray(2) { 500 }
    builder.setVibrate(vibrateArray)

    val notification = builder.build()
    //notification.flags = notification.flags or Notification.FLAG_INSISTENT
    return notification
}

@SuppressLint("NewApi")
fun Context.showWaterNotification(
    channelName: String,
    title: String,
    content: String,
    pendingIntent: PendingIntent
): Notification {

    val channelId = "regymWater"
    if (isOreoPlus()) {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val importance = NotificationManager.IMPORTANCE_HIGH
        NotificationChannel(channelId, channelName, importance).apply {
            setBypassDnd(false)
            enableLights(false)
            enableVibration(true)
            notificationManager.createNotificationChannel(this)
        }
    }

    val builder = NotificationCompat.Builder(this)
        .setContentTitle(title)
        .setContentText(content)
        .setSmallIcon(R.drawable.ic_icon88)
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)
        .setChannelId(channelId)

    builder.setVisibility(NotificationCompat.VISIBILITY_PUBLIC)

    val vibrateArray = LongArray(2) { 500 }
    builder.setVibrate(vibrateArray)

    return builder.build()
}


