package com.el3asas.regym.ui.profile.long_plans

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import com.el3asas.regym.R
import com.el3asas.regym.base.SheetDialogBinding
import com.el3asas.regym.databinding.ReservedLongPlanDetailsFragmentBinding
import com.el3asas.regym.helpers.LongPlans
import com.el3asas.regym.ui.profile.ProfileFragment.Companion.progressClickListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class ReservedLongPlanDetailsFragment : SheetDialogBinding() {

    private val viewModel by viewModel<ReservedLongPlanDetailsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding<ReservedLongPlanDetailsFragmentBinding>(
            inflater,
            R.layout.reserved_long_plan_details_fragment,
            container,
            false
        ).apply {
            viewModel = this@ReservedLongPlanDetailsFragment.viewModel
            lifecycleOwner = this@ReservedLongPlanDetailsFragment
            executePendingBindings()
            setupViews()

            var status: Boolean
            for (i in 0..6) {
                status =
                    this@ReservedLongPlanDetailsFragment.viewModel.pref.getBooleanV("day$i")

                setupSelectedDaysViews(i, status)
            }
        }.root
    }

    private fun ReservedLongPlanDetailsFragmentBinding.setupSelectedDaysViews(
        i: Int,
        status: Boolean
    ) {
        when (i) {
            0 -> {
                handleStatus(specifiedDays.sat, status)
            }
            1 -> {
                handleStatus(specifiedDays.sun, status)
            }
            2 -> {
                handleStatus(specifiedDays.mon, status)
            }
            3 -> {
                handleStatus(specifiedDays.tue, status)
            }
            4 -> {
                handleStatus(specifiedDays.wed, status)
            }
            5 -> {
                handleStatus(specifiedDays.thu, status)
            }
            6 -> {
                handleStatus(specifiedDays.fri, status)
            }
        }
    }

    private fun handleStatus(v: View, b: Boolean) {
        if (b)
            v.background = AppCompatResources.getDrawable(
                requireActivity(),
                R.drawable.selected_calender_item_background
            ) else v.background =
            AppCompatResources.getDrawable(requireActivity(), R.drawable.calender_item_background)
    }

    private fun ReservedLongPlanDetailsFragmentBinding.setupViews() {
        cancelLongPlan.setOnClickListener {
            val longPlan = LongPlans()
            longPlan.init(requireContext(), viewModel?.pref!!)
            longPlan.stopPlan()
            dismiss()
        }

        showLongPlan.setOnClickListener {
            dismiss()
            progressClickListener.postValue(true)
        }
    }

}