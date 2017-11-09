package com.studiojozu.medicheck.domain.model.schedule

import com.studiojozu.common.domain.model.CalendarNoSecond
import com.studiojozu.common.domain.model.general.ADateType

class SchedulePlanDateType : ADateType<SchedulePlanDateType> {
    companion object {
        const val serialVersionUID = 9088775597942158497L
    }

    constructor(millisecond: Any = CalendarNoSecond().calendar) : super(millisecond)
    constructor(year: Int, month: Int, day: Int) : super(year, month, day)
}
