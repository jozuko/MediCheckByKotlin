package com.studiojozu.common.domain.model.validator

import android.support.annotation.StringRes
import com.studiojozu.medicheck.R

abstract class ANumericValidator protected constructor(mMin: Long, mMax: Long, mAllowMinValue: Boolean, mAllowMaxValue: Boolean) : IRequiredValidator {

    private val mMin: Long
    private val mMax: Long

    init {
        val min = if (mMin < mMax) mMin else mMax
        this.mMin = if (mAllowMinValue) min else min.inc()
        val max = if (mMin < mMax) mMax else mMin
        this.mMax = if (mAllowMaxValue) max else max.dec()
    }

    @StringRes
    override fun validate(vararg validateTargets: Any?): Int {
        val requiredResult = super.validate(*validateTargets)
        if (requiredResult != IValidator.NO_ERROR_RESOURCE_ID)
            return requiredResult

        val data = validateTargets[0]!! as String
        if (!isLong(data))
            return R.string.validation_numeric

        return if (data.toLong() in mMin..mMax) IValidator.NO_ERROR_RESOURCE_ID else R.string.validation_out_of_range
    }

    private fun isLong(data: String): Boolean = try {
        data.toLong()
        true
    } catch (e: NumberFormatException) {
        false
    }
}
