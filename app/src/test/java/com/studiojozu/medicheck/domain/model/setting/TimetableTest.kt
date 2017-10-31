package com.studiojozu.medicheck.domain.model.setting

import com.studiojozu.common.domain.model.general.TestDatetimeType
import org.junit.Assert.*
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
class TimetableTest : ATestParent() {

    private val mTimetableNameProperty = findProperty(Timetable::class, "mTimetableName")
    private fun getTimetableName(entity: Timetable): TimetableNameType = mTimetableNameProperty.call(entity) as TimetableNameType

    private val mTimetableTimeProperty = findProperty(Timetable::class, "mTimetableTime")
    private fun getTimetableTime(entity: Timetable): TimetableTimeType = mTimetableTimeProperty.call(entity) as TimetableTimeType

    private val mTimetableDisplayOrderProperty = findProperty(Timetable::class, "mTimetableDisplayOrder")
    private fun getTimetableDisplayOrder(entity: Timetable): TimetableDisplayOrderType = mTimetableDisplayOrderProperty.call(entity) as TimetableDisplayOrderType

    @Test
    @Throws(Exception::class)
    fun constructor_NoParameter() {
        val entity = Timetable()
        assertNotNull(entity.mTimetableId.dbValue)
        assertEquals("", getTimetableName(entity).dbValue)
        assertTrue(0 < getTimetableTime(entity).dbValue)
        assertEquals(0L, getTimetableDisplayOrder(entity).dbValue)
    }

    @Test
    @Throws(Exception::class)
    fun constructor_WithParameter() {
        var entity = Timetable(mTimetableId = TimetableIdType("12345678"))
        assertEquals("12345678", entity.mTimetableId.dbValue)
        assertEquals("", getTimetableName(entity).dbValue)
        assertTrue(0 < getTimetableTime(entity).dbValue)
        assertEquals(0L, getTimetableDisplayOrder(entity).dbValue)

        entity = Timetable(mTimetableId = TimetableIdType("12345678"), mTimetableName = TimetableNameType("sample name"))
        assertEquals("12345678", entity.mTimetableId.dbValue)
        assertEquals("sample name", getTimetableName(entity).dbValue)
        assertTrue(0 < getTimetableTime(entity).dbValue)
        assertEquals(0L, getTimetableDisplayOrder(entity).dbValue)

        val time = Calendar.getInstance()
        time.set(Calendar.HOUR_OF_DAY, 3)
        time.set(Calendar.MINUTE, 4)
        time.set(Calendar.MILLISECOND, 0)
        entity = Timetable(mTimetableId = TimetableIdType("12345678"), mTimetableTime = TimetableTimeType(time))
        time.set(2000, 0, 1, 3, 4, 0)
        assertEquals("12345678", entity.mTimetableId.dbValue)
        assertEquals("", getTimetableName(entity).dbValue)
        assertEquals(time.timeInMillis, getTimetableTime(entity).dbValue)
        assertEquals(0L, getTimetableDisplayOrder(entity).dbValue)

        entity = Timetable(mTimetableId = TimetableIdType("12345678"), mTimetableDisplayOrder = TimetableDisplayOrderType(Long.MAX_VALUE))
        assertEquals("12345678", entity.mTimetableId.dbValue)
        assertEquals("", getTimetableName(entity).dbValue)
        assertTrue(0 < getTimetableTime(entity).dbValue)
        assertEquals(Long.MAX_VALUE, getTimetableDisplayOrder(entity).dbValue)
    }

    @Test
    @Throws(Exception::class)
    fun getTimetableTime() {
        val expect = Calendar.getInstance()
        expect.set(2000, 0, 1, 3, 4, 0)
        expect.set(Calendar.MILLISECOND, 0)

        var entity = Timetable(mTimetableTime = TimetableTimeType(3, 4))
        assertEquals(expect.timeInMillis, entity.getTimetableTime().dbValue)

        expect.set(2000, 0, 1, 15, 4, 0)
        entity = Timetable(mTimetableTime = TimetableTimeType(15, 4))
        assertEquals(expect.timeInMillis, entity.getTimetableTime().dbValue)
    }

    @Test
    @Throws(Exception::class)
    fun getTimetableName() {
        val value = "sample name"
        val entity = Timetable(mTimetableName = TimetableNameType(value))
        assertEquals(value, getTimetableName(entity).dbValue)
    }

