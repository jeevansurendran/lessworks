package com.jeevan.utils

import java.text.SimpleDateFormat
import java.util.*

object Formatter {
    var formatter = SimpleDateFormat("E, dd MMM, hh:mm a", Locale.getDefault())
    fun formatDuration(date: Date): String {
        return formatter.format(date)
    }
}