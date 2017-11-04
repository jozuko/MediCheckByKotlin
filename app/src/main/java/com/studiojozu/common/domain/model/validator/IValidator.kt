package com.studiojozu.common.domain.model.validator

import android.support.annotation.StringRes
import com.studiojozu.medicheck.R

interface IValidator {
    companion object {
        @StringRes
        const val NO_ERROR_RESOURCE_ID = -1
    }

    @StringRes
    fun validate(vararg validateTargets: Any?): Int {
        if (validateTargets.isEmpty())
            return R.string.validation_require

        return NO_ERROR_RESOURCE_ID
    }
}
