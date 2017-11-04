package com.studiojozu.medicheck.domain.model.setting

import java.util.*

class TimetableComparator(private val mComparePattern: ComparePattern) : Comparator<Timetable> {

    override fun compare(timetable1: Timetable, timetable2: Timetable): Int = when (mComparePattern) {
        ComparePattern.Time -> timetable1.compareToTimePriority(timetable2)
        ComparePattern.DisplayOrder -> timetable1.compareToDisplayOrderPriority(timetable2)
    }

    enum class ComparePattern {
        Time,
        DisplayOrder
    }
}
