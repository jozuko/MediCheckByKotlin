package com.studiojozu.medicheck.application

import com.studiojozu.medicheck.di.MediCheckApplication
import com.studiojozu.medicheck.domain.model.setting.Timetable
import com.studiojozu.medicheck.domain.model.setting.repository.TimetableRepository
import javax.inject.Inject

class TimetableFinderService(application: MediCheckApplication) {
    @Inject
    lateinit var timetableRepository: TimetableRepository

    init {
        application.component.inject(this)
    }

    fun findAll(): List<Timetable> =
            timetableRepository.findAll()
}