package com.studiojozu.medicheck.domain.model.medicine.repository

import com.studiojozu.medicheck.domain.model.medicine.Medicine
import com.studiojozu.medicheck.domain.model.medicine.MedicineIdType
import com.studiojozu.medicheck.domain.model.medicine.MedicineTimetableList
import com.studiojozu.medicheck.domain.model.medicine.OneShotType
import com.studiojozu.medicheck.domain.model.setting.Timetable
import com.studiojozu.medicheck.domain.model.setting.TimetableIdType
import com.studiojozu.medicheck.infrastructure.persistence.dao.SqliteMediTimeRelationRepository
import com.studiojozu.medicheck.infrastructure.persistence.entity.SqliteMediTimeRelation

class MediTimeRelationRepository(private var sqliteMediTimeRelationRepository: SqliteMediTimeRelationRepository) {

    fun findTimetableByMedicineId(medicineIdType: MedicineIdType): Array<Timetable> =
            sqliteMediTimeRelationRepository.findTimetableByMedicineId(medicineId = medicineIdType.dbValue).map { it.toTimetable() }.toTypedArray()

    fun findMedicineByTimetableId(timetableId: TimetableIdType): Array<Medicine> =
            sqliteMediTimeRelationRepository.findMedicineByTimetableId(timetableId = timetableId.dbValue).map { it.toMedicine() }.toTypedArray()

    fun deleteByMedicineId(medicineIdType: MedicineIdType) =
            sqliteMediTimeRelationRepository.deleteByMedicineId(medicineId = medicineIdType.dbValue)

    fun insertTimetable(medicineId: MedicineIdType, timetableList: MedicineTimetableList) =
            timetableList.forEach { it ->
                sqliteMediTimeRelationRepository.insert(SqliteMediTimeRelation.build {
                    this.medicineId = medicineId
                    timetableId = it.timetableId
                    oneShot = OneShotType(false)
                })
            }

    fun insertOneShot(medicineId: MedicineIdType) =
            sqliteMediTimeRelationRepository.insert(SqliteMediTimeRelation.build {
                this.medicineId = medicineId
                timetableId = TimetableIdType("")
                oneShot = OneShotType(true)
            })
}
