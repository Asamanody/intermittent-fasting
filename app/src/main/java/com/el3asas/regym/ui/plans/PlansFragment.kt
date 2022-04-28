package com.el3asas.regym.ui.plans

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.el3asas.regym.R
import com.el3asas.regym.base.FragmentBinding
import com.el3asas.regym.databinding.FragmentPlansBinding
import com.el3asas.regym.helpers.Ads
import com.el3asas.regym.utils.getNavigationResult
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlansFragment : FragmentBinding() {

    private lateinit var ads: Ads
    private val plansViewModel by viewModel<PlansViewModel>()
    private lateinit var binding: FragmentPlansBinding

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = binding<FragmentPlansBinding>(inflater, R.layout.fragment_plans, container, false)
            .apply {
                viewModel = this@PlansFragment.plansViewModel
                lifecycleOwner = this@PlansFragment
                executePendingBindings()
                getNavigationResult<Int>("hours")?.observe(this@PlansFragment.viewLifecycleOwner) {
                    plansViewModel.plan4time = it
                    plansViewModel.setCustomPlanTimeInPref(it)
                }
            }
        return binding.root
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
}