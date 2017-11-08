package com.studiojozu.medicheck.infrastructure.persistence.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Ignore
import com.studiojozu.medicheck.domain.model.medicine.*

class SqliteMedicineMedicineUnit(medicineId: MedicineIdType) : SqliteMedicine(medicineId) {

    /** 薬単位.表示文字列  */
    @ColumnInfo(name = "medicine_unit_value")
    var mMedicineUnitValue: MedicineUnitValueType = MedicineUnitValueType()

    /** 薬単位.表示順  */
    @ColumnInfo(name = "medicine_unit_display_order")
    var mMedicineUnitDisplayOrder: MedicineUnitDisplayOrderType = MedicineUnitDisplayOrderType()

    @Ignore
    fun toMedicine(): Medicine {
        val medicineUnit = MedicineUnit(
                mMedicineUnitId = mMedicineUnitId,
                mMedicineUnitValue = mMedicineUnitValue,
                mMedicineUnitDisplayOrder = mMedicineUnitDisplayOrder)

        return Medicine(
                mMedicineId = mMedicineId,
                mMedicineName = mMedicineName,
                mMedicineTakeNumber = mMedicineTakeNumber,
                mMedicineDateNumber = mMedicineDateNumber,
                mMedicineStartDatetime = mMedicineStartDatetime,
                mMedicineInterval = mMedicineInterval,
                mMedicineIntervalMode = mMedicineIntervalMode,
                mMedicinePhoto = mMedicinePhoto,
                mMedicineNeedAlarm = mMedicineNeedAlarm,
                mMedicineDeleteFlag = mMedicineDeleteFlag,
                mMedicineUnit = medicineUnit
        )
    }
}
