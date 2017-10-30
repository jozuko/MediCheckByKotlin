package com.studiojozu.medicheck.domain.model.schedule

import com.studiojozu.common.domain.model.general.ABooleanType

class IsTakeType : ABooleanType<IsTakeType> {
    companion object {
        const val serialVersionUID = -8175393566197539808L
    }

    constructor() : super(false)
    constructor(value: Any) : super(value)
}
