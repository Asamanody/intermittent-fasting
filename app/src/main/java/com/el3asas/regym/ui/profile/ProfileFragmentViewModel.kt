package com.el3asas.regym.ui.profile

import android.annotation.SuppressLint
import android.os.Handler
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.os.bundleOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import com.el3asas.regym.R
import com.el3asas.regym.base.BaseViewModel
import com.el3asas.regym.db.models.ProfileInfo
import com.el3asas.regym.helpers.colSo3rat
import com.el3asas.regym.utils.Pref
import com.el3asas.regym.utils.customSnackBar
import com.el3asas.regym.utils.navigate
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.material.internal.ContextUtils.getActivity
import kotlinx.coroutines.launch

class ProfileFragmentViewModel(val pref: Pref, private val repository: ProfileRepository) :
    BaseViewModel() {
    init {
        getInfo()
    }

    val ageImg = R.drawable.age
    val weightImg = R.drawable.weight
    val heightImg = R.drawable.height
    val effortImg = R.drawable.effort
    val genderImg = R.drawable.gender

    val ageTitle = "العمر"
    val weightTitle = "الوزن"
    val heightTitle = "الطول"
    val genderTitle = "النوع"
    val effortTitle = "المجهود"

    val ageVal = MutableLiveData("25")
    val heightVal = MutableLiveData("170")
    val weightVal = MutableLiveData("65")
    val genderVal = MutableLiveData("ذكر")
    val effortVal = MutableLiveData("ليس مرهقا")
    val bodyStatusT = MutableLiveData("")
    val bodyStatus = MutableLiveData("")
    val adRequest = AdRequest.Builder().build()
    var mInterstitialAd: InterstitialAd? = null

    fun openLongPlanDialog(v: View) {
        InterstitialAd.load(v.context, v.context.getString(R.string.inter), adRequest,
            object : InterstitialAdLoadCallback() {
                @SuppressLint("RestrictedApi")
                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    mInterstitialAd = interstitialAd
                    getActivity(v.context)?.let { mInterstitialAd!!.show(it) }
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    mInterstitialAd = null
                }
            })
        if (v.findNavController().currentDestination?.id == R.id.navigation_profile) {
            if (pref.getBooleanV(pref.lngPlanStartedKey)) {
                val profileFragmentDirections =
                    ProfileFragmentDirections.actionNavigationProfileToReservedLongPlanDetailsFragment()
                navigate(v, profileFragmentDirections)
            } else {
                val profileFragmentDirections =
                    ProfileFragmentDirections.actionNavigationProfileToLongPlanSheetFragment()
                navigate(v, profileFragmentDirections)
            }

            InterstitialAd.load(v.context, v.context.getString(R.string.inter), adRequest,
                object : InterstitialAdLoadCallback() {
                    @SuppressLint("RestrictedApi")
                    override fun onAdLoaded(interstitialAd: InterstitialAd) {
                        mInterstitialAd = interstitialAd
                        getActivity(v.context)?.let { mInterstitialAd?.show(it) }
                    }

                    override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                        mInterstitialAd = null
                    }
                })
        }
    }


    fun getInfo() {
        viewModelScope.launch {
            repository.getInfoData(
                0,
                { msg: String, img: Int ->
                },
                { loading: Boolean -> },
                { info ->
                    ageVal.postValue(info.age.toString())
                    weightVal.postValue(info.weight.toString())
                    heightVal.postValue(info.height.toString())
                    effortVal.postValue(info.effort)
                    genderVal.postValue(info.gender)
                    bodyStatusT.postValue(info.bodyStatus.toString())
                    bodyStatus.postValue(stringForCalories(info.bodyStatus))
                }
            )
        }
    }

    private fun stringForCalories(cal: Int) =
        "يحتاج جسدك ل$cal لابقاء جسدك كما هو عليه الان يجب تقليل سعراتك الحراريه الى ${(cal - 500)} "

    fun clickCalcCalories(v: View, card: CardView, arrow: ImageView) {
        InterstitialAd.load(v.context, v.context.getString(R.string.inter), adRequest,
            object : InterstitialAdLoadCallback() {
                @SuppressLint("RestrictedApi")
                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    mInterstitialAd = interstitialAd
                    getActivity(v.context)?.let { mInterstitialAd!!.show(it) }
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    mInterstitialAd = null
                }
            })

        val value = calcSo3rat(v)
        bodyStatus.postValue(stringForCalories(value))
        bodyStatusT.postValue(value.toString())

        arrow.animate().rotation(0f)
        card.visibility = View.VISIBLE

        Handler().postDelayed({
            arrow.animate().rotation(180f)
            card.visibility = View.GONE
        }, 120000)
    }

    fun openAgeCard(v: View) {
        if (v.findNavController().currentDestination?.id == R.id.navigation_profile) {
            val profileFragmentDirections =
                ProfileFragmentDirections.actionNavigationProfileToAgeDialog()
            profileFragmentDirections.arguments.putAll(
                bundleOf(
                    "age" to (ageVal.value?.toInt() ?: 25)
                )
            )
            navigate(v, profileFragmentDirections)
        }
    }

    fun openWeightCard(v: View) {

        if (v.findNavController().currentDestination?.id == R.id.navigation_profile) {
            val profileFragmentDirections =
                ProfileFragmentDirections.actionNavigationProfileToWeightDialog()
            profileFragmentDirections.arguments.putAll(
                bundleOf(
                    "weight" to (weightVal.value?.toInt()
                        ?: 75)
                )
            )
            navigate(v, profileFragmentDirections)
        }
    }

    fun openHeightCard(v: View) {
        if (v.findNavController().currentDestination?.id == R.id.navigation_profile) {
            val profileFragmentDirections =
                ProfileFragmentDirections.actionNavigationProfileToHeightDialog()
            profileFragmentDirections.arguments.putAll(
                bundleOf(
                    "height" to (heightVal.value?.toInt()
                        ?: 175)
                )
            )

            navigate(v, profileFragmentDirections)
        }
    }

    fun openEffortCard(v: View) {
        if (v.findNavController().currentDestination?.id == R.id.navigation_profile) {
            val profileFragmentDirections =
                ProfileFragmentDirections.actionNavigationProfileToEffortDialog()
            profileFragmentDirections.arguments.putAll(bundleOf("effort" to effortVal.value))
            navigate(v, profileFragmentDirections)
        }
    }

    fun openGenderCard(v: View) {

        if (v.findNavController().currentDestination?.id == R.id.navigation_profile) {
            val profileFragmentDirections =
                ProfileFragmentDirections.actionNavigationProfileToGenderDialog()
            profileFragmentDirections.arguments.putAll(bundleOf("gender" to genderVal.value))

            navigate(v, profileFragmentDirections)
        }
    }

    private fun calcSo3rat(v: View): Int {
        val age: Int
        val height: Int
        val weight: Int
        val effort: String
        val gender: String

        try {
            age = arabicToDecimal(ageVal.value!!.toString()).toInt()
            height = arabicToDecimal(heightVal.value!!.toString()).toInt()
            weight = arabicToDecimal(weightVal.value!!.toString()).toInt()
            effort = effortVal.value!!
            gender = genderVal.value!!
        } catch (e: Exception) {
            Toast.makeText(v.context, "تاكد من ادخال جميع القيم", Toast.LENGTH_SHORT)
                .show()
            return 0
        }
        var bodyStatus = 0
        var i = 1
        when (effort) {
            "ليس مرهقا" -> i = 1
            "مرهقا" -> i = 2
            "مرهقا جدا" -> i = 3
        }
        val colSo3rat = colSo3rat()
        if (gender == v.context.getString(R.string.male)) {
            bodyStatus = colSo3rat.setBodyStatusForMens(i, weight, height, age)
        } else if (gender == v.context.getString(R.string.female)) {
            bodyStatus = colSo3rat.setBodyStatusForWomans(i, weight, height, age)
        }

        viewModelScope.launch {
            if (getCount() == 0) {
                insertToDB(
                    v, ProfileInfo(
                        0,
                        age,
                        weight,
                        gender,
                        height,
                        effort,
                        bodyStatus
                    )
                )
            } else {

                if (v.findNavController().currentDestination?.id == R.id.navigation_profile) {
                    val navToSav =
                        ProfileFragmentDirections.actionNavigationProfileToSaveNewDataDialog()
                    navToSav.arguments.putAll(
                        bundleOf("bodyStatus" to stringForCalories(bodyStatus))
                    )
                    navigate(v, navToSav)
                }
            }
        }

        return bodyStatus
    }

    fun insertToDB(v: View, profileInfo: ProfileInfo) {
        viewModelScope.launch {
            repository.insertInfo(profileInfo,
                onLoading = {
                    isLoading.postValue(true)
                },
                onError = { msg: String, res: Int ->
                    customSnackBar(v, msg, res) {}
                },
                onSuccess = {
                    isLoading.postValue(false)
                }
            )
        }
    }

    private suspend fun getCount(): Int {
        return repository.getCount()
    }

    private fun arabicToDecimal(number: String): String {
        var result = ""
        var en = '0'
        for (ch in number) {
            en = ch
            when (ch) {
                '۰' -> en = '0'
                '۱' -> en = '1'
                '۲' -> en = '2'
                '۳' -> en = '3'
                '۴' -> en = '4'
                '۵' -> en = '5'
                '۶' -> en = '6'
                '۷' -> en = '7'
                '۸' -> en = '8'
                '۹' -> en = '9'
            }
            result = "${result}$en"
        }
        return result
    }

}