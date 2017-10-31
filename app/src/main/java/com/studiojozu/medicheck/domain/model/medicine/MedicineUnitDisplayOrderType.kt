package com.studiojozu.medicheck.domain.model.medicine

import com.studiojozu.common.domain.model.general.ALongType

class MedicineUnitDisplayOrderType : ALongType<MedicineUnitDisplayOrderType> {
    companion object {
        const val serialVersionUID = 2199596378021713080L
    }

    constructor() : super()
    constructor(value: Any) : super(value)
}

