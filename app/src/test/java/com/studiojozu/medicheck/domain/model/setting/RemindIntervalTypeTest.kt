@file:Suppress("FunctionName")

package com.studiojozu.medicheck.domain.model.setting

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.powermock.core.classloader.annotations.PowerMockIgnore
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import java.util.*

@RunWith(RobolectricTestRunner::class)
@Config(manifest = "src/main/AndroidManifest.xml")
@PowerMockIgnore("org.mockito.*", "org.robolectric.*", "android.*")
class RemindIntervalTypeTest : ATestParent() {
    private val mValueProperty = findProperty(RemindIntervalType::class, "mValue")

    @Test
    @Throws(Exception::class)
    fun constructor_NoParameter() {
        val remindIntervalType = RemindIntervalType()
        assertEquals(RemindIntervalType.RemindIntervalPattern.MINUTE_5, mValueProperty.call(remindIntervalType))
    }

    @Test
    @Throws(Exception::class)
    fun constructor_RemindIntervalPattern() {
        var param: RemindIntervalType.RemindIntervalPattern = RemindIntervalType.RemindIntervalPattern.MINUTE_1
        assertEquals(param, mValueProperty.call(RemindIntervalType(param)))

        param = RemindIntervalType.RemindIntervalPattern.MINUTE_5
        assertEquals(param, mValueProperty.call(RemindIntervalType(param)))

        param = RemindIntervalType.RemindIntervalPattern.MINUTE_10
        assertEquals(param, mValueProperty.call(RemindIntervalType(param)))

        param = RemindIntervalType.RemindIntervalPattern.MINUTE_15
        assertEquals(param, mValueProperty.call(RemindIntervalType(param)))

        param = RemindIntervalType.RemindIntervalPattern.MINUTE_30
        assertEquals(param, mValueProperty.call(RemindIntervalType(param)))

        param = RemindIntervalType.RemindIntervalPattern.HOUR_1
        assertEquals(param, mValueProperty.call(RemindIntervalType(param)))
    }

    @Test
    @Throws(Exception::class)
    fun constructor_Long() {
        var param: Long = 1
        assertEquals(RemindIntervalType.RemindIntervalPattern.MINUTE_1, mValueProperty.call(RemindIntervalType(param)))

        param = 5
        assertEquals(RemindIntervalType.RemindIntervalPattern.MINUTE_5, mValueProperty.call(RemindIntervalType(param)))

        param = 10
        assertEquals(RemindIntervalType.RemindIntervalPattern.MINUTE_10, mValueProperty.call(RemindIntervalType(param)))

        param = 15
        assertEquals(RemindIntervalType.RemindIntervalPattern.MINUTE_15, mValueProperty.call(RemindIntervalType(param)))

        param = 30
        assertEquals(RemindIntervalType.RemindIntervalPattern.MINUTE_30, mValueProperty.call(RemindIntervalType(param)))

        param = 60
        assertEquals(RemindIntervalType.RemindIntervalPattern.HOUR_1, mValueProperty.call(RemindIntervalType(param)))
    }

    @Test
    @Throws(Exception::class)
    fun constructor_Int() {
        @Suppress("RedundantExplicitType")
        var param: Int = 1
        assertEquals(RemindIntervalType.RemindIntervalPattern.MINUTE_1, mValueProperty.call(RemindIntervalType(param)))

        param = 5
        assertEquals(RemindIntervalType.RemindIntervalPattern.MINUTE_5, mValueProperty.call(RemindIntervalType(param)))

        param = 10
        assertEquals(RemindIntervalType.RemindIntervalPattern.MINUTE_10, mValueProperty.call(RemindIntervalType(param)))

        param = 15
        assertEquals(RemindIntervalType.RemindIntervalPattern.MINUTE_15, mValueProperty.call(RemindIntervalType(param)))

        param = 30
        assertEquals(RemindIntervalType.RemindIntervalPattern.MINUTE_30, mValueProperty.call(RemindIntervalType(param)))

        param = 60
        assertEquals(RemindIntervalType.RemindIntervalPattern.HOUR_1, mValueProperty.call(RemindIntervalType(param)))
    }

    @Test
    @Throws(Exception::class)
    fun constructor_RemindIntervalType() = assertEquals(RemindIntervalType.RemindIntervalPattern.MINUTE_1, mValueProperty.call(RemindIntervalType(RemindIntervalType(1))))

    @Test
    @Throws(Exception::class)
    fun constructor_Unknown() = try {
        RemindIntervalType("1")
        fail()
    } catch (e: IllegalArgumentException) {
        assertTrue(true)
    }

