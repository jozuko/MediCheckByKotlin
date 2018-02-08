package com.studiojozu.medicheck.infrastructure.persistence.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Ignore
import com.studiojozu.medicheck.domain.model.medicine.*

class SqliteMedicineMedicineUnit(medicineId: MedicineIdType) : SqliteMedicine(medicineId) {

    /** 薬単位.表示文字列  */
    @ColumnInfo(name = "medicine_unit_value")
    var medicineUnitValue: MedicineUnitValueType = MedicineUnitValueType()

    /** 薬単位.表示順  */
    @ColumnInfo(name = "medicine_unit_display_order")
    var medicineUnitDisplayOrder: MedicineUnitDisplayOrderType = MedicineUnitDisplayOrderType()

    @Ignore
    fun toMedicine(): Medicine {
        val medicineUnit = MedicineUnit(
                medicineUnitId = medicineUnitId,
                medicineUnitValue = medicineUnitValue,
                medicineUnitDisplayOrder = medicineUnitDisplayOrder)

        return Medicine(
                medicineId = medicineId,
                medicineName = medicineName,
                medicineTakeNumber = medicineTakeNumber,
                medicineDateNumber = medicineDateNumber,
                medicineStartDatetime = medicineStartDatetime,
                medicineInterval = medicineInterval,
                medicineIntervalMode = medicineIntervalMode,
                medicinePhoto = medicinePhoto,
                medicineNeedAlarm = medicineNeedAlarm,
                medicineDeleteFlag = medicineDeleteFlag,
                medicineUnit = medicineUnit
        )
    }
}
