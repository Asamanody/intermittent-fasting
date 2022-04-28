package com.el3asas.regym.ui.profile.long_plans

import com.el3asas.regym.base.BaseViewModel
import com.el3asas.regym.utils.Pref

class ReservedLongPlanDetailsViewModel(val pref: Pref) : BaseViewModel() {
    private val type =
        if (pref.getBooleanV(pref.lngPlanIsWeekKey))
            "اسبوعيه"
        else
            "شهريه"

    val weekly_monthly = "أنت بالفعل تتبع خطه صيام $type"

    val hours = pref.getIntV(pref.lngPlanSelectedPlanKey)

}