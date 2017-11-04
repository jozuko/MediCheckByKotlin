package com.studiojozu.medicheck.domain.model.alarm

import com.studiojozu.medicheck.domain.model.medicine.Medicine
import com.studiojozu.medicheck.domain.model.person.Person
import com.studiojozu.medicheck.domain.model.schedule.PlanDateType
import com.studiojozu.medicheck.domain.model.schedule.Schedule
import com.studiojozu.medicheck.domain.model.setting.Setting
import com.studiojozu.medicheck.domain.model.setting.Timetable
import com.studiojozu.medicheck.domain.model.setting.TimetableComparator
import com.studiojozu.medicheck.domain.model.setting.TimetableTimeType
import java.util.*

data class AlarmSchedule(private val mSchedule: Schedule,
                         private val mTimetable: Timetable,
                         private val mMedicine: Medicine,
                         private val mPerson: Person) {

    val medicineName: String
        get() = mMedicine.mMedicineName.displayValue

    val personName: String
        get() = mPerson.mPersonName.displayValue

    private val alarmDate: PlanDateType
        get() = mSchedule.mPlanDate

    private val alarmTime: TimetableTimeType
        get() = mTimetable.getTimetableTime()

    fun isNeedAlarm(now: Calendar, setting: Setting): Boolean {
        if (isSameDateTime(now)) return true
        if (!setting.useReminder()) return false

        return if (setting.isRemindTimeout(now, alarmDate, alarmTime)) false else setting.isRemindTiming(now, alarmDate, alarmTime)
    }

    private fun isSameDateTime(compareTarget: Calendar): Boolean
            = alarmDate.sameDate(compareTarget) && alarmTime.sameTime(compareTarget)

    fun compareDate(other: AlarmSchedule): Int
            = alarmDate.compareTo(other.alarmDate)

    fun compareTime(other: AlarmSchedule): Int
            = TimetableComparator(TimetableComparator.ComparePattern.Time).compare(mTimetable, other.mTimetable)
}
