package com.studiojozu.common.domain.model.validator

import android.support.annotation.StringRes
import com.studiojozu.medicheck.R

interface IRequiredValidator : IValidator {

    @StringRes
    override fun validate(vararg validateTargets: Any?): Int {
        val superResult = super.validate(*validateTargets)
        if (superResult != IValidator.NO_ERROR_RESOURCE_ID)
            return superResult

        val value = validateTargets[0]
        return when {
            value == null -> R.string.validation_require
            value !is String -> R.string.validation_require
            value.isEmpty() -> R.string.validation_require
            else -> IValidator.NO_ERROR_RESOURCE_ID
        }
    }
}
