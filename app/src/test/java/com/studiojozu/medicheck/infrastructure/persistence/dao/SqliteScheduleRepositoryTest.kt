package com.studiojozu.medicheck.infrastructure.persistence.dao

import com.studiojozu.medicheck.domain.model.medicine.*
import com.studiojozu.medicheck.domain.model.schedule.*
import com.studiojozu.medicheck.domain.model.setting.*
import com.studiojozu.medicheck.infrastructure.persistence.database.AppDatabase
import com.studiojozu.medicheck.infrastructure.persistence.entity.SqliteSchedule
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.powermock.core.classloader.annotations.PowerMockIgnore
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import java.util.*

@Suppress("LocalVariableName", "FunctionName")
@RunWith(RobolectricTestRunner::class)
@Config(manifest = "src/main/AndroidManifest.xml")
@PowerMockIgnore("org.mockito.*", "org.robolectric.*", "android.*")
class SqliteScheduleRepositoryTest : ATestParent() {
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
    fun crud() {
        val database = AppDatabase.getAppDatabase(RuntimeEnvironment.application.applicationContext)
        val dao = database.scheduleDao()

        // select no data
        var schedules = dao.findAll()
        assertNotNull(schedules)
        assertEquals(0, schedules.size)

        // insert - 1
        dao.insert(setSqliteSchedule(schedule1))
        schedules = dao.findAll()
        assertEquals(1, schedules.size)
        assert(schedule1, schedules[0])

        // insert - 2
        dao.insert(setSqliteSchedule(schedule2))
        schedules = dao.findAll()
        assertEquals(2, schedules.size)
        assert(schedule1, schedules[0])
        assert(schedule2, schedules[1])

        // insert - 3
        dao.insert(setSqliteSchedule(schedule3))
        schedules = dao.findAll()
        assertEquals(3, schedules.size)
        assert(schedule1, schedules[0])
        assert(schedule2, schedules[1])
        assert(schedule3, schedules[2])

        // insert - 4
        dao.insert(setSqliteSchedule(schedule4))
        schedules = dao.findAll()
        assertEquals(4, schedules.size)
        assert(schedule1, schedules[0])
        assert(schedule2, schedules[1])
        assert(schedule3, schedules[2])
        assert(schedule4, schedules[3])

        // update - 1
        dao.insert(setSqliteSchedule(schedule5))
        schedules = dao.findAll()
        assertEquals(4, schedules.size)
        assert(schedule2, schedules[0])
        assert(schedule3, schedules[1])
        assert(schedule4, schedules[2])
        assert(schedule5, schedules[3])

        // update - 2
        dao.insert(setSqliteSchedule(schedule6))
        schedules = dao.findAll()
        assertEquals(4, schedules.size)
        assert(schedule2, schedules[0])
        assert(schedule3, schedules[1])
        assert(schedule4, schedules[2])
        assert(schedule6, schedules[3])

        // delete - 1
        dao.delete(setSqliteSchedule(schedule1))
        schedules = dao.findAll()
        assertEquals(3, schedules.size)
        assert(schedule2, schedules[0])
        assert(schedule3, schedules[1])
        assert(schedule4, schedules[2])

        // delete - 1, 2, 3, 4
        dao.delete(setSqliteSchedule(schedule1))
        dao.delete(setSqliteSchedule(schedule2))
        dao.delete(setSqliteSchedule(schedule3))
        dao.delete(setSqliteSchedule(schedule4))
        schedules = dao.findAll()
        assertEquals(0, schedules.size)
    }

    @Test
    @Throws(Exception::class)
    fun findAlertAll() {
        val database = AppDatabase.getAppDatabase(RuntimeEnvironment.application.applicationContext)
        val dao = database.scheduleDao()

        // insert
        dao.insert(setSqliteSchedule(schedule1)) // base entity
        dao.insert(setSqliteSchedule(schedule2)) // deference date
        dao.insert(setSqliteSchedule(schedule3)) // deference time
        dao.insert(setSqliteSchedule(schedule4)) // deference id

        // findById
        var scheduleArray = dao.findAlarmAll()
        assertEquals(4, scheduleArray.size)
        assert(schedule1, scheduleArray[0])
        assert(schedule2, scheduleArray[1])
        assert(schedule3, scheduleArray[2])
        assert(schedule4, scheduleArray[3])

        // update alarm-off
        dao.insert(setSqliteSchedule(schedule5)) // already taken
        scheduleArray = dao.findAlarmAll()
        assertEquals(3, scheduleArray.size)
        assert(schedule2, scheduleArray[0])
        assert(schedule3, scheduleArray[1])
        assert(schedule4, scheduleArray[2])

        // update alarm-on
        dao.insert(setSqliteSchedule(schedule1)) // base entity
        scheduleArray = dao.findAlarmAll()
        assertEquals(4, scheduleArray.size)
        assert(schedule2, scheduleArray[0])
        assert(schedule3, scheduleArray[1])
        assert(schedule4, scheduleArray[2])
        assert(schedule1, scheduleArray[3])

        // update alarm-on, is_take
        dao.insert(setSqliteSchedule(schedule6)) // already taken
        scheduleArray = dao.findAlarmAll()
        assertEquals(3, scheduleArray.size)
        assert(schedule2, scheduleArray[0])
        assert(schedule3, scheduleArray[1])
        assert(schedule4, scheduleArray[2])

        // delete
        dao.delete(setSqliteSchedule(schedule1))
        dao.delete(setSqliteSchedule(schedule2))
        dao.delete(setSqliteSchedule(schedule3))
        dao.delete(setSqliteSchedule(schedule4))
    }

