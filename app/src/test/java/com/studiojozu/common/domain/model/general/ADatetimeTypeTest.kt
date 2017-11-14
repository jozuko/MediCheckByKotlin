@file:Suppress("FunctionName")

package com.studiojozu.common.domain.model.general

import com.studiojozu.medicheck.domain.model.setting.ATestParent
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.util.*

@RunWith(RobolectricTestRunner::class)
@Config(manifest = "src/main/AndroidManifest.xml")
class ADatetimeTypeTest : ATestParent() {

    private val mValueProperty = findProperty(TestDatetimeType::class, "mValue")

    @Test
    @Throws(Exception::class)
    fun constructor_Calendar() {
        val now = Calendar.getInstance()
        val testDatetimeType = TestDatetimeType(now)

        now.set(Calendar.SECOND, 0)
        now.set(Calendar.MILLISECOND, 0)
        assertEquals(now, mValueProperty.call(testDatetimeType))
    }

    @Test
    @Throws(Exception::class)
    fun constructor_Long() {
        val now = Calendar.getInstance()
        val testDatetimeType = TestDatetimeType(now.timeInMillis)

        now.set(Calendar.SECOND, 0)
        now.set(Calendar.MILLISECOND, 0)
        assertEquals(now, mValueProperty.call(testDatetimeType))
    }

    @Test
    @Throws(Exception::class)
    fun constructor_TestDatetimeType() {
        val now = Calendar.getInstance()
        val testDatetimeType = TestDatetimeType(TestDatetimeType(now))

        now.set(Calendar.SECOND, 0)
        now.set(Calendar.MILLISECOND, 0)
        assertEquals(now, mValueProperty.call(testDatetimeType))
    }

    @Test
    @Throws(Exception::class)
    fun constructor_Unknown() = try {
        TestDatetimeType("test")
        fail()
    } catch (e: IllegalArgumentException) {
        assertEquals("unknown type.", e.message)
    }

    @Test
    @Throws(Exception::class)
    fun constructor_DateModelTimeModel() {
        val now = Calendar.getInstance()
        val dateModel = TestDateType(now.get(Calendar.YEAR), now.get(Calendar.MONTH) + 1, now.get(Calendar.DAY_OF_MONTH))
        val timeModel = TestTimeType(now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE))

