package com.studiojozu.common.domain.model

import java.util.*

class CalendarNoSecond {

    private var value: Calendar = Calendar.getInstance()

    val calendar: Calendar
        get() = value

    constructor(year: Int = -1, month: Int = -1, dayOfMonth: Int = -1, hourOfDay: Int = -1, minute: Int = -1) {
        value = Calendar.getInstance()
        value.timeInMillis = System.currentTimeMillis()

        if (year > -1)
            value.set(Calendar.YEAR, year)
        if (month > -1)
            value.set(Calendar.MONTH, month)
        if (dayOfMonth > -1)
            value.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        if (hourOfDay > -1)
            value.set(Calendar.HOUR_OF_DAY, hourOfDay)
        if (minute > -1)
            value.set(Calendar.MINUTE, minute)

        value.set(Calendar.SECOND, 0)
        value.set(Calendar.MILLISECOND, 0)
    }

    constructor(millisecond: Long) {
        value = Calendar.getInstance()
        value.timeInMillis = millisecond
        value.set(Calendar.SECOND, 0)
        value.set(Calendar.MILLISECOND, 0)
    }
}