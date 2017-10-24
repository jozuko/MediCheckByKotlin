package com.studiojozu.common.domain.model

import com.studiojozu.medicheck.R
import com.studiojozu.medicheck.domain.model.setting.ATestParent
import junit.framework.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.powermock.core.classloader.annotations.PowerMockIgnore
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.math.BigDecimal

@Suppress("FunctionName")
@RunWith(RobolectricTestRunner::class)
@Config(manifest = "src/main/AndroidManifest.xml")
@PowerMockIgnore("org.mockito.*", "org.robolectric.*", "android.*")
class ANumericDecimalValidatorTest : ATestParent() {
    class TestNumericDecimalValidator constructor(mMin: BigDecimal, mMax: BigDecimal, mAllowMinValue: Boolean, mAllowMaxValue: Boolean) : ANumericDecimalValidator(mMin, mMax, mAllowMinValue, mAllowMaxValue)

    @Test
    @Throws(Exception::class)
    fun validate_illegalType() {
        val mMin = BigDecimal(0)
        val mMax = BigDecimal(0)
        val mAllowMinValue = true
        val mAllowMaxValue = true
        val validator = TestNumericDecimalValidator(mMin, mMax, mAllowMinValue, mAllowMaxValue)

        assertEquals(IValidator.NO_ERROR_RESOURCE_ID, validator.validate())
        assertEquals(IValidator.NO_ERROR_RESOURCE_ID, validator.validate(1))
        assertEquals(R.string.validation_numeric, validator.validate(""))
        assertEquals(R.string.validation_numeric, validator.validate("a"))
    }

    @Test
    @Throws(Exception::class)
    fun validate_OutOfRange() {
        var validator = TestNumericDecimalValidator(BigDecimal(0), BigDecimal(0), false, false)
        assertEquals(R.string.validation_out_of_range, validator.validate("0"))

        validator = TestNumericDecimalValidator(BigDecimal(0), BigDecimal(10), false, false)
        assertEquals(R.string.validation_out_of_range, validator.validate("0"))

        validator = TestNumericDecimalValidator(BigDecimal(0), BigDecimal(10), false, false)
        assertEquals(R.string.validation_out_of_range, validator.validate("10"))

        validator = TestNumericDecimalValidator(BigDecimal(0), BigDecimal(10), true, false)
        assertEquals(R.string.validation_out_of_range, validator.validate("-1"))

        validator = TestNumericDecimalValidator(BigDecimal(0), BigDecimal(10), false, true)
        assertEquals(R.string.validation_out_of_range, validator.validate("11"))
    }


    @Test
    @Throws(Exception::class)
    fun validate_success() {
        var validator = TestNumericDecimalValidator(BigDecimal(0), BigDecimal(0), true, true)
        assertEquals(IValidator.NO_ERROR_RESOURCE_ID, validator.validate("0"))

        validator = TestNumericDecimalValidator(BigDecimal(0), BigDecimal(10), true, false)
        assertEquals(IValidator.NO_ERROR_RESOURCE_ID, validator.validate("0"))

        validator = TestNumericDecimalValidator(BigDecimal(0), BigDecimal(10), false, true)
        assertEquals(IValidator.NO_ERROR_RESOURCE_ID, validator.validate("10"))

        validator = TestNumericDecimalValidator(BigDecimal(0), BigDecimal(10), false, false)
        assertEquals(IValidator.NO_ERROR_RESOURCE_ID, validator.validate("1"))

        validator = TestNumericDecimalValidator(BigDecimal(0), BigDecimal(10), false, false)
        assertEquals(IValidator.NO_ERROR_RESOURCE_ID, validator.validate("9"))

        validator = TestNumericDecimalValidator(BigDecimal(10), BigDecimal(0), false, false)
        assertEquals(IValidator.NO_ERROR_RESOURCE_ID, validator.validate("9"))
    }
}

