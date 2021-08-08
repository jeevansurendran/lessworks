package com.jeevan.utils

import java.util.*

object DateTimeUtils {

    fun setDateTime(date: Date, hour: Int, minute: Int): Date {
        val cal = Calendar.getInstance()
        cal.time = date
        cal.set(Calendar.HOUR_OF_DAY, hour)
        cal.set(Calendar.MINUTE, minute)
        return cal.time
    }
}