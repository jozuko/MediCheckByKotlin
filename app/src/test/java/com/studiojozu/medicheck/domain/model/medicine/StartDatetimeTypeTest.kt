package com.studiojozu.medicheck.domain.model.medicine

import com.studiojozu.common.domain.model.general.TestDateType
import com.studiojozu.common.domain.model.general.TestTimeType
import com.studiojozu.medicheck.domain.model.setting.ATestParent
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.util.*

@Suppress("FunctionName")
@RunWith(RobolectricTestRunner::class)
@Config(manifest = "src/main/AndroidManifest.xml")
class StartDatetimeTypeTest : ATestParent() {

    @Test
    @Throws(Exception::class)
    fun constructor_NoParameter() {
        val startDatetimeType = MedicineStartDatetimeType()
        Assert.assertNotNull(startDatetimeType.dbValue)
        Assert.assertTrue(0 < startDatetimeType.dbValue)
    }

    @Test
    @Throws(Exception::class)
    fun constructor_Calendar() {
        val now = Calendar.getInstance()
        val startDatetimeType = MedicineStartDatetimeType(now)

        now.set(Calendar.SECOND, 0)
        now.set(Calendar.MILLISECOND, 0)
        Assert.assertEquals(now.timeInMillis, startDatetimeType.dbValue)
    }

    @Test
    @Throws(Exception::class)
    fun constructor_Long() {
        val now = Calendar.getInstance()
        val startDatetimeType = MedicineStartDatetimeType(now.timeInMillis)

        now.set(Calendar.SECOND, 0)
        now.set(Calendar.MILLISECOND, 0)
        Assert.assertEquals(now.timeInMillis, startDatetimeType.dbValue)
    }

    @Test
    @Throws(Exception::class)
    fun constructor_StartDatetimeType() {
        val now = Calendar.getInstance()
        val startDatetimeType = MedicineStartDatetimeType(MedicineStartDatetimeType(now))

        now.set(Calendar.SECOND, 0)
        now.set(Calendar.MILLISECOND, 0)
        Assert.assertEquals(now.timeInMillis, startDatetimeType.dbValue)
    }

    @Test
    @Throws(Exception::class)
    fun constructor_DateModelTimeModel() {
        val now = Calendar.getInstance()
        now.set(2017, 0, 2, 3, 4, 0)
        now.set(Calendar.MILLISECOND, 0)

        val dateModel = TestDateType(now)
        val timeModel = TestTimeType(now)
        val startDatetimeType = MedicineStartDatetimeType(dateModel, timeModel)

        Assert.assertEquals(now.timeInMillis, startDatetimeType.dbValue)
    }

    @Test
    @Throws(Exception::class)
    fun constructor_Unknown() = try {
        MedicineStartDatetimeType("test")
        Assert.fail()
    } catch (e: IllegalArgumentException) {
        Assert.assertEquals("unknown type.", e.message)
    }
}