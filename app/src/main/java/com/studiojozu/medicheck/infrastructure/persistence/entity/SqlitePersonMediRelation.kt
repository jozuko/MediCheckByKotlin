package com.studiojozu.medicheck.infrastructure.persistence.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import com.studiojozu.medicheck.domain.model.medicine.MedicineIdType
import com.studiojozu.medicheck.domain.model.person.PersonIdType

@Entity(tableName = "person_medi_relation", primaryKeys = arrayOf("medicine_id", "person_id"))
open class SqlitePersonMediRelation(medicineId: MedicineIdType, personId: PersonIdType) {
    class Builder {
        lateinit var medicineId: MedicineIdType
        lateinit var personId: PersonIdType

        fun build(): SqlitePersonMediRelation =
                SqlitePersonMediRelation(medicineId = medicineId, personId = personId)

    }

    companion object {
        fun build(f: Builder.() -> Unit): SqlitePersonMediRelation {
            val builder = Builder()
            builder.f()
            return builder.build()
        }
    }

    /** 薬ID */
    @Suppress("CanBePrimaryConstructorProperty")
    @ColumnInfo(name = "medicine_id")
    var medicineId: MedicineIdType = medicineId

    /** 飲む人ID */
    @Suppress("CanBePrimaryConstructorProperty")
    @ColumnInfo(name = "person_id")
    var personId: PersonIdType = personId
}