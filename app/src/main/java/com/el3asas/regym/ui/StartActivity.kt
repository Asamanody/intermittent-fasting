package com.el3asas.regym.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.el3asas.regym.R
import com.el3asas.regym.ui.adapter.ImagesAdapter
import com.el3asas.regym.utils.Pref
import org.koin.android.ext.android.inject

class StartActivity : AppCompatActivity() {
    private val pref by inject<Pref>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        val decorView = window.decorView
        val uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN
        decorView.systemUiVisibility = uiOptions
        val viewPager = findViewById<ViewPager>(R.id.imagesScroller)
        val imagesAdapter = ImagesAdapter(this@StartActivity)
        viewPager.adapter = imagesAdapter
        val skip = findViewById<Button>(R.id.exitScroller)

        skip.setOnClickListener { view: View? ->
            pref.isNotFirstTime(true)
            startActivity(Intent(this@StartActivity, MainActivity::class.java))
            finish()
        }
    }
}