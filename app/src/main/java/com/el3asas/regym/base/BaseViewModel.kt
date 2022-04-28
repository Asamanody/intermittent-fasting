package com.el3asas.regym.base

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.el3asas.regym.utils.clearNavigateStack

open class BaseViewModel : ViewModel() {
    val isLoading = MutableLiveData(true)

    fun onBack(v: View) {
        clearNavigateStack(v, null)
    }
}