@file:Suppress("FunctionName")

package com.studiojozu.common.domain.model.general

import com.studiojozu.medicheck.domain.model.setting.ATestParent
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.powermock.core.classloader.annotations.PowerMockIgnore
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.util.*

@RunWith(RobolectricTestRunner::class)
@Config(manifest = "src/main/AndroidManifest.xml")
@PowerMockIgnore("org.mockito.*", "org.robolectric.*", "android.*")
class ATimeTypeTest : ATestParent() {

    private val mValueProperty = findProperty(TestTimeType::class, "mValue")

    @Test
    @Throws(Exception::class)
    fun constructor_Calendar() {
        val now = Calendar.getInstance()
        val testTimeType = TestTimeType(now)

        now.set(Calendar.YEAR, 2000)
        now.set(Calendar.MONTH, 0)
        now.set(Calendar.DAY_OF_MONTH, 1)
        now.set(Calendar.SECOND, 0)
        now.set(Calendar.MILLISECOND, 0)
        assertEquals(now, mValueProperty.call(testTimeType))
    }

    @Test
    @Throws(Exception::class)
    fun constructor_Long() {
        val now = Calendar.getInstance()
        val testTimeType = TestTimeType(now.timeInMillis)

        now.set(Calendar.YEAR, 2000)
        now.set(Calendar.MONTH, 0)
        now.set(Calendar.DAY_OF_MONTH, 1)
        now.set(Calendar.SECOND, 0)
        now.set(Calendar.MILLISECOND, 0)
        assertEquals(now, mValueProperty.call(testTimeType))
    }

    @Test
    @Throws(Exception::class)
    fun constructor_TestTimeType() {
        val now = Calendar.getInstance()
        val testTimeType = TestTimeType(TestTimeType(now))

        now.set(Calendar.YEAR, 2000)
        now.set(Calendar.MONTH, 0)
        now.set(Calendar.DAY_OF_MONTH, 1)
        now.set(Calendar.SECOND, 0)
        now.set(Calendar.MILLISECOND, 0)
        assertEquals(now, mValueProperty.call(testTimeType))
    }

    @Test
    @Throws(Exception::class)
    fun constructor_TestDatetimeType() {
        val now = Calendar.getInstance()
        val testTimeType = TestTimeType(TestDatetimeType(now))

        now.set(Calendar.YEAR, 2000)
        now.set(Calendar.MONTH, 0)
        now.set(Calendar.DAY_OF_MONTH, 1)
        now.set(Calendar.SECOND, 0)
        now.set(Calendar.MILLISECOND, 0)
        assertEquals(now, mValueProperty.call(testTimeType))
    }

    @Test
    @Throws(Exception::class)
    fun constructor_Unknown() {
        try {
            TestTimeType("test")
            fail()
        } catch (e: IllegalArgumentException) {
            assertEquals("unknown type.", e.message)
        }
    }

    @Test
    @Throws(Exception::class)
    fun constructor_HourMinute() {
        val now = Calendar.getInstance()
        now.set(2000, 0, 1, 2, 3, 0)
        now.set(Calendar.MILLISECOND, 0)

        val testTimeType = TestTimeType(2, 3)
        assertEquals(now, mValueProperty.call(testTimeType))
    }

    @Test
    @Throws(Exception::class)
    fun compareTo() {
        val nowSmall = Calendar.getInstance()
        nowSmall.set(2000, 0, 1, 2, 3, 0)
        nowSmall.set(Calendar.MILLISECOND, 0)

        val nowBig = Calendar.getInstance()
        nowBig.set(2000, 0, 1, 23, 59, 0)
        nowBig.set(Calendar.MILLISECOND, 0)

        assertTrue(TestTimeType(nowSmall) == TestTimeType(nowSmall))
        assertTrue(TestTimeType(nowSmall) < TestTimeType(nowBig))
        assertTrue(TestTimeType(nowBig) > TestTimeType(nowSmall))
    }

    @Test
    @Throws(Exception::class)
    fun dbValue() {
        val now = Calendar.getInstance()
        val testTimeType = TestTimeType(now)

        now.set(Calendar.YEAR, 2000)
        now.set(Calendar.MONTH, 0)
        now.set(Calendar.DAY_OF_MONTH, 1)
        now.set(Calendar.SECOND, 0)
        now.set(Calendar.MILLISECOND, 0)
        assertEquals(now.timeInMillis, testTimeType.dbValue)
    }

    @Test
    @Throws(Exception::class)
    fun displayValue() {
        val now = Calendar.getInstance()

        now.set(2000, 0, 1, 2, 3, 0)
        now.set(Calendar.MILLISECOND, 0)
        assertEquals("2:03", TestTimeType(now).displayValue)

        now.set(2000, 0, 1, 23, 59, 0)
        now.set(Calendar.MILLISECOND, 0)
        assertEquals("23:59", TestTimeType(now).displayValue)
    }

    @Test
    @Throws(Exception::class)
    fun sameTime() {
        val nowSmall = Calendar.getInstance()
        nowSmall.set(2000, 0, 1, 2, 3, 0)
        nowSmall.set(Calendar.MILLISECOND, 0)

        val nowSmall2 = Calendar.getInstance()
        nowSmall2.set(2000, 0, 2, 2, 3, 0)
        nowSmall2.set(Calendar.MILLISECOND, 0)

        val nowBig = Calendar.getInstance()
        nowBig.set(2000, 0, 1, 23, 59, 0)
        nowBig.set(Calendar.MILLISECOND, 0)

        assertTrue(TestTimeType(nowSmall).sameTime(nowSmall))
        assertTrue(TestTimeType(nowSmall).sameTime(nowSmall2))
        assertFalse(TestTimeType(nowSmall).sameTime(nowBig))
    }

    @Test
    @Throws(Exception::class)
    fun clone() {
        val now = Calendar.getInstance()
        now.set(2000, 0, 1, 2, 3, 0)
        now.set(Calendar.MILLISECOND, 0)

        val timeType1 = TestTimeType(now)
        val timeType2 = timeType1.clone()
        assertTrue(timeType1 == timeType2)

        val mValue1 = mValueProperty.call(timeType1) as Calendar
        mValue1.set(2000, 11, 31, 2, 3, 0)
        assertTrue(timeType1 != timeType2)
    }
}