@file:Suppress("FunctionName")

package com.studiojozu.common.domain.model.general

import com.studiojozu.medicheck.domain.model.setting.ATestParent
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.util.*

@RunWith(RobolectricTestRunner::class)
@Config(manifest = "src/main/AndroidManifest.xml")
class ADateTypeTest : ATestParent() {

    private val mValueProperty = findProperty(TestDateType::class, "mValue")

    @Test
    @Throws(Exception::class)
    fun constructor_Calendar() {
        val now = Calendar.getInstance()
        val testDateType = TestDateType(now)

        now.set(Calendar.HOUR_OF_DAY, 0)
        now.set(Calendar.MINUTE, 0)
        now.set(Calendar.SECOND, 0)
        now.set(Calendar.MILLISECOND, 0)
        assertEquals(now, mValueProperty.call(testDateType))
    }

    @Test
    @Throws(Exception::class)
    fun constructor_Long() {
        val now = Calendar.getInstance()
        val testDateType = TestDateType(now.timeInMillis)

        now.set(Calendar.HOUR_OF_DAY, 0)
        now.set(Calendar.MINUTE, 0)
        now.set(Calendar.SECOND, 0)
        now.set(Calendar.MILLISECOND, 0)
        assertEquals(now, mValueProperty.call(testDateType))
    }

    @Test
    @Throws(Exception::class)
    fun constructor_TestDateType() {
        val now = Calendar.getInstance()
        val testDateType = TestDateType(TestDateType(now))

        now.set(Calendar.HOUR_OF_DAY, 0)
        now.set(Calendar.MINUTE, 0)
        now.set(Calendar.SECOND, 0)
        now.set(Calendar.MILLISECOND, 0)
        assertEquals(now, mValueProperty.call(testDateType))
    }

    @Test
    @Throws(Exception::class)
    fun constructor_TestDatetimeType() {
        val now = Calendar.getInstance()
        val testDateType = TestDateType(TestDatetimeType(now))

        now.set(Calendar.HOUR_OF_DAY, 0)
        now.set(Calendar.MINUTE, 0)
        now.set(Calendar.SECOND, 0)
        now.set(Calendar.MILLISECOND, 0)
        assertEquals(now, mValueProperty.call(testDateType))
    }

    @Test
    @Throws(Exception::class)
    fun constructor_Unknown() = try {
        TestDateType("test")
        fail()
    } catch (e: IllegalArgumentException) {
        assertEquals("unknown type.", e.message)
    }

    @Test
    @Throws(Exception::class)
    fun constructor_YearMonthDay() {
        val now = Calendar.getInstance()
        now.set(2017, 0, 2, 0, 0, 0)
        now.set(Calendar.MILLISECOND, 0)

        val testDateType = TestDateType(2017, 1, 2)
        assertEquals(now, mValueProperty.call(testDateType))
    }

    @Test
    @Throws(Exception::class)
    fun compareTo() {
        val nowSmall = Calendar.getInstance()
        nowSmall.set(2017, 0, 2, 3, 4, 0)
        nowSmall.set(Calendar.MILLISECOND, 0)

        val nowBig = Calendar.getInstance()
        nowBig.set(2017, 11, 31, 23, 59, 0)
        nowBig.set(Calendar.MILLISECOND, 0)

        assertTrue(TestDateType(nowSmall) == TestDateType(nowSmall))
        assertTrue(TestDateType(nowSmall) < TestDateType(nowBig))
        assertTrue(TestDateType(nowBig) > TestDateType(nowSmall))
    }

    @Test
    @Throws(Exception::class)
    fun equalsCalendar() {
        val nowSmall = Calendar.getInstance()
        nowSmall.set(2017, 0, 2, 0, 0, 0)
        nowSmall.set(Calendar.MILLISECOND, 0)

        val nowSmall2 = Calendar.getInstance()
        nowSmall2.set(2017, 0, 2, 1, 0, 0)
        nowSmall2.set(Calendar.MILLISECOND, 0)

        val nowBig = Calendar.getInstance()
        nowBig.set(2017, 11, 31, 0, 0, 0)
        nowBig.set(Calendar.MILLISECOND, 0)

        assertTrue(TestDateType(nowSmall).sameDate(nowSmall))
        assertTrue(TestDateType(nowSmall).sameDate(nowSmall2))
        assertFalse(TestDateType(nowSmall).sameDate(nowBig))
    }

    @Test
    @Throws(Exception::class)
    fun addDay() {
        val now = Calendar.getInstance()
        now.set(2017, 0, 2, 0, 0, 0)
        now.set(Calendar.MILLISECOND, 0)
        val testDateType = TestDateType(now)

        assertEquals(now.timeInMillis, testDateType.addDay(0).dbValue)

        now.set(2017, 0, 3, 0, 0, 0)
        assertEquals(now.timeInMillis, testDateType.addDay(1).dbValue)

        now.set(2017, 0, 1, 0, 0, 0)
        assertEquals(now.timeInMillis, testDateType.addDay(-1).dbValue)

        now.set(2017, 1, 1, 0, 0, 0)
        assertEquals(now.timeInMillis, testDateType.addDay(30).dbValue)
    }

    @Test
    @Throws(Exception::class)
    fun dbValue() {
        val now = Calendar.getInstance()
        val testDateType = TestDateType(now)

        now.set(Calendar.HOUR_OF_DAY, 0)
        now.set(Calendar.MINUTE, 0)
        now.set(Calendar.SECOND, 0)
        now.set(Calendar.MILLISECOND, 0)
        assertEquals(now.timeInMillis, testDateType.dbValue)
    }

    @Test
    @Throws(Exception::class)
    fun displayValue() {
        val now = Calendar.getInstance()

        now.set(2017, 0, 2, 3, 4, 0)
        now.set(Calendar.MILLISECOND, 0)
        assertEquals("17/01/02", TestDateType(now).displayValue)

        now.set(2017, 11, 31, 23, 59, 0)
        now.set(Calendar.MILLISECOND, 0)
        assertEquals("17/12/31", TestDateType(now).displayValue)
    }
}