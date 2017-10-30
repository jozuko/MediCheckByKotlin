package com.studiojozu.medicheck.domain.model.schedule

import com.studiojozu.common.domain.model.general.ADatetimeType
import com.studiojozu.common.domain.model.general.TestDatetimeType
import com.studiojozu.medicheck.domain.model.medicine.Medicine
import com.studiojozu.medicheck.domain.model.medicine.MedicineDateNumberType
import com.studiojozu.medicheck.domain.model.medicine.MedicineTimetableList
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
class ScheduleListTest : ATestParent() {

    private val calculateMedicineNumberFunction = findFunction(ScheduleList::class, "calculateMedicineNumber")
    private val getNextStartDatetimeFunction = findFunction(ScheduleList::class, "getNextStartDatetime")
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
    fun calculateMedicineNumberFunction_OneShotMedicine() {
        val scheduleList = ScheduleList()
        val timetableList = MedicineTimetableList()
        timetableList.isOneShotMedicine = true

        val result: Int = calculateMedicineNumberFunction.call(scheduleList, timetableList, MedicineDateNumberType()) as Int
        assertEquals(0, result)
    }

    @Test
    @Throws(Exception::class)
    fun calculateMedicineNumberFunction_ThreeTimesInDay() {
        val timetableList = MedicineTimetableList()
        timetableList.isOneShotMedicine = false
        timetableList.setTimetableList(mutableListOf(timetable1, timetable2, timetable3))

        val dateNumber = MedicineDateNumberType(7)

        val scheduleList = ScheduleList()
        val result: Int = calculateMedicineNumberFunction.call(scheduleList, timetableList, dateNumber) as Int
        assertEquals(21, result)
    }

    @Test
    @Throws(Exception::class)
    fun getNextStartDatetimeFunction_planDateNull() {
        val medicine = Medicine()
        medicine.setStartDatetime(2017, 1, 2, 3, 4)

        val scheduleList = ScheduleList()
        val result: ADatetimeType<*> = getNextStartDatetimeFunction.call(scheduleList, medicine, null) as ADatetimeType<*>
        assertEquals(medicine.startDatetime, result)
    }

    @Test
    @Throws(Exception::class)
    fun getNextStartDatetimeFunction_notLastTimetable() {
        val medicine = Medicine()
        medicine.setStartDatetime(2017, 1, 2, 3, 4)
        medicine.setTimetableList(mutableListOf(timetable1, timetable2, timetable3))

        val planDateTime = TestDatetimeType(2017, 1, 2, 7, 0)
        val planDate = PlanDate(mPlanDatetime = planDateTime, mTimetableId = timetable1.mTimetableId)

        val scheduleList = ScheduleList()
        val result: ADatetimeType<*> = getNextStartDatetimeFunction.call(scheduleList, medicine, planDate) as ADatetimeType<*>
        assertEquals(planDateTime, result)
    }

    @Test
    @Throws(Exception::class)
    fun getNextStartDatetimeFunction_isLastTimetableIntervalOneDay() {
        val medicine = Medicine()
        medicine.setStartDatetime(2017, 1, 2, 3, 4)
        medicine.setTimetableList(mutableListOf(timetable1, timetable2, timetable3))
        medicine.setTakeInterval(1, TakeIntervalModeType.DateIntervalPattern.DAYS)

        val planDateTime = TestDatetimeType(2017, 1, 2, 19, 0)
        val planDate = PlanDate(mPlanDatetime = planDateTime, mTimetableId = timetable3.mTimetableId)

        val scheduleList = ScheduleList()
        val result: ADatetimeType<*> = getNextStartDatetimeFunction.call(scheduleList, medicine, planDate) as ADatetimeType<*>
        assertEquals("17/01/03 0:00", result.displayValue)
    }

