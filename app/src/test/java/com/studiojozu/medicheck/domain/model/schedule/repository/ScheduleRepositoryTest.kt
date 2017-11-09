package com.studiojozu.medicheck.domain.model.schedule.repository

import com.studiojozu.medicheck.di.MediCheckTestApplication
import com.studiojozu.medicheck.domain.model.medicine.*
import com.studiojozu.medicheck.domain.model.schedule.*
import com.studiojozu.medicheck.domain.model.setting.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.powermock.core.classloader.annotations.PowerMockIgnore
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import java.util.*
import javax.inject.Inject

@Suppress("FunctionName")
@RunWith(RobolectricTestRunner::class)
@Config(manifest = "src/main/AndroidManifest.xml", application = MediCheckTestApplication::class)
@PowerMockIgnore("org.mockito.*", "org.robolectric.*", "android.*")
class ScheduleRepositoryTest : ATestParent() {

    @Inject
    lateinit var scheduleRepository: ScheduleRepository

    @Before
    fun setUp() = (RuntimeEnvironment.application as MediCheckTestApplication).mAppTestComponent.inject(this)

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
                mMedicineId = MedicineIdType("medicine01"),
                mTimetableId = TimetableIdType("time01"),
                mSchedulePlanDate = SchedulePlanDateType(calendar1),
                mScheduleNeedAlarm = ScheduleNeedAlarmType(true),
                mScheduleIsTake = ScheduleIsTakeType(false))

        schedule2 = schedule1.copy(mSchedulePlanDate = SchedulePlanDateType(calendar2))

        schedule3 = schedule1.copy(mTimetableId = TimetableIdType("time02"))

        schedule4 = schedule1.copy(mMedicineId = MedicineIdType("medicine02"))

        schedule5 = schedule1.copy(mScheduleNeedAlarm = ScheduleNeedAlarmType(false))

        schedule6 = schedule1.copy(mScheduleIsTake = ScheduleIsTakeType(true))
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
        scheduleRepository.deleteExceptHistoryByMedicineId(schedule1.mMedicineId)

        // findAll
        val scheduleArray2 = scheduleRepository.findAll()
        assertEquals(2, scheduleArray2.size)

        // delete
        scheduleRepository.deleteAllByMedicineId(schedule1.mMedicineId)
        scheduleRepository.deleteAllByMedicineId(schedule4.mMedicineId)
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
        scheduleRepository.deleteAllByMedicineId(schedule1.mMedicineId)

        // findAll
        val scheduleArray2 = scheduleRepository.findAll()
        assertEquals(1, scheduleArray2.size)

        // delete
        scheduleRepository.deleteAllByMedicineId(schedule1.mMedicineId)
        scheduleRepository.deleteAllByMedicineId(schedule4.mMedicineId)
    }

    @Test
    @Throws(Exception::class)
    fun insertAll_OneShot() {
        val medicine = Medicine(
                mMedicineStartDatetime = MedicineStartDatetimeType(2020, 1, 2, 3, 4),
                mTimetableList = MedicineTimetableList(),
                mMedicineDateNumber = MedicineDateNumberType(7)
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
                mMedicineStartDatetime = MedicineStartDatetimeType(2017, 1, 2, 3, 4),
                mTimetableList = MedicineTimetableList(mutableListOf(timetable1, timetable2, timetable3)),
                mMedicineDateNumber = MedicineDateNumberType(7),
                mMedicineInterval = MedicineIntervalType(0),
                mMedicineIntervalMode = MedicineIntervalModeType(MedicineIntervalModeType.DateIntervalPattern.DAYS)
        )
        val scheduleList = ScheduleList()
        scheduleList.createScheduleList(medicine)

        scheduleRepository.insertAll(scheduleList)

        val schedules = scheduleRepository.findAll()
        assertEquals(21, schedules.size)

        var index = 0
        assertEquals(medicine.mMedicineId, schedules[index].mMedicineId)
        assertEquals("17/01/02", schedules[index].mSchedulePlanDate.displayValue)
        assertEquals(timetable1.mTimetableId.dbValue, schedules[index].mTimetableId.dbValue)

        index = 1
        assertEquals(medicine.mMedicineId, schedules[index].mMedicineId)
        assertEquals("17/01/02", schedules[index].mSchedulePlanDate.displayValue)
        assertEquals(timetable2.mTimetableId.dbValue, schedules[index].mTimetableId.dbValue)

        index = 2
        assertEquals(medicine.mMedicineId, schedules[index].mMedicineId)
        assertEquals("17/01/02", schedules[index].mSchedulePlanDate.displayValue)
        assertEquals(timetable3.mTimetableId.dbValue, schedules[index].mTimetableId.dbValue)

        index = 3
        assertEquals(medicine.mMedicineId, schedules[index].mMedicineId)
        assertEquals("17/01/03", schedules[index].mSchedulePlanDate.displayValue)
        assertEquals(timetable1.mTimetableId.dbValue, schedules[index].mTimetableId.dbValue)

        index = 4
        assertEquals(medicine.mMedicineId, schedules[index].mMedicineId)
        assertEquals("17/01/03", schedules[index].mSchedulePlanDate.displayValue)
        assertEquals(timetable2.mTimetableId.dbValue, schedules[index].mTimetableId.dbValue)

        index = 5
        assertEquals(medicine.mMedicineId, schedules[index].mMedicineId)
        assertEquals("17/01/03", schedules[index].mSchedulePlanDate.displayValue)
        assertEquals(timetable3.mTimetableId.dbValue, schedules[index].mTimetableId.dbValue)

        index = 6
        assertEquals(medicine.mMedicineId, schedules[index].mMedicineId)
        assertEquals("17/01/04", schedules[index].mSchedulePlanDate.displayValue)
        assertEquals(timetable1.mTimetableId.dbValue, schedules[index].mTimetableId.dbValue)

        index = 7
        assertEquals(medicine.mMedicineId, schedules[index].mMedicineId)
        assertEquals("17/01/04", schedules[index].mSchedulePlanDate.displayValue)
        assertEquals(timetable2.mTimetableId.dbValue, schedules[index].mTimetableId.dbValue)

        index = 8
        assertEquals(medicine.mMedicineId, schedules[index].mMedicineId)
        assertEquals("17/01/04", schedules[index].mSchedulePlanDate.displayValue)
        assertEquals(timetable3.mTimetableId.dbValue, schedules[index].mTimetableId.dbValue)

        index = 9
        assertEquals(medicine.mMedicineId, schedules[index].mMedicineId)
        assertEquals("17/01/05", schedules[index].mSchedulePlanDate.displayValue)
        assertEquals(timetable1.mTimetableId.dbValue, schedules[index].mTimetableId.dbValue)

        index = 10
        assertEquals(medicine.mMedicineId, schedules[index].mMedicineId)
        assertEquals("17/01/05", schedules[index].mSchedulePlanDate.displayValue)
        assertEquals(timetable2.mTimetableId.dbValue, schedules[index].mTimetableId.dbValue)

        index = 11
        assertEquals(medicine.mMedicineId, schedules[index].mMedicineId)
        assertEquals("17/01/05", schedules[index].mSchedulePlanDate.displayValue)
        assertEquals(timetable3.mTimetableId.dbValue, schedules[index].mTimetableId.dbValue)

        index = 12
        assertEquals(medicine.mMedicineId, schedules[index].mMedicineId)
        assertEquals("17/01/06", schedules[index].mSchedulePlanDate.displayValue)
        assertEquals(timetable1.mTimetableId.dbValue, schedules[index].mTimetableId.dbValue)

        index = 13
        assertEquals(medicine.mMedicineId, schedules[index].mMedicineId)
        assertEquals("17/01/06", schedules[index].mSchedulePlanDate.displayValue)
        assertEquals(timetable2.mTimetableId.dbValue, schedules[index].mTimetableId.dbValue)

        index = 14
        assertEquals(medicine.mMedicineId, schedules[index].mMedicineId)
        assertEquals("17/01/06", schedules[index].mSchedulePlanDate.displayValue)
        assertEquals(timetable3.mTimetableId.dbValue, schedules[index].mTimetableId.dbValue)

        index = 15
        assertEquals(medicine.mMedicineId, schedules[index].mMedicineId)
        assertEquals("17/01/07", schedules[index].mSchedulePlanDate.displayValue)
        assertEquals(timetable1.mTimetableId.dbValue, schedules[index].mTimetableId.dbValue)

        index = 16
        assertEquals(medicine.mMedicineId, schedules[index].mMedicineId)
        assertEquals("17/01/07", schedules[index].mSchedulePlanDate.displayValue)
        assertEquals(timetable2.mTimetableId.dbValue, schedules[index].mTimetableId.dbValue)

        index = 17
        assertEquals(medicine.mMedicineId, schedules[index].mMedicineId)
        assertEquals("17/01/07", schedules[index].mSchedulePlanDate.displayValue)
        assertEquals(timetable3.mTimetableId.dbValue, schedules[index].mTimetableId.dbValue)

        index = 18
        assertEquals(medicine.mMedicineId, schedules[index].mMedicineId)
        assertEquals("17/01/08", schedules[index].mSchedulePlanDate.displayValue)
        assertEquals(timetable1.mTimetableId.dbValue, schedules[index].mTimetableId.dbValue)

        index = 19
        assertEquals(medicine.mMedicineId, schedules[index].mMedicineId)
        assertEquals("17/01/08", schedules[index].mSchedulePlanDate.displayValue)
        assertEquals(timetable2.mTimetableId.dbValue, schedules[index].mTimetableId.dbValue)

        index = 20
        assertEquals(medicine.mMedicineId, schedules[index].mMedicineId)
        assertEquals("17/01/08", schedules[index].mSchedulePlanDate.displayValue)
        assertEquals(timetable3.mTimetableId.dbValue, schedules[index].mTimetableId.dbValue)
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
        scheduleRepository.deleteAllByMedicineId(schedule1.mMedicineId)
        scheduleRepository.deleteAllByMedicineId(schedule4.mMedicineId)
    }

}