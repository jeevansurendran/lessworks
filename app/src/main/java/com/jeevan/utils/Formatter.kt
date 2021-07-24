package com.jeevan.utils

import java.text.SimpleDateFormat
import java.util.*

object Formatter {

    fun formatDuration(date: Date): String {
        val formatter = SimpleDateFormat("E, dd MMM, hh:mm a", Locale.getDefault())
        return formatter.format(date)
    }

    fun formatDurationISO(date: String): String {
        val parsedDate =
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault()).parse(date)
        val formatter = SimpleDateFormat("E, dd MMM, hh:mm a", Locale.getDefault())
        return formatter.format(parsedDate)
    }

    fun formatTimeISO(date: String): String {
        val parsedDate =
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault()).parse(date)
        val formatter = SimpleDateFormat("hh:mm a", Locale.getDefault())
        return formatter.format(parsedDate)
    }
}