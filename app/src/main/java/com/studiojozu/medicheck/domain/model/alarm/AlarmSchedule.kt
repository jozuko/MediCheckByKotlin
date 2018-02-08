package com.studiojozu.medicheck.domain.model.alarm

import com.studiojozu.medicheck.domain.model.medicine.Medicine
import com.studiojozu.medicheck.domain.model.person.Person
import com.studiojozu.medicheck.domain.model.schedule.Schedule
import com.studiojozu.medicheck.domain.model.schedule.SchedulePlanDateType
import com.studiojozu.medicheck.domain.model.setting.Setting
import com.studiojozu.medicheck.domain.model.setting.Timetable
import com.studiojozu.medicheck.domain.model.setting.TimetableComparator
import com.studiojozu.medicheck.domain.model.setting.TimetableTimeType
import java.util.*

data class AlarmSchedule(private val schedule: Schedule,
                         private val timetable: Timetable,
                         private val medicine: Medicine,
                         private val person: Person) {

    val medicineName: String
        get() = medicine.medicineName.displayValue

    val personName: String
        get() = person.personName.displayValue

    private val alarmDate: SchedulePlanDateType
        get() = schedule.schedulePlanDate

    private val alarmTime: TimetableTimeType
        get() = timetable.timetableTime

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
            = TimetableComparator(TimetableComparator.ComparePattern.TIME).compare(timetable, other.timetable)
}