    @Test
    @Throws(Exception::class)
    fun getNextStartDatetimeFunction_isLastTimetableIntervalTwoDay() {
        val medicine = Medicine()
        medicine.setStartDatetime(2017, 1, 2, 3, 4)
        medicine.setTimetableList(mutableListOf(timetable1, timetable2, timetable3))
        medicine.setTakeInterval(2, TakeIntervalModeType.DateIntervalPattern.DAYS)

        val planDateTime = TestDatetimeType(2017, 1, 2, 19, 0)
        val planDate = PlanDate(mPlanDatetime = planDateTime, mTimetableId = timetable3.mTimetableId)

        val scheduleList = ScheduleList()
        val result: ADatetimeType<*> = getNextStartDatetimeFunction.call(scheduleList, medicine, planDate) as ADatetimeType<*>
        assertEquals("17/01/04 0:00", result.displayValue)
    }

    @Test
    @Throws(Exception::class)
    fun getNextStartDatetimeFunction_isLastTimetableIntervalOneMonth() {
        val medicine = Medicine()
        medicine.setStartDatetime(2017, 1, 2, 3, 4)
        medicine.setTimetableList(mutableListOf(timetable1, timetable2, timetable3))
        medicine.setTakeInterval(1, TakeIntervalModeType.DateIntervalPattern.MONTH)

        val planDateTime = TestDatetimeType(2017, 1, 2, 19, 0)
        val planDate = PlanDate(mPlanDatetime = planDateTime, mTimetableId = timetable3.mTimetableId)

        val scheduleList = ScheduleList()
        val result: ADatetimeType<*> = getNextStartDatetimeFunction.call(scheduleList, medicine, planDate) as ADatetimeType<*>
        assertEquals("17/02/01 0:00", result.displayValue)
    }

    @Test
    @Throws(Exception::class)
    fun getNextStartDatetimeFunction_isLastTimetableIntervalTwoMonth() {
        val medicine = Medicine()
        medicine.setStartDatetime(2017, 1, 2, 3, 4)
        medicine.setTimetableList(mutableListOf(timetable1, timetable2, timetable3))
        medicine.setTakeInterval(15, TakeIntervalModeType.DateIntervalPattern.MONTH)

        val planDateTime = TestDatetimeType(2017, 1, 2, 19, 0)
        val planDate = PlanDate(mPlanDatetime = planDateTime, mTimetableId = timetable3.mTimetableId)

        val scheduleList = ScheduleList()
        val result: ADatetimeType<*> = getNextStartDatetimeFunction.call(scheduleList, medicine, planDate) as ADatetimeType<*>
        assertEquals("17/02/15 0:00", result.displayValue)
    }

    @Test
    @Throws(Exception::class)
    fun createScheduleList_OneShotMedicine() {
        val medicine = Medicine()
        medicine.timetableList.isOneShotMedicine = true

        val scheduleList = ScheduleList()
        scheduleList.createScheduleList(medicine)

        assertEquals(0, scheduleList.count())
    }

    @Test
    @Throws(Exception::class)
    fun createScheduleList_SevenDaysThreeTimesInDay() {
        val medicine = Medicine()
        medicine.setStartDatetime(2017, 1, 2, 3, 4)
        medicine.setTimetableList(mutableListOf(timetable1, timetable2, timetable3))
        medicine.setDateNumber(7)
        medicine.setTakeInterval(1, TakeIntervalModeType.DateIntervalPattern.DAYS)

        val scheduleList = ScheduleList()
        scheduleList.createScheduleList(medicine)

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
    fun createScheduleList_ThreeDaysOneTimesInTwoDays() {
        val medicine = Medicine()
        medicine.setStartDatetime(2017, 1, 2, 3, 4)
        medicine.setTimetableList(mutableListOf(timetable1))
        medicine.setDateNumber(3)
        medicine.setTakeInterval(2, TakeIntervalModeType.DateIntervalPattern.DAYS)

        val scheduleList = ScheduleList()
        scheduleList.createScheduleList(medicine)

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
    fun createScheduleList_OnceAMonthForSixMonths() {
        val medicine = Medicine()
        medicine.setStartDatetime(2017, 1, 2, 3, 4)
        medicine.setTimetableList(mutableListOf(timetable1))
        medicine.setDateNumber(6)
        medicine.setTakeInterval(15, TakeIntervalModeType.DateIntervalPattern.MONTH)

        val scheduleList = ScheduleList()
        scheduleList.createScheduleList(medicine)

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