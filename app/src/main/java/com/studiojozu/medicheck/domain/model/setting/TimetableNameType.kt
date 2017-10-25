package com.studiojozu.medicheck.domain.model.setting

import com.studiojozu.common.domain.model.general.ATextType

class TimetableNameType : ATextType<TimetableNameType> {
    companion object {
        const val serialVersionUID = 8465585151530155440L
    }

    constructor() : super("")
    constructor(value: Any?) : super(value)
}
