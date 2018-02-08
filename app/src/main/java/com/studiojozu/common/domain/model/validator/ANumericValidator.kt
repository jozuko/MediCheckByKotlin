package com.studiojozu.common.domain.model.validator

import android.support.annotation.StringRes
import com.studiojozu.medicheck.R

abstract class ANumericValidator protected constructor(min: Long, max: Long, allowMinValue: Boolean, allowMaxValue: Boolean) : IRequiredValidator {

    private val min: Long
    private val max: Long

    init {
        val minValue = if (min < max) min else max
        val maxValue = if (min < max) max else min
        this.min = if (allowMinValue) minValue else minValue.inc()
        this.max = if (allowMaxValue) maxValue else maxValue.dec()
    }

    @StringRes
    override fun validate(vararg validateTargets: Any?): Int {
        val requiredResult = super.validate(*validateTargets)
        if (requiredResult != IValidator.NO_ERROR_RESOURCE_ID)
            return requiredResult

        val data = validateTargets[0]!! as String
        if (!isLong(data))
            return R.string.validation_numeric

        return if (data.toLong() in min..max) IValidator.NO_ERROR_RESOURCE_ID else R.string.validation_out_of_range
    }

    private fun isLong(data: String): Boolean = try {
        data.toLong()
        true
    } catch (e: NumberFormatException) {
        false
    }
}
