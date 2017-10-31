package com.studiojozu.medicheck.domain.model.medicine

import com.studiojozu.common.domain.model.general.ATextType

class MedicineUnitValueType : ATextType<MedicineUnitValueType> {
    companion object {
        const val serialVersionUID = 2054493100462525550L
    }

    constructor() : super("")
    constructor(value: Any?) : super(value)
}
