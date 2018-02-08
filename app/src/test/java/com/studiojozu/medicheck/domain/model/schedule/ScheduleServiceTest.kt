package com.studiojozu.medicheck.domain.model.schedule

import com.studiojozu.medicheck.domain.model.medicine.*
import com.studiojozu.medicheck.domain.model.setting.*
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Suppress("FunctionName")
@RunWith(RobolectricTestRunner::class)
@Config(manifest = "src/main/AndroidManifest.xml")
class ScheduleServiceTest : ATestParent() {

    private val mScheduleListProperty = findProperty(ScheduleList::class, "scheduleList")
    @Suppress("UNCHECKED_CAST")
    private fun getScheduleList(entity: ScheduleList): MutableList<Schedule> = mScheduleListProperty.call(entity) as MutableList<Schedule>

    private val timetable1 = Timetable(
            timetableId = TimetableIdType("time0001"),
            timetableName = TimetableNameType("朝"),
            timetableTime = TimetableTimeType(7, 0),
            timetableDisplayOrder = TimetableDisplayOrderType(1))

    private val timetable2 = Timetable(
            timetableId = TimetableIdType("time0002"),
            timetableName = TimetableNameType("昼"),
            timetableTime = TimetableTimeType(12, 30),
            timetableDisplayOrder = TimetableDisplayOrderType(2))

    private val timetable3 = Timetable(
            timetableId = TimetableIdType("time0003"),
            timetableName = TimetableNameType("夜"),
            timetableTime = TimetableTimeType(19, 0),
            timetableDisplayOrder = TimetableDisplayOrderType(3))

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
        val timetableList = MedicineTimetableList()
        timetableList.setTimetableList(mutableListOf(timetable1, timetable2, timetable3))

        val medicine = Medicine(
                medicineStartDatetime = MedicineStartDatetimeType(2017, 1, 2, 3, 4),
                timetableList = timetableList,
                medicineDateNumber = MedicineDateNumberType(7),
                medicineInterval = MedicineIntervalType(0),
                medicineIntervalMode = MedicineIntervalModeType(MedicineIntervalModeType.DateIntervalPattern.DAYS)
        )

        val scheduleService = ScheduleService()
        val scheduleList = scheduleService.createScheduleList(medicine)
        val scheduleListProperty = getScheduleList(scheduleList)

        assertEquals(21, scheduleListProperty.count())

        var index = 0
        assertEquals("17/01/02", scheduleListProperty[index].schedulePlanDate.displayValue)
        assertEquals(timetable1.timetableId.dbValue, scheduleListProperty[index].timetableId.dbValue)

        index = 1
        assertEquals("17/01/02", scheduleListProperty[index].schedulePlanDate.displayValue)
        assertEquals(timetable2.timetableId.dbValue, scheduleListProperty[index].timetableId.dbValue)

        index = 2
        assertEquals("17/01/02", scheduleListProperty[index].schedulePlanDate.displayValue)
        assertEquals(timetable3.timetableId.dbValue, scheduleListProperty[index].timetableId.dbValue)

        index = 3
        assertEquals("17/01/03", scheduleListProperty[index].schedulePlanDate.displayValue)
        assertEquals(timetable1.timetableId.dbValue, scheduleListProperty[index].timetableId.dbValue)

        index = 4
        assertEquals("17/01/03", scheduleListProperty[index].schedulePlanDate.displayValue)
        assertEquals(timetable2.timetableId.dbValue, scheduleListProperty[index].timetableId.dbValue)

        index = 5
        assertEquals("17/01/03", scheduleListProperty[index].schedulePlanDate.displayValue)
        assertEquals(timetable3.timetableId.dbValue, scheduleListProperty[index].timetableId.dbValue)

        index = 6
        assertEquals("17/01/04", scheduleListProperty[index].schedulePlanDate.displayValue)
        assertEquals(timetable1.timetableId.dbValue, scheduleListProperty[index].timetableId.dbValue)

        index = 7
        assertEquals("17/01/04", scheduleListProperty[index].schedulePlanDate.displayValue)
        assertEquals(timetable2.timetableId.dbValue, scheduleListProperty[index].timetableId.dbValue)

        index = 8
        assertEquals("17/01/04", scheduleListProperty[index].schedulePlanDate.displayValue)
        assertEquals(timetable3.timetableId.dbValue, scheduleListProperty[index].timetableId.dbValue)

        index = 9
        assertEquals("17/01/05", scheduleListProperty[index].schedulePlanDate.displayValue)
        assertEquals(timetable1.timetableId.dbValue, scheduleListProperty[index].timetableId.dbValue)

        index = 10
        assertEquals("17/01/05", scheduleListProperty[index].schedulePlanDate.displayValue)
        assertEquals(timetable2.timetableId.dbValue, scheduleListProperty[index].timetableId.dbValue)

        index = 11
        assertEquals("17/01/05", scheduleListProperty[index].schedulePlanDate.displayValue)
        assertEquals(timetable3.timetableId.dbValue, scheduleListProperty[index].timetableId.dbValue)

        index = 12
        assertEquals("17/01/06", scheduleListProperty[index].schedulePlanDate.displayValue)
        assertEquals(timetable1.timetableId.dbValue, scheduleListProperty[index].timetableId.dbValue)

        index = 13
        assertEquals("17/01/06", scheduleListProperty[index].schedulePlanDate.displayValue)
        assertEquals(timetable2.timetableId.dbValue, scheduleListProperty[index].timetableId.dbValue)

