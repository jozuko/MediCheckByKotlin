package com.studiojozu.medicheck.infrastructure.persistence.converter

import android.arch.persistence.room.TypeConverter
import com.studiojozu.medicheck.domain.model.medicine.MedicineUnitDisplayOrderType
import com.studiojozu.medicheck.domain.model.medicine.MedicineUnitIdType
import com.studiojozu.medicheck.domain.model.medicine.MedicineUnitValueType

class SqliteMedicineUnitConverters {
    @TypeConverter
    fun toMedicineUnitIdType(value: String) = MedicineUnitIdType(value)

    @TypeConverter
    fun toString(value: MedicineUnitIdType) = value.dbValue

    @TypeConverter
    fun toMedicineUnitValueType(value: String) = MedicineUnitValueType(value)

    @TypeConverter
    fun toString(value: MedicineUnitValueType) = value.dbValue

    @TypeConverter
    fun toMedicineUnitDisplayOrderType(value: Long) = MedicineUnitDisplayOrderType(value)

    @TypeConverter
    fun toLong(value: MedicineUnitDisplayOrderType) = value.dbValue
}