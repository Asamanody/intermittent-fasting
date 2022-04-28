package com.el3asas.regym.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.transition.TransitionInflater

open class FragmentBinding : Fragment() {
    protected inline fun <reified T : ViewDataBinding> binding(
        inflater: LayoutInflater,
        @LayoutRes resId: Int,
        container: ViewGroup?,
        attach: Boolean
    ): T = DataBindingUtil.inflate(inflater, resId, container, attach)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)
        sharedElementReturnTransition =
            TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)
    }
}