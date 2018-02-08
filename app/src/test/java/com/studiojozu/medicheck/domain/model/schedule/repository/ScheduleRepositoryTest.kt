package com.studiojozu.medicheck.domain.model.schedule.repository

import com.studiojozu.medicheck.di.MediCheckTestApplication
import com.studiojozu.medicheck.domain.model.medicine.*
import com.studiojozu.medicheck.domain.model.schedule.*
import com.studiojozu.medicheck.domain.model.setting.*
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import java.util.*
import javax.inject.Inject

@Suppress("FunctionName")
@RunWith(RobolectricTestRunner::class)
@Config(manifest = "src/main/AndroidManifest.xml", application = MediCheckTestApplication::class)
class ScheduleRepositoryTest : ATestParent() {

    @Inject
    lateinit var scheduleRepository: ScheduleRepository

    @Before
    fun setUp() = (RuntimeEnvironment.application as MediCheckTestApplication).mAppTestComponent.inject(this)

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
    private val calendar1 = Calendar.getInstance()
    private val calendar2: Calendar

    private val schedule1: Schedule
    private val schedule2: Schedule
    private val schedule3: Schedule
    private val schedule4: Schedule
    private val schedule5: Schedule
    private val schedule6: Schedule

    init {
        calendar1.set(2017, 0, 2, 0, 0, 0)
        calendar1.set(Calendar.MILLISECOND, 0)

        calendar2 = calendar1.clone() as Calendar
        calendar2.add(Calendar.DAY_OF_MONTH, 1)

        schedule1 = Schedule(
                medicineId = MedicineIdType("medicine01"),
                timetableId = TimetableIdType("time01"),
                schedulePlanDate = SchedulePlanDateType(calendar1),
                scheduleNeedAlarm = ScheduleNeedAlarmType(true),
                scheduleIsTake = ScheduleIsTakeType(false))

        schedule2 = schedule1.copy(schedulePlanDate = SchedulePlanDateType(calendar2))

        schedule3 = schedule1.copy(timetableId = TimetableIdType("time02"))

        schedule4 = schedule1.copy(medicineId = MedicineIdType("medicine02"))

        schedule5 = schedule1.copy(scheduleNeedAlarm = ScheduleNeedAlarmType(false))

        schedule6 = schedule1.copy(scheduleIsTake = ScheduleIsTakeType(true))
    }

    @Test
    @Throws(Exception::class)
    fun deleteExceptHistoryByMedicineId() {
        // insert
        scheduleRepository.insert(schedule6) // already taken
        scheduleRepository.insert(schedule2) // deference date
        scheduleRepository.insert(schedule3) // deference time
        scheduleRepository.insert(schedule4) // deference id

        // findAll
        val scheduleArray1 = scheduleRepository.findAll()
        assertEquals(4, scheduleArray1.size)

        // deleteExceptHistoryByMedicineId
        scheduleRepository.deleteExceptHistoryByMedicineId(schedule1.medicineId)

        // findAll
        val scheduleArray2 = scheduleRepository.findAll()
        assertEquals(2, scheduleArray2.size)

        // delete
        scheduleRepository.deleteAllByMedicineId(schedule1.medicineId)
        scheduleRepository.deleteAllByMedicineId(schedule4.medicineId)
    }

    @Test
    @Throws(Exception::class)
    fun deleteAllByMedicineId() {
        // insert
        scheduleRepository.insert(schedule6) // already taken
        scheduleRepository.insert(schedule2) // deference date
        scheduleRepository.insert(schedule3) // deference time
        scheduleRepository.insert(schedule4) // deference id

        // findAll
        val scheduleArray1 = scheduleRepository.findAll()
        assertEquals(4, scheduleArray1.size)

        // deleteAllByMedicineId
        scheduleRepository.deleteAllByMedicineId(schedule1.medicineId)

        // findAll
        val scheduleArray2 = scheduleRepository.findAll()
        assertEquals(1, scheduleArray2.size)

        // delete
        scheduleRepository.deleteAllByMedicineId(schedule1.medicineId)
        scheduleRepository.deleteAllByMedicineId(schedule4.medicineId)
    }

    @Test
    @Throws(Exception::class)
    fun insertAll_OneShot() {
        val medicine = Medicine(
                medicineStartDatetime = MedicineStartDatetimeType(2020, 1, 2, 3, 4),
                timetableList = MedicineTimetableList(),
                medicineDateNumber = MedicineDateNumberType(7)
        )
        medicine.setOneShotMedicine(true)
        val scheduleList = ScheduleList()
        scheduleList.createScheduleList(medicine)

        scheduleRepository.insertAll(scheduleList)

        val schedules = scheduleRepository.findAll()
        assertEquals(0, schedules.size)
    }