    @Test
    @Throws(Exception::class)
    fun dbValue() {
        assertEquals(1, RemindIntervalType(RemindIntervalType.RemindIntervalPattern.MINUTE_1).dbValue)
        assertEquals(5, RemindIntervalType(RemindIntervalType.RemindIntervalPattern.MINUTE_5).dbValue)
        assertEquals(10, RemindIntervalType(RemindIntervalType.RemindIntervalPattern.MINUTE_10).dbValue)
        assertEquals(15, RemindIntervalType(RemindIntervalType.RemindIntervalPattern.MINUTE_15).dbValue)
        assertEquals(30, RemindIntervalType(RemindIntervalType.RemindIntervalPattern.MINUTE_30).dbValue)
        assertEquals(60, RemindIntervalType(RemindIntervalType.RemindIntervalPattern.HOUR_1).dbValue)
    }

    @Test
    @Throws(Exception::class)
    fun displayValue() = try {
        RemindIntervalType(RemindIntervalType.RemindIntervalPattern.MINUTE_1).displayValue
        fail()
    } catch (e: RuntimeException) {
        assertEquals("you need to call getDisplayValue(Resources).", e.message)
    }

    @Test
    @Throws(Exception::class)
    fun compareTo() {
        var remindIntervalType1 = RemindIntervalType(RemindIntervalType.RemindIntervalPattern.MINUTE_1)
        var remindIntervalType2 = RemindIntervalType(RemindIntervalType.RemindIntervalPattern.MINUTE_1)
        assertTrue(remindIntervalType1 == remindIntervalType2)

        remindIntervalType1 = RemindIntervalType(RemindIntervalType.RemindIntervalPattern.MINUTE_1)
        remindIntervalType2 = RemindIntervalType(RemindIntervalType.RemindIntervalPattern.MINUTE_5)
        assertTrue(remindIntervalType1 < remindIntervalType2)

        remindIntervalType1 = RemindIntervalType(RemindIntervalType.RemindIntervalPattern.HOUR_1)
        remindIntervalType2 = RemindIntervalType(RemindIntervalType.RemindIntervalPattern.MINUTE_1)
        assertTrue(remindIntervalType1 > remindIntervalType2)
    }

    @Test
    @Throws(Exception::class)
    @Config(qualifiers = "ja")
    fun getDisplayValue_ResourcesJa() {
        val context = RuntimeEnvironment.application.applicationContext
        var remindIntervalType = RemindIntervalType(RemindIntervalType.RemindIntervalPattern.MINUTE_1)
        assertEquals("1分", remindIntervalType.getDisplayValue(context.resources))

        remindIntervalType = RemindIntervalType(RemindIntervalType.RemindIntervalPattern.MINUTE_5)
        assertEquals("5分", remindIntervalType.getDisplayValue(context.resources))

        remindIntervalType = RemindIntervalType(RemindIntervalType.RemindIntervalPattern.HOUR_1)
        assertEquals("1時間", remindIntervalType.getDisplayValue(context.resources))
    }

    @Test
    @Throws(Exception::class)
    fun getDisplayValue_ResourcesDefaultLocale() {
        val context = RuntimeEnvironment.application.applicationContext
        var remindIntervalType = RemindIntervalType(RemindIntervalType.RemindIntervalPattern.MINUTE_1)
        assertEquals("1 minute", remindIntervalType.getDisplayValue(context.resources))

        remindIntervalType = RemindIntervalType(RemindIntervalType.RemindIntervalPattern.MINUTE_5)
        assertEquals("5 minutes", remindIntervalType.getDisplayValue(context.resources))

        remindIntervalType = RemindIntervalType(RemindIntervalType.RemindIntervalPattern.HOUR_1)
        assertEquals("1 hour", remindIntervalType.getDisplayValue(context.resources))
    }

    @Test
    @Throws(Exception::class)
    @Config(qualifiers = "ja")
    fun getAllValues() {
        val valueMap = RemindIntervalType.getAllValues(RuntimeEnvironment.application.applicationContext)

        val expectMap = TreeMap<Int, String>()
        expectMap.put(1, "1分")
        expectMap.put(5, "5分")
        expectMap.put(10, "10分")
        expectMap.put(15, "15分")
        expectMap.put(30, "30分")
        expectMap.put(60, "1時間")

        valueMap.entries.forEach { (k, v) ->
            assertEquals(expectMap[k], v)
        }
    }
}