package com.el3asas.regym.helpers

import android.content.Context
import com.el3asas.regym.utils.LONG_PLAN_ALARM_REQUEST_CODE
import com.el3asas.regym.utils.Pref
import com.el3asas.regym.utils.endAlarm
import com.el3asas.regym.utils.startAlarm
import java.util.*

class LongPlans {
    private lateinit var context: Context
    private lateinit var pref: Pref
    fun init(context: Context, pref: Pref) {
        this.context = context
        this.pref = pref
    }

    fun getCalenders(
        selected: Array<Boolean>,
        hour: Int,
        minute: Int,
        am_bm: Int,
        start: Long,
        end: Long
    ): Calendar? {
        val endC = Calendar.getInstance()
        endC.timeInMillis = end
        val s = Calendar.getInstance()
        var i: Int = s[Calendar.DAY_OF_WEEK]
        while (i < selected.size) {
            if (selected[i]) {
                val c = Calendar.getInstance()
                c[Calendar.DAY_OF_WEEK] = i
                c[Calendar.MINUTE] = minute
                c[Calendar.SECOND] = 0
                c[Calendar.HOUR_OF_DAY] = hour
                c[Calendar.AM_PM] = am_bm
                s.time.time = start
                if (c.after(s) && c.before(endC)) {0
                    return c
                }
            }
            i++
        }
        for (i1 in selected.indices) {
            if (selected[i1]) {
                val c = Calendar.getInstance()
                c[Calendar.DAY_OF_WEEK] = i1
                c[Calendar.MINUTE] = minute
                c[Calendar.SECOND] = 0
                c[Calendar.HOUR_OF_DAY] = hour
                c[Calendar.AM_PM] = am_bm
                s.time.time = start
                if (c.before(s) && c.before(endC)) {
                    c.add(Calendar.DAY_OF_WEEK, 7)
                }
                if (c.after(Calendar.getInstance()) && c.before(endC)) {
                    return c
                }
            }
        }
        return null
    }

    fun startPlan(calendar: Calendar) {
        startAlarm(context, calendar.timeInMillis, LONG_PLAN_ALARM_REQUEST_CODE)
    }

    fun setupPlan(
        hour: Int,
        minute: Int,
        am_bm: Int,
        start: Long,
        end: Long,
        selectedPlan: Int,
        isWeek: Boolean,
        selectedDays: Array<Boolean>
    ) {
        pref.setLongPlan(true, hour, minute, am_bm, start, end, selectedPlan, isWeek, selectedDays)
    }

    fun stopPlan() {
        endAlarm(context, LONG_PLAN_ALARM_REQUEST_CODE)
        pref.endLongPlan()
    }
}