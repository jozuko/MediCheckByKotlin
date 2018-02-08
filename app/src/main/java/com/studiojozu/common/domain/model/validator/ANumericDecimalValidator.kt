package com.studiojozu.common.domain.model.validator

import android.support.annotation.StringRes
import com.studiojozu.medicheck.R
import java.math.BigDecimal

abstract class ANumericDecimalValidator protected constructor(min: BigDecimal, max: BigDecimal, allowMinValue: Boolean, allowMaxValue: Boolean) : IRequiredValidator {

    private val min: BigDecimal
    private val max: BigDecimal

    init {
        val minValue = if (min < max) min else max
        val maxValue = if (min < max) max else min
        this.min = if (allowMinValue) minValue else minValue + BigDecimal(1)
        this.max = if (allowMaxValue) maxValue else maxValue - BigDecimal(1)
    }

    @StringRes
    override fun validate(vararg validateTargets: Any?): Int {
        val requiredResult = super.validate(*validateTargets)
        if (requiredResult != IValidator.NO_ERROR_RESOURCE_ID)
            return requiredResult

        val data = validateTargets[0]!! as String
        if (!isDecimal(data))
            return R.string.validation_numeric

        return if (BigDecimal(data) in this.min..this.max) IValidator.NO_ERROR_RESOURCE_ID else R.string.validation_out_of_range
    }

    private fun isDecimal(data: String): Boolean = try {
        BigDecimal(data)
        true
    } catch (e: NumberFormatException) {
        false
    }
}
