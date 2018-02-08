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
class AIdTypeTest : ATestParent() {

    private val mValueProperty = findProperty(TestIdType::class, "value")

    @Test
    @Throws(Exception::class)
    fun constructor_NoParameter() = assertNotNull(mValueProperty.call(TestIdType()))

    @Test
    @Throws(Exception::class)
    fun constructor_Null() = assertNotNull(mValueProperty.call(TestIdType(null)))

    @Test
    @Throws(Exception::class)
    fun constructor_Int() = assertNotNull(mValueProperty.call(TestIdType("1")))

    @Test
    @Throws(Exception::class)
    fun constructor_AIdType() {
        val value1 = TestIdType()
        val value2 = TestIdType(value1)
        assertNotNull(value2)
        assertTrue(value1 == value2)
    }

    @Test
    @Throws(Exception::class)
    fun constructor_Unknown() = try {
        TestIdType(Calendar.getInstance())
        fail()
    } catch (e: IllegalArgumentException) {
        assertEquals("unknown type.", e.message)
    }
}