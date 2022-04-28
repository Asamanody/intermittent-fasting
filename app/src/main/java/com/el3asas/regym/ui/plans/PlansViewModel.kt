package com.el3asas.regym.ui.plans

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.el3asas.regym.R
import com.el3asas.regym.base.BaseViewModel
import com.el3asas.regym.utils.Pref
import com.el3asas.regym.utils.navigate
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.material.internal.ContextUtils

class PlansViewModel(private val pref: Pref) : BaseViewModel() {
    val plan4info1 = MutableLiveData("")
    val plan4info2 = MutableLiveData("")
    val plan4Title = MutableLiveData("")
    var plan4time = pref.getIntV(pref.customTimeKey, 1)
    private var mInterstitialAd: InterstitialAd? = null
    private val adRequest = AdRequest.Builder().build()

    init {
        setCustomTimeCardView(pref.getIntV(pref.customTimeKey, 1))
    }

    fun openPlanFragment(v: View, i: Int) {
        InterstitialAd.load(v.context, v.context.getString(R.string.inter), adRequest,
            object : InterstitialAdLoadCallback() {
                @SuppressLint("RestrictedApi")
                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    mInterstitialAd = interstitialAd
                    ContextUtils.getActivity(v.context)?.let { mInterstitialAd!!.show(it) }
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    mInterstitialAd = null
                }
            })

        val plansFragmentDirections =
            PlansFragmentDirections.actionNavigationHomeToPlanFragment()
        val extras =
            FragmentNavigatorExtras(v.findViewById<TextView>(R.id.plan_title) to "planeTitle")
        when (i) {
            1 -> {
                plansFragmentDirections.time = 16
                navigate(v, plansFragmentDirections, extras)
            }
            2 -> {
                plansFragmentDirections.time = 18
                navigate(v, plansFragmentDirections, extras)
            }
            3 -> {
                plansFragmentDirections.time = 20
                navigate(v, plansFragmentDirections, extras)
            }
            4 -> {
                plansFragmentDirections.time = plan4time
                navigate(v, plansFragmentDirections, extras)
            }
        }
    }

    fun openCustomizePlanDialog(v: View, title: TextView) {
        if (v.findNavController().currentDestination?.id == R.id.navigation_plans) {
            val plansFragmentDirections =
                PlansFragmentDirections.actionNavigationHomeToCustomPlanTimeDialog()
            plansFragmentDirections.time = plan4time
            val extras =
                FragmentNavigatorExtras(title to "planeTitle")

            navigate(v, plansFragmentDirections, extras)
        }
    }

    fun setCustomPlanTimeInPref(value: Int) {
        pref.setIntV(pref.customTimeKey, value)
        setCustomTimeCardView(value)
    }

    private fun setCustomTimeCardView(value: Int) {
        plan4Title.postValue("$value-${24 - value}")
        plan4info1.postValue("الصيام لمده $value ساعه")
        plan4info2.postValue("فتره تناول الطعام ${24 - value} ساعات")
    }
}