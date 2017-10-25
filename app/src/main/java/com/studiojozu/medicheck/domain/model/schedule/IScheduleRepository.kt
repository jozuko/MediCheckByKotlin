package com.studiojozu.medicheck.domain.model.schedule

import android.content.Context

import com.studiojozu.medicheck.domain.model.medicine.MedicineIdType

interface IScheduleRepository {
    fun getNeedAlerts(context: Context): List<Schedule>

    fun addAll(context: Context, medicineId: MedicineIdType, scheduleList: ScheduleList)

    fun removeIgnoreHistory(context: Context, medicineId: MedicineIdType)
}
