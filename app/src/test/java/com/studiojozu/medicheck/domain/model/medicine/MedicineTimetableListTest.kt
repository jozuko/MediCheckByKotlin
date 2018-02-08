package com.studiojozu.medicheck.domain.model.medicine

import com.studiojozu.common.domain.model.general.TestDatetimeType
import com.studiojozu.medicheck.domain.model.setting.*
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Suppress("UNCHECKED_CAST", "FunctionName")
@RunWith(RobolectricTestRunner::class)
@Config(manifest = "src/main/AndroidManifest.xml")
class MedicineTimetableListTest : ATestParent() {

    private val mTimetableListProperty = findProperty(MedicineTimetableList::class, "timetableList")
    private fun getTimetableList(entity: MedicineTimetableList): MutableList<Timetable> = mTimetableListProperty.call(entity) as MutableList<Timetable>

    private val timetable1 = Timetable(
            timetableId = TimetableIdType("time0001"),
            timetableName = TimetableNameType("朝"),
            timetableTime = TimetableTimeType(7, 0),
            timetableDisplayOrder = TimetableDisplayOrderType(3))

    private val timetable2 = Timetable(
            timetableId = TimetableIdType("time0002"),
            timetableName = TimetableNameType("昼"),
            timetableTime = TimetableTimeType(12, 30),
            timetableDisplayOrder = TimetableDisplayOrderType(2))

    private val timetable3 = Timetable(
            timetableId = TimetableIdType("time0003"),
            timetableName = TimetableNameType("夜"),
            timetableTime = TimetableTimeType(19, 0),
            timetableDisplayOrder = TimetableDisplayOrderType(1))

    @Test
    @Throws(Exception::class)
    fun constructor_NoParameter() {
        val listValueObject = MedicineTimetableList()
        assertEquals(0, listValueObject.count)
        assertEquals(false, listValueObject.isOneShotMedicine)
    }

    @Test
    @Throws(Exception::class)
    fun constructor_WithTimetableList() {
        val listValueObject = MedicineTimetableList(mutableListOf(timetable1, timetable2, timetable3))
        assertEquals(3, listValueObject.count)
        assertEquals(false, listValueObject.isOneShotMedicine)

        val timetableList = getTimetableList(listValueObject)
        assertEquals(timetable1, timetableList[0])
        assertEquals(timetable2, timetableList[1])
        assertEquals(timetable3, timetableList[2])
    }

    @Test
    @Throws(Exception::class)
    fun timetableListOrderByTime() {
        val listValueObject = MedicineTimetableList(mutableListOf(timetable3, timetable2, timetable1))

        val timetableList = getTimetableList(listValueObject)
        assertEquals(timetable3, timetableList[0])
        assertEquals(timetable2, timetableList[1])
        assertEquals(timetable1, timetableList[2])

        val sortedList = listValueObject.timetableListOrderByTime
        assertEquals(timetable1, sortedList[0])
        assertEquals(timetable2, sortedList[1])
        assertEquals(timetable3, sortedList[2])

        // test for copy()
        assertFalse(timetableList === sortedList)
        assertFalse(timetableList[2] === sortedList[0])
        assertFalse(timetableList[1] === sortedList[1])
        assertFalse(timetableList[0] === sortedList[2])
    }

    @Test
    @Throws(Exception::class)
    fun timetableListOrderByDisplayOrder() {
        val listValueObject = MedicineTimetableList(mutableListOf(timetable1, timetable2, timetable3))

        val timetableList = getTimetableList(listValueObject)
        assertEquals(timetable1, timetableList[0])
        assertEquals(timetable2, timetableList[1])
        assertEquals(timetable3, timetableList[2])

        val sortedList = listValueObject.timetableListOrderByDisplayOrder
        assertEquals(timetable3, sortedList[0])
        assertEquals(timetable2, sortedList[1])
        assertEquals(timetable1, sortedList[2])

        // test for copy()
        assertFalse(timetableList === sortedList)
        assertFalse(timetableList[2] === sortedList[0])
        assertFalse(timetableList[1] === sortedList[1])
        assertFalse(timetableList[0] === sortedList[2])
    }

    @Test
    @Throws(Exception::class)
    fun displayValue() {
        var listValueObject = MedicineTimetableList(mutableListOf(timetable1, timetable2, timetable3))
        assertEquals("朝(7:00)\n昼(12:30)\n夜(19:00)", listValueObject.displayValue)

        listValueObject = MedicineTimetableList(mutableListOf(timetable1))
        assertEquals("朝(7:00)", listValueObject.displayValue)

        listValueObject = MedicineTimetableList()
        assertEquals("", listValueObject.displayValue)
    }

