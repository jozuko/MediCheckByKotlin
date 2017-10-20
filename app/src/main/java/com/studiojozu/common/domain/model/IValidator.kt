package com.studiojozu.common.domain.model

import android.support.annotation.StringRes

interface IValidator {
    companion object {
        @StringRes
        const val NO_ERROR_RESOURCE_ID = -1
    }

    @StringRes
    fun validate(vararg validateTargets: Any): Int
}
