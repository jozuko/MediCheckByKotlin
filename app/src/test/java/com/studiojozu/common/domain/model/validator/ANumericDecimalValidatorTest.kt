package com.studiojozu.common.domain.model.validator

import com.studiojozu.medicheck.R
import com.studiojozu.medicheck.domain.model.medicine.MedicineUnit
import com.studiojozu.medicheck.domain.model.medicine.MedicineUnitDisplayOrderType
import com.studiojozu.medicheck.domain.model.medicine.MedicineUnitIdType
import com.studiojozu.medicheck.domain.model.medicine.MedicineUnitValueType
import com.studiojozu.medicheck.domain.model.setting.ATestParent
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.math.BigDecimal

@Suppress("FunctionName")
@RunWith(RobolectricTestRunner::class)
@Config(manifest = "src/main/AndroidManifest.xml")
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

    @Test
    @Throws(Exception::class)
    fun compareToDisplayOrderPriority() {
        val entity1 = MedicineUnit(
                medicineUnitId = MedicineUnitIdType("11111111"),
                medicineUnitValue = MedicineUnitValueType("1111"),
                medicineUnitDisplayOrder = MedicineUnitDisplayOrderType(1))

        val entity2 = MedicineUnit(
                medicineUnitId = MedicineUnitIdType("11111111"),
                medicineUnitValue = MedicineUnitValueType("1111"),
                medicineUnitDisplayOrder = MedicineUnitDisplayOrderType(2))

        val entity3 = entity1.copy()

        assertTrue(entity1.compareToDisplayOrderPriority(entity2) < 0)
        assertTrue(entity2.compareToDisplayOrderPriority(entity1) > 0)
        assertTrue(entity1.compareToDisplayOrderPriority(entity3) == 0)
        assertFalse(entity1 === entity3)
    }

    @Test
    @Throws(Exception::class)
    fun compareToDisplayValuePriority() {
        val entity1 = MedicineUnit(
                medicineUnitId = MedicineUnitIdType("11111111"),
                medicineUnitValue = MedicineUnitValueType("1111"),
                medicineUnitDisplayOrder = MedicineUnitDisplayOrderType(1))

        val entity2 = MedicineUnit(
                medicineUnitId = MedicineUnitIdType("11111111"),
                medicineUnitValue = MedicineUnitValueType("2222"),
                medicineUnitDisplayOrder = MedicineUnitDisplayOrderType(1))

        val entity3 = entity1.copy()

        assertTrue(entity1.compareToDisplayOrderPriority(entity2) < 0)
        assertTrue(entity2.compareToDisplayOrderPriority(entity1) > 0)
        assertTrue(entity1.compareToDisplayOrderPriority(entity3) == 0)
        assertFalse(entity1 === entity3)
    }
}

