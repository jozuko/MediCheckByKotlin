package com.studiojozu.medicheck.domain.model.medicine.repository

import com.studiojozu.medicheck.domain.model.medicine.MedicineUnit
import com.studiojozu.medicheck.domain.model.medicine.MedicineUnitIdType
import com.studiojozu.medicheck.infrastructure.persistence.dao.SqliteMedicineUnitRepository
import com.studiojozu.medicheck.infrastructure.persistence.entity.SqliteMedicineUnit

class MedicineUnitRepository(private val sqliteMedicineUnitRepository: SqliteMedicineUnitRepository) {

    fun findAll(): List<MedicineUnit> =
            sqliteMedicineUnitRepository.findAll().map { it.toMedicineUnit() }

    fun findById(medicineUnitIdType: MedicineUnitIdType): MedicineUnit? {
        val sqliteMedicineUnit = sqliteMedicineUnitRepository.findById(medicineUnitId = medicineUnitIdType.dbValue) ?: return null
        return sqliteMedicineUnit.toMedicineUnit()
    }

    fun insert(medicineUnit: MedicineUnit) =
            sqliteMedicineUnitRepository.insert(sqliteMedicineUnit = SqliteMedicineUnit.build { mMedicineUnit = medicineUnit })

    fun delete(medicineUnit: MedicineUnit) =
            sqliteMedicineUnitRepository.delete(sqliteMedicineUnit = SqliteMedicineUnit.build { mMedicineUnit = medicineUnit })
}
