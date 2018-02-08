package com.studiojozu.medicheck.domain.model.setting

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.util.*

@Suppress("FunctionName")
@RunWith(RobolectricTestRunner::class)
@Config(manifest = "src/main/AndroidManifest.xml")
class TimetableComparatorTest : ATestParent() {
    private val entity1 = Timetable(
            timetableId = TimetableIdType("1111"),
            timetableName = TimetableNameType("sample name 1"),
            timetableTime = TimetableTimeType(10, 0),
            timetableDisplayOrder = TimetableDisplayOrderType(1L))

    private val entity2 = Timetable(
            timetableId = TimetableIdType("1111"),
            timetableName = TimetableNameType("sample name 2"),
            timetableTime = TimetableTimeType(9, 0),
            timetableDisplayOrder = TimetableDisplayOrderType(2L))

    private val entity3 = Timetable(
            timetableId = TimetableIdType("1111"),
            timetableName = TimetableNameType("sample name 3"),
            timetableTime = TimetableTimeType(8, 59),
            timetableDisplayOrder = TimetableDisplayOrderType(3L))

    private val entity4 = Timetable(
            timetableId = TimetableIdType("1111"),
            timetableName = TimetableNameType("sample name 4"),
            timetableTime = TimetableTimeType(8, 59),
            timetableDisplayOrder = TimetableDisplayOrderType(3L))

    private val entity5 = Timetable(
            timetableId = TimetableIdType("2222"),
            timetableName = TimetableNameType("sample name 4"),
            timetableTime = TimetableTimeType(8, 59),
            timetableDisplayOrder = TimetableDisplayOrderType(3L))

    @Test
    @Throws(Exception::class)
    fun priorityTime() {
        val list = listOf(entity3, entity2, entity4, entity1, entity5)
        assertEquals(entity3, list[0])
        assertEquals(entity2, list[1])
        assertEquals(entity4, list[2])
        assertEquals(entity1, list[3])
        assertEquals(entity5, list[4])

        Collections.sort(list, TimetableComparator(TimetableComparator.ComparePattern.TIME))
        assertEquals(entity3, list[0])
        assertEquals(entity4, list[1])
        assertEquals(entity5, list[2])
        assertEquals(entity2, list[3])
        assertEquals(entity1, list[4])
    }

    @Test
    @Throws(Exception::class)
    fun priorityDisplayOrder() {
        val list = listOf(entity3, entity2, entity4, entity1, entity5)
        assertEquals(entity3, list[0])
        assertEquals(entity2, list[1])
        assertEquals(entity4, list[2])
        assertEquals(entity1, list[3])
        assertEquals(entity5, list[4])

        Collections.sort(list, TimetableComparator(TimetableComparator.ComparePattern.DISPLAY_ORDER))
        assertEquals(entity1, list[0])
        assertEquals(entity2, list[1])
        assertEquals(entity3, list[2])
        assertEquals(entity4, list[3])
        assertEquals(entity5, list[4])
    }
}