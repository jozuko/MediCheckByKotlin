package com.studiojozu.medicheck.domain.model.setting

import com.studiojozu.common.domain.model.general.ADateType
import com.studiojozu.common.domain.model.general.ADatetimeType
import com.studiojozu.common.domain.model.general.ATimeType

class ReminderDatetimeType : ADatetimeType<ReminderDatetimeType> {
    companion object {
        const val serialVersionUID = 8355513483581527244L
    }

    constructor(millisecond: Any) : super(millisecond)
    constructor(dateModel: ADateType<*>, timeModel: ATimeType<*>) : super(dateModel, timeModel)
}
