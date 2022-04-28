package com.el3asas.regym.ui.calories

import android.graphics.Bitmap
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.os.bundleOf
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.el3asas.regym.R
import com.el3asas.regym.base.FragmentBinding
import com.el3asas.regym.databinding.FragmentCaloriesBinding
import com.el3asas.regym.helpers.Ads
import com.el3asas.regym.helpers.PrimaryOPJ
import com.el3asas.regym.ui.adapter.PrimaryAdapter
import com.el3asas.regym.utils.navigate
import org.koin.androidx.viewmodel.ext.android.viewModel

class CaloriesFragment : FragmentBinding(), PrimaryAdapter.ItemClickListener {

    private lateinit var ads: Ads
    private lateinit var binding: FragmentCaloriesBinding
    private val calViewModel by viewModel<CaloriesViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)

        sharedElementReturnTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = binding<FragmentCaloriesBinding>(
            inflater,
            R.layout.fragment_calories,
            container,
            false
        ).apply {
            viewModel = this@CaloriesFragment.calViewModel
            lifecycleOwner = this@CaloriesFragment
            executePendingBindings()
            //getListForAdapter()
            parentRecycleView.apply {
                adapter = PrimaryAdapter(
                    this@CaloriesFragment, requireContext(), getListForAdapter()
                )
                postponeEnterTransition()
                viewTreeObserver
                    .addOnPreDrawListener {
                        startPostponedEnterTransition()
                        true
                    }
            }
        }
        return binding.root
    }

    /****
     *
     * replace with bitmap imgs
     */
    private fun getListForAdapter(): ArrayList<PrimaryOPJ> {
        val primaryOPJS = ArrayList<PrimaryOPJ>()
        primaryOPJS.add(
            PrimaryOPJ(
                "السعرات الحراريه للاسماك",
                R.drawable.fish
            )
        )
        primaryOPJS.add(
            PrimaryOPJ(
                "السعرات الحراريه للحوم",
                R.drawable.l7m
            )
        )
        primaryOPJS.add(
            PrimaryOPJ(
                "السعرات الحراريه للحوم المصنعه",
                R.drawable.l7m2
            )
        )
        primaryOPJS.add(
            PrimaryOPJ(
                "السعرات الحراريه للمشروبات والعصائر",
                R.drawable.aser
            )
        )
        primaryOPJS.add(
            PrimaryOPJ(
                "السعرات الحراريه للالبان ومنتجاتها",
                R.drawable.lban2
            )
        )
        primaryOPJS.add(
            PrimaryOPJ(
                "السعرات الحراريه لحلويات", R.drawable.shho
            )
        )
        primaryOPJS.add(
            PrimaryOPJ(
                "السعرات الحراريه للمكسرات",
                R.drawable.mokasa
            )
        )
        primaryOPJS.add(
            PrimaryOPJ(
                "السعرات الحراريه للبقوليات والنشويات",
                R.drawable.paqoliat
            )
        )
        primaryOPJS.add(
            PrimaryOPJ(
                "السعرات الحراريه للدهون",
                R.drawable.dhon
            )
        )
        primaryOPJS.add(
            PrimaryOPJ(
                "السعرات الحراريه للخضراوات",
                R.drawable.khdra
            )
        )
        primaryOPJS.add(
            PrimaryOPJ(
                "السعرات الحراريه للفواكه",
                R.drawable.fruts
            )
        )
        return primaryOPJS
    }

    override fun onItemClick(
        view: View,
        cardView: CardView,
        imageButton: ImageButton,
        text: TextView,
        imgView: ImageView,
        title: String,
        img: Bitmap
    ) {
        val caloriesFragmentDirection =
            CaloriesFragmentDirections.actionNavigationSlideshowToCaloriesForCat()
        caloriesFragmentDirection.arguments.putAll(bundleOf("img" to img, "title" to title))
        val extras = FragmentNavigatorExtras(
            text to "title00",
            imgView to "img00",
            cardView to "card00",
            imageButton to "back00"
        )
        navigate(view, caloriesFragmentDirection, extras)
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