package com.studiojozu.medicheck.domain.model.medicine.validator

import com.studiojozu.common.domain.model.validator.IValidator
import com.studiojozu.medicheck.R
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
class MedicineUnitValueValidatorTest : ATestParent() {

    @Test
    @Throws(Exception::class)
    fun success() {
        val validator = MedicineUnitValueValidator()
        Assert.assertEquals(IValidator.NO_ERROR_RESOURCE_ID, validator.validate(" "))
        Assert.assertEquals(IValidator.NO_ERROR_RESOURCE_ID, validator.validate("abc"))
        Assert.assertEquals(IValidator.NO_ERROR_RESOURCE_ID, validator.validate("薬の単位"))
        Assert.assertEquals(IValidator.NO_ERROR_RESOURCE_ID, validator.validate("錠"))
    }

    @Test
    @Throws(Exception::class)
    fun failed() {
        val validator = MedicineUnitValueValidator()
        val data: String? = null
        Assert.assertEquals(R.string.validation_require, validator.validate())
        Assert.assertEquals(R.string.validation_require, validator.validate(data))
    }
}