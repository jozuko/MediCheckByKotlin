@file:Suppress("FunctionName")

package com.studiojozu.common.domain.model.general

import android.content.ContentValues
import com.studiojozu.medicheck.domain.model.setting.ATestParent
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.powermock.core.classloader.annotations.PowerMockIgnore
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = "src/main/AndroidManifest.xml")
@PowerMockIgnore("org.mockito.*", "org.robolectric.*", "android.*")
class ABooleanTypeTest : ATestParent() {

    @Test
    @Throws(Exception::class)
    fun constructor_Boolean() {
        var value = true
        assertTrue(TestBooleanType(value).isTrue)
        value = false
        assertFalse(TestBooleanType(value).isTrue)
    }

    @Test
    @Throws(Exception::class)
    fun constructor_Long() {
        var value: Long = 1
        assertTrue(TestBooleanType(value).isTrue)

        value = 0
        assertFalse(TestBooleanType(value).isTrue)
    }

    @Test
    @Throws(Exception::class)
    fun constructor_Int() {
        @Suppress("RedundantExplicitType")
        var value: Int = 1
        assertTrue(TestBooleanType(value).isTrue)

        value = 0
        assertFalse(TestBooleanType(value).isTrue)
    }

    @Test
    @Throws(Exception::class)
    fun constructor_ABooleanType() {
        var value = TestBooleanType(true)
        assertTrue(TestBooleanType(value).isTrue)

        value = TestBooleanType(false)
        assertFalse(TestBooleanType(value).isTrue)
    }

    @Test
    @Throws(Exception::class)
    fun constructor_Unknown() = try {
        TestBooleanType("test")
        fail()
    } catch (e: IllegalArgumentException) {
        assertEquals("unknown type.", e.message)
    }

    @Test
    @Throws(Exception::class)
    fun dbValue() {
        var value = true
        assertEquals(1, TestBooleanType(value).dbValue)
        value = false
        assertEquals(0, TestBooleanType(value).dbValue)
    }

    @Test
    @Throws(Exception::class)
    fun displayValue() {
        var value = true
        assertEquals("true", TestBooleanType(value).displayValue)
        value = false
        assertEquals("false", TestBooleanType(value).displayValue)
    }

    @Test
    @Throws(Exception::class)
    fun setContentValue() {
        val contentValue = ContentValues()
        val columnName = "columnName"

        TestBooleanType(true).setContentValue(columnName, contentValue)
        assertEquals(1, contentValue.get(columnName))

        contentValue.clear()
        TestBooleanType(false).setContentValue(columnName, contentValue)
        assertEquals(0, contentValue.get(columnName))
    }

    @Test
    @Throws(Exception::class)
    fun compareTo() {
        assertTrue(TestBooleanType(true) == TestBooleanType(true))
        assertTrue(TestBooleanType(true) > TestBooleanType(false))
        assertTrue(TestBooleanType(false) < TestBooleanType(true))
    }

    @Test
    @Throws(Exception::class)
    fun clone() {
        val value1 = TestBooleanType(true)
        val value2 = value1.clone()
        assertTrue(value1 == value2)
        assertFalse(value1 === value2)
    }
}