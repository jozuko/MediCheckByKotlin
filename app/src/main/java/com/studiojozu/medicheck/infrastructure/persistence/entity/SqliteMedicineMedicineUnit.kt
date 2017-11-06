package com.studiojozu.medicheck.infrastructure.persistence.entity

import android.arch.persistence.room.ColumnInfo

class SqliteMedicineMedicineUnit(medicineId: String) : SqliteMedicine(medicineId) {

    /** 薬単位.表示文字列  */
    @ColumnInfo(name = "medicine_unit_value")
    var mMedicineUnitValue: String = ""

    /** 薬単位.表示順  */
    @ColumnInfo(name = "medicine_unit_display_order")
    var mMedicineUnitDisplayOrder: Long = 0
}
