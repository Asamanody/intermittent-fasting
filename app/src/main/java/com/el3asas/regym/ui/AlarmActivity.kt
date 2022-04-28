package com.el3asas.regym.ui

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.el3asas.regym.databinding.ActivityAlarmBinding
import com.el3asas.regym.utils.ALARM_INTENT_PENDING_INTENT
import com.el3asas.regym.utils.hideNotification

class AlarmActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityAlarmBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN or
                    WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                    WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON,
            WindowManager.LayoutParams.FLAG_FULLSCREEN or
                    WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                    WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
        )

        binding.cancelAlarm.setOnClickListener {
            this.hideNotification(ALARM_INTENT_PENDING_INTENT)
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        val decorView = window.decorView
        val uiOptions: Int = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        decorView.systemUiVisibility = uiOptions
    }

}