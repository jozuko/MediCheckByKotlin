package com.studiojozu.medicheck.infrastructure.persistence.converter

import android.arch.persistence.room.TypeConverter
import com.studiojozu.medicheck.domain.model.setting.RemindIntervalType
import com.studiojozu.medicheck.domain.model.setting.RemindTimeoutType
import com.studiojozu.medicheck.domain.model.setting.UseReminderType

class SqliteSettingConverters {
    @TypeConverter
    fun toUseReminderType(value: Boolean) = UseReminderType(value)

    @TypeConverter
    fun toBoolean(value: UseReminderType) = value.isTrue

    @TypeConverter
    fun toRemindIntervalType(value: Int) = RemindIntervalType(value)

    @TypeConverter
    fun toInt(value: RemindIntervalType) = value.dbValue

    @TypeConverter
    fun toRemindTimeoutType(value: Int) = RemindTimeoutType(value)

    @TypeConverter
    fun toInt(value: RemindTimeoutType) = value.dbValue
}