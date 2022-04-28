package com.el3asas.regym.receivers

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.os.bundleOf
import androidx.navigation.NavDeepLinkBuilder
import com.el3asas.regym.R
import com.el3asas.regym.utils.Pref
import com.el3asas.regym.utils.WATER_NOTIFICATION_ID
import com.el3asas.regym.utils.showWaterNotification
import com.el3asas.regym.utils.startWaterAlarm
import org.koin.java.KoinJavaComponent.inject
import java.util.*

class WaterTimeReceiver : BroadcastReceiver() {
    private val pref by inject<Pref>(Pref::class.java)
    override fun onReceive(context: Context, intent: Intent) {
        val waterCalendar = Calendar.getInstance()
        waterCalendar.add(Calendar.HOUR, 1)
        startWaterAlarm(context, waterCalendar.timeInMillis)
        val pendingIntent = NavDeepLinkBuilder(context)
            .setGraph(R.navigation.mobile_navigation)
            .setDestination(R.id.planFragment)
            .setArguments(
                bundleOf(
                    "time" to
                            if (pref.getBooleanV(pref.startPlanKey) && pref.getIntV(pref.startedPlanTimeKey) > 0) {
                                pref.getIntV(pref.startedPlanTimeKey)
                            } else if (pref.getBooleanV(pref.lngPlanStartedKey) && pref.getIntV(pref.lngPlanSelectedPlanKey) > 0)
                                pref.getIntV(pref.lngPlanSelectedPlanKey)
                            else
                                6,
                    "fromReceiver" to true
                )
            ).createPendingIntent()
        val notification = context.showWaterNotification(
            waterNotificationChannel,
            context.getString(R.string.waterTitle),
            context.getString(R.string.waterBody),
            pendingIntent
        )
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.notify(WATER_NOTIFICATION_ID, notification)
    }

    companion object {
        private const val waterNotificationChannel = "waterChannel"
    }
}