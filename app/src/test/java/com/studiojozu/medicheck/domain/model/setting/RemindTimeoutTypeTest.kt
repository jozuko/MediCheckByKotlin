package com.studiojozu.medicheck.domain.model.setting

import android.content.ContentValues
import com.studiojozu.common.domain.model.general.TestDateType
import com.studiojozu.common.domain.model.general.TestDatetimeType
import com.studiojozu.common.domain.model.general.TestTimeType
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.powermock.core.classloader.annotations.PowerMockIgnore
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import java.util.*

@Suppress("FunctionName")
@RunWith(RobolectricTestRunner::class)
@Config(manifest = "src/main/AndroidManifest.xml")
@PowerMockIgnore("org.mockito.*", "org.robolectric.*", "android.*")
class RemindTimeoutTypeTest : ATestParent() {
    private val mValueProperty = findProperty(RemindTimeoutType::class, "mValue")

    @Test
    @Throws(Exception::class)
    fun constructor_NoParameter() {
        val model = RemindTimeoutType()
        assertEquals(RemindTimeoutType.RemindTimeoutPattern.HOUR_24, mValueProperty.call(model))
    }

    @Test
    @Throws(Exception::class)
    fun constructor_Copy() {
        val model = RemindTimeoutType(RemindTimeoutType(RemindTimeoutType.RemindTimeoutPattern.HOUR_1))
        assertEquals(RemindTimeoutType.RemindTimeoutPattern.HOUR_1, mValueProperty.call(model))
    }

    @Test
    @Throws(Exception::class)
    fun constructor_RemindTimeoutPattern() {
        var param = RemindTimeoutType.RemindTimeoutPattern.HOUR_1
        assertEquals(param, mValueProperty.call(RemindTimeoutType(param)))

        param = RemindTimeoutType.RemindTimeoutPattern.HOUR_2
        assertEquals(param, mValueProperty.call(RemindTimeoutType(param)))

        param = RemindTimeoutType.RemindTimeoutPattern.HOUR_6
        assertEquals(param, mValueProperty.call(RemindTimeoutType(param)))

        param = RemindTimeoutType.RemindTimeoutPattern.HOUR_9
        assertEquals(param, mValueProperty.call(RemindTimeoutType(param)))

        param = RemindTimeoutType.RemindTimeoutPattern.HOUR_12
        assertEquals(param, mValueProperty.call(RemindTimeoutType(param)))

        param = RemindTimeoutType.RemindTimeoutPattern.HOUR_24
        assertEquals(param, mValueProperty.call(RemindTimeoutType(param)))

        param = RemindTimeoutType.RemindTimeoutPattern.MINUTE_1
        assertEquals(param, mValueProperty.call(RemindTimeoutType(param)))

        param = RemindTimeoutType.RemindTimeoutPattern.MINUTE_5
        assertEquals(param, mValueProperty.call(RemindTimeoutType(param)))

        param = RemindTimeoutType.RemindTimeoutPattern.MINUTE_10
        assertEquals(param, mValueProperty.call(RemindTimeoutType(param)))

        param = RemindTimeoutType.RemindTimeoutPattern.MINUTE_15
        assertEquals(param, mValueProperty.call(RemindTimeoutType(param)))

        param = RemindTimeoutType.RemindTimeoutPattern.MINUTE_30
        assertEquals(param, mValueProperty.call(RemindTimeoutType(param)))
    }

