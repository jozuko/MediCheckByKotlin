package com.studiojozu.medicheck.domain.model

import android.content.Context

import com.studiojozu.medicheck.domain.model.medicine.MedicineIdType
import com.studiojozu.medicheck.domain.model.person.PersonIdType

interface IPersonMediRelationRepository {
    fun remove(context: Context, medicineIdType: MedicineIdType)

    fun add(context: Context, personId: PersonIdType, medicineIdType: MedicineIdType)
}
