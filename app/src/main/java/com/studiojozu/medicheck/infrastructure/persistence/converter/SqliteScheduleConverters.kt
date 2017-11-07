package com.studiojozu.medicheck.infrastructure.persistence.converter

import android.arch.persistence.room.TypeConverter
import com.studiojozu.medicheck.domain.model.schedule.ScheduleIsTakeType
import com.studiojozu.medicheck.domain.model.schedule.ScheduleNeedAlarmType
import com.studiojozu.medicheck.domain.model.schedule.SchedulePlanDateType
import com.studiojozu.medicheck.domain.model.schedule.ScheduleTookDatetimeType

class SqliteScheduleConverters {
    @TypeConverter
    fun toSchedulePlanDateType(value: Long) = SchedulePlanDateType(value)

    @TypeConverter
    fun toLong(value: SchedulePlanDateType) = value.dbValue

    @TypeConverter
    fun toScheduleNeedAlarmType(value: Boolean) = ScheduleNeedAlarmType(value)

    @TypeConverter
    fun toBoolean(value: ScheduleNeedAlarmType) = value.isTrue

    @TypeConverter
    fun toScheduleIsTakeType(value: Boolean) = ScheduleIsTakeType(value)

    @TypeConverter
    fun toBoolean(value: ScheduleIsTakeType) = value.isTrue

    @TypeConverter
    fun toScheduleTookDatetimeType(value: Long) = ScheduleTookDatetimeType(value)

    @TypeConverter
    fun toLong(value: ScheduleTookDatetimeType) = value.dbValue

}