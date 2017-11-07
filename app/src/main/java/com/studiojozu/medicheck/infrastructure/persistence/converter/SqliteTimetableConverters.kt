package com.studiojozu.medicheck.infrastructure.persistence.converter

import android.arch.persistence.room.TypeConverter
import com.studiojozu.medicheck.domain.model.medicine.IsOneShotType
import com.studiojozu.medicheck.domain.model.setting.TimetableDisplayOrderType
import com.studiojozu.medicheck.domain.model.setting.TimetableIdType
import com.studiojozu.medicheck.domain.model.setting.TimetableNameType
import com.studiojozu.medicheck.domain.model.setting.TimetableTimeType

class SqliteTimetableConverters {
    @TypeConverter
    fun toTimetableIdType(value: String) = TimetableIdType(value)

    @TypeConverter
    fun toString(value: TimetableIdType) = value.dbValue

    @TypeConverter
    fun toTimetableNameType(value: String) = TimetableNameType(value)

    @TypeConverter
    fun toString(value: TimetableNameType) = value.dbValue

    @TypeConverter
    fun toTimetableTimeType(value: Long) = TimetableTimeType(value)

    @TypeConverter
    fun toLong(value: TimetableTimeType) = value.dbValue

    @TypeConverter
    fun toTimetableDisplayOrderType(value: Long) = TimetableDisplayOrderType(value)

    @TypeConverter
    fun toLong(value: TimetableDisplayOrderType) = value.dbValue

    @TypeConverter
    fun toIsOneShot(value: Boolean) = IsOneShotType(value)

    @TypeConverter
    fun toBoolean(value: IsOneShotType) = value.isTrue
}