    @Test
    @Throws(Exception::class)
    fun insertAll_ThreeTimesInDay() {
        val medicine = Medicine(
                medicineStartDatetime = MedicineStartDatetimeType(2017, 1, 2, 3, 4),
                timetableList = MedicineTimetableList(mutableListOf(timetable1, timetable2, timetable3)),
                medicineDateNumber = MedicineDateNumberType(7),
                medicineInterval = MedicineIntervalType(0),
                medicineIntervalMode = MedicineIntervalModeType(MedicineIntervalModeType.DateIntervalPattern.DAYS)
        )
        val scheduleList = ScheduleList()
        scheduleList.createScheduleList(medicine)

        scheduleRepository.insertAll(scheduleList)

        val schedules = scheduleRepository.findAll()
        assertEquals(21, schedules.size)

        var index = 0
        assertEquals(medicine.medicineId, schedules[index].medicineId)
        assertEquals("17/01/02", schedules[index].schedulePlanDate.displayValue)
        assertEquals(timetable1.timetableId.dbValue, schedules[index].timetableId.dbValue)

        index = 1
        assertEquals(medicine.medicineId, schedules[index].medicineId)
        assertEquals("17/01/02", schedules[index].schedulePlanDate.displayValue)
        assertEquals(timetable2.timetableId.dbValue, schedules[index].timetableId.dbValue)

        index = 2
        assertEquals(medicine.medicineId, schedules[index].medicineId)
        assertEquals("17/01/02", schedules[index].schedulePlanDate.displayValue)
        assertEquals(timetable3.timetableId.dbValue, schedules[index].timetableId.dbValue)

        index = 3
        assertEquals(medicine.medicineId, schedules[index].medicineId)
        assertEquals("17/01/03", schedules[index].schedulePlanDate.displayValue)
        assertEquals(timetable1.timetableId.dbValue, schedules[index].timetableId.dbValue)

        index = 4
        assertEquals(medicine.medicineId, schedules[index].medicineId)
        assertEquals("17/01/03", schedules[index].schedulePlanDate.displayValue)
        assertEquals(timetable2.timetableId.dbValue, schedules[index].timetableId.dbValue)

        index = 5
        assertEquals(medicine.medicineId, schedules[index].medicineId)
        assertEquals("17/01/03", schedules[index].schedulePlanDate.displayValue)
        assertEquals(timetable3.timetableId.dbValue, schedules[index].timetableId.dbValue)

        index = 6
        assertEquals(medicine.medicineId, schedules[index].medicineId)
        assertEquals("17/01/04", schedules[index].schedulePlanDate.displayValue)
        assertEquals(timetable1.timetableId.dbValue, schedules[index].timetableId.dbValue)

        index = 7
        assertEquals(medicine.medicineId, schedules[index].medicineId)
        assertEquals("17/01/04", schedules[index].schedulePlanDate.displayValue)
        assertEquals(timetable2.timetableId.dbValue, schedules[index].timetableId.dbValue)

        index = 8
        assertEquals(medicine.medicineId, schedules[index].medicineId)
        assertEquals("17/01/04", schedules[index].schedulePlanDate.displayValue)
        assertEquals(timetable3.timetableId.dbValue, schedules[index].timetableId.dbValue)

        index = 9
        assertEquals(medicine.medicineId, schedules[index].medicineId)
        assertEquals("17/01/05", schedules[index].schedulePlanDate.displayValue)
        assertEquals(timetable1.timetableId.dbValue, schedules[index].timetableId.dbValue)

        index = 10
        assertEquals(medicine.medicineId, schedules[index].medicineId)
        assertEquals("17/01/05", schedules[index].schedulePlanDate.displayValue)
        assertEquals(timetable2.timetableId.dbValue, schedules[index].timetableId.dbValue)

        index = 11
        assertEquals(medicine.medicineId, schedules[index].medicineId)
        assertEquals("17/01/05", schedules[index].schedulePlanDate.displayValue)
        assertEquals(timetable3.timetableId.dbValue, schedules[index].timetableId.dbValue)

        index = 12
        assertEquals(medicine.medicineId, schedules[index].medicineId)
        assertEquals("17/01/06", schedules[index].schedulePlanDate.displayValue)
        assertEquals(timetable1.timetableId.dbValue, schedules[index].timetableId.dbValue)

        index = 13
        assertEquals(medicine.medicineId, schedules[index].medicineId)
        assertEquals("17/01/06", schedules[index].schedulePlanDate.displayValue)
        assertEquals(timetable2.timetableId.dbValue, schedules[index].timetableId.dbValue)

        index = 14
        assertEquals(medicine.medicineId, schedules[index].medicineId)
        assertEquals("17/01/06", schedules[index].schedulePlanDate.displayValue)
        assertEquals(timetable3.timetableId.dbValue, schedules[index].timetableId.dbValue)

        index = 15
        assertEquals(medicine.medicineId, schedules[index].medicineId)
        assertEquals("17/01/07", schedules[index].schedulePlanDate.displayValue)
        assertEquals(timetable1.timetableId.dbValue, schedules[index].timetableId.dbValue)

        index = 16
        assertEquals(medicine.medicineId, schedules[index].medicineId)
        assertEquals("17/01/07", schedules[index].schedulePlanDate.displayValue)
        assertEquals(timetable2.timetableId.dbValue, schedules[index].timetableId.dbValue)

        index = 17
        assertEquals(medicine.medicineId, schedules[index].medicineId)
        assertEquals("17/01/07", schedules[index].schedulePlanDate.displayValue)
        assertEquals(timetable3.timetableId.dbValue, schedules[index].timetableId.dbValue)

        index = 18
        assertEquals(medicine.medicineId, schedules[index].medicineId)
        assertEquals("17/01/08", schedules[index].schedulePlanDate.displayValue)
        assertEquals(timetable1.timetableId.dbValue, schedules[index].timetableId.dbValue)

        index = 19
        assertEquals(medicine.medicineId, schedules[index].medicineId)
        assertEquals("17/01/08", schedules[index].schedulePlanDate.displayValue)
        assertEquals(timetable2.timetableId.dbValue, schedules[index].timetableId.dbValue)

        index = 20
        assertEquals(medicine.medicineId, schedules[index].medicineId)
        assertEquals("17/01/08", schedules[index].schedulePlanDate.displayValue)
        assertEquals(timetable3.timetableId.dbValue, schedules[index].timetableId.dbValue)
    }

