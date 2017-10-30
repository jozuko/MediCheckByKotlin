package com.studiojozu.medicheck.domain.model.schedule

import com.studiojozu.common.domain.model.general.ABooleanType

class ScheduleNeedAlarmType : ABooleanType<ScheduleNeedAlarmType> {
    companion object {
        const val serialVersionUID = -1817659640674585986L
    }

    constructor() : super(true)
    constructor(value: Any) : super(value)
}
