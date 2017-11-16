package com.studiojozu.medicheck.domain.model.medicine.repository

import com.studiojozu.medicheck.domain.model.medicine.IsOneShotType
import com.studiojozu.medicheck.domain.model.medicine.Medicine
import com.studiojozu.medicheck.domain.model.medicine.MedicineIdType
import com.studiojozu.medicheck.domain.model.person.Person
import com.studiojozu.medicheck.domain.model.schedule.Schedule
import com.studiojozu.medicheck.domain.model.setting.Timetable
import com.studiojozu.medicheck.infrastructure.persistence.dao.SqliteMedicineMedicineUnitRepository
import com.studiojozu.medicheck.infrastructure.persistence.dao.SqliteMedicineRepository
import com.studiojozu.medicheck.infrastructure.persistence.dao.SqliteMedicineUnitRepository
import com.studiojozu.medicheck.infrastructure.persistence.entity.*

class MedicineViewRepository(private val sqliteMedicineRepository: SqliteMedicineRepository,
                             private val sqliteMedicineUnitRepository: SqliteMedicineUnitRepository,
                             private val sqliteMedicineMedicineUnitRepository: SqliteMedicineMedicineUnitRepository) {

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

    fun delete(medicine: Medicine,
               person: Person? = null,
               timetableArray: Array<Timetable> = emptyArray(),
               scheduleArray: Array<Schedule> = emptyArray()) {

        val sqliteMedicine = SqliteMedicine.build { mMedicine = medicine }

        val sqlitePersonMediRelation = person?.let {
            arrayOf(SqlitePersonMediRelation.build {
                mMedicineId = medicine.mMedicineId
                mPersonId = person.mPersonId
            })
        }

        val sqliteMediTimeRelationArray = timetableArray.map {
            SqliteMediTimeRelation.build {
                mMedicineId = medicine.mMedicineId
                mTimetableId = it.mTimetableId
                mIsOneShot = IsOneShotType(false)
            }
        }

        val sqliteScheduleArray = scheduleArray.map { SqliteSchedule.build { mSchedule = it } }

        sqliteMedicineRepository.deleteMedicine(
                sqliteMedicine,
                sqlitePersonMediRelation ?: emptyArray(),
                sqliteMediTimeRelationArray.toTypedArray(),
                sqliteScheduleArray.toTypedArray())
    }
}
