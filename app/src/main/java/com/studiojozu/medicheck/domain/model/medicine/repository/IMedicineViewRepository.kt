package com.studiojozu.medicheck.domain.model.medicine.repository

import android.content.Context

import com.studiojozu.medicheck.domain.model.medicine.Medicine
import com.studiojozu.medicheck.domain.model.medicine.MedicineIdType

interface IMedicineViewRepository {
    fun findMedicineById(context: Context, medicineIdType: MedicineIdType): Medicine?

    fun findAll(context: Context): Set<Medicine>?

    fun add(context: Context, medicine: Medicine)

    fun remove(context: Context, medicineIdType: MedicineIdType)
}