    @Test
    @Throws(Exception::class)
    fun iterate() {
        val listValueObject = MedicineTimetableList(mutableListOf(timetable1, timetable2, timetable3))
        val iterator = listValueObject.iterator()

        assertTrue(iterator.hasNext())
        assertEquals(timetable1, iterator.next())
        assertTrue(iterator.hasNext())
        assertEquals(timetable2, iterator.next())
        assertTrue(iterator.hasNext())
        assertEquals(timetable3, iterator.next())
        assertFalse(iterator.hasNext())
    }

    @Test
    @Throws(Exception::class)
    fun setTimetableList() {
        val listValueObject = MedicineTimetableList()
        var timetableList = getTimetableList(listValueObject)
        assertEquals(0, listValueObject.count)
        assertTrue(timetableList.isEmpty())

        // test for null
        listValueObject.setTimetableList(null)
        timetableList = getTimetableList(listValueObject)
        assertEquals(0, listValueObject.count)
        assertTrue(timetableList.isEmpty())

        // test for 3 object list
        listValueObject.setTimetableList(mutableListOf(timetable1, timetable2, timetable3))
        assertEquals(3, listValueObject.count)
        timetableList = getTimetableList(listValueObject)
        assertFalse(timetableList.isEmpty())
        assertEquals(timetable1, timetableList[0])
        assertEquals(timetable2, timetableList[1])
        assertEquals(timetable3, timetableList[2])

        // test for null again
        listValueObject.setTimetableList(null)
        timetableList = getTimetableList(listValueObject)
        assertEquals(0, listValueObject.count)
        assertTrue(timetableList.isEmpty())
    }

    @Test
    @Throws(Exception::class)
    fun getPlanDate() {
        val listValueObject = MedicineTimetableList(mutableListOf(timetable1, timetable2, timetable3))

        var startDatetime = TestDatetimeType(2017, 1, 1, 0, 0)
        assertEquals("17/01/01 7:00", listValueObject.getPlanDate(startDatetime).planDatetime.displayValue)

        startDatetime = TestDatetimeType(2017, 1, 1, 6, 59)
        assertEquals("17/01/01 7:00", listValueObject.getPlanDate(startDatetime).planDatetime.displayValue)

        startDatetime = TestDatetimeType(2017, 1, 1, 7, 0)
        assertEquals("17/01/01 12:30", listValueObject.getPlanDate(startDatetime).planDatetime.displayValue)

        startDatetime = TestDatetimeType(2017, 1, 1, 12, 0)
        assertEquals("17/01/01 12:30", listValueObject.getPlanDate(startDatetime).planDatetime.displayValue)

        startDatetime = TestDatetimeType(2017, 1, 1, 12, 30)
        assertEquals("17/01/01 19:00", listValueObject.getPlanDate(startDatetime).planDatetime.displayValue)

        startDatetime = TestDatetimeType(2017, 1, 1, 19, 0)
        assertEquals("17/01/02 7:00", listValueObject.getPlanDate(startDatetime).planDatetime.displayValue)
    }

    @Test
    @Throws(Exception::class)
    fun isFinalTime() {
        val listValueObject = MedicineTimetableList(mutableListOf(timetable1, timetable2, timetable3))

        var timetableId = TimetableIdType("time0001")
        assertFalse(listValueObject.isFinalTime(timetableId))

        timetableId = TimetableIdType("time0002")
        assertFalse(listValueObject.isFinalTime(timetableId))

        timetableId = TimetableIdType("time0003")
        assertTrue(listValueObject.isFinalTime(timetableId))
    }

    @Test
    @Throws(Exception::class)
    fun contain() {
        val listValueObject = MedicineTimetableList(mutableListOf(timetable1, timetable2, timetable3))

        var timetable = Timetable(
                timetableId = TimetableIdType("time0001"),
                timetableName = TimetableNameType("朝"),
                timetableTime = TimetableTimeType(7, 0),
                timetableDisplayOrder = TimetableDisplayOrderType(3))
        assertTrue(listValueObject.contain(timetable))

        timetable = Timetable(
                timetableId = TimetableIdType("time0001"),
                timetableName = TimetableNameType("昼"),
                timetableTime = TimetableTimeType(12, 30),
                timetableDisplayOrder = TimetableDisplayOrderType(2))
        assertFalse(listValueObject.contain(timetable))

        assertFalse(listValueObject.contain(null))
    }

}