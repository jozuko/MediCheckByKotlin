package com.studiojozu.medicheck.domain.model.schedule

import com.studiojozu.medicheck.domain.model.medicine.MedicineIdType
import com.studiojozu.medicheck.domain.model.setting.TimetableIdType

import java.io.Serializable

data class Schedule(val mMedicineId: MedicineIdType,
                    val mPlanDate: PlanDateType,
                    val mTimetableId: TimetableIdType,
                    val mNeedAlarm: ScheduleNeedAlarmType = ScheduleNeedAlarmType(),
                    val mIsTake: IsTakeType = IsTakeType(),
                    val mTookDatetime: TookDatetimeType = TookDatetimeType()) : Serializable {

    companion object {
        const val serialVersionUID = 8903872909322659419L
    }
}
