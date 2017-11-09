package com.studiojozu.common.domain.model

import java.util.*

class CalendarNoSecond {

    private var mValue: Calendar = Calendar.getInstance()

    val calendar: Calendar
        get() = mValue

    constructor(year: Int = -1, month: Int = -1, dayOfMonth: Int = -1, hourOfDay: Int = -1, minute: Int = -1) {
        mValue = Calendar.getInstance()
        mValue.timeInMillis = System.currentTimeMillis()

        if (year > -1)
            mValue.set(Calendar.YEAR, year)
        if (month > -1)
            mValue.set(Calendar.MONTH, month)
        if (dayOfMonth > -1)
            mValue.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        if (hourOfDay > -1)
            mValue.set(Calendar.HOUR_OF_DAY, hourOfDay)
        if (minute > -1)
            mValue.set(Calendar.MINUTE, minute)

        mValue.set(Calendar.SECOND, 0)
        mValue.set(Calendar.MILLISECOND, 0)
    }

    constructor(millisecond: Long) {
        mValue = Calendar.getInstance()
        mValue.timeInMillis = millisecond
        mValue.set(Calendar.SECOND, 0)
        mValue.set(Calendar.MILLISECOND, 0)
    }
}