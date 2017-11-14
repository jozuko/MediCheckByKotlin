package com.studiojozu.common.domain.model.validator

import com.studiojozu.medicheck.R
import com.studiojozu.medicheck.domain.model.setting.ATestParent
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Suppress("FunctionName")
@RunWith(RobolectricTestRunner::class)
@Config(manifest = "src/main/AndroidManifest.xml")
class ANumericValidatorTest : ATestParent() {
    class TestNumericValidator constructor(mMin: Long, mMax: Long, mAllowMinValue: Boolean, mAllowMaxValue: Boolean) : ANumericValidator(mMin, mMax, mAllowMinValue, mAllowMaxValue)

    @Test
    @Throws(Exception::class)
    fun validate_illegalType() {
        val mMin = 0L
        val mMax = 0L
        val mAllowMinValue = true
        val mAllowMaxValue = true
        val validator = TestNumericValidator(mMin, mMax, mAllowMinValue, mAllowMaxValue)

        assertEquals(R.string.validation_require, validator.validate())
        assertEquals(R.string.validation_require, validator.validate(1))
        assertEquals(R.string.validation_require, validator.validate(""))
        assertEquals(R.string.validation_numeric, validator.validate("a"))
        val value: String? = null
        assertEquals(R.string.validation_require, validator.validate(value))
    }

    @Test
    @Throws(Exception::class)
    fun validate_OutOfRange() {
        var validator = TestNumericValidator(0L, 0L, false, false)
        assertEquals(R.string.validation_out_of_range, validator.validate("0"))

        validator = TestNumericValidator(0L, 10L, false, false)
        assertEquals(R.string.validation_out_of_range, validator.validate("0"))

        validator = TestNumericValidator(0L, 10L, false, false)
        assertEquals(R.string.validation_out_of_range, validator.validate("10"))

        validator = TestNumericValidator(0L, 10L, true, false)
        assertEquals(R.string.validation_out_of_range, validator.validate("-1"))

        validator = TestNumericValidator(0L, 10L, false, true)
        assertEquals(R.string.validation_out_of_range, validator.validate("11"))
    }

    @Test
    @Throws(Exception::class)
    fun validate_success() {
        var validator = TestNumericValidator(0L, 0L, true, true)
        assertEquals(IValidator.NO_ERROR_RESOURCE_ID, validator.validate("0"))

        validator = TestNumericValidator(0L, 10L, true, false)
        assertEquals(IValidator.NO_ERROR_RESOURCE_ID, validator.validate("0"))

        validator = TestNumericValidator(0L, 10L, false, true)
        assertEquals(IValidator.NO_ERROR_RESOURCE_ID, validator.validate("10"))

        validator = TestNumericValidator(0L, 10L, false, false)
        assertEquals(IValidator.NO_ERROR_RESOURCE_ID, validator.validate("1"))

        validator = TestNumericValidator(0L, 10L, false, false)
        assertEquals(IValidator.NO_ERROR_RESOURCE_ID, validator.validate("9"))

        validator = TestNumericValidator(10L, 0L, false, false)
        assertEquals(IValidator.NO_ERROR_RESOURCE_ID, validator.validate("9"))
    }
}