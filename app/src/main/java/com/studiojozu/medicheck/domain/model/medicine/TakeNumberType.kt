package com.studiojozu.medicheck.domain.model.medicine

import com.studiojozu.common.domain.model.general.ATextType

class TakeNumberType : ATextType<TakeNumberType> {
    companion object {
        const val serialVersionUID = 6806353498301151954L
    }

    constructor() : super("0")
    constructor(value: Any) : super(value)
}
