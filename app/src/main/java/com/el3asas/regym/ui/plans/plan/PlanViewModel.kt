package com.el3asas.regym.ui.plans.plan

import android.view.View
import android.widget.Button
import androidx.constraintlayout.widget.Group
import androidx.lifecycle.MutableLiveData
import com.el3asas.regym.base.BaseViewModel
import com.el3asas.regym.helpers.DiffLeftTime
import com.el3asas.regym.utils.*
import java.util.*

class PlanViewModel(private val pref: Pref) : BaseViewModel() {

    private val isStarted = pref.getBooleanV(pref.startPlanKey)
    private val time = pref.getIntV(pref.startedPlanTimeKey)
    var myTime = 6
    var planTitle = MutableLiveData("")
    val diffLeftTime = DiffLeftTime()
    val from = MutableLiveData(pref.getStringV(pref.planFromKey))
    val to = MutableLiveData(pref.getStringV(pref.planToKey))
    private lateinit var startPlanListener: StartPlanListener

    fun setupStartEndPlanListener(listener: StartPlanListener) {
        startPlanListener = listener
    }

    fun checkIfStartedPlan(t: Int) =
        if (!isStarted) {
            false
        } else {
            setupProgress(pref.getLngV(pref.planStartTimeKey))
            time == t
        }

    fun startPlanClickListener(
        v: Group,
        sB: Button
    ) {
        val startC = Calendar.getInstance()
        val endC = Calendar.getInstance()
        endC.add(Calendar.HOUR, myTime)

        setupProgress(startC.timeInMillis)
        v.visibility = View.VISIBLE
        sB.visibility = View.GONE

        startPlan(pref, myTime, startC, endC)
        startPlanListener.onStartPlan()
    }

    fun stopPlanClickListener(v: Group, sB: Button) {
        v.visibility = View.GONE
        sB.visibility = View.VISIBLE
        diffLeftTime.diff = 0
        endPlan(pref)
        //endWaterAlarm(v.context, WATER_ALARM_REQUEST_CODE)
        startPlanListener.onStopPlan()
    }

    private fun setupProgress(start: Long) {
        val current = Calendar.getInstance()
        diffLeftTime.diff = myTime * 1000 * 60 * 60L
        diffLeftTime.letfTime = current.timeInMillis - start
    }

    interface StartPlanListener {
        fun onStartPlan()

        fun onStopPlan()
    }
}