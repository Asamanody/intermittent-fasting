package com.el3asas.regym.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.el3asas.regym.helpers.LongPlans
import com.el3asas.regym.utils.PLAN_ALARM_REQUEST_CODE
import com.el3asas.regym.utils.Pref
import com.el3asas.regym.utils.startAlarm
import org.koin.java.KoinJavaComponent.inject
import timber.log.Timber

class DeviceOpenReceiver : BroadcastReceiver() {
    private val pref by inject<Pref>(Pref::class.java)
    override fun onReceive(p0: Context, p1: Intent) {
        if (Intent.ACTION_BOOT_COMPLETED == p1.action) {
            if (pref.getBooleanV(pref.startPlanKey)) {
                startAlarm(
                    p0, pref.getLngV(pref.planEndTimeKey),
                    PLAN_ALARM_REQUEST_CODE
                )
            }

            if (pref.getBooleanV(pref.lngPlanStartedKey)) {
                val lngPlanStarted = pref.getBooleanV(pref.lngPlanStartedKey)
                if (lngPlanStarted) {
                    val lngPlanHour = pref.getIntV(pref.lngPlanHourKey)
                    val lngPlanMinute = pref.getIntV(pref.lngPlanMinuteKey)
                    val lngPlanAm_Bm = pref.getIntV(pref.lngPlanAm_BmKey)
                    val lngPlanEndTime = pref.getLngV(pref.lngPlanEndTimeKey)
                    val lngPlanStartTime = pref.getLngV(pref.lngPlanStartTimeKey)

                    val longPlans = LongPlans()
                    longPlans.init(p0, pref)

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
        }
    }
}