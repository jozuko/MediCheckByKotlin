package com.studiojozu.medicheck.infrastructure.persistence.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.studiojozu.medicheck.domain.model.medicine.MedicineUnitDisplayOrderType
import com.studiojozu.medicheck.domain.model.medicine.MedicineUnitIdType
import com.studiojozu.medicheck.domain.model.medicine.MedicineUnitValueType

@Entity(tableName = "medicine_unit")
class SqliteMedicineUnit(medicineUnitId: MedicineUnitIdType) {
    /** ID  */
    @PrimaryKey
    @ColumnInfo(name = "medicine_unit_id")
    var mMedicineUnitId: MedicineUnitIdType = medicineUnitId

    /** 表示文字列  */
    @ColumnInfo(name = "medicine_unit_value")
    var mMedicineUnitValue: MedicineUnitValueType = MedicineUnitValueType()

    /** 表示順  */
    @ColumnInfo(name = "medicine_unit_display_order")
    var mMedicineUnitDisplayOrder: MedicineUnitDisplayOrderType = MedicineUnitDisplayOrderType()
}
