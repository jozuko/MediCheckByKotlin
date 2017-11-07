package com.studiojozu.medicheck.infrastructure.persistence.entity

import android.arch.persistence.room.ColumnInfo
import com.studiojozu.medicheck.domain.model.medicine.MedicineIdType
import com.studiojozu.medicheck.domain.model.medicine.MedicineUnitDisplayOrderType
import com.studiojozu.medicheck.domain.model.medicine.MedicineUnitValueType

class SqliteMedicineMedicineUnit(medicineId: MedicineIdType) : SqliteMedicine(medicineId) {

    /** 薬単位.表示文字列  */
    @ColumnInfo(name = "medicine_unit_value")
    var mMedicineUnitValue: MedicineUnitValueType = MedicineUnitValueType()

    /** 薬単位.表示順  */
    @ColumnInfo(name = "medicine_unit_display_order")
    var mMedicineUnitDisplayOrder: MedicineUnitDisplayOrderType = MedicineUnitDisplayOrderType()
}
