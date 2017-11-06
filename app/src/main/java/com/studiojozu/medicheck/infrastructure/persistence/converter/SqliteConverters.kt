package com.studiojozu.medicheck.infrastructure.persistence.converter

import android.arch.persistence.room.TypeConverter
import java.util.*

class SqliteConverters {

    @TypeConverter
    fun fromTimestamp(value: Long): Calendar {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = value
        return calendar
    }

    @TypeConverter
    fun toTimestamp(value: Calendar): Long = value.timeInMillis
}