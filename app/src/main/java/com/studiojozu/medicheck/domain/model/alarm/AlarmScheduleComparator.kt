package com.studiojozu.medicheck.domain.model.alarm

import java.util.*

class AlarmScheduleComparator : Comparator<AlarmSchedule> {
    override fun compare(alarmSchedule1: AlarmSchedule, alarmSchedule2: AlarmSchedule): Int {
        val compareDate = alarmSchedule1.compareDate(alarmSchedule2)
        if (compareDate != 0) return compareDate

        return alarmSchedule1.compareTime(alarmSchedule2)
    }
}
