package com.studiojozu.medicheck.domain.model.setting

import junit.framework.Assert.assertEquals
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
class TimetableComparatorTest : ATestParent() {
    private val entity1 = Timetable(
            mTimetableId = TimetableIdType("1111"),
            mTimetableName = TimetableNameType("sample name 1"),
            mTimetableTime = TimetableTimeType(10, 0),
            mTimetableDisplayOrder = TimetableDisplayOrderType(1L))

    private val entity2 = Timetable(
            mTimetableId = TimetableIdType("1111"),
            mTimetableName = TimetableNameType("sample name 2"),
            mTimetableTime = TimetableTimeType(9, 0),
            mTimetableDisplayOrder = TimetableDisplayOrderType(2L))

    private val entity3 = Timetable(
            mTimetableId = TimetableIdType("1111"),
            mTimetableName = TimetableNameType("sample name 3"),
            mTimetableTime = TimetableTimeType(8, 59),
            mTimetableDisplayOrder = TimetableDisplayOrderType(3L))

    private val entity4 = Timetable(
            mTimetableId = TimetableIdType("1111"),
            mTimetableName = TimetableNameType("sample name 4"),
            mTimetableTime = TimetableTimeType(8, 59),
            mTimetableDisplayOrder = TimetableDisplayOrderType(3L))

    private val entity5 = Timetable(
            mTimetableId = TimetableIdType("2222"),
            mTimetableName = TimetableNameType("sample name 4"),
            mTimetableTime = TimetableTimeType(8, 59),
            mTimetableDisplayOrder = TimetableDisplayOrderType(3L))

    @Test
    @Throws(Exception::class)
    fun priorityTime() {
        val list = listOf(entity3, entity2, entity4, entity1, entity5)
        assertEquals(entity3, list[0])
        assertEquals(entity2, list[1])
        assertEquals(entity4, list[2])
        assertEquals(entity1, list[3])
        assertEquals(entity5, list[4])

        Collections.sort(list, TimetableComparator(TimetableComparator.ComparePattern.Time))
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

        Collections.sort(list, TimetableComparator(TimetableComparator.ComparePattern.DisplayOrder))
        assertEquals(entity1, list[0])
        assertEquals(entity2, list[1])
        assertEquals(entity3, list[2])
        assertEquals(entity4, list[3])
        assertEquals(entity5, list[4])
    }
}