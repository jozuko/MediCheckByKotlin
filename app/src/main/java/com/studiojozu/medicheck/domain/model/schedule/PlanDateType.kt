package com.studiojozu.medicheck.domain.model.schedule

import com.studiojozu.common.domain.model.general.ADateType
import java.util.*

class PlanDateType : ADateType<PlanDateType> {
    companion object {
        const val serialVersionUID = 9088775597942158497L
    }

    constructor(millisecond: Any = Calendar.getInstance()) : super(millisecond)
    constructor(year: Int, month: Int, day: Int) : super(year, month, day)
}
