package com.studiojozu.medicheck.application

import com.studiojozu.medicheck.di.MediCheckApplication
import com.studiojozu.medicheck.domain.model.medicine.repository.MediTimeRelationRepository
import com.studiojozu.medicheck.domain.model.setting.Timetable
import com.studiojozu.medicheck.domain.model.setting.repository.TimetableRepository
import javax.inject.Inject

class TimetableRegisterService(application: MediCheckApplication) {
    @Inject
    lateinit var timetableRepository: TimetableRepository

    @Inject
    lateinit var mediTimeRelationRepository: MediTimeRelationRepository

    init {
        application.component.inject(this)
    }

    fun insert(timetable: Timetable) =
            timetableRepository.insert(timetable)

    fun update(timetables: List<Timetable>) =
            timetableRepository.update(timetables)

    fun delete(timetable: Timetable): ErrorType {
        if (timetableRepository.findAll().size == 1) return ErrorType.LAST_ONE
        if (mediTimeRelationRepository.findMedicineByTimetableId(timetableId = timetable.timetableId).isNotEmpty()) return ErrorType.EXIST_RELATION_OTHER

        timetableRepository.delete(timetable)
        return ErrorType.NO_ERROR
    }

    enum class ErrorType {
        NO_ERROR,
        LAST_ONE,
        EXIST_RELATION_OTHER
    }
}