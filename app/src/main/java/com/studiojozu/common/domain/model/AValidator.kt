package com.studiojozu.common.domain.model

import com.studiojozu.medicheck.R
import java.math.BigDecimal

abstract class AValidator : IValidator {

    protected fun requiredCheck(value: Any?): Int = when {
        value == null -> R.string.validation_require
        value !is String -> R.string.validation_require
        value.isEmpty() -> R.string.validation_require
        else -> IValidator.NO_ERROR_RESOURCE_ID
    }

    protected fun isLong(data: String): Boolean = try {
        data.toLong()
        true
    } catch (e: NumberFormatException) {
        false
    }

    protected fun isDecimal(data: String): Boolean = try {
        BigDecimal(data)
        true
    } catch (e: NumberFormatException) {
        false
    }
}