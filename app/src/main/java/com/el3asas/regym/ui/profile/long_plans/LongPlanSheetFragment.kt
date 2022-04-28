package com.el3asas.regym.ui.profile.long_plans

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import com.el3asas.regym.R
import com.el3asas.regym.base.SheetDialogBinding
import com.el3asas.regym.databinding.LongPlanSheetFragmentBinding
import com.el3asas.regym.helpers.LongPlans
import com.el3asas.regym.utils.Pref
import com.el3asas.regym.utils.customSnackBar
import com.shawnlin.numberpicker.NumberPicker
import org.koin.android.ext.android.inject
import java.util.*

class LongPlanSheetFragment : SheetDialogBinding(), View.OnClickListener {
    private var page = 1
    private lateinit var binding: LongPlanSheetFragmentBinding
    private val selected = arrayOf(false, false, false, false, false, false, false)
    private val pref by inject<Pref>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = binding(
            inflater,
            R.layout.long_plan_sheet_fragment,
            container,
            false
        )
        return binding.root
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            binding.next.id -> {
                if (page == 2) {
                    if (isValidData()) {
                        isNext(View.VISIBLE, View.GONE)
                        page++
                    } else {
//                        customSnackBar(
//                            p0,
//                            getString(R.string.confirm_chose_days),
//                            R.drawable.ic_error
//                        ) {}
                        Toast.makeText(
                            p0.context,
                            getString(R.string.confirm_chose_days),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } else {
                    isNext(View.VISIBLE, View.GONE)
                    page++
                }
            }
            binding.back.id -> {
                page--
                isNext(View.GONE, View.VISIBLE)
            }
            binding.finishBtn.id -> {
                val end: Long =
                    if (binding.planLength.group.checkedRadioButtonId == R.id.weekly)
                        System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000L
                    else
                        System.currentTimeMillis() + 30 * 24 * 60 * 60 * 1000L
                val longPlans = LongPlans()
                longPlans.init(requireContext(), pref)
                longPlans.startPlan(
                    longPlans.getCalenders(
                        selected,
                        binding.startPlanTime.hours.value,
                        binding.startPlanTime.minutes.value,
                        binding.startPlanTime.amPm.value,
                        Calendar.getInstance().timeInMillis,
                        end
                    )!!
                )
                longPlans.setupPlan(
                    binding.startPlanTime.hours.value,
                    binding.startPlanTime.minutes.value,
                    binding.startPlanTime.amPm.value,
                    Calendar.getInstance().timeInMillis,
                    end,
                    binding.planTime.value,
                    binding.planLength.group.checkedRadioButtonId == R.id.weekly,
                    selected
                )
                dismiss()
            }
            R.id.sat -> {
                selected[0] = !selected[0]
                selectedListener(p0, selected[0])
            }
            R.id.sun -> {
                selected[1] = !selected[1]
                selectedListener(p0, selected[1])
            }
            R.id.mon -> {
                selected[2] = !selected[2]
                selectedListener(p0, selected[2])
            }
            R.id.tue -> {
                selected[3] = !selected[3]
                selectedListener(p0, selected[3])
            }
            R.id.wed -> {
                selected[4] = !selected[4]
                selectedListener(p0, selected[4])
            }
            R.id.thu -> {
                selected[5] = !selected[5]
                selectedListener(p0, selected[5])
            }
            R.id.fri -> {
                selected[6] = !selected[6]
                selectedListener(p0, selected[6])
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.next.setOnClickListener(this@LongPlanSheetFragment)
        binding.back.setOnClickListener(this@LongPlanSheetFragment)
        binding.finishBtn.setOnClickListener(this@LongPlanSheetFragment)

        binding.specifiedDays.sat.setOnClickListener(this@LongPlanSheetFragment)
        binding.specifiedDays.sun.setOnClickListener(this@LongPlanSheetFragment)
        binding.specifiedDays.mon.setOnClickListener(this@LongPlanSheetFragment)
        binding.specifiedDays.tue.setOnClickListener(this@LongPlanSheetFragment)
        binding.specifiedDays.wed.setOnClickListener(this@LongPlanSheetFragment)
        binding.specifiedDays.thu.setOnClickListener(this@LongPlanSheetFragment)
        binding.specifiedDays.fri.setOnClickListener(this@LongPlanSheetFragment)

        setPicker(
            Calendar.getInstance()[Calendar.HOUR],
            Calendar.getInstance()[Calendar.MINUTE],
            Calendar.getInstance()[Calendar.AM_PM]
        )
    }

    private fun setPicker(hour: Int, min: Int, ap: Int) {
        setNumPickerStyle(binding.startPlanTime.hours)
        setNumPickerStyle(binding.startPlanTime.minutes)
        setNumPickerStyle(binding.startPlanTime.amPm)
        binding.startPlanTime.amPm.displayedValues = arrayOf("AM", "PM")
        binding.startPlanTime.hours.value = hour
        binding.startPlanTime.minutes.value = min
        binding.startPlanTime.amPm.value = ap
    }


    private fun setNumPickerStyle(numberPicker: NumberPicker?) {
        numberPicker!!.dividerColor = ContextCompat.getColor(
            requireActivity(),
            R.color.actv
        )
        numberPicker.setDividerColorResource(R.color.actv)
        numberPicker.textColor = ContextCompat.getColor(
            requireActivity(),
            R.color.inActv
        )
        numberPicker.setTextColorResource(R.color.inActv)
        numberPicker.selectedTextColor = ContextCompat.getColor(
            requireActivity(),
            R.color.actv
        )
        numberPicker.setSelectedTextColorResource(R.color.actv)
    }

    private fun selectedListener(v: View?, b: Boolean) {
        if (b) v!!.background = AppCompatResources.getDrawable(
            requireActivity(),
            R.drawable.selected_calender_item_background
        ) else v!!.background =
            AppCompatResources.getDrawable(requireActivity(), R.drawable.calender_item_background)
    }

    private fun isValidData(): Boolean {
        for (i in selected) {
            if (i)
                return true
        }
        return false
    }

    private fun isNext(v1: Int, v2: Int) {
        when (page) {
            1 -> {
                binding.title.text = getString(R.string.enter_specifid_days)
                binding.back.visibility = v1
                binding.specifiedDays.root.visibility = v1
                binding.planLength.root.visibility = v2
            }
            2 -> {
                binding.title.text = getString(R.string.enter_start_plan_time)
                binding.specifiedDays.root.visibility = v2
                binding.startPlanTime.root.visibility = v1

            }
            3 -> {
                binding.title.text = getString(R.string.enter_plan_time)
                binding.startPlanTime.root.visibility = v2
                binding.planTime.visibility = v1
            }
            4 -> {
                binding.title.text = getString(R.string.finish_setup_lPlan)
                binding.finishBtn.visibility = v1
                binding.finishImg.visibility = v1
                binding.planTime.visibility = v2
                binding.next.visibility = v2
                binding.back.visibility = v2
            }
        }
    }
}