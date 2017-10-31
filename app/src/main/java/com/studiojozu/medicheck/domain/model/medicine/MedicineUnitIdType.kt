package com.studiojozu.medicheck.domain.model.medicine

import com.studiojozu.common.domain.model.general.AIdType

class MedicineUnitIdType : AIdType<MedicineUnitIdType> {
    companion object {
        const val serialVersionUID = -4014725377594558521L
    }

    constructor() : super()
    constructor(value: Any) : super(value)
}
