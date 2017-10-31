package com.studiojozu.medicheck.domain.model.medicine

import com.studiojozu.common.domain.model.general.AIdType

/**
 * 薬IDを管理するクラス
 */
class MedicineIdType : AIdType<MedicineIdType> {
    companion object {
        const val serialVersionUID = 6915190177094626179L
    }

    constructor() : super()
    constructor(value: Any?) : super(value)
}
