package com.studiojozu.medicheck.application

import com.studiojozu.medicheck.di.MediCheckApplication
import com.studiojozu.medicheck.domain.model.medicine.Medicine
import com.studiojozu.medicheck.domain.model.medicine.MedicineIdType
import com.studiojozu.medicheck.domain.model.medicine.repository.MedicineViewRepository
import com.studiojozu.medicheck.domain.model.person.repository.PersonMediRelationRepository
import javax.inject.Inject

class MedicineDeleteService(application: MediCheckApplication) {

    @Inject
    lateinit var medicineViewRepository: MedicineViewRepository
    @Inject
    lateinit var personMediRelationRepository: PersonMediRelationRepository

    init {
        application.mComponent.inject(this)
    }

    fun deleteMedicine(medicineIdType: MedicineIdType) {
        medicineViewRepository.delete(Medicine(mMedicineId = medicineIdType))
        personMediRelationRepository.deleteByMedicineId(medicineIdType)
    }
}