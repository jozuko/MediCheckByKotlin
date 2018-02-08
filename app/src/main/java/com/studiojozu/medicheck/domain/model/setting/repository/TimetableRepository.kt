package com.studiojozu.medicheck.domain.model.setting.repository

import com.studiojozu.medicheck.domain.model.setting.Timetable
import com.studiojozu.medicheck.domain.model.setting.TimetableIdType
import com.studiojozu.medicheck.infrastructure.persistence.dao.SqliteTimetableRepository
import com.studiojozu.medicheck.infrastructure.persistence.entity.SqliteTimetable

class TimetableRepository(private val sqliteTimetableRepository: SqliteTimetableRepository) {

    fun findAll(): List<Timetable> =
            sqliteTimetableRepository.findAll().map { it.toTimetable() }

    fun findById(timetableId: TimetableIdType): Timetable? {
        val sqliteTimetable = sqliteTimetableRepository.findById(timetableId.dbValue) ?: return null
        return sqliteTimetable.toTimetable()
    }

    fun maxDisplayOrder() =
            sqliteTimetableRepository.maxDisplayOrder()

    fun insert(timetable: Timetable) =
            sqliteTimetableRepository.insert(SqliteTimetable.build { this.timetable = timetable })

    fun delete(timetable: Timetable) =
            sqliteTimetableRepository.delete(SqliteTimetable.build { this.timetable = timetable })
}
