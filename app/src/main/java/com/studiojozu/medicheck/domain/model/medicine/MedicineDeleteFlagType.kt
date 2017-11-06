package com.studiojozu.medicheck.domain.model.medicine

import com.studiojozu.common.domain.model.general.ABooleanType

class MedicineDeleteFlagType : ABooleanType<MedicineDeleteFlagType> {
    companion object {
        const val serialVersionUID = -4357707301598095745L
    }

    constructor() : super(false)
    constructor(value: Any) : super(value)
}
