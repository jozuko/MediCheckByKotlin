package com.studiojozu.medicheck.domain.model

import com.studiojozu.medicheck.domain.model.medicine.MedicineIdType
import com.studiojozu.medicheck.domain.model.person.Person
import com.studiojozu.medicheck.domain.model.person.PersonIdType
import com.studiojozu.medicheck.infrastructure.persistence.dao.SqlitePersonMediRelationRepository
import com.studiojozu.medicheck.infrastructure.persistence.entity.SqlitePersonMediRelation

class PersonMediRelationRepository(private val sqlitePersonMediRelationRepository: SqlitePersonMediRelationRepository) {

    fun findPersonByMedicineId(medicineIdType: MedicineIdType): Person? {
        val sqlitePerson = sqlitePersonMediRelationRepository.findPersonByMedicineId(medicineIdType.dbValue) ?: return null
        return sqlitePerson.toPerson()
    }

    fun findMedicineByPersonId(personIdType: PersonIdType) =
            sqlitePersonMediRelationRepository.findMedicineByPersonId(personId = personIdType.dbValue).map { it.toMedicine() }

    fun existByPersonIdMedicineId(personIdType: PersonIdType, medicineIdType: MedicineIdType): Boolean {
        val person = findPersonByMedicineId(medicineIdType) ?: return false
        return person.mPersonId == personIdType
    }

    fun deleteByMedicineId(medicineIdType: MedicineIdType) {
        sqlitePersonMediRelationRepository.deleteByMedicineId(medicineId = medicineIdType.dbValue)
    }

    fun insert(personId: PersonIdType, medicineIdType: MedicineIdType) {
        sqlitePersonMediRelationRepository.insert(SqlitePersonMediRelation.build {
            mMedicineId = medicineIdType
            mPersonId = personId
        })
    }
}
