package com.studiojozu.medicheck.domain.model.medicine

import com.studiojozu.common.domain.model.general.ATextType

class MedicineNameType : ATextType<MedicineNameType> {
    companion object {
        const val serialVersionUID = 7689473074804188066L
    }

    constructor() : super("")
    constructor(value: Any?) : super(value)
}
