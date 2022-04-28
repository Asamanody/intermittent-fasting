package com.el3asas.regym.helpers

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.el3asas.regym.R

class NotificationHelper(base: Context?) : ContextWrapper(base) {
    private var channelID: String? = null
    private var channelName: String? = null
    private var contitle: String? = null
    private var conText: String? = null
    private var mManager: NotificationManager? = null
    @RequiresApi(api = Build.VERSION_CODES.O)
    private fun createChannel() {
        if (manager!!.getNotificationChannel(channelName) == null) {
            val channel =
                NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH)
            manager!!.createNotificationChannel(channel)
        }
    }

    private val manager: NotificationManager?
        get() {
            if (mManager == null) {
                mManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            }
            return mManager
        }
    val channelNotification: NotificationCompat.Builder
        get() = NotificationCompat.Builder(applicationContext, channelID!!)
            .setContentTitle(contitle)
            .setContentText(conText)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setSmallIcon(R.drawable.ic_icon88)

    fun setNotification(
        channelID: String?,
        channelName: String?,
        conTitle: String?,
        conText: String?
    ) {
        this.channelID = channelID
        this.channelName = channelName
        contitle = conTitle
        this.conText = conText
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) createChannel()
    }

    fun notifi(i: Int, notification: Notification?) {
        mManager!!.notify(i, notification)
    }
}