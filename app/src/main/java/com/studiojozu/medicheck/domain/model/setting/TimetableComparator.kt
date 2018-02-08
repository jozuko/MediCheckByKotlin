package com.studiojozu.medicheck.domain.model.setting

import java.util.*

class TimetableComparator(private val comparePattern: ComparePattern) : Comparator<Timetable> {

    override fun compare(timetable1: Timetable, timetable2: Timetable): Int = when (comparePattern) {
        ComparePattern.TIME -> timetable1.compareToTimePriority(timetable2)
        ComparePattern.DISPLAY_ORDER -> timetable1.compareToDisplayOrderPriority(timetable2)
    }

    enum class ComparePattern {
        TIME,
        DISPLAY_ORDER
    }
}
