package com.studiojozu.medicheck.domain.model.schedule

import com.studiojozu.common.domain.model.general.ADatetimeType
import com.studiojozu.common.domain.model.general.TestDatetimeType
import com.studiojozu.medicheck.domain.model.medicine.*
import com.studiojozu.medicheck.domain.model.setting.*
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Suppress("FunctionName")
@RunWith(RobolectricTestRunner::class)
@Config(manifest = "src/main/AndroidManifest.xml")
class ScheduleListTest : ATestParent() {

    private val calculateMedicineNumberFunction = findFunction(ScheduleList::class, "calculateMedicineNumber")
    private val getNextStartDatetimeFunction = findFunction(ScheduleList::class, "getNextStartDatetime")
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
        val medicine = Medicine(medicineStartDatetime = MedicineStartDatetimeType(2017, 1, 2, 3, 4))
        val scheduleList = ScheduleList()
        val result: ADatetimeType<*> = getNextStartDatetimeFunction.call(scheduleList, medicine, null) as ADatetimeType<*>
        assertEquals(medicine.medicineStartDatetime, result)
    }

    @Test
    @Throws(Exception::class)
    fun getNextStartDatetimeFunction_notLastTimetable() {
        val medicine = Medicine(
                medicineStartDatetime = MedicineStartDatetimeType(2017, 1, 2, 3, 4),
                timetableList = MedicineTimetableList(mutableListOf(timetable1, timetable2, timetable3))
        )

        val planDateTime = TestDatetimeType(2017, 1, 2, 7, 0)
        val planDate = PlanDate(planDatetime = planDateTime, timetableId = timetable1.timetableId)

        val scheduleList = ScheduleList()
        val result: ADatetimeType<*> = getNextStartDatetimeFunction.call(scheduleList, medicine, planDate) as ADatetimeType<*>
        assertEquals(planDateTime, result)
    }

    @Test
    @Throws(Exception::class)
    fun getNextStartDatetimeFunction_isLastTimetableIntervalOneDay() {
        val medicine = Medicine(
                medicineStartDatetime = MedicineStartDatetimeType(2017, 1, 2, 3, 4),
                timetableList = MedicineTimetableList(mutableListOf(timetable1, timetable2, timetable3)),
                medicineInterval = MedicineIntervalType(0),
                medicineIntervalMode = MedicineIntervalModeType(MedicineIntervalModeType.DateIntervalPattern.DAYS)
        )

        val planDateTime = TestDatetimeType(2017, 1, 2, 19, 0)
        val planDate = PlanDate(planDatetime = planDateTime, timetableId = timetable3.timetableId)

        val scheduleList = ScheduleList()
        val result: ADatetimeType<*> = getNextStartDatetimeFunction.call(scheduleList, medicine, planDate) as ADatetimeType<*>
        assertEquals("17/01/03 0:00", result.displayValue)
    }

    @Test
    @Throws(Exception::class)
    fun getNextStartDatetimeFunction_isLastTimetableIntervalTwoDay() {
        val medicine = Medicine(
                medicineStartDatetime = MedicineStartDatetimeType(2017, 1, 2, 3, 4),
                timetableList = MedicineTimetableList(mutableListOf(timetable1, timetable2, timetable3)),
                medicineInterval = MedicineIntervalType(1),
                medicineIntervalMode = MedicineIntervalModeType(MedicineIntervalModeType.DateIntervalPattern.DAYS)
        )

        val planDateTime = TestDatetimeType(2017, 1, 2, 19, 0)
        val planDate = PlanDate(planDatetime = planDateTime, timetableId = timetable3.timetableId)

        val scheduleList = ScheduleList()
        val result: ADatetimeType<*> = getNextStartDatetimeFunction.call(scheduleList, medicine, planDate) as ADatetimeType<*>
        assertEquals("17/01/04 0:00", result.displayValue)
    }

    @Test
    @Throws(Exception::class)
    fun getNextStartDatetimeFunction_isLastTimetableIntervalMonthPattern1() {
        val medicine = Medicine(
                medicineStartDatetime = MedicineStartDatetimeType(2017, 1, 2, 3, 4),
                timetableList = MedicineTimetableList(mutableListOf(timetable1, timetable2, timetable3)),
                medicineInterval = MedicineIntervalType(1),
                medicineIntervalMode = MedicineIntervalModeType(MedicineIntervalModeType.DateIntervalPattern.MONTH)
        )

        val planDateTime = TestDatetimeType(2017, 1, 2, 19, 0)
        val planDate = PlanDate(planDatetime = planDateTime, timetableId = timetable3.timetableId)

        val scheduleList = ScheduleList()
        val result: ADatetimeType<*> = getNextStartDatetimeFunction.call(scheduleList, medicine, planDate) as ADatetimeType<*>
        assertEquals("17/02/01 0:00", result.displayValue)
    }

    @Test
    @Throws(Exception::class)
    fun getNextStartDatetimeFunction_isLastTimetableIntervalMonthPattern2() {
        val medicine = Medicine(
                medicineStartDatetime = MedicineStartDatetimeType(2017, 1, 2, 3, 4),
                timetableList = MedicineTimetableList(mutableListOf(timetable1, timetable2, timetable3)),
                medicineInterval = MedicineIntervalType(15),
                medicineIntervalMode = MedicineIntervalModeType(MedicineIntervalModeType.DateIntervalPattern.MONTH)
        )

        val planDateTime = TestDatetimeType(2017, 1, 2, 19, 0)
        val planDate = PlanDate(planDatetime = planDateTime, timetableId = timetable3.timetableId)

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
        val medicine = Medicine(
                medicineStartDatetime = MedicineStartDatetimeType(2017, 1, 2, 3, 4),
                timetableList = MedicineTimetableList(mutableListOf(timetable1, timetable2, timetable3)),
                medicineDateNumber = MedicineDateNumberType(7),
                medicineInterval = MedicineIntervalType(0),
                medicineIntervalMode = MedicineIntervalModeType(MedicineIntervalModeType.DateIntervalPattern.DAYS)
        )

        val scheduleList = ScheduleList()
        scheduleList.createScheduleList(medicine)

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
    fun createScheduleList_ThreeDaysOneTimesInTwoDays() {
        val medicine = Medicine(
                medicineStartDatetime = MedicineStartDatetimeType(2017, 1, 2, 3, 4),
                timetableList = MedicineTimetableList(mutableListOf(timetable1)),
                medicineDateNumber = MedicineDateNumberType(3),
                medicineInterval = MedicineIntervalType(1),
                medicineIntervalMode = MedicineIntervalModeType(MedicineIntervalModeType.DateIntervalPattern.DAYS)
        )

        val scheduleList = ScheduleList()
        scheduleList.createScheduleList(medicine)

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

    /**
     * 開始日 2017/1/2
     * 毎月 15日
     */
    @Test
    @Throws(Exception::class)
    fun createScheduleList_OnceAMonthForSixMonths() {
        val medicine = Medicine(
                medicineStartDatetime = MedicineStartDatetimeType(2017, 1, 2, 3, 4),
                timetableList = MedicineTimetableList(mutableListOf(timetable1)),
                medicineDateNumber = MedicineDateNumberType(6),
                medicineInterval = MedicineIntervalType(15),
                medicineIntervalMode = MedicineIntervalModeType(MedicineIntervalModeType.DateIntervalPattern.MONTH)
        )

        val scheduleList = ScheduleList()
        scheduleList.createScheduleList(medicine)

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

    /**
     * 開始日 2017/10/31
     * 毎月 15日
     */
    @Test
    @Throws(Exception::class)
    fun createScheduleList_OnceAMonthForSixMonthsPattern2() {
        val medicine = Medicine(
                medicineStartDatetime = MedicineStartDatetimeType(2017, 10, 31, 3, 4),
                timetableList = MedicineTimetableList(mutableListOf(timetable1)),
                medicineDateNumber = MedicineDateNumberType(3),
                medicineInterval = MedicineIntervalType(15),
                medicineIntervalMode = MedicineIntervalModeType(MedicineIntervalModeType.DateIntervalPattern.MONTH)
        )

        val scheduleList = ScheduleList()
        scheduleList.createScheduleList(medicine)

        val scheduleListProperty = getScheduleList(scheduleList)

        assertEquals(3, scheduleListProperty.count())

        var index = 0
        assertEquals("17/11/15", scheduleListProperty[index].schedulePlanDate.displayValue)
        assertEquals(timetable1.timetableId.dbValue, scheduleListProperty[index].timetableId.dbValue)

        index = 1
        assertEquals("17/12/15", scheduleListProperty[index].schedulePlanDate.displayValue)
        assertEquals(timetable1.timetableId.dbValue, scheduleListProperty[index].timetableId.dbValue)

        index = 2
        assertEquals("18/01/15", scheduleListProperty[index].schedulePlanDate.displayValue)
        assertEquals(timetable1.timetableId.dbValue, scheduleListProperty[index].timetableId.dbValue)
    }

    /**
     * 開始日 2017/1/15
     * 毎月 31日(月末)
     */
    @Test
    @Throws(Exception::class)
    fun createScheduleList_OnceAMonthForSixMonthsPattern3() {
        val medicine = Medicine(
                medicineStartDatetime = MedicineStartDatetimeType(2017, 1, 15, 3, 4),
                timetableList = MedicineTimetableList(mutableListOf(timetable1)),
                medicineDateNumber = MedicineDateNumberType(6),
                medicineInterval = MedicineIntervalType(31),
                medicineIntervalMode = MedicineIntervalModeType(MedicineIntervalModeType.DateIntervalPattern.MONTH)
        )

        val scheduleList = ScheduleList()
        scheduleList.createScheduleList(medicine)

        val scheduleListProperty = getScheduleList(scheduleList)

        assertEquals(6, scheduleListProperty.count())

        var index = 0
        assertEquals("17/01/31", scheduleListProperty[index].schedulePlanDate.displayValue)
        assertEquals(timetable1.timetableId.dbValue, scheduleListProperty[index].timetableId.dbValue)

        index = 1
        assertEquals("17/02/28", scheduleListProperty[index].schedulePlanDate.displayValue)
        assertEquals(timetable1.timetableId.dbValue, scheduleListProperty[index].timetableId.dbValue)

        index = 2
        assertEquals("17/03/31", scheduleListProperty[index].schedulePlanDate.displayValue)
        assertEquals(timetable1.timetableId.dbValue, scheduleListProperty[index].timetableId.dbValue)

        index = 3
        assertEquals("17/04/30", scheduleListProperty[index].schedulePlanDate.displayValue)
        assertEquals(timetable1.timetableId.dbValue, scheduleListProperty[index].timetableId.dbValue)

        index = 4
        assertEquals("17/05/31", scheduleListProperty[index].schedulePlanDate.displayValue)
        assertEquals(timetable1.timetableId.dbValue, scheduleListProperty[index].timetableId.dbValue)

        index = 5
        assertEquals("17/06/30", scheduleListProperty[index].schedulePlanDate.displayValue)
        assertEquals(timetable1.timetableId.dbValue, scheduleListProperty[index].timetableId.dbValue)
    }

    @Test
    @Throws(Exception::class)
    fun iterate() {
        val medicine = Medicine(
                medicineStartDatetime = MedicineStartDatetimeType(2017, 10, 31, 3, 4),
                timetableList = MedicineTimetableList(mutableListOf(timetable1)),
                medicineDateNumber = MedicineDateNumberType(3),
                medicineInterval = MedicineIntervalType(15),
                medicineIntervalMode = MedicineIntervalModeType(MedicineIntervalModeType.DateIntervalPattern.MONTH)
        )

        val scheduleList = ScheduleList()
        scheduleList.createScheduleList(medicine)
        val iterator = scheduleList.iterator()

        assertTrue(iterator.hasNext())
        assertEquals("17/11/15", iterator.next().schedulePlanDate.displayValue)
        assertTrue(iterator.hasNext())
        assertEquals("17/12/15", iterator.next().schedulePlanDate.displayValue)
        assertTrue(iterator.hasNext())
        assertEquals("18/01/15", iterator.next().schedulePlanDate.displayValue)
        assertFalse(iterator.hasNext())
    }

}