    @Test
    @Throws(Exception::class)
    fun constructor_Long() {
        var param = 1L
        assertEquals(RemindTimeoutType.RemindTimeoutPattern.MINUTE_1, mValueProperty.call(RemindTimeoutType(param)))

        param = 5L
        assertEquals(RemindTimeoutType.RemindTimeoutPattern.MINUTE_5, mValueProperty.call(RemindTimeoutType(param)))

        param = 10L
        assertEquals(RemindTimeoutType.RemindTimeoutPattern.MINUTE_10, mValueProperty.call(RemindTimeoutType(param)))

        param = 15L
        assertEquals(RemindTimeoutType.RemindTimeoutPattern.MINUTE_15, mValueProperty.call(RemindTimeoutType(param)))

        param = 30L
        assertEquals(RemindTimeoutType.RemindTimeoutPattern.MINUTE_30, mValueProperty.call(RemindTimeoutType(param)))

        param = 1L * 60L
        assertEquals(RemindTimeoutType.RemindTimeoutPattern.HOUR_1, mValueProperty.call(RemindTimeoutType(param)))

        param = 2L * 60L
        assertEquals(RemindTimeoutType.RemindTimeoutPattern.HOUR_2, mValueProperty.call(RemindTimeoutType(param)))

        param = 6L * 60L
        assertEquals(RemindTimeoutType.RemindTimeoutPattern.HOUR_6, mValueProperty.call(RemindTimeoutType(param)))

        param = 9L * 60L
        assertEquals(RemindTimeoutType.RemindTimeoutPattern.HOUR_9, mValueProperty.call(RemindTimeoutType(param)))

        param = 12L * 60L
        assertEquals(RemindTimeoutType.RemindTimeoutPattern.HOUR_12, mValueProperty.call(RemindTimeoutType(param)))

        param = 24L * 60L
        assertEquals(RemindTimeoutType.RemindTimeoutPattern.HOUR_24, mValueProperty.call(RemindTimeoutType(param)))
    }

    @Test
    @Throws(Exception::class)
    fun constructor_Int() {
        var param = 1
        assertEquals(RemindTimeoutType.RemindTimeoutPattern.MINUTE_1, mValueProperty.call(RemindTimeoutType(param)))

        param = 5
        assertEquals(RemindTimeoutType.RemindTimeoutPattern.MINUTE_5, mValueProperty.call(RemindTimeoutType(param)))

        param = 10
        assertEquals(RemindTimeoutType.RemindTimeoutPattern.MINUTE_10, mValueProperty.call(RemindTimeoutType(param)))

        param = 15
        assertEquals(RemindTimeoutType.RemindTimeoutPattern.MINUTE_15, mValueProperty.call(RemindTimeoutType(param)))

        param = 30
        assertEquals(RemindTimeoutType.RemindTimeoutPattern.MINUTE_30, mValueProperty.call(RemindTimeoutType(param)))

        param = 1 * 60
        assertEquals(RemindTimeoutType.RemindTimeoutPattern.HOUR_1, mValueProperty.call(RemindTimeoutType(param)))

        param = 2 * 60
        assertEquals(RemindTimeoutType.RemindTimeoutPattern.HOUR_2, mValueProperty.call(RemindTimeoutType(param)))

        param = 6 * 60
        assertEquals(RemindTimeoutType.RemindTimeoutPattern.HOUR_6, mValueProperty.call(RemindTimeoutType(param)))

        param = 9 * 60
        assertEquals(RemindTimeoutType.RemindTimeoutPattern.HOUR_9, mValueProperty.call(RemindTimeoutType(param)))

        param = 12 * 60
        assertEquals(RemindTimeoutType.RemindTimeoutPattern.HOUR_12, mValueProperty.call(RemindTimeoutType(param)))

        param = 24 * 60
        assertEquals(RemindTimeoutType.RemindTimeoutPattern.HOUR_24, mValueProperty.call(RemindTimeoutType(param)))
    }

    @Test
    @Throws(Exception::class)
    fun constructor_Unknown() = try {
        RemindTimeoutType("1")
        fail()
    } catch (e: IllegalArgumentException) {
        assertTrue(true)
    }

    @Test
    @Throws(Exception::class)
    fun dbValue() {
        assertEquals(1, RemindTimeoutType(RemindTimeoutType.RemindTimeoutPattern.MINUTE_1).dbValue)
        assertEquals(5, RemindTimeoutType(RemindTimeoutType.RemindTimeoutPattern.MINUTE_5).dbValue)
        assertEquals(10, RemindTimeoutType(RemindTimeoutType.RemindTimeoutPattern.MINUTE_10).dbValue)
        assertEquals(15, RemindTimeoutType(RemindTimeoutType.RemindTimeoutPattern.MINUTE_15).dbValue)
        assertEquals(30, RemindTimeoutType(RemindTimeoutType.RemindTimeoutPattern.MINUTE_30).dbValue)
        assertEquals(1 * 60, RemindTimeoutType(RemindTimeoutType.RemindTimeoutPattern.HOUR_1).dbValue)
        assertEquals(2 * 60, RemindTimeoutType(RemindTimeoutType.RemindTimeoutPattern.HOUR_2).dbValue)
        assertEquals(6 * 60, RemindTimeoutType(RemindTimeoutType.RemindTimeoutPattern.HOUR_6).dbValue)
        assertEquals(9 * 60, RemindTimeoutType(RemindTimeoutType.RemindTimeoutPattern.HOUR_9).dbValue)
        assertEquals(12 * 60, RemindTimeoutType(RemindTimeoutType.RemindTimeoutPattern.HOUR_12).dbValue)
        assertEquals(24 * 60, RemindTimeoutType(RemindTimeoutType.RemindTimeoutPattern.HOUR_24).dbValue)
    }