    @Test
    @Throws(Exception::class)
    fun deleteExceptHistoryByMedicineId() {
        val database = AppDatabase.getAppDatabase(RuntimeEnvironment.application.applicationContext)
        val dao = database.scheduleDao()

        // insert
        dao.insert(setSqliteSchedule(schedule6)) // already taken
        dao.insert(setSqliteSchedule(schedule2)) // deference date
        dao.insert(setSqliteSchedule(schedule3)) // deference time
        dao.insert(setSqliteSchedule(schedule4)) // deference id

        // findAll
        val scheduleArray1 = dao.findAll()
        assertEquals(4, scheduleArray1.size)

        // deleteExceptHistoryByMedicineId
        dao.deleteExceptHistoryByMedicineId(schedule1.mMedicineId.dbValue)

        // findAll
        val scheduleArray2 = dao.findAll()
        assertEquals(2, scheduleArray2.size)

        // delete
        dao.delete(setSqliteSchedule(schedule1))
        dao.delete(setSqliteSchedule(schedule2))
        dao.delete(setSqliteSchedule(schedule3))
        dao.delete(setSqliteSchedule(schedule4))
    }

    @Test
    @Throws(Exception::class)
    fun deleteAllByMedicineId() {
        val database = AppDatabase.getAppDatabase(RuntimeEnvironment.application.applicationContext)
        val dao = database.scheduleDao()

        // insert
        dao.insert(setSqliteSchedule(schedule6)) // already taken
        dao.insert(setSqliteSchedule(schedule2)) // deference date
        dao.insert(setSqliteSchedule(schedule3)) // deference time
        dao.insert(setSqliteSchedule(schedule4)) // deference id

        // findAll
        val scheduleArray1 = dao.findAll()
        assertEquals(4, scheduleArray1.size)

        // deleteAllByMedicineId
        dao.deleteAllByMedicineId(schedule1.mMedicineId.dbValue)

        // findAll
        val scheduleArray2 = dao.findAll()
        assertEquals(1, scheduleArray2.size)

        // delete
        dao.delete(setSqliteSchedule(schedule1))
        dao.delete(setSqliteSchedule(schedule2))
        dao.delete(setSqliteSchedule(schedule3))
        dao.delete(setSqliteSchedule(schedule4))
    }

    @Test
    @Throws(Exception::class)
    fun insertAll_ThreeTimesInDay() {
        val database = AppDatabase.getAppDatabase(RuntimeEnvironment.application.applicationContext)
        val dao = database.scheduleDao()

        val timetable1 = Timetable(
                mTimetableId = TimetableIdType("time0001"),
                mTimetableName = TimetableNameType("朝"),
                mTimetableTime = TimetableTimeType(7, 0),
                mTimetableDisplayOrder = TimetableDisplayOrderType(1))

        val timetable2 = Timetable(
                mTimetableId = TimetableIdType("time0002"),
                mTimetableName = TimetableNameType("昼"),
                mTimetableTime = TimetableTimeType(12, 30),
                mTimetableDisplayOrder = TimetableDisplayOrderType(2))

        val timetable3 = Timetable(
                mTimetableId = TimetableIdType("time0003"),
                mTimetableName = TimetableNameType("夜"),
                mTimetableTime = TimetableTimeType(19, 0),
                mTimetableDisplayOrder = TimetableDisplayOrderType(3))

        val medicine = Medicine(
                mMedicineStartDatetime = MedicineStartDatetimeType(2020, 1, 2, 3, 4),
                mTimetableList = MedicineTimetableList(mutableListOf(timetable1, timetable2, timetable3)),
                mMedicineDateNumber = MedicineDateNumberType(7),
                mMedicineInterval = MedicineIntervalType(0),
                mMedicineIntervalMode = MedicineIntervalModeType(MedicineIntervalModeType.DateIntervalPattern.DAYS)
        )
        val scheduleList = ScheduleList()
        scheduleList.createScheduleList(medicine)

        scheduleList.forEach { it ->
            dao.insert(SqliteSchedule.build { mSchedule = it })
        }

        val schedules = dao.findAll()
        assertEquals(21, schedules.size)
    }

    private fun setSqliteSchedule(entity: Schedule): SqliteSchedule =
            SqliteSchedule.build { mSchedule = entity }

    private fun assert(expect: Schedule, actual: SqliteSchedule) {
        assertEquals(expect.mMedicineId, actual.mMedicineId)
        assertEquals(expect.mSchedulePlanDate, actual.mSchedulePlanDate)
        assertEquals(expect.mTimetableId, actual.mTimetableId)
        assertEquals(expect.mScheduleNeedAlarm, actual.mScheduleNeedAlarm)
        assertEquals(expect.mScheduleIsTake, actual.mScheduleIsTake)
        assertEquals(expect.mScheduleTookDatetime, actual.mScheduleTookDatetime)
    }
}