package com.studiojozu.medicheck.infrastructure.persistence.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import com.studiojozu.medicheck.domain.model.medicine.MedicineIdType
import com.studiojozu.medicheck.domain.model.person.PersonIdType

@Entity(tableName = "person_medi_relation", primaryKeys = arrayOf("medicine_id", "person_id"))
open class SqlitePersonMediRelation(medicineId: MedicineIdType, personId: PersonIdType) {

    /** 薬ID */
    @ColumnInfo(name = "medicine_id")
    var mMedicineId: MedicineIdType = medicineId

    /** 飲む人ID */
    @ColumnInfo(name = "person_id")
    var mPersonId: PersonIdType = personId
}