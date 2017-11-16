package com.studiojozu.medicheck.domain.model.medicine.repository

import com.studiojozu.medicheck.domain.model.medicine.IsOneShotType
import com.studiojozu.medicheck.domain.model.medicine.Medicine
import com.studiojozu.medicheck.domain.model.medicine.MedicineIdType
import com.studiojozu.medicheck.domain.model.person.Person
import com.studiojozu.medicheck.domain.model.schedule.Schedule
import com.studiojozu.medicheck.domain.model.setting.Timetable
import com.studiojozu.medicheck.infrastructure.persistence.dao.SqliteMedicineMedicineUnitRepository
import com.studiojozu.medicheck.infrastructure.persistence.dao.SqliteMedicineRepository
import com.studiojozu.medicheck.infrastructure.persistence.entity.*

class MedicineViewRepository(private val sqliteMedicineRepository: SqliteMedicineRepository,
                             private val sqliteMedicineMedicineUnitRepository: SqliteMedicineMedicineUnitRepository) {

    fun findAllNoTimetable(): List<Medicine> =
            sqliteMedicineMedicineUnitRepository.findAll().map { it.toMedicine() }

    fun findByMedicineId(medicineId: MedicineIdType): Medicine? {
        val sqliteMedicine = sqliteMedicineMedicineUnitRepository.findByMedicineId(medicineId.dbValue) ?: return null
        return sqliteMedicine.toMedicine()
    }

    fun insert(medicine: Medicine,
               person: Person? = null,
               timetableArray: Array<Timetable> = emptyArray(),
               scheduleArray: Array<Schedule> = emptyArray()) {

        val sqliteMedicine = toSqliteMedicine(medicine)
        val sqliteMedicineUnit = toSqliteMedicineUnit(medicine)
        val sqlitePersonMediRelationArray = toSqlitePersonMediRelationArray(medicine, person)
        val sqliteMediTimeRelationArray = toSqliteMediTimeRelationArray(medicine, timetableArray)
        val sqliteScheduleArray = toSqliteScheduleArray(scheduleArray)

        sqliteMedicineRepository.insertMedicine(sqliteMedicine,
                sqliteMedicineUnit,
                sqlitePersonMediRelationArray,
                sqliteMediTimeRelationArray,
                sqliteScheduleArray)
    }

    fun delete(medicine: Medicine,
               person: Person? = null,
               timetableArray: Array<Timetable> = emptyArray(),
               scheduleArray: Array<Schedule> = emptyArray()) {

        val sqliteMedicine = toSqliteMedicine(medicine)
        val sqlitePersonMediRelationArray = toSqlitePersonMediRelationArray(medicine, person)
        val sqliteMediTimeRelationArray = toSqliteMediTimeRelationArray(medicine, timetableArray)
        val sqliteScheduleArray = toSqliteScheduleArray(scheduleArray)

        sqliteMedicineRepository.deleteMedicine(
                sqliteMedicine,
                sqlitePersonMediRelationArray,
                sqliteMediTimeRelationArray,
                sqliteScheduleArray)
    }

    private fun toSqliteMedicine(medicine: Medicine) =
            SqliteMedicine.build { mMedicine = medicine }

    private fun toSqliteMedicineUnit(medicine: Medicine) =
            SqliteMedicineUnit.build { mMedicineUnit = medicine.mMedicineUnit }

    private fun toSqlitePersonMediRelationArray(medicine: Medicine, person: Person?): Array<SqlitePersonMediRelation> {
        person ?: return emptyArray()

        return arrayOf(SqlitePersonMediRelation.build {
            mMedicineId = medicine.mMedicineId
            mPersonId = person.mPersonId
        })
    }

    private fun toSqliteMediTimeRelationArray(medicine: Medicine, timetableArray: Array<Timetable>): Array<SqliteMediTimeRelation> =
            timetableArray.map {
                SqliteMediTimeRelation.build {
                    mMedicineId = medicine.mMedicineId
                    mTimetableId = it.mTimetableId
                    mIsOneShot = IsOneShotType(false)
                }
            }.toTypedArray()

    private fun toSqliteScheduleArray(scheduleArray: Array<Schedule>): Array<SqliteSchedule> =
            scheduleArray.map { SqliteSchedule.build { mSchedule = it } }.toTypedArray()

}
