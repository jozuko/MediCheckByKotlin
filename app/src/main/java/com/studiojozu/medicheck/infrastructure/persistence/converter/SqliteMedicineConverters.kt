package com.studiojozu.medicheck.infrastructure.persistence.converter

import android.arch.persistence.room.TypeConverter
import com.studiojozu.medicheck.domain.model.medicine.*

class SqliteMedicineConverters {
    @TypeConverter
    fun toMedicineIdType(value: String) = MedicineIdType(value)

    @TypeConverter
    fun toString(value: MedicineIdType) = value.dbValue

    @TypeConverter
    fun toMedicineNameType(value: String) = MedicineNameType(value)

    @TypeConverter
    fun toString(value: MedicineNameType) = value.dbValue

    @TypeConverter
    fun toMedicineTakeNumberType(value: String) = MedicineTakeNumberType(value)

    @TypeConverter
    fun toString(value: MedicineTakeNumberType) = value.dbValue

    @TypeConverter
    fun toMedicineDateNumberType(value: Int) = MedicineDateNumberType(value)

    @TypeConverter
    fun toInt(value: MedicineDateNumberType) = value.dbValue.toInt()

    @TypeConverter
    fun toMedicineStartDatetimeType(value: Long) = MedicineStartDatetimeType(value)

    @TypeConverter
    fun toLong(value: MedicineStartDatetimeType) = value.dbValue

    @TypeConverter
    fun toMedicineIntervalType(value: Int) = MedicineIntervalType(value)

    @TypeConverter
    fun toInt(value: MedicineIntervalType) = value.dbValue.toInt()

    @TypeConverter
    fun toMedicineIntervalModeType(value: Int) = MedicineIntervalModeType(value)

    @TypeConverter
    fun toInt(value: MedicineIntervalModeType) = value.dbValue

    @TypeConverter
    fun toMedicinePhotoType(value: String) = MedicinePhotoType(value)

    @TypeConverter
    fun toString(value: MedicinePhotoType) = value.dbValue

    @TypeConverter
    fun toMedicineNeedAlarmType(value: Boolean) = MedicineNeedAlarmType(value)

    @TypeConverter
    fun toBoolean(value: MedicineNeedAlarmType) = value.isTrue

    @TypeConverter
    fun toMedicineDeleteFlagType(value: Boolean) = MedicineDeleteFlagType(value)

    @TypeConverter
    fun toBoolean(value: MedicineDeleteFlagType) = value.isTrue
}