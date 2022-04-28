package com.el3asas.regym.helpers

import android.content.Context
import android.view.View
import com.google.android.ads.nativetemplates.TemplateView
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.nativead.NativeAd

class Ads {
    private var nativeAd: NativeAd? = null
    private var adLoader: AdLoader? = null
    private var plus = ""
    private lateinit var context: Context
    private lateinit var name: String
    private lateinit var templateView: TemplateView
    private lateinit var adName: String
    fun refreshAd(context: Context, name: String, templateView: TemplateView, ad_name: String) {
        this.context = context
        this.name = name
        this.templateView = templateView
        this.adName = ad_name

        adLoader = AdLoader.Builder(context, ad_name)
            .forNativeAd { nativeAd: NativeAd? ->
                templateView.setNativeAd(nativeAd)
                templateView.visibility = View.VISIBLE
                this.nativeAd = nativeAd
            }
            .withAdListener(object : AdListener() {
                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    super.onAdFailedToLoad(loadAdError)
                    reloadAd()
                }

                override fun onAdLoaded() {
                    super.onAdLoaded()
//                    reLoadAd()
                }

            })
            .build()
        with(adLoader) { this?.loadAd(AdRequest.Builder().build()) }
    }

    fun destroyAd() {
        try {
            if (adLoader != null) adLoader = null
            if (nativeAd != null) nativeAd!!.destroy()
//        if (handler != null) {
//            handler!!.removeCallbacks(runnable!!)
//            handler = null
//        }
        } catch (e: Exception) { }
    }

//    private fun reLoadAd() {
//        if (adLoader != null) {
//            handler = Handler(Looper.myLooper()!!)
//            runnable = Runnable { reloadAd() }
//            handler!!.postDelayed(runnable!!, 3000)
//        }
//    }

    private fun reloadAd() {
        try {
            if (adLoader != null) {
                adLoader?.loadAd(AdRequest.Builder().build())
            } else {
                refreshAd(context, name, templateView, adName)
            }
        } catch (ignored: Exception) {
        }
    }
}