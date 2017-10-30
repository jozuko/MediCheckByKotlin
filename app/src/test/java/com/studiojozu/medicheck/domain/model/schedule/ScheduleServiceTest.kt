package com.studiojozu.medicheck.domain.model.schedule

import com.studiojozu.medicheck.domain.model.medicine.Medicine
import com.studiojozu.medicheck.domain.model.medicine.TakeIntervalModeType
import com.studiojozu.medicheck.domain.model.setting.*
import junit.framework.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.powermock.core.classloader.annotations.PowerMockIgnore
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Suppress("FunctionName")
@RunWith(RobolectricTestRunner::class)
@Config(manifest = "src/main/AndroidManifest.xml")
@PowerMockIgnore("org.mockito.*", "org.robolectric.*", "android.*")
class ScheduleServiceTest : ATestParent() {

    private val mScheduleListProperty = findProperty(ScheduleList::class, "mScheduleList")
    @Suppress("UNCHECKED_CAST")
    private fun getScheduleList(entity: ScheduleList): MutableList<Schedule> = mScheduleListProperty.call(entity) as MutableList<Schedule>

    private val timetable1 = Timetable(
            mTimetableId = TimetableIdType("time0001"),
            mTimetableName = TimetableNameType("朝"),
            mTimetableTime = TimetableTimeType(7, 0),
            mTimetableDisplayOrder = TimetableDisplayOrderType(1))

    private val timetable2 = Timetable(
            mTimetableId = TimetableIdType("time0002"),
            mTimetableName = TimetableNameType("昼"),
            mTimetableTime = TimetableTimeType(12, 30),
            mTimetableDisplayOrder = TimetableDisplayOrderType(2))

    private val timetable3 = Timetable(
            mTimetableId = TimetableIdType("time0003"),
            mTimetableName = TimetableNameType("夜"),
            mTimetableTime = TimetableTimeType(19, 0),
            mTimetableDisplayOrder = TimetableDisplayOrderType(3))

    @Test
    @Throws(Exception::class)
    fun getScheduleList_OneShotMedicine() {
        val medicine = Medicine()
        medicine.timetableList.isOneShotMedicine = true

        val scheduleService = ScheduleService()
        val scheduleList = scheduleService.createScheduleList(medicine)
        val scheduleListProperty = getScheduleList(scheduleList)

        assertEquals(0, scheduleListProperty.count())
    }

    @Test
    @Throws(Exception::class)
    fun getScheduleList_SevenDaysThreeTimesInDay() {
        val medicine = Medicine()
        medicine.setStartDatetime(2017, 1, 2, 3, 4)
        medicine.setTimetableList(mutableListOf(timetable1, timetable2, timetable3))
        medicine.setDateNumber(7)
        medicine.setTakeInterval(1, TakeIntervalModeType.DateIntervalPattern.DAYS)

        val scheduleService = ScheduleService()
        val scheduleList = scheduleService.createScheduleList(medicine)
        val scheduleListProperty = getScheduleList(scheduleList)

        assertEquals(21, scheduleListProperty.count())

        var index = 0
        assertEquals("17/01/02", scheduleListProperty[index].mPlanDate.displayValue)
        assertEquals(timetable1.mTimetableId.dbValue, scheduleListProperty[index].mTimetableId.dbValue)

        index = 1
        assertEquals("17/01/02", scheduleListProperty[index].mPlanDate.displayValue)
        assertEquals(timetable2.mTimetableId.dbValue, scheduleListProperty[index].mTimetableId.dbValue)

        index = 2
        assertEquals("17/01/02", scheduleListProperty[index].mPlanDate.displayValue)
        assertEquals(timetable3.mTimetableId.dbValue, scheduleListProperty[index].mTimetableId.dbValue)

        index = 3
        assertEquals("17/01/03", scheduleListProperty[index].mPlanDate.displayValue)
        assertEquals(timetable1.mTimetableId.dbValue, scheduleListProperty[index].mTimetableId.dbValue)

        index = 4
        assertEquals("17/01/03", scheduleListProperty[index].mPlanDate.displayValue)
        assertEquals(timetable2.mTimetableId.dbValue, scheduleListProperty[index].mTimetableId.dbValue)

        index = 5
        assertEquals("17/01/03", scheduleListProperty[index].mPlanDate.displayValue)
        assertEquals(timetable3.mTimetableId.dbValue, scheduleListProperty[index].mTimetableId.dbValue)

        index = 6
        assertEquals("17/01/04", scheduleListProperty[index].mPlanDate.displayValue)
        assertEquals(timetable1.mTimetableId.dbValue, scheduleListProperty[index].mTimetableId.dbValue)

        index = 7
        assertEquals("17/01/04", scheduleListProperty[index].mPlanDate.displayValue)
        assertEquals(timetable2.mTimetableId.dbValue, scheduleListProperty[index].mTimetableId.dbValue)

        index = 8
        assertEquals("17/01/04", scheduleListProperty[index].mPlanDate.displayValue)
        assertEquals(timetable3.mTimetableId.dbValue, scheduleListProperty[index].mTimetableId.dbValue)

        index = 9
        assertEquals("17/01/05", scheduleListProperty[index].mPlanDate.displayValue)
        assertEquals(timetable1.mTimetableId.dbValue, scheduleListProperty[index].mTimetableId.dbValue)

        index = 10
        assertEquals("17/01/05", scheduleListProperty[index].mPlanDate.displayValue)
        assertEquals(timetable2.mTimetableId.dbValue, scheduleListProperty[index].mTimetableId.dbValue)

        index = 11
        assertEquals("17/01/05", scheduleListProperty[index].mPlanDate.displayValue)
        assertEquals(timetable3.mTimetableId.dbValue, scheduleListProperty[index].mTimetableId.dbValue)

        index = 12
        assertEquals("17/01/06", scheduleListProperty[index].mPlanDate.displayValue)
        assertEquals(timetable1.mTimetableId.dbValue, scheduleListProperty[index].mTimetableId.dbValue)

        index = 13
        assertEquals("17/01/06", scheduleListProperty[index].mPlanDate.displayValue)
        assertEquals(timetable2.mTimetableId.dbValue, scheduleListProperty[index].mTimetableId.dbValue)

        index = 14
        assertEquals("17/01/06", scheduleListProperty[index].mPlanDate.displayValue)
        assertEquals(timetable3.mTimetableId.dbValue, scheduleListProperty[index].mTimetableId.dbValue)

        index = 15
        assertEquals("17/01/07", scheduleListProperty[index].mPlanDate.displayValue)
        assertEquals(timetable1.mTimetableId.dbValue, scheduleListProperty[index].mTimetableId.dbValue)

        index = 16
        assertEquals("17/01/07", scheduleListProperty[index].mPlanDate.displayValue)
        assertEquals(timetable2.mTimetableId.dbValue, scheduleListProperty[index].mTimetableId.dbValue)

        index = 17
        assertEquals("17/01/07", scheduleListProperty[index].mPlanDate.displayValue)
        assertEquals(timetable3.mTimetableId.dbValue, scheduleListProperty[index].mTimetableId.dbValue)

        index = 18
        assertEquals("17/01/08", scheduleListProperty[index].mPlanDate.displayValue)
        assertEquals(timetable1.mTimetableId.dbValue, scheduleListProperty[index].mTimetableId.dbValue)

        index = 19
        assertEquals("17/01/08", scheduleListProperty[index].mPlanDate.displayValue)
        assertEquals(timetable2.mTimetableId.dbValue, scheduleListProperty[index].mTimetableId.dbValue)

        index = 20
        assertEquals("17/01/08", scheduleListProperty[index].mPlanDate.displayValue)
        assertEquals(timetable3.mTimetableId.dbValue, scheduleListProperty[index].mTimetableId.dbValue)
    }

