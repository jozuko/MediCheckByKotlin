package com.studiojozu.common.domain.model.validator

import android.support.annotation.StringRes
import com.studiojozu.medicheck.R
import java.math.BigDecimal

abstract class ANumericDecimalValidator protected constructor(mMin: BigDecimal, mMax: BigDecimal, mAllowMinValue: Boolean, mAllowMaxValue: Boolean) : IRequiredValidator {

    private val mMin: BigDecimal
    private val mMax: BigDecimal

    init {
        val min = if (mMin < mMax) mMin else mMax
        this.mMin = if (mAllowMinValue) min else min + BigDecimal(1)
        val max = if (mMin < mMax) mMax else mMin
        this.mMax = if (mAllowMaxValue) max else max - BigDecimal(1)
    }

    @StringRes
    override fun validate(vararg validateTargets: Any?): Int {
        val requiredResult = super.validate(*validateTargets)
        if (requiredResult != IValidator.NO_ERROR_RESOURCE_ID)
            return requiredResult

        val data = validateTargets[0]!! as String
        if (!isDecimal(data))
            return R.string.validation_numeric

        return if (BigDecimal(data) in this.mMin..this.mMax) IValidator.NO_ERROR_RESOURCE_ID else R.string.validation_out_of_range
    }

    private fun isDecimal(data: String): Boolean = try {
        BigDecimal(data)
        true
    } catch (e: NumberFormatException) {
        false
    }
}
