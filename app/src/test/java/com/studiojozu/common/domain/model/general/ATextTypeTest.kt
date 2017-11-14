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
class ATextTypeTest : ATestParent() {

    private val mValueProperty = findProperty(TestTextType::class, "mValue")

    @Test
    @Throws(Exception::class)
    fun constructor_NoParameter() = assertEquals("", mValueProperty.call(TestTextType()))

    @Test
    @Throws(Exception::class)
    fun constructor_Null() {
        val value: String? = null
        assertEquals("", mValueProperty.call(TestTextType(value)))
    }

    @Test
    @Throws(Exception::class)
    fun constructor_String() = assertEquals("test", mValueProperty.call(TestTextType("test")))

    @Test
    @Throws(Exception::class)
    fun constructor_APhotoType() = assertEquals("test", mValueProperty.call(TestTextType(TestTextType("test"))))

    @Test
    @Throws(Exception::class)
    fun constructor_Unknown() = try {
        TestTextType(Calendar.getInstance())
        fail()
    } catch (e: IllegalArgumentException) {
        assertEquals("unknown type.", e.message)
    }

    @Test
    @Throws(Exception::class)
    fun clone() {
        val value1 = TestTextType("test")
        val value2 = value1.clone()
        assertTrue(value1 == value2)
        assertFalse(value1 === value2)
    }

    @Test
    @Throws(Exception::class)
    fun dbValue() {
        assertEquals("", TestTextType().dbValue)
        assertEquals("test", TestTextType("test").dbValue)
    }

    @Test
    @Throws(Exception::class)
    fun displayValue() {
        assertEquals("", TestTextType().displayValue)
        assertEquals("test", TestTextType("test").displayValue)
    }

    @Test
    @Throws(Exception::class)
    fun compareTo() {
        assertTrue(TestTextType("test") == TestTextType("test"))
        assertEquals("test".compareTo("test test"), TestTextType("test").compareTo(TestTextType("test test")))
    }
}