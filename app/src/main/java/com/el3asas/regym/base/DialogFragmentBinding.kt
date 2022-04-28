package com.el3asas.regym.base

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment

open class DialogFragmentBinding : DialogFragment() {
    protected inline fun <reified T : ViewDataBinding>
            binding(
        inflater: LayoutInflater,
        layout: Int,
        parent: ViewGroup?,
        attach: Boolean
    ) = DataBindingUtil.inflate<T>(inflater, layout, parent, attach)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }
}