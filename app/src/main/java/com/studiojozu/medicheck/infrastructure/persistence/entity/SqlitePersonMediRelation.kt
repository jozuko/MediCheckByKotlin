package com.studiojozu.medicheck.infrastructure.persistence.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity

@Entity(tableName = "person_medi_relation", primaryKeys = arrayOf("medicine_id", "person_id"))
open class SqlitePersonMediRelation(medicineId: String, personId: String) {

    /** 薬ID */
    @ColumnInfo(name = "medicine_id")
    var mMedicineId: String = medicineId

    /** 飲む人ID */
    @ColumnInfo(name = "person_id")
    var mPersonId: String = personId
}