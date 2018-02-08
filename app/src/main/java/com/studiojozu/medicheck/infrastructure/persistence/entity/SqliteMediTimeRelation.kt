package com.studiojozu.medicheck.infrastructure.persistence.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import com.studiojozu.medicheck.domain.model.medicine.MedicineIdType
import com.studiojozu.medicheck.domain.model.medicine.OneShotType
import com.studiojozu.medicheck.domain.model.setting.TimetableIdType

@Entity(tableName = "medi_time_relation", primaryKeys = arrayOf("medicine_id", "timetable_id"))
open class SqliteMediTimeRelation(medicineId: MedicineIdType, timetableId: TimetableIdType) {
    class Builder {
        lateinit var medicineId: MedicineIdType
        lateinit var timetableId: TimetableIdType
        lateinit var oneShot: OneShotType

        fun build(): SqliteMediTimeRelation {
            val sqliteMediTimeRelation = SqliteMediTimeRelation(medicineId = medicineId, timetableId = timetableId)
            sqliteMediTimeRelation.oneShot = oneShot

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
    @Suppress("CanBePrimaryConstructorProperty")
    @ColumnInfo(name = "medicine_id")
    var medicineId: MedicineIdType = medicineId

    /** タイムテーブルID */
    @Suppress("CanBePrimaryConstructorProperty")
    @ColumnInfo(name = "timetable_id")
    var timetableId: TimetableIdType = timetableId

    /** 頓服？ */
    @ColumnInfo(name = "is_one_shot")
    var oneShot: OneShotType = OneShotType()
}