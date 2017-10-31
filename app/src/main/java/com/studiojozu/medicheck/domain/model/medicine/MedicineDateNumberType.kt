package com.studiojozu.medicheck.domain.model.medicine

import com.studiojozu.common.domain.model.general.ALongType

class MedicineDateNumberType : ALongType<MedicineDateNumberType> {
    companion object {
        const val serialVersionUID = 4831886646728241799L
    }

    constructor() : super(0)
    constructor(value: Any) : super(value)
}
