package com.studiojozu.medicheck.domain.model.medicine.repository

import android.content.Context

import com.studiojozu.medicheck.domain.model.medicine.MedicineUnit
import com.studiojozu.medicheck.domain.model.medicine.MedicineUnitIdType

interface IMedicineUnitRepository {
    fun findMedicineUnitById(context: Context, medicineUnitIdType: MedicineUnitIdType): MedicineUnit?

    fun findAll(context: Context): Collection<MedicineUnit>

    fun add(context: Context, medicineUnit: MedicineUnit)
}
