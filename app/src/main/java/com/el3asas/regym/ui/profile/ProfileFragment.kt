package com.el3asas.regym.ui.profile

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.MutableLiveData
import com.el3asas.regym.R
import com.el3asas.regym.base.FragmentBinding
import com.el3asas.regym.databinding.ProfileFragmentBinding
import com.el3asas.regym.db.models.ProfileInfo
import com.el3asas.regym.helpers.Ads
import com.el3asas.regym.utils.Pref
import com.el3asas.regym.utils.getNavigationResult
import com.el3asas.regym.utils.navigate
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : FragmentBinding() {
    private lateinit var ads: Ads
    private val profileFragmentViewModel by viewModel<ProfileFragmentViewModel>()
    private lateinit var binding: ProfileFragmentBinding
    private var isDark = false
    private val pref by inject<Pref>()

    companion object {
        val progressClickListener = MutableLiveData(false)
    }

    @SuppressLint("CutPasteId")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = binding<ProfileFragmentBinding>(
            inflater,
            R.layout.profile_fragment,
            container,
            false
        ).apply {
            viewModel = this@ProfileFragment.profileFragmentViewModel
            lifecycleOwner = this@ProfileFragment
            executePendingBindings()

            isDark = pref.isDarkModeStatus()
            if (isDark) {
                dark.setImageResource(R.drawable.ic_sun)
            } else {
                dark.setImageResource(R.drawable.ic_moon__1_)
            }

            getNavigationResult<String>("age")?.observe(this@ProfileFragment.viewLifecycleOwner) {
                profileFragmentViewModel.ageVal.postValue(it)
            }

            getNavigationResult<String>("height")?.observe(
                this@ProfileFragment.viewLifecycleOwner
            ) {
                profileFragmentViewModel.heightVal.postValue(it)
            }
            getNavigationResult<String>("weight")?.observe(
                this@ProfileFragment.viewLifecycleOwner
            ) {
                profileFragmentViewModel.weightVal.postValue(it)
            }
            getNavigationResult<String>("gender")?.observe(
                this@ProfileFragment.viewLifecycleOwner
            ) {
                profileFragmentViewModel.genderVal.postValue(it)
            }
            getNavigationResult<String>("effort")?.observe(
                this@ProfileFragment.viewLifecycleOwner
            ) {
                profileFragmentViewModel.effortVal.postValue(it)
            }

            getNavigationResult<Boolean>("saveOrNot")?.observe(
                this@ProfileFragment.viewLifecycleOwner
            ) {
                if (it) {
                    val age = profileFragmentViewModel.ageVal.value!!
                    val weight = profileFragmentViewModel.weightVal.value!!
                    val gender = profileFragmentViewModel.genderVal.value
                    val height = profileFragmentViewModel.heightVal.value!!
                    val effort = profileFragmentViewModel.effortVal.value
                    val bodyStatus = profileFragmentViewModel.bodyStatusT.value!!

                    profileFragmentViewModel.insertToDB(
                        requireView(), profileInfo = ProfileInfo(
                            0,
                            age.toInt(),
                            weight.toInt(),
                            gender,
                            height.toInt(),
                            effort,
                            bodyStatus.toInt()
                        )
                    )
                } else {
                    try {
                        profileFragmentViewModel.getInfo()
                    } catch (e: Exception) {
                    }
                }
            }

        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.goToInfo.setOnClickListener {
            if (binding.info.visibility == View.GONE) {
                binding.arrow.animate().rotation(0f)
                binding.info.visibility = View.VISIBLE

                InterstitialAd.load(requireContext(),
                    getString(R.string.inter),
                    profileFragmentViewModel.adRequest,
                    object : InterstitialAdLoadCallback() {
                        override fun onAdLoaded(interstitialAd: InterstitialAd) {
                            profileFragmentViewModel.mInterstitialAd = interstitialAd
                            profileFragmentViewModel.mInterstitialAd?.show(requireActivity())
                        }

                        override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                            profileFragmentViewModel.mInterstitialAd = null
                        }
                    })

            } else {
                binding.arrow.animate().rotation(180f)
                binding.info.visibility = View.GONE

                Handler().postDelayed({
                    binding.arrow.animate().rotation(180f)
                    binding.info.visibility = View.GONE
                }, 120000)
            }
        }

        binding.dark.setOnClickListener {
            isDark = if (isDark) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.dark.setImageResource(R.drawable.ic_sun)
                pref.isDarkModeStatus(false)
                false
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.dark.setImageResource(R.drawable.ic_moon__1_)
                pref.isDarkModeStatus(true)
                true
            }
        }
        progressClickListenerHandler()
        ads = Ads()
        ads.refreshAd(requireContext(), "dialog", binding.myTemplate, getString(R.string.smlladg))

    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (this::ads.isInitialized)
            ads.destroyAd()
    }

    private fun progressClickListenerHandler() {
        progressClickListener.observe(viewLifecycleOwner) {
            if (it) {
                val dir = ProfileFragmentDirections.actionPlanFragment()
                dir.time = profileFragmentViewModel.pref.getIntV(pref.lngPlanSelectedPlanKey)
                navigate(requireView(), dir)
                progressClickListener.postValue(false)
            }
        }
    }
}