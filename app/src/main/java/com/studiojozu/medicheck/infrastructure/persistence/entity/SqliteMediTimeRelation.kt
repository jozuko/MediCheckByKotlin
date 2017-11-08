package com.studiojozu.medicheck.infrastructure.persistence.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import com.studiojozu.medicheck.domain.model.medicine.IsOneShotType
import com.studiojozu.medicheck.domain.model.medicine.MedicineIdType
import com.studiojozu.medicheck.domain.model.setting.TimetableIdType

@Entity(tableName = "medi_time_relation", primaryKeys = arrayOf("medicine_id", "timetable_id"))
open class SqliteMediTimeRelation(medicineId: MedicineIdType, timetableId: TimetableIdType) {
    class Builder {
        lateinit var mMedicineId: MedicineIdType
        lateinit var mTimetableId: TimetableIdType
        lateinit var mIsOneShot: IsOneShotType

        fun build(): SqliteMediTimeRelation {
            val sqliteMediTimeRelation = SqliteMediTimeRelation(medicineId = mMedicineId, timetableId = mTimetableId)
            sqliteMediTimeRelation.mIsOneShot = mIsOneShot

            return sqliteMediTimeRelation
        }
    }

    companion object {
        fun build(f: Builder.() -> Unit): SqliteMediTimeRelation {
            val builder = Builder()
            builder.f()
            return builder.build()
        }
    }

    /** 薬ID */
    @ColumnInfo(name = "medicine_id")
    var mMedicineId: MedicineIdType = medicineId

    /** タイムテーブルID */
    @ColumnInfo(name = "timetable_id")
    var mTimetableId: TimetableIdType = timetableId

    /** 頓服？ */
    @ColumnInfo(name = "is_one_shot")
    var mIsOneShot: IsOneShotType = IsOneShotType()
}