package com.studiojozu.medicheck.domain.model.medicine

import com.studiojozu.medicheck.domain.model.setting.ATestParent
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Suppress("FunctionName")
@RunWith(RobolectricTestRunner::class)
@Config(manifest = "src/main/AndroidManifest.xml")
class MedicineIntervalModeTypeTest : ATestParent() {
    private val mValueProperty = findProperty(MedicineIntervalModeType::class, "mValue")

    @Test
    @Throws(Exception::class)
    fun constructor_NoParameter() {
        val takeIntervalModeType = MedicineIntervalModeType()
        assertEquals(MedicineIntervalModeType.DateIntervalPattern.DAYS, mValueProperty.call(takeIntervalModeType))
    }

    @Test
    @Throws(Exception::class)
    fun constructor_DateIntervalPattern() {
        var param: MedicineIntervalModeType.DateIntervalPattern = MedicineIntervalModeType.DateIntervalPattern.MONTH
        assertEquals(param, mValueProperty.call(MedicineIntervalModeType(param)))

        param = MedicineIntervalModeType.DateIntervalPattern.DAYS
        assertEquals(param, mValueProperty.call(MedicineIntervalModeType(param)))
    }

    @Test
    @Throws(Exception::class)
    fun constructor_Long() {
        var param: Long = 0
        assertEquals(MedicineIntervalModeType.DateIntervalPattern.DAYS, mValueProperty.call(MedicineIntervalModeType(param)))

        param = 1
        assertEquals(MedicineIntervalModeType.DateIntervalPattern.MONTH, mValueProperty.call(MedicineIntervalModeType(param)))
    }

    @Test
    @Throws(Exception::class)
    fun constructor_Int() {
        @Suppress("RedundantExplicitType")
        var param: Int = 0
        assertEquals(MedicineIntervalModeType.DateIntervalPattern.DAYS, mValueProperty.call(MedicineIntervalModeType(param)))

        param = 1
        assertEquals(MedicineIntervalModeType.DateIntervalPattern.MONTH, mValueProperty.call(MedicineIntervalModeType(param)))
    }

    @Test
    @Throws(Exception::class)
    fun constructor_TakeIntervalModeType() {
        var param = MedicineIntervalModeType(MedicineIntervalModeType.DateIntervalPattern.MONTH)
        assertEquals(MedicineIntervalModeType.DateIntervalPattern.MONTH, mValueProperty.call(MedicineIntervalModeType(param)))

        param = MedicineIntervalModeType(MedicineIntervalModeType.DateIntervalPattern.DAYS)
        assertEquals(MedicineIntervalModeType.DateIntervalPattern.DAYS, mValueProperty.call(MedicineIntervalModeType(param)))
    }

    @Test
    @Throws(Exception::class)
    fun constructor_Unknown() = try {
        MedicineIntervalModeType("0")
        fail()
    } catch (e: IllegalArgumentException) {
        assertTrue(true)
    }

    @Test
    @Throws(Exception::class)
    fun dbValue() {
        assertEquals(0, MedicineIntervalModeType(MedicineIntervalModeType.DateIntervalPattern.DAYS).dbValue)
        assertEquals(1, MedicineIntervalModeType(MedicineIntervalModeType.DateIntervalPattern.MONTH).dbValue)
    }

    @Test
    @Throws(Exception::class)
    fun displayValue() {
        assertEquals("DAYS", MedicineIntervalModeType(MedicineIntervalModeType.DateIntervalPattern.DAYS).displayValue)
        assertEquals("MONTH", MedicineIntervalModeType(MedicineIntervalModeType.DateIntervalPattern.MONTH).displayValue)
    }

    @Test
    @Throws(Exception::class)
    fun compareTo() {
        var takeIntervalModeType1 = MedicineIntervalModeType(MedicineIntervalModeType.DateIntervalPattern.DAYS)
        var takeIntervalModeType2 = MedicineIntervalModeType(MedicineIntervalModeType.DateIntervalPattern.DAYS)
        assertTrue(takeIntervalModeType1 == takeIntervalModeType2)

        takeIntervalModeType1 = MedicineIntervalModeType(MedicineIntervalModeType.DateIntervalPattern.DAYS)
        takeIntervalModeType2 = MedicineIntervalModeType(MedicineIntervalModeType.DateIntervalPattern.MONTH)
        assertTrue(takeIntervalModeType1 < takeIntervalModeType2)

        takeIntervalModeType1 = MedicineIntervalModeType(MedicineIntervalModeType.DateIntervalPattern.MONTH)
        takeIntervalModeType2 = MedicineIntervalModeType(MedicineIntervalModeType.DateIntervalPattern.DAYS)
        assertTrue(takeIntervalModeType1 > takeIntervalModeType2)
    }
}