    @Test
    @Throws(Exception::class)
    fun propertyTimetableNameWithTime() {
        val name = "sample name"

        var entity = Timetable(mTimetableName = TimetableNameType(name), mTimetableTime = TimetableTimeType(0, 0))
        assertEquals("sample name(0:00)", entity.timetableNameWithTime)

        entity = Timetable(mTimetableName = TimetableNameType(name), mTimetableTime = TimetableTimeType(23, 59))
        assertEquals("sample name(23:59)", entity.timetableNameWithTime)
    }

    @Test
    @Throws(Exception::class)
    fun getPlanDateTime() {
        val day = Calendar.getInstance()
        day.set(2017, 10, 1, 0, 0, 0)
        day.set(Calendar.MILLISECOND, 0)

        val time = Calendar.getInstance()
        time.set(2000, 0, 1, 3, 4, 0)
        time.set(Calendar.MILLISECOND, 0)

        val planDateTime = Calendar.getInstance()
        planDateTime.set(2017, 10, 1, 3, 4, 0)
        planDateTime.set(Calendar.MILLISECOND, 0)

        val dateTimeType = TestDatetimeType(day)

        val entity = Timetable(mTimetableId = TimetableIdType("12345678"), mTimetableTime = TimetableTimeType(3, 4))

        val actualResult = entity.getPlanDateTime(dateTimeType)
        assertEquals("12345678", actualResult.mTimetableId.dbValue)
        assertEquals(planDateTime.timeInMillis, actualResult.mPlanDatetime.dbValue)
        assertEquals(day.timeInMillis, actualResult.planDate.dbValue)
    }

    @Test
    @Throws(Exception::class)
    fun compareToTimePriority() {
        val time1 = Calendar.getInstance()
        time1.set(2000, 0, 1, 3, 4, 0)
        time1.set(Calendar.MILLISECOND, 0)

        val time2 = Calendar.getInstance()
        time2.set(2000, 0, 1, 3, 5, 0)
        time2.set(Calendar.MILLISECOND, 0)

        val entity1 = Timetable(
                mTimetableId = TimetableIdType("1111"),
                mTimetableName = TimetableNameType("sample name"),
                mTimetableTime = TimetableTimeType(time1),
                mTimetableDisplayOrder = TimetableDisplayOrderType(2L))

        val entity2 = Timetable(
                mTimetableId = TimetableIdType("1111"),
                mTimetableName = TimetableNameType("sample name"),
                mTimetableTime = TimetableTimeType(time2),
                mTimetableDisplayOrder = TimetableDisplayOrderType(1L))

        val entity3 = entity1.copy()

        assertTrue(entity1.compareToTimePriority(entity2) < 0)
        assertTrue(entity2.compareToTimePriority(entity1) > 0)
        assertTrue(entity1.compareToTimePriority(entity3) == 0)
        assertFalse(entity1 === entity3)
    }

    @Test
    @Throws(Exception::class)
    fun compareToDisplayOrderPriority() {
        val time1 = Calendar.getInstance()
        time1.set(2000, 0, 1, 3, 4, 0)
        time1.set(Calendar.MILLISECOND, 0)

        val time2 = Calendar.getInstance()
        time2.set(2000, 0, 1, 3, 5, 0)
        time2.set(Calendar.MILLISECOND, 0)

        val entity1 = Timetable(
                mTimetableId = TimetableIdType("1111"),
                mTimetableName = TimetableNameType("sample name"),
                mTimetableTime = TimetableTimeType(time1),
                mTimetableDisplayOrder = TimetableDisplayOrderType(2L))

        val entity2 = Timetable(
                mTimetableId = TimetableIdType("1111"),
                mTimetableName = TimetableNameType("sample name"),
                mTimetableTime = TimetableTimeType(time2),
                mTimetableDisplayOrder = TimetableDisplayOrderType(1L))

        val entity3 = entity1.copy()

        assertTrue(entity1.compareToDisplayOrderPriority(entity2) > 0)
        assertTrue(entity2.compareToDisplayOrderPriority(entity1) < 0)
        assertTrue(entity1.compareToDisplayOrderPriority(entity3) == 0)
        assertFalse(entity1 === entity3)
    }

}