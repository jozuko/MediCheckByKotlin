package com.studiojozu.medicheck.domain.model

import android.content.Context

import com.studiojozu.medicheck.domain.model.medicine.Medicine
import com.studiojozu.medicheck.domain.model.medicine.MedicineIdType
import com.studiojozu.medicheck.domain.model.person.Person
import com.studiojozu.medicheck.domain.model.person.PersonIdType

interface IPersonMediViewRepository {

    fun findPersonByMedicineId(context: Context, medicineIdType: MedicineIdType): Person?

    fun findMedicineByPersonId(context: Context, personIdType: PersonIdType): Set<Medicine>?

    fun existByPersonIdMedicineId(context: Context, personIdType: PersonIdType, medicineIdType: MedicineIdType): Boolean

    fun getPersonNumberByMedicineId(context: Context, medicineIdType: MedicineIdType): Int

    fun getMedicineNumberByPersonId(context: Context, personIdType: PersonIdType): Int
}
