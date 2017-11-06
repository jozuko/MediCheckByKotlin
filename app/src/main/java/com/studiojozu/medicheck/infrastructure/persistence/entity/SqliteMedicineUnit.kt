package com.studiojozu.medicheck.infrastructure.persistence.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "medicine_unit")
class SqliteMedicineUnit(medicineUnitId: String) {
    /** ID  */
    @PrimaryKey
    @ColumnInfo(name = "medicine_unit_id")
    var mMedicineUnitId: String = medicineUnitId

    /** 表示文字列  */
    @ColumnInfo(name = "medicine_unit_value")
    var mMedicineUnitValue: String = ""

    /** 表示順  */
    @ColumnInfo(name = "medicine_unit_display_order")
    var mMedicineUnitDisplayOrder: Long = 0
}
