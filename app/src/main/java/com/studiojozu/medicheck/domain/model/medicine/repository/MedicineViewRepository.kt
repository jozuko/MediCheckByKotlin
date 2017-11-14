package com.studiojozu.medicheck.domain.model.medicine.repository

import com.studiojozu.medicheck.domain.model.medicine.Medicine
import com.studiojozu.medicheck.domain.model.medicine.MedicineIdType
import com.studiojozu.medicheck.infrastructure.persistence.dao.*
import com.studiojozu.medicheck.infrastructure.persistence.entity.SqliteMedicine
import com.studiojozu.medicheck.infrastructure.persistence.entity.SqliteMedicineUnit

class MedicineViewRepository(private val sqliteMedicineRepository: SqliteMedicineRepository,
                             private val sqliteMedicineUnitRepository: SqliteMedicineUnitRepository,
                             private val sqliteMedicineMedicineUnitRepository: SqliteMedicineMedicineUnitRepository,
                             private val sqlitePersonMediRelationRepository: SqlitePersonMediRelationRepository,
                             private val sqliteMediTimeRelationRepository: SqliteMediTimeRelationRepository,
                             private val sqliteScheduleRepository: SqliteScheduleRepository) {

    fun findAllNoTimetable(): List<Medicine> =
            sqliteMedicineMedicineUnitRepository.findAll().map { it.toMedicine() }

    fun findByMedicineId(medicineId: MedicineIdType): Medicine? {
        val sqliteMedicine = sqliteMedicineMedicineUnitRepository.findByMedicineId(medicineId.dbValue) ?: return null
        return sqliteMedicine.toMedicine()
    }

    fun insert(medicine: Medicine) {
        sqliteMedicineRepository.insert(sqliteMedicine = SqliteMedicine.build { mMedicine = medicine })
        sqliteMedicineUnitRepository.insert(sqliteMedicineUnit = SqliteMedicineUnit.build { mMedicineUnit = medicine.mMedicineUnit })
    }

    fun delete(medicine: Medicine) =
            sqliteMedicineRepository.deleteMedicine(
                    sqliteMedicine = SqliteMedicine.build { mMedicine = medicine },
                    sqlitePersonMediRelationRepository = sqlitePersonMediRelationRepository,
                    sqliteMediTimeRelationRepository = sqliteMediTimeRelationRepository,
                    sqliteScheduleRepository = sqliteScheduleRepository)
}