    @Test
    @Throws(Exception::class)
    fun findAlertAll() {
        // insert
        scheduleRepository.insert(schedule1) // base entity
        scheduleRepository.insert(schedule2) // deference date
        scheduleRepository.insert(schedule3) // deference time
        scheduleRepository.insert(schedule4) // deference id

        // findById
        var scheduleArray = scheduleRepository.findAlarmAll()
        assertEquals(4, scheduleArray.size)

        // update alarm-off
        scheduleRepository.insert(schedule5) // already taken
        scheduleArray = scheduleRepository.findAlarmAll()
        assertEquals(3, scheduleArray.size)

        // update alarm-on
        scheduleRepository.insert(schedule1) // base entity
        scheduleArray = scheduleRepository.findAlarmAll()
        assertEquals(4, scheduleArray.size)

        // update alarm-on, is_take
        scheduleRepository.insert(schedule6) // already taken
        scheduleArray = scheduleRepository.findAlarmAll()
        assertEquals(3, scheduleArray.size)

        // delete
        scheduleRepository.deleteAllByMedicineId(schedule1.medicineId)
        scheduleRepository.deleteAllByMedicineId(schedule4.medicineId)
    }

    @Test
    @Throws(Exception::class)
    fun findByMedicineId() {
        // insert
        scheduleRepository.insert(schedule6) // already taken
        scheduleRepository.insert(schedule2) // deference date
        scheduleRepository.insert(schedule3) // deference time
        scheduleRepository.insert(schedule4) // deference id

        // findByMedicineId
        val scheduleArray = scheduleRepository.findByMedicineId(schedule1.medicineId)
        Assert.assertTrue(scheduleArray.isNotEmpty())
        assertEquals(schedule6.medicineId, scheduleArray[0].medicineId)
        assertEquals(schedule6.timetableId, scheduleArray[0].timetableId)
        assertEquals(schedule6.schedulePlanDate, scheduleArray[0].schedulePlanDate)

        assertEquals(schedule3.medicineId, scheduleArray[1].medicineId)
        assertEquals(schedule3.timetableId, scheduleArray[1].timetableId)
        assertEquals(schedule3.schedulePlanDate, scheduleArray[1].schedulePlanDate)

        assertEquals(schedule2.medicineId, scheduleArray[2].medicineId)
        assertEquals(schedule2.timetableId, scheduleArray[2].timetableId)
        assertEquals(schedule2.schedulePlanDate, scheduleArray[2].schedulePlanDate)

        // delete
        scheduleRepository.deleteAllByMedicineId(schedule6.medicineId)
        scheduleRepository.deleteAllByMedicineId(schedule2.medicineId)
        scheduleRepository.deleteAllByMedicineId(schedule3.medicineId)
        scheduleRepository.deleteAllByMedicineId(schedule4.medicineId)
    }

}