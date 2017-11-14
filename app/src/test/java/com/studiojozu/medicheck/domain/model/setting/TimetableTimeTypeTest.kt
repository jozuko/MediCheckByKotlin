package com.studiojozu.medicheck.domain.model.setting

import com.studiojozu.common.domain.model.general.TestDatetimeType
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.util.*

@Suppress("FunctionName")
@RunWith(RobolectricTestRunner::class)
@Config(manifest = "src/main/AndroidManifest.xml")
class TimetableTimeTypeTest : ATestParent() {
    @Test
    @Throws(Exception::class)
    fun constructor() {
        Assert.assertTrue(0 < TimetableTimeType().dbValue)

        val now = Calendar.getInstance()
        now.set(Calendar.YEAR, 2000)
        now.set(Calendar.MONTH, 0)
        now.set(Calendar.DAY_OF_MONTH, 1)
        now.set(Calendar.SECOND, 0)
        now.set(Calendar.MILLISECOND, 0)

        var model = TimetableTimeType(now)
        Assert.assertEquals(now.timeInMillis, model.dbValue)

        model = TimetableTimeType(now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE))
        Assert.assertEquals(now.timeInMillis, model.dbValue)
    }

    @Test
    @Throws(Exception::class)
    fun replaceHourMinute() {
        val now = Calendar.getInstance()
        now.set(Calendar.SECOND, 0)
        now.set(Calendar.MILLISECOND, 0)

        val timeParam = Calendar.getInstance()
        timeParam.set(Calendar.YEAR, 2000)
        timeParam.set(Calendar.MONTH, 0)
        timeParam.set(Calendar.DAY_OF_MONTH, 1)
        timeParam.set(Calendar.HOUR_OF_DAY, 15)
        timeParam.set(Calendar.MINUTE, 4)
        timeParam.set(Calendar.SECOND, 0)
        timeParam.set(Calendar.MILLISECOND, 0)

        val expect = now.clone() as Calendar
        expect.set(Calendar.HOUR_OF_DAY, timeParam.get(Calendar.HOUR_OF_DAY))
        expect.set(Calendar.MINUTE, timeParam.get(Calendar.MINUTE))

        val dateTimeModel = TestDatetimeType(now)
        Assert.assertEquals(now.timeInMillis, dateTimeModel.dbValue)

        val newDateTimeModel = TimetableTimeType(timeParam).replaceHourMinute(dateTimeModel)
        Assert.assertEquals(now.timeInMillis, dateTimeModel.dbValue)

        Assert.assertNotEquals(now.timeInMillis, newDateTimeModel.dbValue)
        Assert.assertNotEquals(timeParam.timeInMillis, newDateTimeModel.dbValue)
        Assert.assertEquals(expect.timeInMillis, newDateTimeModel.dbValue)
    }
}