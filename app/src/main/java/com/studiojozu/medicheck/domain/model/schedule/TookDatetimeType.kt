package com.studiojozu.medicheck.domain.model.schedule

import com.studiojozu.common.domain.model.general.ADateType
import com.studiojozu.common.domain.model.general.ADatetimeType
import com.studiojozu.common.domain.model.general.ATimeType
import java.util.*

class TookDatetimeType : ADatetimeType<TookDatetimeType> {
    companion object {
        const val serialVersionUID = 4076300522326519800L
    }

    constructor(millisecond: Any = Calendar.getInstance()) : super(millisecond)
    constructor(year: Int, month: Int, date: Int, hourOfDay: Int, minute: Int) : super(year, month, date, hourOfDay, minute)
    constructor(dateModel: ADateType<*>, timeModel: ATimeType<*>) : super(dateModel, timeModel)
}
