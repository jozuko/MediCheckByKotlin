package com.studiojozu.medicheck.infrastructure.persistence.converter

import android.arch.persistence.room.TypeConverter
import com.studiojozu.medicheck.domain.model.person.PersonDisplayOrderType
import com.studiojozu.medicheck.domain.model.person.PersonIdType
import com.studiojozu.medicheck.domain.model.person.PersonNameType
import com.studiojozu.medicheck.domain.model.person.PersonPhotoType

class SqlitePersonConverters {
    @TypeConverter
    fun toPersonIdType(value: String) = PersonIdType(value)

    @TypeConverter
    fun toString(value: PersonIdType) = value.dbValue

    @TypeConverter
    fun toPersonNameType(value: String) = PersonNameType(value)

    @TypeConverter
    fun toString(value: PersonNameType) = value.dbValue

    @TypeConverter
    fun toPersonPhotoType(value: String) = PersonPhotoType(value)

    @TypeConverter
    fun toString(value: PersonPhotoType) = value.dbValue

    @TypeConverter
    fun toPersonDisplayOrderType(value: Long) = PersonDisplayOrderType(value)

    @TypeConverter
    fun toLong(value: PersonDisplayOrderType) = value.dbValue
}