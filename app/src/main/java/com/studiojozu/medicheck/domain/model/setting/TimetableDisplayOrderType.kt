package com.studiojozu.medicheck.domain.model.setting

import com.studiojozu.common.domain.model.general.ALongType

class TimetableDisplayOrderType : ALongType<TimetableDisplayOrderType> {
    companion object {
        const val serialVersionUID = 2643592220296427861L
    }

    constructor() : super()
    constructor(value: Any) : super(value)
}

