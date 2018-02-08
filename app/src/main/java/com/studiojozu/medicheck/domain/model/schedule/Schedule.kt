package com.studiojozu.medicheck.domain.model.schedule

import com.studiojozu.medicheck.domain.model.medicine.MedicineIdType
import com.studiojozu.medicheck.domain.model.setting.TimetableIdType

import java.io.Serializable

data class Schedule(val medicineId: MedicineIdType,
                    val schedulePlanDate: SchedulePlanDateType,
                    val timetableId: TimetableIdType,
                    val scheduleNeedAlarm: ScheduleNeedAlarmType = ScheduleNeedAlarmType(),
                    val scheduleIsTake: ScheduleIsTakeType = ScheduleIsTakeType(),
                    val scheduleTookDatetime: ScheduleTookDatetimeType = ScheduleTookDatetimeType()) : Serializable {

    companion object {
        const val serialVersionUID = 8903872909322659419L
    }
}
