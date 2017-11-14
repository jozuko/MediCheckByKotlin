package com.studiojozu.medicheck.domain.model.medicine.validator

import com.studiojozu.common.domain.model.validator.IValidator
import com.studiojozu.medicheck.R
import com.studiojozu.medicheck.domain.model.setting.ATestParent
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Suppress("FunctionName")
@RunWith(RobolectricTestRunner::class)
@Config(manifest = "src/main/AndroidManifest.xml")
class TakeNumberValidatorTest : ATestParent() {

    @Test
    @Throws(Exception::class)
    fun success_days() {
        val validator = TakeNumberValidator()
        Assert.assertEquals(IValidator.NO_ERROR_RESOURCE_ID, validator.validate("1"))
        Assert.assertEquals(IValidator.NO_ERROR_RESOURCE_ID, validator.validate("2"))
        Assert.assertEquals(IValidator.NO_ERROR_RESOURCE_ID, validator.validate((Integer.MAX_VALUE - 1).toString()))
        Assert.assertEquals(IValidator.NO_ERROR_RESOURCE_ID, validator.validate(Integer.MAX_VALUE.toString()))
    }

    @Test
    @Throws(Exception::class)
    fun failed_days() {
        val validator = TakeNumberValidator()
        Assert.assertEquals(R.string.validation_out_of_range, validator.validate("0"))
        Assert.assertEquals(R.string.validation_out_of_range, validator.validate(Long.MAX_VALUE.toString()))
        Assert.assertEquals(R.string.validation_require, validator.validate(""))
        Assert.assertEquals(R.string.validation_numeric, validator.validate("a"))
    }
}