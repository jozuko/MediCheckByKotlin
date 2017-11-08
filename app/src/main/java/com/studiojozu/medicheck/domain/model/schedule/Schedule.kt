package com.studiojozu.medicheck.domain.model.schedule

import com.studiojozu.medicheck.domain.model.medicine.MedicineIdType
import com.studiojozu.medicheck.domain.model.setting.TimetableIdType

import java.io.Serializable

data class Schedule(val mMedicineId: MedicineIdType,
                    val mSchedulePlanDate: SchedulePlanDateType,
                    val mTimetableId: TimetableIdType,
                    val mScheduleNeedAlarm: ScheduleNeedAlarmType = ScheduleNeedAlarmType(),
                    val mScheduleIsTake: ScheduleIsTakeType = ScheduleIsTakeType(),
                    val mScheduleTookDatetime: ScheduleTookDatetimeType = ScheduleTookDatetimeType()) : Serializable {

    companion object {
        const val serialVersionUID = 8903872909322659419L
    }
}