        index = 14
        assertEquals("17/01/06", scheduleListProperty[index].schedulePlanDate.displayValue)
        assertEquals(timetable3.timetableId.dbValue, scheduleListProperty[index].timetableId.dbValue)

        index = 15
        assertEquals("17/01/07", scheduleListProperty[index].schedulePlanDate.displayValue)
        assertEquals(timetable1.timetableId.dbValue, scheduleListProperty[index].timetableId.dbValue)

        index = 16
        assertEquals("17/01/07", scheduleListProperty[index].schedulePlanDate.displayValue)
        assertEquals(timetable2.timetableId.dbValue, scheduleListProperty[index].timetableId.dbValue)

        index = 17
        assertEquals("17/01/07", scheduleListProperty[index].schedulePlanDate.displayValue)
        assertEquals(timetable3.timetableId.dbValue, scheduleListProperty[index].timetableId.dbValue)

        index = 18
        assertEquals("17/01/08", scheduleListProperty[index].schedulePlanDate.displayValue)
        assertEquals(timetable1.timetableId.dbValue, scheduleListProperty[index].timetableId.dbValue)

        index = 19
        assertEquals("17/01/08", scheduleListProperty[index].schedulePlanDate.displayValue)
        assertEquals(timetable2.timetableId.dbValue, scheduleListProperty[index].timetableId.dbValue)

        index = 20
        assertEquals("17/01/08", scheduleListProperty[index].schedulePlanDate.displayValue)
        assertEquals(timetable3.timetableId.dbValue, scheduleListProperty[index].timetableId.dbValue)
    }

    @Test
    @Throws(Exception::class)
    fun getScheduleList_ThreeDaysOneTimesInTwoDays() {
        val timetableList = MedicineTimetableList()
        timetableList.setTimetableList(mutableListOf(timetable1))

        val medicine = Medicine(
                medicineStartDatetime = MedicineStartDatetimeType(2017, 1, 2, 3, 4),
                timetableList = timetableList,
                medicineDateNumber = MedicineDateNumberType(3),
                medicineInterval = MedicineIntervalType(1),
                medicineIntervalMode = MedicineIntervalModeType(MedicineIntervalModeType.DateIntervalPattern.DAYS)
        )

        val scheduleService = ScheduleService()
        val scheduleList = scheduleService.createScheduleList(medicine)
        val scheduleListProperty = getScheduleList(scheduleList)

        assertEquals(3, scheduleListProperty.count())

        var index = 0
        assertEquals("17/01/02", scheduleListProperty[index].schedulePlanDate.displayValue)
        assertEquals(timetable1.timetableId.dbValue, scheduleListProperty[index].timetableId.dbValue)

        index = 1
        assertEquals("17/01/04", scheduleListProperty[index].schedulePlanDate.displayValue)
        assertEquals(timetable1.timetableId.dbValue, scheduleListProperty[index].timetableId.dbValue)

        index = 2
        assertEquals("17/01/06", scheduleListProperty[index].schedulePlanDate.displayValue)
        assertEquals(timetable1.timetableId.dbValue, scheduleListProperty[index].timetableId.dbValue)
    }

    @Test
    @Throws(Exception::class)
    fun getScheduleList_OnceAMonthForSixMonths() {
        val timetableList = MedicineTimetableList()
        timetableList.setTimetableList(mutableListOf(timetable1))

        val medicine = Medicine(
                medicineStartDatetime = MedicineStartDatetimeType(2017, 1, 2, 3, 4),
                timetableList = timetableList,
                medicineDateNumber = MedicineDateNumberType(6),
                medicineInterval = MedicineIntervalType(15),
                medicineIntervalMode = MedicineIntervalModeType(MedicineIntervalModeType.DateIntervalPattern.MONTH)
        )

        val scheduleService = ScheduleService()
        val scheduleList = scheduleService.createScheduleList(medicine)
        val scheduleListProperty = getScheduleList(scheduleList)

        assertEquals(6, scheduleListProperty.count())

        var index = 0
        assertEquals("17/01/15", scheduleListProperty[index].schedulePlanDate.displayValue)
        assertEquals(timetable1.timetableId.dbValue, scheduleListProperty[index].timetableId.dbValue)

        index = 1
        assertEquals("17/02/15", scheduleListProperty[index].schedulePlanDate.displayValue)
        assertEquals(timetable1.timetableId.dbValue, scheduleListProperty[index].timetableId.dbValue)

        index = 2
        assertEquals("17/03/15", scheduleListProperty[index].schedulePlanDate.displayValue)
        assertEquals(timetable1.timetableId.dbValue, scheduleListProperty[index].timetableId.dbValue)

        index = 3
        assertEquals("17/04/15", scheduleListProperty[index].schedulePlanDate.displayValue)
        assertEquals(timetable1.timetableId.dbValue, scheduleListProperty[index].timetableId.dbValue)

        index = 4
        assertEquals("17/05/15", scheduleListProperty[index].schedulePlanDate.displayValue)
        assertEquals(timetable1.timetableId.dbValue, scheduleListProperty[index].timetableId.dbValue)

        index = 5
        assertEquals("17/06/15", scheduleListProperty[index].schedulePlanDate.displayValue)
        assertEquals(timetable1.timetableId.dbValue, scheduleListProperty[index].timetableId.dbValue)
    }

}
