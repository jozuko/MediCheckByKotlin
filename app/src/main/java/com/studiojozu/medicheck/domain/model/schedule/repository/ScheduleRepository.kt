package com.studiojozu.medicheck.domain.model.schedule.repository

import com.studiojozu.medicheck.domain.model.medicine.MedicineIdType
import com.studiojozu.medicheck.domain.model.schedule.Schedule
import com.studiojozu.medicheck.domain.model.schedule.ScheduleList
import com.studiojozu.medicheck.infrastructure.persistence.dao.SqliteScheduleRepository
import com.studiojozu.medicheck.infrastructure.persistence.entity.SqliteSchedule

class ScheduleRepository(private val sqliteScheduleRepository: SqliteScheduleRepository) {

    fun findAlarmAll(): List<Schedule> =
            sqliteScheduleRepository.findAlarmAll().map { it.toSchedule() }

    fun insertAll(scheduleList: ScheduleList) = scheduleList.forEach { it ->
        sqliteScheduleRepository.insert(SqliteSchedule.build { mSchedule = it })
    }

    fun deleteExceptHistoryByMedicineId(medicineId: MedicineIdType) =
            sqliteScheduleRepository.deleteExceptHistoryByMedicineId(medicineId = medicineId.dbValue)

    fun deleteAllByMedicineId(medicineId: MedicineIdType) =
            sqliteScheduleRepository.deleteAllByMedicineId(medicineId = medicineId.dbValue)
}
