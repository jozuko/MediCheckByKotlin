package com.studiojozu.medicheck.application

import com.studiojozu.medicheck.di.MediCheckApplication
import com.studiojozu.medicheck.domain.model.medicine.Medicine
import com.studiojozu.medicheck.domain.model.medicine.repository.MediTimeRelationRepository
import com.studiojozu.medicheck.domain.model.medicine.repository.MedicineViewRepository
import com.studiojozu.medicheck.domain.model.person.repository.PersonMediRelationRepository
import com.studiojozu.medicheck.domain.model.schedule.repository.ScheduleRepository
import javax.inject.Inject

class MedicineDeleteService(application: MediCheckApplication) {

    @Inject
    lateinit var medicineViewRepository: MedicineViewRepository
    @Inject
    lateinit var personMediRelationRepository: PersonMediRelationRepository
    @Inject
    lateinit var mediTimeRelationRepository: MediTimeRelationRepository
    @Inject
    lateinit var scheduleRepository: ScheduleRepository

    init {
        application.component.inject(this)
    }

    fun deleteMedicine(medicine: Medicine) {
        val person = personMediRelationRepository.findPersonByMedicineId(medicineIdType = medicine.medicineId)
        val sqliteMediTimeRelationArray = mediTimeRelationRepository.findTimetableByMedicineId(medicineIdType = medicine.medicineId)
        val sqliteScheduleArray = scheduleRepository.findByMedicineId(medicineIdType = medicine.medicineId)

        medicineViewRepository.delete(medicine,
                person,
                sqliteMediTimeRelationArray,
                sqliteScheduleArray)
    }

}