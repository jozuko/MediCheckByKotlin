package com.studiojozu.common.domain.model

import com.studiojozu.medicheck.domain.model.setting.ATestParent
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.util.*

@Suppress("FunctionName")
@RunWith(RobolectricTestRunner::class)
@Config(manifest = "src/main/AndroidManifest.xml")
class CalendarNoSecondTest : ATestParent() {

    @Test
    @Throws
    fun constructor_NoParameter() {
        val calendar = CalendarNoSecond().calendar

        assertTrue(calendar.get(Calendar.YEAR) > -1)
        assertTrue(calendar.get(Calendar.MONTH) > -1)
        assertTrue(calendar.get(Calendar.DAY_OF_MONTH) > -1)
        assertTrue(calendar.get(Calendar.HOUR_OF_DAY) > -1)
        assertTrue(calendar.get(Calendar.MINUTE) > -1)
        assertEquals(0, calendar.get(Calendar.SECOND))
        assertEquals(0, calendar.get(Calendar.MILLISECOND))
    }

    @Test
    @Throws
    fun constructor_YearParameter() {
        val calendar = CalendarNoSecond(year = 2017).calendar

        assertEquals(2017, calendar.get(Calendar.YEAR))
        assertTrue(calendar.get(Calendar.MONTH) > -1)
        assertTrue(calendar.get(Calendar.DAY_OF_MONTH) > -1)
        assertTrue(calendar.get(Calendar.HOUR_OF_DAY) > -1)
        assertTrue(calendar.get(Calendar.MINUTE) > -1)
        assertEquals(0, calendar.get(Calendar.SECOND))
        assertEquals(0, calendar.get(Calendar.MILLISECOND))
    }

    @Test
    @Throws
    fun constructor_MonthParameter() {
        var calendar = CalendarNoSecond(month = 0).calendar
        assertTrue(calendar.get(Calendar.YEAR) > -1)
        assertEquals(0, calendar.get(Calendar.MONTH))
        assertTrue(calendar.get(Calendar.DAY_OF_MONTH) > -1)
        assertTrue(calendar.get(Calendar.HOUR_OF_DAY) > -1)
        assertTrue(calendar.get(Calendar.MINUTE) > -1)
        assertEquals(0, calendar.get(Calendar.SECOND))
        assertEquals(0, calendar.get(Calendar.MILLISECOND))

        calendar = CalendarNoSecond(month = 11).calendar
        assertTrue(calendar.get(Calendar.YEAR) > -1)
        assertEquals(11, calendar.get(Calendar.MONTH))
        assertTrue(calendar.get(Calendar.DAY_OF_MONTH) > -1)
        assertTrue(calendar.get(Calendar.HOUR_OF_DAY) > -1)
        assertTrue(calendar.get(Calendar.MINUTE) > -1)
        assertEquals(0, calendar.get(Calendar.SECOND))
        assertEquals(0, calendar.get(Calendar.MILLISECOND))
    }

    @Test
    @Throws
    fun constructor_DayOfMonthParameter() {
        var calendar = CalendarNoSecond(dayOfMonth = 1).calendar
        assertTrue(calendar.get(Calendar.YEAR) > -1)
        assertTrue(calendar.get(Calendar.MONTH) > -1)
        assertEquals(1, calendar.get(Calendar.DAY_OF_MONTH))
        assertTrue(calendar.get(Calendar.HOUR_OF_DAY) > -1)
        assertTrue(calendar.get(Calendar.MINUTE) > -1)
        assertEquals(0, calendar.get(Calendar.SECOND))
        assertEquals(0, calendar.get(Calendar.MILLISECOND))

        calendar = CalendarNoSecond(dayOfMonth = 28).calendar
        assertTrue(calendar.get(Calendar.YEAR) > -1)
        assertTrue(calendar.get(Calendar.MONTH) > -1)
        assertEquals(28, calendar.get(Calendar.DAY_OF_MONTH))
        assertTrue(calendar.get(Calendar.HOUR_OF_DAY) > -1)
        assertTrue(calendar.get(Calendar.MINUTE) > -1)
        assertEquals(0, calendar.get(Calendar.SECOND))
        assertEquals(0, calendar.get(Calendar.MILLISECOND))
    }

    @Test
    @Throws
    fun constructor_HourOfMonthParameter() {
        var calendar = CalendarNoSecond(hourOfDay = 0).calendar
        assertTrue(calendar.get(Calendar.YEAR) > -1)
        assertTrue(calendar.get(Calendar.MONTH) > -1)
        assertTrue(calendar.get(Calendar.DAY_OF_MONTH) > -1)
        assertEquals(0, calendar.get(Calendar.HOUR_OF_DAY))
        assertTrue(calendar.get(Calendar.MINUTE) > -1)
        assertEquals(0, calendar.get(Calendar.SECOND))
        assertEquals(0, calendar.get(Calendar.MILLISECOND))

        calendar = CalendarNoSecond(hourOfDay = 23).calendar
        assertTrue(calendar.get(Calendar.YEAR) > -1)
        assertTrue(calendar.get(Calendar.MONTH) > -1)
        assertTrue(calendar.get(Calendar.DAY_OF_MONTH) > -1)
        assertEquals(23, calendar.get(Calendar.HOUR_OF_DAY))
        assertTrue(calendar.get(Calendar.MINUTE) > -1)
        assertEquals(0, calendar.get(Calendar.SECOND))
        assertEquals(0, calendar.get(Calendar.MILLISECOND))
    }

    @Test
    @Throws
    fun constructor_MinuteParameter() {
        var calendar = CalendarNoSecond(minute = 0).calendar
        assertTrue(calendar.get(Calendar.YEAR) > -1)
        assertTrue(calendar.get(Calendar.MONTH) > -1)
        assertTrue(calendar.get(Calendar.DAY_OF_MONTH) > -1)
        assertTrue(calendar.get(Calendar.HOUR_OF_DAY) > -1)
        assertEquals(0, calendar.get(Calendar.MINUTE))
        assertEquals(0, calendar.get(Calendar.SECOND))
        assertEquals(0, calendar.get(Calendar.MILLISECOND))

        calendar = CalendarNoSecond(minute = 59).calendar
        assertTrue(calendar.get(Calendar.YEAR) > -1)
        assertTrue(calendar.get(Calendar.MONTH) > -1)
        assertTrue(calendar.get(Calendar.DAY_OF_MONTH) > -1)
        assertTrue(calendar.get(Calendar.HOUR_OF_DAY) > -1)
        assertEquals(59, calendar.get(Calendar.MINUTE))
        assertEquals(0, calendar.get(Calendar.SECOND))
        assertEquals(0, calendar.get(Calendar.MILLISECOND))
    }

    @Test
    @Throws
    fun constructor_MillisecondParameter() {
        val now = Calendar.getInstance()
        val calendar = CalendarNoSecond(millisecond = now.timeInMillis).calendar
        assertEquals(now.get(Calendar.YEAR), calendar.get(Calendar.YEAR))
        assertEquals(now.get(Calendar.MONTH), calendar.get(Calendar.MONTH))
        assertEquals(now.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.DAY_OF_MONTH))
        assertEquals(now.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.HOUR_OF_DAY))
        assertEquals(now.get(Calendar.MINUTE), calendar.get(Calendar.MINUTE))
        assertEquals(0, calendar.get(Calendar.SECOND))
        assertEquals(0, calendar.get(Calendar.MILLISECOND))
    }

}
