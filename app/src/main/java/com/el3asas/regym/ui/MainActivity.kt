package com.el3asas.regym.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import com.el3asas.regym.R
import com.el3asas.regym.databinding.ActivityMain2Binding
import com.el3asas.regym.utils.Pref
import com.el3asas.regym.utils.appLaunched
import com.google.android.gms.ads.MobileAds
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    private val pref by inject<Pref>()

    @SuppressLint("StaticFieldLeak")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (pref.isNotFirstTime()) {
            if (pref.isDarkModeStatus()) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        } else {
            if (resources.configuration.uiMode == UI_MODE_NIGHT_YES && Configuration.UI_MODE_NIGHT_MASK == UI_MODE_NIGHT_YES) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                pref.isDarkModeStatus(true)
            } else {
                pref.isDarkModeStatus(false)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        val binding: ActivityMain2Binding =
            DataBindingUtil.setContentView(this, R.layout.activity_main2)

        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)

        MobileAds.initialize(this)

        if (!pref.isNotFirstTime()) {
            startActivity(Intent(this@MainActivity, StartActivity::class.java))
        }

        AppBarConfiguration(
            setOf(
                R.id.navigation_profile,
                R.id.navigation_plans,
                R.id.navigation_calories
            )
        )

        val navController = findNavController(this, R.id.nav_host_fragment)

        val popupMenu = PopupMenu(this, null)
        popupMenu.inflate(R.menu.bottom_nav_menu)
        val menu = popupMenu.menu
        binding.navView.setupWithNavController(menu, navController)
    }
}