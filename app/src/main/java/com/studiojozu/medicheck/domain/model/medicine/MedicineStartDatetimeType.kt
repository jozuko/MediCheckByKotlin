package com.studiojozu.medicheck.domain.model.medicine

import com.studiojozu.common.domain.model.CalendarNoSecond
import com.studiojozu.common.domain.model.general.ADateType
import com.studiojozu.common.domain.model.general.ADatetimeType
import com.studiojozu.common.domain.model.general.ATimeType

class MedicineStartDatetimeType : ADatetimeType<MedicineStartDatetimeType> {
    companion object {
        const val serialVersionUID = 7612988577243783674L
    }

    constructor() : super(CalendarNoSecond().calendar)
    constructor(millisecond: Any) : super(millisecond)
    constructor(year: Int, month: Int, date: Int, hourOfDay: Int, minute: Int) : super(year, month, date, hourOfDay, minute)
    constructor(dateModel: ADateType<*>, timeModel: ATimeType<*>) : super(dateModel, timeModel)
}
