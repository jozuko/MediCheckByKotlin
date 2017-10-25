package com.studiojozu.medicheck.domain.model.setting

import com.studiojozu.common.domain.model.general.AIdType

class TimetableIdType : AIdType<TimetableIdType> {
    companion object {
        const val serialVersionUID = 2295047399748442857L
    }

    constructor() : super()
    constructor(value: Any) : super(value)
}
