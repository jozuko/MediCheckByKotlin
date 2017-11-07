package com.studiojozu.medicheck.domain.model.schedule

import com.studiojozu.common.domain.model.general.TestDateType
import com.studiojozu.common.domain.model.general.TestTimeType
import com.studiojozu.medicheck.domain.model.setting.ATestParent
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.powermock.core.classloader.annotations.PowerMockIgnore
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.util.*

@Suppress("FunctionName")
@RunWith(RobolectricTestRunner::class)
@Config(manifest = "src/main/AndroidManifest.xml")
@PowerMockIgnore("org.mockito.*", "org.robolectric.*", "android.*")
class ScheduleTookDatetimeTypeTest : ATestParent() {

    @Test
    @Throws(Exception::class)
    fun constructor_Calendar() {
        val now = Calendar.getInstance()
        val tookDatetimeType = ScheduleTookDatetimeType(now)

        now.set(Calendar.SECOND, 0)
        now.set(Calendar.MILLISECOND, 0)
        Assert.assertEquals(now.timeInMillis, tookDatetimeType.dbValue)
    }

    @Test
    @Throws(Exception::class)
    fun constructor_Long() {
        val now = Calendar.getInstance()
        val startDatetimeType = ScheduleTookDatetimeType(now.timeInMillis)

        now.set(Calendar.SECOND, 0)
        now.set(Calendar.MILLISECOND, 0)
        Assert.assertEquals(now.timeInMillis, startDatetimeType.dbValue)
    }

    @Test
    @Throws(Exception::class)
    fun constructor_TookDatetimeType() {
        val now = Calendar.getInstance()
        val tookDatetimeType = ScheduleTookDatetimeType(ScheduleTookDatetimeType(now))

        now.set(Calendar.SECOND, 0)
        now.set(Calendar.MILLISECOND, 0)
        Assert.assertEquals(now.timeInMillis, tookDatetimeType.dbValue)
    }

    @Test
    @Throws(Exception::class)
    fun constructor_DateModelTimeModel() {
        val now = Calendar.getInstance()
        now.set(2017, 0, 2, 3, 4, 0)
        now.set(Calendar.MILLISECOND, 0)

        val dateModel = TestDateType(now)
        val timeModel = TestTimeType(now)
        val tookDatetimeType = ScheduleTookDatetimeType(dateModel, timeModel)

        Assert.assertEquals(now.timeInMillis, tookDatetimeType.dbValue)
    }

    @Test
    @Throws(Exception::class)
    fun constructor_Unknown() = try {
        ScheduleTookDatetimeType("test")
        Assert.fail()
    } catch (e: IllegalArgumentException) {
        Assert.assertEquals("unknown type.", e.message)
    }
}