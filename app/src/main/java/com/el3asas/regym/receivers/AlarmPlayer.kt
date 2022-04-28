package com.el3asas.regym.receivers

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import com.el3asas.regym.R
import com.el3asas.regym.helpers.NotificationHelper
import com.el3asas.regym.utils.Pref
import com.el3asas.regym.utils.endPlan
import org.koin.android.ext.android.inject

class AlarmPlayer : Service() {
    private lateinit var mediaPlayer: MediaPlayer
    private val pref by inject<Pref>()

    @SuppressLint("UnspecifiedImmutableFlag")
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val notificationHelper = NotificationHelper(this)
        if (!intent.getBooleanExtra("cancel", false)) {
            val alarmTone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
            mediaPlayer = MediaPlayer.create(this, alarmTone)
            mediaPlayer.apply { start() }

            endPlan(pref)

            /*
            mediaPlayer.setOnCompletionListener { mp: MediaPlayer ->
                mp.stop()
                mp.release()
                notificationHelper.setNotification(
                    channelId,
                    channelName,
                    "الصيام المتقطع",
                    "تنبيه انتهاء فتره الصيام"
                )
                notificationHelper.notifi(35, notificationHelper.channelNotification.build())
            }*/
            notificationHelper.setNotification(
                channelId,
                channelName,
                "الصيام المتقطع",
                "تنبيه انتهاء فتره الصيام"
            )
            val flag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
                PendingIntent.FLAG_MUTABLE
            else
                PendingIntent.FLAG_UPDATE_CURRENT
            startForeground(
                3, notificationHelper.channelNotification
                    .addAction(
                        R.drawable.ic_icon88, "ايقاف التنبيه",
                        PendingIntent.getService(
                            this,
                            22,
                            intent.putExtra("cancel", true),
                            flag
                        )
                    )
                    .build()
            )


            Handler(Looper.getMainLooper()).postDelayed({
                if (mediaPlayer.isPlaying) {
                    mediaPlayer.stop()
                    mediaPlayer.release()
                    stopSelf()
                    notificationHelper.setNotification(
                        channelId,
                        channelName,
                        "الصيام المتقطع",
                        "تنبيه انتهاء فتره الصيام"
                    )
                    notificationHelper.notifi(35, notificationHelper.channelNotification.build())
                }
            }, 60000)
        } else {
            mediaPlayer.stop()
            mediaPlayer.release()
            notificationHelper.setNotification(
                channelId,
                channelName,
                "الصيام المتقطع",
                "تنبيه انتهاء فتره الصيام"
            )
            notificationHelper.notifi(35, notificationHelper.channelNotification.build())
            stopSelf()
        }
        return START_REDELIVER_INTENT
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    companion object {
        private const val channelId = "AlarmPlayer"
        private const val channelName = "AlarmPlayer"
    }
}