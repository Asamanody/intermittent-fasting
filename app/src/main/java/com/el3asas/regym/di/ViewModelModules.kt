package com.el3asas.regym.di

import com.el3asas.regym.ui.calories.CaloriesViewModel
import com.el3asas.regym.ui.plans.PlansViewModel
import com.el3asas.regym.ui.plans.plan.PlanViewModel
import com.el3asas.regym.ui.profile.ProfileFragmentViewModel
import com.el3asas.regym.ui.profile.long_plans.ReservedLongPlanDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val ViewModelModules = module {
    viewModel { ProfileFragmentViewModel(get(), get()) }
    viewModel { PlansViewModel(get()) }
    viewModel { PlanViewModel(get()) }
    viewModel { CaloriesViewModel() }
    viewModel { ReservedLongPlanDetailsViewModel(get()) }
}