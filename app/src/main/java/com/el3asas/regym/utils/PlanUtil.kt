package com.el3asas.regym.utils

import java.util.*

fun startPlan(pref: Pref, myTime: Int, startC: Calendar, endC: Calendar) {
    pref.setDayPlan(true, myTime, startC.timeInMillis, endC.timeInMillis)
    pref.setStringV(pref.planFromKey, formatTime(startC.time))
    pref.setStringV(pref.planToKey, formatTime(endC.time))
}

fun endPlan(pref: Pref) {
    pref.stopDayPlan()

}