        val testDatetimeType = TestDatetimeType(dateModel, timeModel)
        now.set(Calendar.SECOND, 0)
        now.set(Calendar.MILLISECOND, 0)
        assertEquals(now, mValueProperty.call(testDatetimeType))
    }

    @Test
    @Throws(Exception::class)
    fun constructor_YearMonthDayHourMinute() {
        val now = Calendar.getInstance()
        now.set(2017, 0, 2, 3, 4, 0)
        now.set(Calendar.SECOND, 0)
        now.set(Calendar.MILLISECOND, 0)

        val testDatetimeType = TestDatetimeType(2017, 1, 2, 3, 4)
        assertEquals(now, mValueProperty.call(testDatetimeType))
    }

    @Test
    @Throws(Exception::class)
    fun dbValue() {
        val now = Calendar.getInstance()
        val testDatetimeType = TestDatetimeType(now)

        now.set(Calendar.SECOND, 0)
        now.set(Calendar.MILLISECOND, 0)
        assertEquals(now.timeInMillis, testDatetimeType.dbValue)
    }

    @Test
    @Throws(Exception::class)
    fun displayValue() {
        val now = Calendar.getInstance()

        now.set(2017, 0, 2, 3, 4, 0)
        now.set(Calendar.MILLISECOND, 0)
        val testDatetimeTypeAm = TestDatetimeType(now)
        assertEquals("17/01/02 3:04", testDatetimeTypeAm.displayValue)

        now.set(2017, 11, 31, 23, 59, 0)
        now.set(Calendar.MILLISECOND, 0)
        val testDatetimeTypePm = TestDatetimeType(now)
        assertEquals("17/12/31 23:59", testDatetimeTypePm.displayValue)
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

        Assert.assertTrue(TestDatetimeType(nowSmall) == TestDatetimeType(nowSmall))
        Assert.assertTrue(TestDatetimeType(nowSmall) < TestDatetimeType(nowBig))
        Assert.assertTrue(TestDatetimeType(nowBig) > TestDatetimeType(nowSmall))
    }

    @Test
    @Throws(Exception::class)
    fun year() {
        val now = Calendar.getInstance()
        now.set(2017, 0, 2, 3, 4, 0)
        now.set(Calendar.MILLISECOND, 0)
        assertEquals(2017, TestDatetimeType(now).year)
    }

    @Test
    @Throws(Exception::class)
    fun month() {
        val now = Calendar.getInstance()
        now.set(2017, 0, 2, 3, 4, 0)
        now.set(Calendar.MILLISECOND, 0)
        assertEquals(0, TestDatetimeType(now).month)
    }

    @Test
    @Throws(Exception::class)
    fun dayOfMonth() {
        val now = Calendar.getInstance()
        now.set(2017, 0, 2, 3, 4, 0)
        now.set(Calendar.MILLISECOND, 0)
        assertEquals(2, TestDatetimeType(now).dayOfMonth)
    }

    @Test
    @Throws(Exception::class)
    fun hourOfDay() {
        val now = Calendar.getInstance()
        now.set(2017, 0, 2, 3, 4, 0)
        now.set(Calendar.MILLISECOND, 0)
        assertEquals(3, TestDatetimeType(now).hourOfDay)

        now.set(2017, 0, 2, 16, 4, 0)
        now.set(Calendar.MILLISECOND, 0)
        assertEquals(16, TestDatetimeType(now).hourOfDay)
    }

    @Test
    @Throws(Exception::class)
    fun minute() {
        val now = Calendar.getInstance()
        now.set(2017, 0, 2, 3, 4, 0)
        now.set(Calendar.MILLISECOND, 0)
        assertEquals(4, TestDatetimeType(now).minute)
    }

    @Test
    @Throws(Exception::class)
    fun diffMinutes() {
        val nowSmall = Calendar.getInstance()
        nowSmall.set(2017, 0, 2, 3, 0, 0)
        nowSmall.set(Calendar.MILLISECOND, 0)

        val nowBig = Calendar.getInstance()
        nowBig.set(2017, 0, 2, 4, 0, 0)
        nowBig.set(Calendar.MILLISECOND, 0)

        val nowBig2 = Calendar.getInstance()
        nowBig2.set(2017, 0, 2, 3, 1, 0)
        nowBig2.set(Calendar.MILLISECOND, 0)

        assertEquals(0, TestDatetimeType(nowSmall).diffMinutes(TestDatetimeType(nowSmall)))
        assertEquals(1, TestDatetimeType(nowBig2).diffMinutes(TestDatetimeType(nowSmall)))
        assertEquals(-60, TestDatetimeType(nowSmall).diffMinutes(TestDatetimeType(nowBig)))
        assertEquals(60, TestDatetimeType(nowBig).diffMinutes(TestDatetimeType(nowSmall)))
    }

    @Test
    @Throws(Exception::class)
    fun setHourMinute() {
        val now = Calendar.getInstance()
        now.set(2017, 0, 2, 3, 4, 0)
        now.set(Calendar.MILLISECOND, 0)

        val testDatetimeType = TestDatetimeType(now)
        testDatetimeType.setHourMinute(5, 6)

        now.set(Calendar.HOUR_OF_DAY, 5)
        now.set(Calendar.MINUTE, 6)
        assertEquals(now.timeInMillis, TestDatetimeType(now).dbValue)
    }

    @Test
    @Throws(Exception::class)
    fun addMinute() {
        val now = Calendar.getInstance()
        now.set(2017, 0, 2, 3, 4, 0)
        now.set(Calendar.MILLISECOND, 0)
        val testDatetimeType = TestDatetimeType(now)

        assertEquals(now.timeInMillis, testDatetimeType.addMinute(0).dbValue)

        now.set(2017, 0, 2, 3, 5, 0)
        assertEquals(now.timeInMillis, testDatetimeType.addMinute(1).dbValue)

        now.set(2017, 0, 2, 3, 3, 0)
        assertEquals(now.timeInMillis, testDatetimeType.addMinute(-1).dbValue)

        now.set(2017, 0, 2, 4, 4, 0)
        assertEquals(now.timeInMillis, testDatetimeType.addMinute(60).dbValue)
    }

    @Test
    @Throws(Exception::class)
    fun addDay() {
        val now = Calendar.getInstance()
        now.set(2017, 0, 2, 3, 4, 0)
        now.set(Calendar.MILLISECOND, 0)
        val testDatetimeType = TestDatetimeType(now)

        assertEquals(now.timeInMillis, testDatetimeType.addDay(0).dbValue)

        now.set(2017, 0, 3, 3, 4, 0)
        assertEquals(now.timeInMillis, testDatetimeType.addDay(1).dbValue)

        now.set(2017, 0, 1, 3, 4, 0)
        assertEquals(now.timeInMillis, testDatetimeType.addDay(-1).dbValue)

        now.set(2017, 1, 1, 3, 4, 0)
        assertEquals(now.timeInMillis, testDatetimeType.addDay(30).dbValue)
    }

    @Test
    @Throws(Exception::class)
    fun addMonth() {
        val now = Calendar.getInstance()
        now.set(2017, 0, 2, 3, 4, 0)
        now.set(Calendar.MILLISECOND, 0)
        val testDatetimeType = TestDatetimeType(now)

        assertEquals(now.timeInMillis, testDatetimeType.addMonth(0).dbValue)

        now.set(2017, 1, 2, 3, 4, 0)
        assertEquals(now.timeInMillis, testDatetimeType.addMonth(1).dbValue)

        now.set(2016, 11, 2, 3, 4, 0)
        assertEquals(now.timeInMillis, testDatetimeType.addMonth(-1).dbValue)

        now.set(2018, 0, 2, 3, 4, 0)
        assertEquals(now.timeInMillis, testDatetimeType.addMonth(12).dbValue)
    }
}