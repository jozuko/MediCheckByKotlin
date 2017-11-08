package com.studiojozu.medicheck.domain.model.medicine.repository

import com.studiojozu.medicheck.domain.model.medicine.Medicine
import com.studiojozu.medicheck.domain.model.medicine.MedicineIdType
import com.studiojozu.medicheck.infrastructure.persistence.dao.SqliteMedicineMedicineUnitRepository
import com.studiojozu.medicheck.infrastructure.persistence.dao.SqliteMedicineRepository
import com.studiojozu.medicheck.infrastructure.persistence.dao.SqliteMedicineUnitRepository
import com.studiojozu.medicheck.infrastructure.persistence.entity.SqliteMedicine
import com.studiojozu.medicheck.infrastructure.persistence.entity.SqliteMedicineUnit

class MedicineViewRepository(private val sqliteMedicineRepository: SqliteMedicineRepository,
                             private val sqliteMedicineUnitRepository: SqliteMedicineUnitRepository,
                             private val sqliteMedicineMedicineUnitRepository: SqliteMedicineMedicineUnitRepository) {

    fun findAllNoTimetable(): List<Medicine> =
            sqliteMedicineMedicineUnitRepository.findAll().map { it.toMedicine() }

    fun findById(medicineId: MedicineIdType): Medicine? {
        val sqliteMedicine = sqliteMedicineMedicineUnitRepository.findByMedicineId(medicineId.dbValue) ?: return null
        return sqliteMedicine.toMedicine()
    }

    fun insert(medicine: Medicine) {
        sqliteMedicineRepository.insert(sqliteMedicine = SqliteMedicine.build { mMedicine = medicine })
        sqliteMedicineUnitRepository.insert(sqliteMedicineUnit = SqliteMedicineUnit.build { mMedicineUnit = medicine.mMedicineUnit })
    }

    fun delete(medicine: Medicine) {
        sqliteMedicineRepository.delete(SqliteMedicine.build { mMedicine = medicine })
    }
}
