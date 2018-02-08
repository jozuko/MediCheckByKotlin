@file:Suppress("FunctionName")

package com.studiojozu.common.domain.model.general

import android.annotation.SuppressLint
import android.net.Uri
import com.studiojozu.medicheck.domain.model.setting.ATestParent
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.io.File
import java.util.*

@RunWith(RobolectricTestRunner::class)
@Config(manifest = "src/main/AndroidManifest.xml")
class APhotoTypeTest : ATestParent() {

    private val mValueProperty = findProperty(TestPhotoType::class, "value")

    @Test
    @Throws(Exception::class)
    fun constructor_NoParameter() = assertEquals("", mValueProperty.call(TestPhotoType()))

    @Test
    @Throws(Exception::class)
    fun constructor_Null() {
        val value: String? = null
        assertEquals("", mValueProperty.call(TestPhotoType(value)))
    }

    @Test
    @Throws(Exception::class)
    fun constructor_String() = assertEquals("test", mValueProperty.call(TestPhotoType("test")))

    @Test
    @Throws(Exception::class)
    fun constructor_APhotoType() = assertEquals("test", mValueProperty.call(TestPhotoType(TestPhotoType("test"))))

    @Test
    @Throws(Exception::class)
    fun constructor_Unknown() = try {
        TestPhotoType(Calendar.getInstance())
        fail()
    } catch (e: IllegalArgumentException) {
        assertEquals("unknown type.", e.message)
    }

    @Test
    @Throws(Exception::class)
    fun clone() {
        val value1 = TestPhotoType("test")
        val value2 = value1.clone()
        assertTrue(value1 == value2)
        assertFalse(value1 === value2)
    }

    @SuppressLint("SetWorldReadable")
    @Test
    @Throws(Exception::class)
    fun photoUri() {
        assertNull(TestPhotoType().photoUri)
        assertNull(TestPhotoType(null).photoUri)
        assertNull(TestPhotoType("").photoUri)

        val notExistsFile = "notExist-1234567890.txt"
        assertNull(TestPhotoType(notExistsFile).photoUri)

        val workDir = File("./workAPhotoTypeTest")
        val existFile = File(workDir.absoluteFile, "existFile.txt")
        try {
            existFile.parentFile.mkdirs()
            existFile.createNewFile()

            // ignore windows.
            if (existFile.absolutePath.startsWith(File.pathSeparator)) {
                existFile.setReadable(false, false)
                assertNull(TestPhotoType(existFile.absolutePath).photoUri)
                existFile.setReadable(true, false)
            }

            assertEquals((Uri.fromFile(existFile)).path, (TestPhotoType(existFile.absolutePath).photoUri)!!.path)
        } finally {
            existFile.deleteOnExit()
            workDir.deleteOnExit()
        }
    }
}