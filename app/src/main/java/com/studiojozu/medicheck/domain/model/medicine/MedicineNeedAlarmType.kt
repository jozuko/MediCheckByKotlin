package com.studiojozu.medicheck.domain.model.medicine

import com.studiojozu.common.domain.model.general.ABooleanType

class MedicineNeedAlarmType : ABooleanType<MedicineNeedAlarmType> {
    companion object {
        const val serialVersionUID = 3912664133628844098L
    }

    constructor() : super(true)
    constructor(value: Any) : super(value)
}
