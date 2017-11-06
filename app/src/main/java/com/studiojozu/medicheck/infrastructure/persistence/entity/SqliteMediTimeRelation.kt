package com.studiojozu.medicheck.infrastructure.persistence.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity

@Entity(tableName = "medi_time_relation", primaryKeys = arrayOf("medicine_id", "timetable_id"))
open class SqliteMediTimeRelation(medicineId: String) {
    /** 薬ID */
    @ColumnInfo(name = "medicine_id")
    var mMedicineId: String = medicineId

    /** タイムテーブルID */
    @ColumnInfo(name = "timetable_id")
    var mTimetableId: String = ""

    /** 頓服？ */
    @ColumnInfo(name = "is_one_shot")
    var mIsOneShot: Boolean = true
}