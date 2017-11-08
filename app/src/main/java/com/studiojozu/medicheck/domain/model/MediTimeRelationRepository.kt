package com.studiojozu.medicheck.domain.model

import com.studiojozu.medicheck.domain.model.medicine.IsOneShotType
import com.studiojozu.medicheck.domain.model.medicine.MedicineIdType
import com.studiojozu.medicheck.domain.model.medicine.MedicineTimetableList
import com.studiojozu.medicheck.domain.model.setting.TimetableIdType
import com.studiojozu.medicheck.infrastructure.persistence.dao.SqliteMediTimeRelationRepository
import com.studiojozu.medicheck.infrastructure.persistence.entity.SqliteMediTimeRelation

class MediTimeRelationRepository(private var sqliteMediTimeRelationRepository: SqliteMediTimeRelationRepository) {

    fun findTimetableByMedicineId(medicineIdType: MedicineIdType) =
            sqliteMediTimeRelationRepository.findTimetableByMedicineId(medicineId = medicineIdType.dbValue).map { it.toTimetable() }

    fun deleteByMedicineId(medicineIdType: MedicineIdType) {
        sqliteMediTimeRelationRepository.deleteByMedicineId(medicineId = medicineIdType.dbValue)
    }

    fun insertTimetable(medicineId: MedicineIdType, timetableList: MedicineTimetableList) {
        timetableList.forEach { it ->
            sqliteMediTimeRelationRepository.insert(SqliteMediTimeRelation.build {
                mMedicineId = medicineId
                mTimetableId = it.mTimetableId
                mIsOneShot = IsOneShotType(false)
            })
        }
    }

    fun insertOneShot(medicineId: MedicineIdType) {
        sqliteMediTimeRelationRepository.insert(SqliteMediTimeRelation.build {
            mMedicineId = medicineId
            mTimetableId = TimetableIdType("")
            mIsOneShot = IsOneShotType(true)
        })
    }
}
