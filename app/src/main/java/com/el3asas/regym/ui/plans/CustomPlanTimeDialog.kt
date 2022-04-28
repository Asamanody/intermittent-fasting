package com.el3asas.regym.ui.plans

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.el3asas.regym.R
import com.el3asas.regym.base.SheetDialogBinding
import com.el3asas.regym.utils.setNavigationResult

class CustomPlanTimeDialog : SheetDialogBinding() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding<com.el3asas.regym.databinding.DialogCutomPlanTimeBinding>(
            inflater,
            R.layout.dialog_cutom_plan_time,
            container,
            false
        ).apply {
            go.setOnClickListener {
                setNavigationResult(numberPicker.value, "hours")
                this@CustomPlanTimeDialog.dismiss()
            }
        }.root
    }

}