package com.studiojozu.medicheck.application

import com.studiojozu.medicheck.di.MediCheckApplication
import com.studiojozu.medicheck.domain.model.medicine.Medicine
import com.studiojozu.medicheck.domain.model.medicine.MedicineIdType
import com.studiojozu.medicheck.domain.model.medicine.MedicineUnit
import com.studiojozu.medicheck.domain.model.medicine.repository.MedicineUnitRepository
import com.studiojozu.medicheck.domain.model.medicine.repository.MedicineViewRepository
import com.studiojozu.medicheck.domain.model.setting.Timetable
import com.studiojozu.medicheck.domain.model.setting.repository.TimetableRepository
import javax.inject.Inject

class MedicineFinderService(application: MediCheckApplication) {
    @Inject
    lateinit var medicineViewRepository: MedicineViewRepository
    @Inject
    lateinit var medicineUnitRepository: MedicineUnitRepository
    @Inject
    lateinit var timetableRepository: TimetableRepository

    init {
        application.mComponent.inject(this)
    }

    fun existMedicine(): Boolean {
        val medicines = medicineViewRepository.findAllNoTimetable()
        return medicines.isNotEmpty()
    }

    fun findByMedicineId(medicineId: MedicineIdType): Medicine =
            medicineViewRepository.findById(medicineId) ?: Medicine(mMedicineId = medicineId)

    val defaultMedicineUnit: MedicineUnit?
        get() {
            val medicineUnits = medicineUnitRepository.findAll()
            return if (medicineUnits.isEmpty()) MedicineUnit() else medicineUnits[0]
        }

    val defaultTimetable: Timetable?
        get() {
            val timetables = timetableRepository.findAll()
            return if (timetables.isEmpty()) null else timetables[0]
        }
}