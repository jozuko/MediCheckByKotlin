package com.studiojozu.medicheck.domain.model.medicine

import com.studiojozu.common.domain.model.general.APhotoType

class MedicinePhotoType : APhotoType<MedicinePhotoType> {
    companion object {
        const val serialVersionUID = 2920557811262773058L
    }

    constructor() : super("")
    constructor(value: Any) : super(value)
}
