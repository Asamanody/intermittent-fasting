package com.el3asas.regym.ui.plans.plan

import android.app.NotificationManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.Group
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.el3asas.regym.R
import com.el3asas.regym.base.FragmentBinding
import com.el3asas.regym.databinding.FragmentPlanBinding
import com.el3asas.regym.helpers.Ads
import com.el3asas.regym.utils.*
import com.mikhaellopez.circularprogressbar.CircularProgressBar
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class PlanFragment : FragmentBinding(), PlanViewModel.StartPlanListener {
    private lateinit var ads: Ads
    private val args by navArgs<PlanFragmentArgs>()
    private val planViewModel by viewModel<PlanViewModel>()
    private lateinit var job: Job
    private var isStarted = false
    private lateinit var binding: FragmentPlanBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = binding<FragmentPlanBinding>(inflater, R.layout.fragment_plan, container, false)
            .apply {
                viewModel = this@PlanFragment.planViewModel
                lifecycleOwner = this@PlanFragment
                executePendingBindings()

                planViewModel.myTime = args.time

                if (args.fromReceiver) {
                    val notificationManager =
                        requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                    notificationManager.cancel(LONG_PLAN_NOTIFICATION_ID)
                }

                planViewModel.planTitle.postValue(args.time.toString() + "/" + (24 - args.time))
                when (planViewModel.checkIfStartedPlan(args.time)) {
                    true -> {
                        isStarted = true
                        groupSyam.visibility = View.VISIBLE
                        start.visibility = View.GONE
                        setupTimerUpdate(progressbar, clck, groupSyam, start)
                    }
                    false -> {
                        isStarted = false
                        groupSyam.visibility = View.GONE
                        start.visibility = View.VISIBLE
                    }
                }
                back.setOnClickListener {
                    clearNavigateStack(this@PlanFragment)
                }
            }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        planViewModel.setupStartEndPlanListener(this)
    }

    override fun onResume() {
        super.onResume()
        ads = Ads()
        ads.refreshAd(requireContext(), "show", binding.myTemplate, getString(R.string.smlladg))
    }

    override fun onDestroy() {
        super.onDestroy()
        if (this::ads.isInitialized)
            ads.destroyAd()
    }

    private fun setupTimerUpdate(
        circularProgressBar: CircularProgressBar,
        clock: TextView,
        g: Group,
        startBTN: Button
    ) {
        job = lifecycleScope.launch {
            var s = ""
            while (true) {
                delay(1000)
                planViewModel.diffLeftTime.letfTime += 1000L
                s =
                    timeDownToStr(planViewModel.diffLeftTime.diff - planViewModel.diffLeftTime.letfTime)
                clock.text = s
                if (planViewModel.diffLeftTime.diff <= 0) {
                    job.cancel()
                    planViewModel.stopPlanClickListener(g, startBTN)
                }
                circularProgressBar.progress = getLeftTime(
                    planViewModel.diffLeftTime.diff,
                    planViewModel.diffLeftTime.letfTime
                )
            }
        }
    }

    private fun getLeftTime(all: Long, left: Long): Float {
        return left.toFloat() / all * 100
    }

    private fun timeDownToStr(difference: Long): String {
        val hours = (difference / (1000 * 60 * 60)).toInt()
        val min = (difference - 1000 * 60 * 60 * hours).toInt() / (1000 * 60)
        val second = (difference - (1000 * 60 * 60 * hours + min * 60 * 1000)).toInt() / 1000
        return String.format(
            Locale.ENGLISH,
            "%02d : %02d : %02d",
            if (hours > 0) hours else -hours,
            if (min > 0) min else -min,
            second
        )
    }

    override fun onStartPlan() {
        setupTimerUpdate(binding.progressbar, binding.clck, binding.groupSyam, binding.start)
        val calendar = Calendar.getInstance()
        planViewModel.from.postValue(formatTime(calendar.time))
        calendar.add(Calendar.HOUR, planViewModel.myTime)
        planViewModel.to.postValue(formatTime(calendar.time))
        startAlarm(requireContext(), calendar.timeInMillis, PLAN_ALARM_REQUEST_CODE)
        val waterCalendar = Calendar.getInstance()
        waterCalendar.add(Calendar.MINUTE, 1)
        startWaterAlarm(requireContext(), waterCalendar.timeInMillis)
    }

    override fun onStopPlan() {
        job.cancel()
        endAlarm(requireContext(), PLAN_ALARM_REQUEST_CODE)
        endWaterAlarm(requireContext())
    }
}