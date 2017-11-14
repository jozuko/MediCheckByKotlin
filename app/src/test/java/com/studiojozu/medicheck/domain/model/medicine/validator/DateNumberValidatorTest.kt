package com.studiojozu.medicheck.domain.model.medicine.validator

import com.studiojozu.common.domain.model.validator.IValidator
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
class DateNumberValidatorTest : ATestParent() {

    @Test
    @Throws(Exception::class)
    fun success() {
        val validator = DateNumberValidator()
        assertEquals(IValidator.NO_ERROR_RESOURCE_ID, validator.validate("1"))
        assertEquals(IValidator.NO_ERROR_RESOURCE_ID, validator.validate("2"))
        assertEquals(IValidator.NO_ERROR_RESOURCE_ID, validator.validate("364"))
        assertEquals(IValidator.NO_ERROR_RESOURCE_ID, validator.validate("365"))
    }

    @Test
    @Throws(Exception::class)
    fun failed() {
        val validator = DateNumberValidator()
        assertEquals(R.string.validation_out_of_range, validator.validate("0"))
        assertEquals(R.string.validation_out_of_range, validator.validate("366"))
        assertEquals(R.string.validation_require, validator.validate(""))
        assertEquals(R.string.validation_numeric, validator.validate("a"))
    }
}