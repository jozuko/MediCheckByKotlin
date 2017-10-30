package com.studiojozu.medicheck.domain.model.person

import com.studiojozu.common.domain.model.general.AIdType

class PersonIdType : AIdType<PersonIdType> {
    companion object {
        const val serialVersionUID = 1526728745548403076L
    }

    constructor() : super()
    constructor(value: Any) : super(value)
}
