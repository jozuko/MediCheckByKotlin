@file:Suppress("FunctionName")

package com.studiojozu.common.domain.model.general

import android.content.ContentValues
import com.studiojozu.medicheck.domain.model.setting.ATestParent
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.powermock.core.classloader.annotations.PowerMockIgnore
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.util.*

@RunWith(RobolectricTestRunner::class)
@Config(manifest = "src/main/AndroidManifest.xml")
@PowerMockIgnore("org.mockito.*", "org.robolectric.*", "android.*")
class ALongTypeTest : ATestParent() {

    private val mValueProperty = findProperty(TestLongType::class, "mValue")

    @Test
    @Throws(Exception::class)
    fun constructor_Long() {
        assertEquals(Long.MIN_VALUE, mValueProperty.call(TestLongType(Long.MIN_VALUE)))
        assertEquals(Long.MAX_VALUE, mValueProperty.call(TestLongType(Long.MAX_VALUE)))
    }

    @Test
    @Throws(Exception::class)
    fun constructor_Int() {
        assertEquals(Int.MIN_VALUE.toLong(), mValueProperty.call(TestLongType(Int.MIN_VALUE)))
        assertEquals(Int.MAX_VALUE.toLong(), mValueProperty.call(TestLongType(Int.MAX_VALUE)))
    }

    @Test
    @Throws(Exception::class)
    fun constructor_ALongType() {
        assertEquals(Int.MIN_VALUE.toLong(), mValueProperty.call(TestLongType(TestLongType(Int.MIN_VALUE))))
        assertEquals(Int.MAX_VALUE.toLong(), mValueProperty.call(TestLongType(TestLongType(Int.MAX_VALUE))))
    }

    @Test
    @Throws(Exception::class)
    fun constructor_Unknown() = assertEquals(-1L, mValueProperty.call(TestLongType(Calendar.getInstance())))

    @Test
    @Throws(Exception::class)
    fun dbValue() = assertEquals(Long.MIN_VALUE, TestLongType(Long.MIN_VALUE).dbValue)

    @Test
    @Throws(Exception::class)
    fun displayValue() = assertEquals(Long.MIN_VALUE.toString(), TestLongType(Long.MIN_VALUE).displayValue)

    @Test
    @Throws(Exception::class)
    fun setContentValue() {
        val contentValue = ContentValues()
        val columnName = "columnName"

        TestLongType(Long.MAX_VALUE).setContentValue(columnName, contentValue)
        assertEquals(Long.MAX_VALUE, contentValue.get(columnName))

        contentValue.clear()
        TestLongType(Int.MAX_VALUE).setContentValue(columnName, contentValue)
        assertEquals(Int.MAX_VALUE.toLong(), contentValue.get(columnName))
    }

    @Test
    @Throws(Exception::class)
    fun compareTo() {
        Assert.assertTrue(TestLongType(Long.MIN_VALUE) == TestLongType(Long.MIN_VALUE))
        Assert.assertTrue(TestLongType(Long.MIN_VALUE) < TestLongType(Long.MAX_VALUE))
        Assert.assertTrue(TestLongType(Long.MAX_VALUE) > TestLongType(Long.MIN_VALUE))
    }

    @Test
    @Throws(Exception::class)
    fun clone() {
        val value1 = TestLongType(Long.MAX_VALUE)
        val value2 = value1.clone()
        Assert.assertTrue(value1 == value2)
        Assert.assertFalse(value1 === value2)
    }
}