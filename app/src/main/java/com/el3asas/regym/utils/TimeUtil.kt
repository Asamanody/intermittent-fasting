package com.el3asas.regym.utils

import java.text.SimpleDateFormat
import java.util.*

fun formatTime(d: Date): String {
    val simpleDateFormat = SimpleDateFormat("hh:mm aa", Locale.ENGLISH)
    return simpleDateFormat.format(d)
}