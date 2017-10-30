package com.studiojozu.medicheck.domain.model.person

import com.studiojozu.common.domain.model.general.ATextType

class PersonNameType : ATextType<PersonNameType> {
    companion object {
        const val serialVersionUID = 5123729176127085413L
    }

    constructor() : super("")
    constructor(value: Any?) : super(value)
}
