package com.studiojozu.medicheck.domain.model.medicine.validator

import android.support.annotation.StringRes
import com.studiojozu.common.domain.model.validator.IValidator
import com.studiojozu.medicheck.R
import com.studiojozu.medicheck.domain.model.setting.Timetable

class MedicineTimetableValidator : IValidator {

    @StringRes
    @Suppress("UNCHECKED_CAST")
    override fun validate(vararg validateTargets: Any?): Int {
        if (validateTargets.size < 2)
            return R.string.validation_require_timetable

        if (validateTargets[0] == null || validateTargets[0] !is Boolean)
            return R.string.validation_require_timetable

        if (validateTargets[1] == null || validateTargets[1] !is List<*>)
            return R.string.validation_require_timetable

        val isOneShot = validateTargets[0] as Boolean
        val timetableList = validateTargets[1] as List<Timetable>

        if (isOneShot)
            return IValidator.NO_ERROR_RESOURCE_ID

        if (timetableList.isEmpty())
            return R.string.validation_require_select

        return IValidator.NO_ERROR_RESOURCE_ID
    }
}
