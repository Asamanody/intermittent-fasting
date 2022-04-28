package com.el3asas.regym.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager.widget.PagerAdapter
import com.el3asas.regym.R

class ImagesAdapter(private val context: Context) : PagerAdapter() {
    private val lst_images = intArrayOf(
        R.drawable.slomab,
        R.drawable.weight,
        R.drawable.plan,
        R.drawable.food,
        R.drawable.afterseam,
        R.drawable.last
    )
    private val lst_titles = intArrayOf(
        R.string.app_name,
        R.string.weghtt,
        R.string.seam,
        R.string.so3ratS,
        R.string.afterSyamt,
        R.string.goal
    )
    private val lst_description = intArrayOf(
        R.string.openInfo,
        R.string.weeghhht,
        R.string.seami,
        R.string.so3ratSi,
        R.string.afterSyam,
        R.string.goall
    )

    override fun getCount(): Int {
        return lst_titles.size
    }

    override fun isViewFromObject(view: View, o: Any): Boolean {
        return view === o
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as ConstraintLayout)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var view: View? = null
        if (inflater != null) {
            view = inflater.inflate(R.layout.one_item_slider, container, false)
        }
        assert(view != null)
        val info = view!!.findViewById<TextView>(R.id.infoTitle)
        val img = view.findViewById<ImageView>(R.id.img)
        val description = view.findViewById<TextView>(R.id.description)
        info.setText(lst_titles[position])
        img.setBackgroundResource(lst_images[position])
        description.setText(lst_description[position])
        container.addView(view)
        return view
    }
}