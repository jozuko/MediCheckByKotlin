package com.studiojozu.medicheck.infrastructure.persistence.dao

import com.studiojozu.medicheck.domain.model.medicine.MedicineIdType
import com.studiojozu.medicheck.domain.model.schedule.Schedule
import com.studiojozu.medicheck.domain.model.schedule.ScheduleIsTakeType
import com.studiojozu.medicheck.domain.model.schedule.ScheduleNeedAlarmType
import com.studiojozu.medicheck.domain.model.schedule.SchedulePlanDateType
import com.studiojozu.medicheck.domain.model.setting.ATestParent
import com.studiojozu.medicheck.domain.model.setting.TimetableIdType
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

@RunWith(RobolectricTestRunner::class)
@Config(manifest = "src/main/AndroidManifest.xml")
@PowerMockIgnore("org.mockito.*", "org.robolectric.*", "android.*")
class SqliteScheduleRepositoryTest : ATestParent() {
    private val calendar = Calendar.getInstance()

    private val schedule1: Schedule
    private val schedule2: Schedule
    private val schedule3: Schedule
    private val schedule4: Schedule

    init {
        calendar.set(2017, 0, 2, 0, 0, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        schedule1 = Schedule(
                mMedicineId = MedicineIdType(),
                mTimetableId = TimetableIdType(),
                mSchedulePlanDate = SchedulePlanDateType(calendar),
                mScheduleNeedAlert = ScheduleNeedAlarmType(true),
                mScheduleIsTake = ScheduleIsTakeType(false))

        schedule2 = Schedule(
                mMedicineId = MedicineIdType(),
                mTimetableId = TimetableIdType(),
                mSchedulePlanDate = SchedulePlanDateType(calendar),
                mScheduleNeedAlert = ScheduleNeedAlarmType(false),
                mScheduleIsTake = ScheduleIsTakeType(false))

        schedule3 = Schedule(
                mMedicineId = MedicineIdType(),
                mTimetableId = TimetableIdType(),
                mSchedulePlanDate = SchedulePlanDateType(calendar),
                mScheduleNeedAlert = ScheduleNeedAlarmType(false),
                mScheduleIsTake = ScheduleIsTakeType(true))

        schedule4 = Schedule(
                mMedicineId = MedicineIdType(),
                mTimetableId = TimetableIdType(),
                mSchedulePlanDate = SchedulePlanDateType(calendar),
                mScheduleNeedAlert = ScheduleNeedAlarmType(true),
                mScheduleIsTake = ScheduleIsTakeType(true))
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

        // insert
        val insertEntity = schedule1.copy()
        dao.insert(setSqliteSchedule(insertEntity))
        schedules = dao.findAll()
        assertEquals(1, schedules.size)
        assert(insertEntity, schedules[0])

        // update
        val updateEntity = insertEntity.copy(mScheduleIsTake = ScheduleIsTakeType(true))
        dao.insert(setSqliteSchedule(updateEntity))
        schedules = dao.findAll()
        assertEquals(1, schedules.size)
        assert(updateEntity, schedules[0])

        // delete
        val deleteEntity = insertEntity.copy()
        dao.delete(setSqliteSchedule(deleteEntity))
        schedules = dao.findAll()
        assertEquals(0, schedules.size)
    }

    @Test
    @Throws(Exception::class)
    fun findAlertAll() {
        val database = AppDatabase.getAppDatabase(RuntimeEnvironment.application.applicationContext)
        val dao = database.scheduleDao()

        // insert
        dao.insert(setSqliteSchedule(schedule1))
        dao.insert(setSqliteSchedule(schedule2))
        dao.insert(setSqliteSchedule(schedule3))
        dao.insert(setSqliteSchedule(schedule4))

        // findById
        val scheduleArray1 = dao.findAlertAll()
        assertEquals(1, scheduleArray1.size)
        assert(schedule1, scheduleArray1[0])

        // delete
        dao.delete(setSqliteSchedule(schedule1))

        // findById
        val scheduleArray2 = dao.findAlertAll()
        assertEquals(0, scheduleArray2.size)

        // delete
        dao.delete(setSqliteSchedule(schedule2))
        dao.delete(setSqliteSchedule(schedule3))
        dao.delete(setSqliteSchedule(schedule4))
    }

    private fun setSqliteSchedule(entity: Schedule): SqliteSchedule {
        val sqliteSchedule = SqliteSchedule(
                medicineId = entity.mMedicineId,
                schedulePlanDate = entity.mSchedulePlanDate,
                timetableId = entity.mTimetableId)
        sqliteSchedule.mScheduleNeedAlarm = entity.mScheduleNeedAlert
        sqliteSchedule.mScheduleIsTake = entity.mScheduleIsTake
        sqliteSchedule.mScheduleTookDatetime = entity.mScheduleTookDatetime

        return sqliteSchedule
    }

    private fun assert(expect: Schedule, actual: SqliteSchedule) {
        assertEquals(expect.mMedicineId, actual.mMedicineId)
        assertEquals(expect.mSchedulePlanDate, actual.mSchedulePlanDate)
        assertEquals(expect.mTimetableId, actual.mTimetableId)
        assertEquals(expect.mScheduleNeedAlert, actual.mScheduleNeedAlarm)
        assertEquals(expect.mScheduleIsTake, actual.mScheduleIsTake)
        assertEquals(expect.mScheduleTookDatetime, actual.mScheduleTookDatetime)
    }
}