package com.el3asas.regym.utils

import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat.getColor
import com.el3asas.regym.R
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar


fun customSnackBar(
    v: View,
    message: String,
    iconId: Int,
    onDismiss: () -> Unit
) {
    try {

        val sb: Snackbar = Snackbar.make(v, message, Snackbar.LENGTH_SHORT)

        val sbView = sb.view

        sbView.setBackgroundColor(getColor(v.context, R.color.buttonRedBg))

        val textView = sbView.findViewById<View>(R.id.snackbar_text) as TextView

        textView.setTextColor(getColor(v.context, R.color.white))

        textView.setCompoundDrawablesWithIntrinsicBounds(iconId, 0, 0, 0)

        textView.gravity = Gravity.TOP

        textView.compoundDrawablePadding = 12

        sb.addCallback(object : BaseTransientBottomBar.BaseCallback<Snackbar>() {
            override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                super.onDismissed(transientBottomBar, event)
                onDismiss()
            }
        })
        sb.show()
    } catch (ignored: Exception) {
    }
}