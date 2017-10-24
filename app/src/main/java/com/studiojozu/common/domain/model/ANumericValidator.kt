package com.studiojozu.common.domain.model

import android.support.annotation.StringRes
import com.studiojozu.medicheck.R
import org.jetbrains.annotations.Contract

abstract class ANumericValidator protected constructor(mMin: Long, mMax: Long, private val mAllowMinValue: Boolean, private val mAllowMaxValue: Boolean) : IValidator {

    private val mMin: Long = if (mMin < mMax) mMin else mMax
    private val mMax: Long = if (mMin < mMax) mMax else mMin

    @StringRes
    override fun validate(vararg validateTargets: Any): Int {
        if (validateTargets.isEmpty())
            return IValidator.NO_ERROR_RESOURCE_ID

        if (validateTargets[0] !is String)
            return IValidator.NO_ERROR_RESOURCE_ID

        val data = validateTargets[0] as String

        if (!isNumeric(data))
            return R.string.validation_numeric

        return if (!checkRange(java.lang.Long.parseLong(data))) R.string.validation_out_of_range else IValidator.NO_ERROR_RESOURCE_ID
    }

    private fun isNumeric(data: String): Boolean = try {
        java.lang.Long.parseLong(data)
        true
    } catch (e: NumberFormatException) {
        false
    }

    @Contract(pure = true)
    private fun checkRange(data: Long): Boolean = compareMinValue(data) && compareMaxValue(data)

    @Contract(pure = true)
    private fun compareMinValue(data: Long): Boolean = if (mAllowMinValue) data >= mMin else data > mMin

    @Contract(pure = true)
    private fun compareMaxValue(data: Long): Boolean = if (mAllowMaxValue) data <= mMax else data < mMax
}
