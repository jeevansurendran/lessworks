package com.jeevan.utils

import java.text.SimpleDateFormat
import java.time.Instant
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAccessor
import java.util.*


object Formatter {

    fun formatDuration(date: Date): String {
        val formatter = SimpleDateFormat("E, dd MMM, hh:mm a", Locale.getDefault())
        return formatter.format(date)
    }

    fun formatDurationISO(date: String): String {
        val isoFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        isoFormatter.timeZone = TimeZone.getTimeZone("GMT")
        val parsedDate = isoFormatter.parse(date)
        val formatter = SimpleDateFormat("E, dd MMM, hh:mm a", Locale.getDefault())
        return formatter.format(parsedDate)
    }

    fun formatTimeISO(date: String): String {
        val isoFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        isoFormatter.timeZone = TimeZone.getTimeZone("GMT")
        val parsedDate = isoFormatter.parse(date)
        val formatter = SimpleDateFormat("hh:mm a", Locale.getDefault())
        return formatter.format(parsedDate)
    }

    fun isExpiredDate(date: String): Boolean {
        val ta: TemporalAccessor =
            DateTimeFormatter.ISO_INSTANT.parse(date)
        val i: Instant = Instant.from(ta)
        val d = Date.from(i)
        return d.after(Date())
    }
}