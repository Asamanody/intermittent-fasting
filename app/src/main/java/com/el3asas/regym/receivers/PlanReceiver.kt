package com.el3asas.regym.receivers

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.os.bundleOf
import androidx.navigation.NavDeepLinkBuilder
import com.el3asas.regym.R
import com.el3asas.regym.helpers.LongPlans
import com.el3asas.regym.utils.LONG_PLAN_NOTIFICATION_ID
import com.el3asas.regym.utils.Pref
import com.el3asas.regym.utils.showLongPlanNotification
import org.koin.java.KoinJavaComponent.inject
import timber.log.Timber

class PlanReceiver : BroadcastReceiver() {

    private val pref by inject<Pref>(Pref::class.java)

    @SuppressLint("UnspecifiedImmutableFlag")
    override fun onReceive(context: Context, intent: Intent) {
        val pendingIntent = NavDeepLinkBuilder(context)
            .setGraph(R.navigation.mobile_navigation)
            .setDestination(R.id.planFragment)
            .setArguments(
                bundleOf(
                    "time" to
                            if (pref.getIntV(pref.lngPlanSelectedPlanKey) > 0)
                                pref.getIntV(pref.lngPlanSelectedPlanKey)
                            else
                                6,
                    "fromReceiver" to true
                )
            ).createPendingIntent()

        val notification = context.showLongPlanNotification(
            channelName,
            context.getString(R.string.startLngPln),
            context.getString(R.string.startLngPLnBody),
            pendingIntent
        )

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.notify(LONG_PLAN_NOTIFICATION_ID, notification)

        val lngPlanStarted = pref.getBooleanV(pref.lngPlanStartedKey)
        if (lngPlanStarted) {
            val lngPlanHour = pref.getIntV(pref.lngPlanHourKey)
            val lngPlanMinute = pref.getIntV(pref.lngPlanMinuteKey)
            val lngPlanAm_Bm = pref.getIntV(pref.lngPlanAm_BmKey)
            val lngPlanEndTime = pref.getLngV(pref.lngPlanEndTimeKey)
            val lngPlanStartTime = pref.getLngV(pref.lngPlanStartTimeKey)

            val longPlans = LongPlans()
            longPlans.init(context, pref)

            val selected = arrayOf(false, false, false, false, false, false, false)
            for (i in 0..6) {
                selected[i] = pref.getBooleanV("day$i")
                Timber.d("onReceive: -------- " + selected[i])
            }

            val calendar = longPlans.getCalenders(
                selected,
                lngPlanHour,
                lngPlanMinute,
                lngPlanAm_Bm,
                lngPlanStartTime,
                lngPlanEndTime
            )
            if (calendar != null) {
                longPlans.startPlan(calendar)
            } else {
                longPlans.stopPlan()
            }
        }
    }

    companion object {
        private const val channelName = "LongPlan"
        private const val channelId = "notifier"
    }
}