    @Test
    @Throws(Exception::class)
    fun displayValue() = try {
        RemindTimeoutType(RemindTimeoutType.RemindTimeoutPattern.MINUTE_1).displayValue
        fail()
    } catch (e: RuntimeException) {
        assertEquals("you need to call getDisplayValue(Resources).", e.message)
    }


    @Test
    @Throws(Exception::class)
    fun setContentValue() {
        val contentValue = ContentValues()
        val columnName = "columnName"

        contentValue.clear()
        RemindTimeoutType(RemindTimeoutType.RemindTimeoutPattern.MINUTE_1).setContentValue(columnName, contentValue)
        assertEquals(1, contentValue.get(columnName))
    }

    @Test
    @Throws(Exception::class)
    fun compareTo() {
        var model1 = RemindTimeoutType(RemindTimeoutType.RemindTimeoutPattern.MINUTE_1)
        var model2 = RemindTimeoutType(RemindTimeoutType.RemindTimeoutPattern.MINUTE_1)
        assertTrue(model1 == model2)

        model1 = RemindTimeoutType(RemindTimeoutType.RemindTimeoutPattern.MINUTE_1)
        model2 = RemindTimeoutType(RemindTimeoutType.RemindTimeoutPattern.MINUTE_5)
        assertTrue(model1 < model2)

        model1 = RemindTimeoutType(RemindTimeoutType.RemindTimeoutPattern.HOUR_1)
        model2 = RemindTimeoutType(RemindTimeoutType.RemindTimeoutPattern.MINUTE_1)
        assertTrue(model1 > model2)
    }

    @Test
    @Throws(Exception::class)
    fun isTimeout() {
        val now = Calendar.getInstance()
        now.set(2017, 0, 2, 3, 4, 0)
        now.set(Calendar.MILLISECOND, 0)
        val dateType = TestDateType(now)
        val timeType = TestTimeType(now)

        // same time
        var nowDatetimeType = TestDatetimeType(now)
        var model = RemindTimeoutType(RemindTimeoutType.RemindTimeoutPattern.MINUTE_1)
        assertFalse(model.isTimeout(nowDatetimeType, dateType, timeType))

        // max range(1 minute)
        now.set(2017, 0, 2, 3, 5, 0)
        nowDatetimeType = TestDatetimeType(now)
        model = RemindTimeoutType(RemindTimeoutType.RemindTimeoutPattern.MINUTE_1)
        assertFalse(model.isTimeout(nowDatetimeType, dateType, timeType))

        // out of max range(1 minute) time(1 minute over)
        now.set(2017, 0, 2, 3, 6, 0)
        nowDatetimeType = TestDatetimeType(now)
        model = RemindTimeoutType(RemindTimeoutType.RemindTimeoutPattern.MINUTE_1)
        assertTrue(model.isTimeout(nowDatetimeType, dateType, timeType))

        //  max range(1 hour)
        now.set(2017, 0, 2, 4, 4, 0)
        nowDatetimeType = TestDatetimeType(now)
        model = RemindTimeoutType(RemindTimeoutType.RemindTimeoutPattern.HOUR_1)
        assertFalse(model.isTimeout(nowDatetimeType, dateType, timeType))

        //  out of max range(1 hour) time(1 minute over)
        now.set(2017, 0, 2, 4, 5, 0)
        nowDatetimeType = TestDatetimeType(now)
        model = RemindTimeoutType(RemindTimeoutType.RemindTimeoutPattern.HOUR_1)
        assertTrue(model.isTimeout(nowDatetimeType, dateType, timeType))

        //  max range(1 day)
        now.set(2017, 0, 3, 3, 4, 0)
        nowDatetimeType = TestDatetimeType(now)
        model = RemindTimeoutType(RemindTimeoutType.RemindTimeoutPattern.HOUR_24)
        assertFalse(model.isTimeout(nowDatetimeType, dateType, timeType))

        //  out of max range(1 day) time(1 minute over)
        now.set(2017, 0, 3, 3, 5, 0)
        nowDatetimeType = TestDatetimeType(now)
        model = RemindTimeoutType(RemindTimeoutType.RemindTimeoutPattern.HOUR_24)
        assertTrue(model.isTimeout(nowDatetimeType, dateType, timeType))

        // ignore millisecond (1 millisecond over ... but ignore)
        now.set(2017, 0, 2, 3, 5, 0)
        now.set(Calendar.MILLISECOND, 1)
        nowDatetimeType = TestDatetimeType(now)
        model = RemindTimeoutType(RemindTimeoutType.RemindTimeoutPattern.MINUTE_1)
        assertFalse(model.isTimeout(nowDatetimeType, dateType, timeType))

        // ignore second (1 second over ... but ignore)
        now.set(2017, 0, 2, 3, 5, 1)
        nowDatetimeType = TestDatetimeType(now)
        model = RemindTimeoutType(RemindTimeoutType.RemindTimeoutPattern.MINUTE_1)
        assertFalse(model.isTimeout(nowDatetimeType, dateType, timeType))
    }

