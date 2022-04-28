package com.el3asas.regym.utils

import android.content.Context
import android.content.SharedPreferences

class Pref(name: String, context: Context) {
    val customTimeKey = "customTime"
    val startPlanKey = "planStarted"
    val startedPlanTimeKey = "planTime"
    val planEndTimeKey = "endTime"
    val planStartTimeKey = "startTime"
    val planFromKey = "planFromKey"
    val planToKey = "planToKey"

    val lngPlanStartedKey = "lngPlanStartedKey"
    val lngPlanHourKey = "lngPlanHourKey"
    val lngPlanMinuteKey = "lngPlanMinuteKey"
    val lngPlanAm_BmKey = "lngPlanAm_BmKey"
    val lngPlanEndTimeKey = "lngPlanEndTimeKey"
    val lngPlanStartTimeKey = "lngPlanStartTimeKey"
    val lngPlanSelectedPlanKey = "lngPlanSelectedPlanKey"
    val lngPlanIsWeekKey = "lngIsWeekKey"
    val lngPlanSelectedDays = "lngPlanSelectedDays"
    private val darkModeKey = "darkModeKey"
    private val firstTimeKey = "firstTimeKey"


    fun isNotFirstTime(b: Boolean) {
        val edit = pref.edit()
        edit.putBoolean(firstTimeKey, b)
        edit.apply()
    }

    fun isNotFirstTime(): Boolean {
        return pref.getBoolean(firstTimeKey, false)
    }

    fun isDarkModeStatus(b: Boolean) {
        val edit = pref.edit()
        edit.putBoolean(darkModeKey, b)
        edit.apply()
    }

    fun isDarkModeStatus() = pref.getBoolean(darkModeKey, false)

    private val pref: SharedPreferences = context.getSharedPreferences(name, 0)
    fun setStringV(key: String, value: String) {
        val e = pref.edit()
        e.putString(key, value)
        e.apply()
    }

    fun setIntV(key: String, value: Int) {
        val e = pref.edit()
        e.putInt(key, value)
        e.apply()
    }

    fun setBooleanV(key: String, value: Boolean) {
        val e = pref.edit()
        e.putBoolean(key, value)
        e.apply()
    }

    fun getSetOfString(key: String) =
        pref.getStringSet(key, setOf())

    fun setSetOfString(key: String, set: Set<String>) {
        val editor = pref.edit()

        editor.putStringSet(key, set)
        editor.apply()
    }

    fun getStringV(key: String) = pref.getString(key, "")
    fun getIntV(key: String,int: Int=0
    ) = pref.getInt(key, int)
    fun getLngV(key: String) = pref.getLong(key, 0)
    fun getBooleanV(key: String) = pref.getBoolean(key, false)


    fun setDayPlan(started: Boolean, time: Int, startTime: Long, endTime: Long) {
        val e = pref.edit().apply {
            putBoolean(startPlanKey, started)
            putInt(startedPlanTimeKey, time)
            putLong(planStartTimeKey, startTime)
            putLong(planEndTimeKey, endTime)
        }
        e.apply()
    }

    fun stopDayPlan() {
        val e = pref.edit()
        e.remove(startPlanKey)
        e.remove(startedPlanTimeKey)
        e.remove(planEndTimeKey)
        e.remove(planStartTimeKey)
        e.remove(planFromKey)
        e.remove(planToKey)
        e.apply()
    }

    fun setLongPlan(
        started: Boolean,
        hour: Int,
        minute: Int,
        am_bm: Int,
        startTime: Long,
        endTime: Long,
        selectedPlan: Int,
        isWeek: Boolean,
        selectedDays: Array<Boolean>
    ) {
        val editor = pref.edit()
        editor.putBoolean(lngPlanStartedKey, started)
        editor.putLong(lngPlanStartTimeKey, startTime)
        editor.putInt(lngPlanHourKey, hour)
        editor.putInt(lngPlanMinuteKey, minute)
        editor.putInt(lngPlanAm_BmKey, am_bm)
        editor.putLong(lngPlanEndTimeKey, endTime)
        editor.putInt(lngPlanSelectedPlanKey, selectedPlan)
        editor.putBoolean(lngPlanIsWeekKey, isWeek)
        //editor.putStringSet(lngPlanSelectedDays, selectedDays)
        for (i in selectedDays.indices) {
            editor.putBoolean("day$i", selectedDays[i])
        }
        editor.apply()
    }

    fun endLongPlan() {
        val editor = pref.edit()
        editor.putBoolean(lngPlanStartedKey, false)
        editor.putInt(lngPlanHourKey, 0)
        editor.putInt(lngPlanMinuteKey, 0)
        editor.putLong(lngPlanStartTimeKey, 0)
        editor.putInt(lngPlanAm_BmKey, 0)
        editor.putLong(lngPlanEndTimeKey, 0)
        editor.putInt(lngPlanSelectedPlanKey, 0)
        editor.putBoolean(lngPlanIsWeekKey, false)
        editor.putStringSet(lngPlanSelectedDays, setOf())
        editor.apply()
    }
}