package com.studiojozu.medicheck.domain.model

import android.content.Context

import com.studiojozu.medicheck.domain.model.medicine.MedicineIdType
import com.studiojozu.medicheck.domain.model.medicine.MedicineTimetableList

interface IMediTimeViewRepository {
    fun findTimetableListByMedicineId(context: Context, medicineIdType: MedicineIdType): MedicineTimetableList
}
