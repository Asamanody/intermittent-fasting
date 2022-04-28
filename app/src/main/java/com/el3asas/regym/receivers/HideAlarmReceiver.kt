package com.el3asas.regym.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.el3asas.regym.utils.hideNotification

class HideAlarmReceiver : BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        val id = p1?.extras?.getInt("alarm_id")
        id?.let { p0?.hideNotification(it) }
    }
}