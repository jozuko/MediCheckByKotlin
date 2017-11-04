package com.studiojozu.medicheck.domain.model.medicine.validator

import com.studiojozu.common.domain.model.IValidator
import com.studiojozu.medicheck.R
import com.studiojozu.medicheck.domain.model.medicine.TakeIntervalModeType
import com.studiojozu.medicheck.domain.model.setting.ATestParent
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.powermock.core.classloader.annotations.PowerMockIgnore
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Suppress("FunctionName")
@RunWith(RobolectricTestRunner::class)
@Config(manifest = "src/main/AndroidManifest.xml")
@PowerMockIgnore("org.mockito.*", "org.robolectric.*", "android.*")
class TakeIntervalValidatorTest : ATestParent() {

    @Test
    @Throws(Exception::class)
    fun success_days() {
        val validator = TakeIntervalValidator(TakeIntervalModeType.DateIntervalPattern.DAYS)
        Assert.assertEquals(IValidator.NO_ERROR_RESOURCE_ID, validator.validate("0"))
        Assert.assertEquals(IValidator.NO_ERROR_RESOURCE_ID, validator.validate("1"))
        Assert.assertEquals(IValidator.NO_ERROR_RESOURCE_ID, validator.validate("364"))
        Assert.assertEquals(IValidator.NO_ERROR_RESOURCE_ID, validator.validate("365"))
    }

    @Test
    @Throws(Exception::class)
    fun success_month() {
        val validator = TakeIntervalValidator(TakeIntervalModeType.DateIntervalPattern.MONTH)
        Assert.assertEquals(IValidator.NO_ERROR_RESOURCE_ID, validator.validate("1"))
        Assert.assertEquals(IValidator.NO_ERROR_RESOURCE_ID, validator.validate("2"))
        Assert.assertEquals(IValidator.NO_ERROR_RESOURCE_ID, validator.validate("30"))
        Assert.assertEquals(IValidator.NO_ERROR_RESOURCE_ID, validator.validate("31"))
    }

    @Test
    @Throws(Exception::class)
    fun failed_days() {
        val validator = TakeIntervalValidator(TakeIntervalModeType.DateIntervalPattern.DAYS)
        Assert.assertEquals(R.string.validation_out_of_range, validator.validate("-1"))
        Assert.assertEquals(R.string.validation_out_of_range, validator.validate("366"))
        Assert.assertEquals(R.string.validation_require, validator.validate(""))
        Assert.assertEquals(R.string.validation_numeric, validator.validate("a"))
    }

    @Test
    @Throws(Exception::class)
    fun failed_month() {
        val validator = TakeIntervalValidator(TakeIntervalModeType.DateIntervalPattern.MONTH)
        Assert.assertEquals(R.string.validation_out_of_range, validator.validate("0"))
        Assert.assertEquals(R.string.validation_out_of_range, validator.validate("32"))
        Assert.assertEquals(R.string.validation_require, validator.validate(""))
        Assert.assertEquals(R.string.validation_numeric, validator.validate("a"))
    }
}