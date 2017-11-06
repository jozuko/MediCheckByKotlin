@file:Suppress("FunctionName")

package com.studiojozu.common.domain.model

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
class AValueObjectTest : ATestParent() {

    @Test
    @Throws(Exception::class)
    fun dbWhereValue() = assertEquals(Int.MAX_VALUE.toString(), TestValueObject(Int.MAX_VALUE).dbWhereValue)

    @Test
    @Throws(Exception::class)
    fun test_equals() = assertTrue(TestValueObject(Int.MAX_VALUE) == (TestValueObject(Int.MAX_VALUE)))

    @Test
    @Throws(Exception::class)
    fun test_hashCode() = assertEquals(Int.MAX_VALUE.hashCode(), TestValueObject(Int.MAX_VALUE).hashCode())

    @Test
    @Throws(Exception::class)
    fun clone() {
        val value1 = TestValueObject(Int.MAX_VALUE)
        val value2 = value1.clone()
        assertTrue(value1 == value2)
        assertFalse(value1 === value2)
    }
}