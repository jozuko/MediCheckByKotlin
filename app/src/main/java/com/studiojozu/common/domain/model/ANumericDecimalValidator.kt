package com.studiojozu.common.domain.model

import android.support.annotation.StringRes
import com.studiojozu.medicheck.R
import java.math.BigDecimal

abstract class ANumericDecimalValidator protected constructor(mMin: BigDecimal, mMax: BigDecimal, mAllowMinValue: Boolean, mAllowMaxValue: Boolean) : IValidator {

    private val mMin: BigDecimal
    private val mMax: BigDecimal

    init {
        val min = if (mMin < mMax) mMin else mMax
        this.mMin = if (mAllowMinValue) min else min + BigDecimal(1)
        val max = if (mMin < mMax) mMax else mMin
        this.mMax = if (mAllowMaxValue) max else max - BigDecimal(1)
    }

    @StringRes
    override fun validate(vararg validateTargets: Any): Int {
        if (validateTargets.isEmpty())
            return IValidator.NO_ERROR_RESOURCE_ID

        if (validateTargets[0] !is String)
            return IValidator.NO_ERROR_RESOURCE_ID

        val data = validateTargets[0] as String

        if (!isNumeric(data))
            return R.string.validation_numeric

        return if (BigDecimal(data) in this.mMin..this.mMax) IValidator.NO_ERROR_RESOURCE_ID else R.string.validation_out_of_range
    }

    private fun isNumeric(data: String): Boolean = try {
        BigDecimal(data)
        true
    } catch (e: NumberFormatException) {
        false
    }
}
