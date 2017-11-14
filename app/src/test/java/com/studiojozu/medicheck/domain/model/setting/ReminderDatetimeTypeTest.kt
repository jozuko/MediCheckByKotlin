@file:Suppress("FunctionName")

package com.studiojozu.medicheck.domain.model.setting

import com.studiojozu.common.domain.model.general.TestDateType
import com.studiojozu.common.domain.model.general.TestTimeType
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
class ReminderDatetimeTypeTest : ATestParent() {

    @Test
    @Throws(Exception::class)
    fun constructor_Calendar() {
        val now = Calendar.getInstance()
        val remindDatetimeType = ReminderDatetimeType(now)

        now.set(Calendar.SECOND, 0)
        now.set(Calendar.MILLISECOND, 0)
        assertEquals(now.timeInMillis, remindDatetimeType.dbValue)
    }

    @Test
    @Throws(Exception::class)
    fun constructor_Long() {
        val now = Calendar.getInstance()
        val startDatetimeType = ReminderDatetimeType(now.timeInMillis)

        now.set(Calendar.SECOND, 0)
        now.set(Calendar.MILLISECOND, 0)
        Assert.assertEquals(now.timeInMillis, startDatetimeType.dbValue)
    }

    @Test
    @Throws(Exception::class)
    fun constructor_ReminderDatetimeType() {
        val now = Calendar.getInstance()
        val remindDatetimeType = ReminderDatetimeType(ReminderDatetimeType(now))

        now.set(Calendar.SECOND, 0)
        now.set(Calendar.MILLISECOND, 0)
        assertEquals(now.timeInMillis, remindDatetimeType.dbValue)
    }

    @Test
    @Throws(Exception::class)
    fun constructor_DateModelTimeModel() {
        val now = Calendar.getInstance()
        now.set(2017, 0, 2, 3, 4, 0)
        now.set(Calendar.MILLISECOND, 0)

        val dateModel = TestDateType(now)
        val timeModel = TestTimeType(now)
        val remindDatetimeType = ReminderDatetimeType(dateModel, timeModel)

        assertEquals(now.timeInMillis, remindDatetimeType.dbValue)
    }

    @Test
    @Throws(Exception::class)
    fun constructor_Unknown() = try {
        ReminderDatetimeType("test")
        fail()
    } catch (e: IllegalArgumentException) {
        assertEquals("unknown type.", e.message)
    }
}