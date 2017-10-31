package com.studiojozu.medicheck.domain.model.medicine

import android.content.ContentValues
import com.studiojozu.medicheck.domain.model.setting.ATestParent
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.powermock.core.classloader.annotations.PowerMockIgnore
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Suppress("FunctionName")
@RunWith(RobolectricTestRunner::class)
@Config(manifest = "src/main/AndroidManifest.xml")
@PowerMockIgnore("org.mockito.*", "org.robolectric.*", "android.*")
class TakeIntervalModeTypeTest : ATestParent() {
    private val mValueProperty = findProperty(TakeIntervalModeType::class, "mValue")

    @Test
    @Throws(Exception::class)
    fun constructor_NoParameter() {
        val takeIntervalModeType = TakeIntervalModeType()
        assertEquals(TakeIntervalModeType.DateIntervalPattern.DAYS, mValueProperty.call(takeIntervalModeType))
    }

    @Test
    @Throws(Exception::class)
    fun constructor_DateIntervalPattern() {
        var param: TakeIntervalModeType.DateIntervalPattern = TakeIntervalModeType.DateIntervalPattern.MONTH
        assertEquals(param, mValueProperty.call(TakeIntervalModeType(param)))

        param = TakeIntervalModeType.DateIntervalPattern.DAYS
        assertEquals(param, mValueProperty.call(TakeIntervalModeType(param)))
    }

    @Test
    @Throws(Exception::class)
    fun constructor_Long() {
        var param: Long = 0
        assertEquals(TakeIntervalModeType.DateIntervalPattern.DAYS, mValueProperty.call(TakeIntervalModeType(param)))

        param = 1
        assertEquals(TakeIntervalModeType.DateIntervalPattern.MONTH, mValueProperty.call(TakeIntervalModeType(param)))
    }

    @Test
    @Throws(Exception::class)
    fun constructor_Int() {
        @Suppress("RedundantExplicitType")
        var param: Int = 0
        assertEquals(TakeIntervalModeType.DateIntervalPattern.DAYS, mValueProperty.call(TakeIntervalModeType(param)))

        param = 1
        assertEquals(TakeIntervalModeType.DateIntervalPattern.MONTH, mValueProperty.call(TakeIntervalModeType(param)))
    }

    @Test
    @Throws(Exception::class)
    fun constructor_TakeIntervalModeType() {
        var param = TakeIntervalModeType(TakeIntervalModeType.DateIntervalPattern.MONTH)
        assertEquals(TakeIntervalModeType.DateIntervalPattern.MONTH, mValueProperty.call(TakeIntervalModeType(param)))

        param = TakeIntervalModeType(TakeIntervalModeType.DateIntervalPattern.DAYS)
        assertEquals(TakeIntervalModeType.DateIntervalPattern.DAYS, mValueProperty.call(TakeIntervalModeType(param)))
    }

    @Test
    @Throws(Exception::class)
    fun constructor_Unknown() = try {
        TakeIntervalModeType("0")
        fail()
    } catch (e: IllegalArgumentException) {
        assertTrue(true)
    }

    @Test
    @Throws(Exception::class)
    fun dbValue() {
        assertEquals(0, TakeIntervalModeType(TakeIntervalModeType.DateIntervalPattern.DAYS).dbValue)
        assertEquals(1, TakeIntervalModeType(TakeIntervalModeType.DateIntervalPattern.MONTH).dbValue)
    }

    @Test
    @Throws(Exception::class)
    fun displayValue() {
        assertEquals("DAYS", TakeIntervalModeType(TakeIntervalModeType.DateIntervalPattern.DAYS).displayValue)
        assertEquals("MONTH", TakeIntervalModeType(TakeIntervalModeType.DateIntervalPattern.MONTH).displayValue)
    }

    @Test
    @Throws(Exception::class)
    fun setContentValue() {
        val contentValue = ContentValues()
        val columnName = "columnName"
        val takeIntervalModeType = TakeIntervalModeType(TakeIntervalModeType.DateIntervalPattern.MONTH)

        takeIntervalModeType.setContentValue(columnName, contentValue)
        assertEquals(1, contentValue.get(columnName))
    }

    @Test
    @Throws(Exception::class)
    fun compareTo() {
        var takeIntervalModeType1 = TakeIntervalModeType(TakeIntervalModeType.DateIntervalPattern.DAYS)
        var takeIntervalModeType2 = TakeIntervalModeType(TakeIntervalModeType.DateIntervalPattern.DAYS)
        assertTrue(takeIntervalModeType1 == takeIntervalModeType2)

        takeIntervalModeType1 = TakeIntervalModeType(TakeIntervalModeType.DateIntervalPattern.DAYS)
        takeIntervalModeType2 = TakeIntervalModeType(TakeIntervalModeType.DateIntervalPattern.MONTH)
        assertTrue(takeIntervalModeType1 < takeIntervalModeType2)

        takeIntervalModeType1 = TakeIntervalModeType(TakeIntervalModeType.DateIntervalPattern.MONTH)
        takeIntervalModeType2 = TakeIntervalModeType(TakeIntervalModeType.DateIntervalPattern.DAYS)
        assertTrue(takeIntervalModeType1 > takeIntervalModeType2)
    }
}