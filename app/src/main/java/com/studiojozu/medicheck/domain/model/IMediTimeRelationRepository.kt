package com.studiojozu.medicheck.domain.model

import android.content.Context

import com.studiojozu.medicheck.domain.model.medicine.MedicineIdType
import com.studiojozu.medicheck.domain.model.medicine.MedicineTimetableList

interface IMediTimeRelationRepository {
    fun remove(context: Context, medicineId: MedicineIdType)

    fun add(context: Context, medicineId: MedicineIdType, timetableList: MedicineTimetableList)
}