    @Test
    @Throws(Exception::class)
    @Config(qualifiers = "ja")
    fun getDisplayValue_ResourcesJa() {
        val context = RuntimeEnvironment.application.applicationContext
        var model = RemindTimeoutType(RemindTimeoutType.RemindTimeoutPattern.MINUTE_1)
        assertEquals("1分", model.getDisplayValue(context.resources))

        model = RemindTimeoutType(RemindTimeoutType.RemindTimeoutPattern.MINUTE_5)
        assertEquals("5分", model.getDisplayValue(context.resources))

        model = RemindTimeoutType(RemindTimeoutType.RemindTimeoutPattern.HOUR_1)
        assertEquals("1時間", model.getDisplayValue(context.resources))

        model = RemindTimeoutType(RemindTimeoutType.RemindTimeoutPattern.HOUR_24)
        assertEquals("24時間", model.getDisplayValue(context.resources))
    }

    @Test
    @Throws(Exception::class)
    fun getDisplayValue_ResourcesDefaultLocale() {
        val context = RuntimeEnvironment.application.applicationContext
        var model = RemindTimeoutType(RemindTimeoutType.RemindTimeoutPattern.MINUTE_1)
        assertEquals("1 minute", model.getDisplayValue(context.resources))

        model = RemindTimeoutType(RemindTimeoutType.RemindTimeoutPattern.MINUTE_5)
        assertEquals("5 minutes", model.getDisplayValue(context.resources))

        model = RemindTimeoutType(RemindTimeoutType.RemindTimeoutPattern.HOUR_1)
        assertEquals("1 hour", model.getDisplayValue(context.resources))

        model = RemindTimeoutType(RemindTimeoutType.RemindTimeoutPattern.HOUR_24)
        assertEquals("24 hours", model.getDisplayValue(context.resources))
    }

    @Test
    @Throws(Exception::class)
    @Config(qualifiers = "ja")
    fun getAllValues() {
        val valueMap = RemindTimeoutType.getAllValues(RuntimeEnvironment.application.applicationContext)

        val expectMap = TreeMap<Int, String>()
        expectMap.put(1, "1分")
        expectMap.put(5, "5分")
        expectMap.put(10, "10分")
        expectMap.put(15, "15分")
        expectMap.put(30, "30分")
        expectMap.put(1 * 60, "1時間")
        expectMap.put(2 * 60, "2時間")
        expectMap.put(6 * 60, "6時間")
        expectMap.put(9 * 60, "9時間")
        expectMap.put(12 * 60, "12時間")
        expectMap.put(24 * 60, "24時間")

        valueMap.entries.forEach { (k, v) ->
            assertEquals(expectMap[k], v)
        }
    }
}