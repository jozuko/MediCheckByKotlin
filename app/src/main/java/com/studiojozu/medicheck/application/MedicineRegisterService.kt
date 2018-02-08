package com.studiojozu.medicheck.application

import com.studiojozu.medicheck.di.MediCheckApplication
import com.studiojozu.medicheck.domain.model.medicine.Medicine
import com.studiojozu.medicheck.domain.model.medicine.repository.MedicineViewRepository
import com.studiojozu.medicheck.domain.model.person.PersonIdType
import com.studiojozu.medicheck.domain.model.person.repository.PersonRepository
import com.studiojozu.medicheck.domain.model.schedule.ScheduleService
import javax.inject.Inject

class MedicineRegisterService(application: MediCheckApplication) {

    @Inject
    lateinit var medicineViewRepository: MedicineViewRepository

    @Inject
    lateinit var personRepository: PersonRepository

    @Inject
    lateinit var scheduleService: ScheduleService

    init {
        application.component.inject(this)
    }

    fun registerMedicine(medicine: Medicine, personIdType: PersonIdType) {
        val person = personRepository.findById(personIdType) ?: throw IllegalArgumentException("does not exist person.")
        val scheduleList = scheduleService.createScheduleList(medicine)

        medicineViewRepository.insert(medicine = medicine,
                person = person,
                timetableArray = medicine.timetableList.toList().toTypedArray(),
                scheduleArray = scheduleList.toList().toTypedArray())
    }

}