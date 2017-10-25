package com.studiojozu.medicheck.domain.model.setting

import com.studiojozu.common.domain.model.general.ADatetimeType
import com.studiojozu.common.domain.model.general.ATimeType
import java.util.*

class TimetableTimeType : ATimeType<TimetableTimeType> {
    companion object {
        const val serialVersionUID = 5169596478420128156L
    }

    constructor() : super(System.currentTimeMillis())
    constructor(millisecond: Any) : super(millisecond)
    constructor(hourOfDay: Int, minute: Int) : super(hourOfDay, minute)

    fun replaceHourMinute(datetimeType: ADatetimeType<*>): ADatetimeType<*> = datetimeType.setHourMinute(mValue.get(Calendar.HOUR_OF_DAY), mValue.get(Calendar.MINUTE))
}
