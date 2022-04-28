package com.el3asas.regym.receivers

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.os.Build
import android.os.Handler
import androidx.core.app.NotificationCompat
import com.el3asas.regym.R
import com.el3asas.regym.ui.AlarmActivity
import com.el3asas.regym.utils.*
import org.koin.java.KoinJavaComponent.inject

class AlarmReceiver : BroadcastReceiver() {
    private val pref by inject<Pref>(Pref::class.java)

    @SuppressLint("UnspecifiedImmutableFlag")
    override fun onReceive(context: Context, intent: Intent) {
        endPlan(pref)
        endWaterAlarm(context)
        val alarmTone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        val flag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
            PendingIntent.FLAG_MUTABLE
        else
            PendingIntent.FLAG_UPDATE_CURRENT

        if (context.isScreenOn()) {
            val notification = context.showAlarmNotification(
                PLAN_ALARM_REQUEST_CODE,
                "plan_receiver",
                context.getString(R.string.finishSyamTitle),
                context.getString(R.string.finishSyamContent),
                PendingIntent.getActivity(
                    context, ALARM_INTENT_PENDING_INTENT,
                    Intent(context, AlarmActivity::class.java), flag
                )
            )

            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            notificationManager.notify(PLAN_NOTIFICATION_ID, notification)

            Handler().postDelayed({
                context.hideNotification(PLAN_ALARM_REQUEST_CODE)
            }, 120000)
        } else {
            if (isOreoPlus()) {
                val audioAttributes = AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_ALARM)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build()

                val notificationManager = context.getSystemService(NotificationManager::class.java)
                if (notificationManager.getNotificationChannel("Alarm") == null) {
                    NotificationChannel(
                        "Alarm",
                        "Alarm",
                        NotificationManager.IMPORTANCE_HIGH
                    ).apply {
                        setBypassDnd(true)
                        setSound(alarmTone, audioAttributes)
                        notificationManager.createNotificationChannel(this)
                    }
                }
                val pendingIntent = PendingIntent.getActivity(
                    context,
                    0,
                    Intent(context, AlarmActivity::class.java).apply {
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    },
                    flag
                )

                val builder = NotificationCompat.Builder(context, "Alarm")
                    .setSmallIcon(R.drawable.ic_icon88)
                    .setContentTitle(context.getString(R.string.finishSyamTitle))
                    .setContentText(context.getString(R.string.finishSyamContent))
                    .setAutoCancel(true)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setCategory(NotificationCompat.CATEGORY_ALARM)
                    .setFullScreenIntent(pendingIntent, true)

                try {
                    notificationManager.notify(ALARM_INTENT_PENDING_INTENT, builder.build())
                } catch (e: Exception) {
                }
            } else {
                Intent(context, AlarmActivity::class.java).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(this)
                }
            }
        }
    }
}