    @Test
    @Throws(Exception::class)
    fun getScheduleList_ThreeDaysOneTimesInTwoDays() {
        val medicine = Medicine()
        medicine.setStartDatetime(2017, 1, 2, 3, 4)
        medicine.setTimetableList(mutableListOf(timetable1))
        medicine.setDateNumber(3)
        medicine.setTakeInterval(2, TakeIntervalModeType.DateIntervalPattern.DAYS)

        val scheduleService = ScheduleService()
        val scheduleList = scheduleService.createScheduleList(medicine)
        val scheduleListProperty = getScheduleList(scheduleList)

        assertEquals(3, scheduleListProperty.count())

        var index = 0
        assertEquals("17/01/02", scheduleListProperty[index].mPlanDate.displayValue)
        assertEquals(timetable1.mTimetableId.dbValue, scheduleListProperty[index].mTimetableId.dbValue)

        index = 1
        assertEquals("17/01/04", scheduleListProperty[index].mPlanDate.displayValue)
        assertEquals(timetable1.mTimetableId.dbValue, scheduleListProperty[index].mTimetableId.dbValue)

        index = 2
        assertEquals("17/01/06", scheduleListProperty[index].mPlanDate.displayValue)
        assertEquals(timetable1.mTimetableId.dbValue, scheduleListProperty[index].mTimetableId.dbValue)
    }

    @Test
    @Throws(Exception::class)
    fun getScheduleList_OnceAMonthForSixMonths() {
        val medicine = Medicine()
        medicine.setStartDatetime(2017, 1, 2, 3, 4)
        medicine.setTimetableList(mutableListOf(timetable1))
        medicine.setDateNumber(6)
        medicine.setTakeInterval(15, TakeIntervalModeType.DateIntervalPattern.MONTH)

        val scheduleService = ScheduleService()
        val scheduleList = scheduleService.createScheduleList(medicine)
        val scheduleListProperty = getScheduleList(scheduleList)

        assertEquals(6, scheduleListProperty.count())

        var index = 0
        assertEquals("17/01/15", scheduleListProperty[index].mPlanDate.displayValue)
        assertEquals(timetable1.mTimetableId.dbValue, scheduleListProperty[index].mTimetableId.dbValue)

        index = 1
        assertEquals("17/02/15", scheduleListProperty[index].mPlanDate.displayValue)
        assertEquals(timetable1.mTimetableId.dbValue, scheduleListProperty[index].mTimetableId.dbValue)

        index = 2
        assertEquals("17/03/15", scheduleListProperty[index].mPlanDate.displayValue)
        assertEquals(timetable1.mTimetableId.dbValue, scheduleListProperty[index].mTimetableId.dbValue)

        index = 3
        assertEquals("17/04/15", scheduleListProperty[index].mPlanDate.displayValue)
        assertEquals(timetable1.mTimetableId.dbValue, scheduleListProperty[index].mTimetableId.dbValue)

        index = 4
        assertEquals("17/05/15", scheduleListProperty[index].mPlanDate.displayValue)
        assertEquals(timetable1.mTimetableId.dbValue, scheduleListProperty[index].mTimetableId.dbValue)

        index = 5
        assertEquals("17/06/15", scheduleListProperty[index].mPlanDate.displayValue)
        assertEquals(timetable1.mTimetableId.dbValue, scheduleListProperty[index].mTimetableId.dbValue)
    }

}
