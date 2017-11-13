package com.studiojozu.medicheck.application

import com.studiojozu.common.domain.model.CalendarNoSecond
import com.studiojozu.medicheck.di.MediCheckTestApplication
import com.studiojozu.medicheck.domain.model.medicine.*
import com.studiojozu.medicheck.domain.model.person.Person
import com.studiojozu.medicheck.domain.model.person.PersonDisplayOrderType
import com.studiojozu.medicheck.domain.model.person.PersonIdType
import com.studiojozu.medicheck.domain.model.person.PersonNameType
import com.studiojozu.medicheck.domain.model.schedule.Schedule
import com.studiojozu.medicheck.domain.model.schedule.ScheduleIsTakeType
import com.studiojozu.medicheck.domain.model.schedule.ScheduleNeedAlarmType
import com.studiojozu.medicheck.domain.model.schedule.SchedulePlanDateType
import com.studiojozu.medicheck.domain.model.setting.*
import com.studiojozu.medicheck.infrastructure.persistence.database.AppDatabase
import com.studiojozu.medicheck.infrastructure.persistence.entity.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.powermock.core.classloader.annotations.PowerMockIgnore
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import javax.inject.Inject

@RunWith(RobolectricTestRunner::class)
@Config(manifest = "src/main/AndroidManifest.xml", application = MediCheckTestApplication::class)
@PowerMockIgnore("org.mockito.*", "org.robolectric.*", "android.*")
class AlarmScheduleServiceTest : ATestParent() {
    @Inject
    lateinit var alarmScheduleService: AlarmScheduleService

    @Before
    fun setUp() = (RuntimeEnvironment.application as MediCheckTestApplication).mAppTestComponent.inject(this)

    @Test
    @Throws(Exception::class)
    fun getNeedAlarmSchedules() {
        // no alarm data
        var alarmSchedules = alarmScheduleService.getNeedAlarmSchedules()
        assertEquals(0, alarmSchedules.size)

        createNeedAlarmData()

        // find alarm data
        alarmSchedules = alarmScheduleService.getNeedAlarmSchedules()
        assertEquals(1, alarmSchedules.size)
        assertEquals(medicine1.mMedicineName.displayValue, alarmSchedules[0].medicineName)
        assertEquals(person1.mPersonName.displayValue, alarmSchedules[0].personName)

        deleteNeedAlarmData()
    }

    private fun createNeedAlarmData() {
        database.personDao().insert(SqlitePerson.build { mPerson = person1 })
        database.medicineUnitDao().insert(SqliteMedicineUnit.build { mMedicineUnit = medicineUnit1 })
        database.timetableDao().insert(SqliteTimetable.build { mTimetable = timetable1 })
        database.timetableDao().insert(SqliteTimetable.build { mTimetable = timetable2 })
        database.timetableDao().insert(SqliteTimetable.build { mTimetable = timetable3 })
        database.medicineDao().insert(SqliteMedicine.build { mMedicine = medicine1 })

        database.mediTimeRelationDao().insert(SqliteMediTimeRelation.build {
            mMedicineId = medicine1.mMedicineId
            mTimetableId = timetable1.mTimetableId
            mIsOneShot = IsOneShotType(false)
        })

        database.mediTimeRelationDao().insert(SqliteMediTimeRelation.build {
            mMedicineId = medicine1.mMedicineId
            mTimetableId = timetable2.mTimetableId
            mIsOneShot = IsOneShotType(false)
        })

        database.mediTimeRelationDao().insert(SqliteMediTimeRelation.build {
            mMedicineId = medicine1.mMedicineId
            mTimetableId = timetable3.mTimetableId
            mIsOneShot = IsOneShotType(false)
        })

        database.personMediRelationDao().insert(SqlitePersonMediRelation.build {
            mMedicineId = medicine1.mMedicineId
            mPersonId = person1.mPersonId
        })

        database.scheduleDao().insert(SqliteSchedule.build { mSchedule = schedule1 })
        database.scheduleDao().insert(SqliteSchedule.build { mSchedule = schedule2 })
        database.scheduleDao().insert(SqliteSchedule.build { mSchedule = schedule3 })
    }

    private fun deleteNeedAlarmData() {
        database.personDao().delete(SqlitePerson.build { mPerson = person1 })
        database.medicineUnitDao().delete(SqliteMedicineUnit.build { mMedicineUnit = medicineUnit1 })
        database.timetableDao().delete(SqliteTimetable.build { mTimetable = timetable1 })
        database.timetableDao().delete(SqliteTimetable.build { mTimetable = timetable2 })
        database.timetableDao().delete(SqliteTimetable.build { mTimetable = timetable3 })
        database.medicineDao().delete(SqliteMedicine.build { mMedicine = medicine1 })

        database.mediTimeRelationDao().delete(SqliteMediTimeRelation.build {
            mMedicineId = medicine1.mMedicineId
            mTimetableId = timetable1.mTimetableId
            mIsOneShot = IsOneShotType(false)
        })

        database.mediTimeRelationDao().delete(SqliteMediTimeRelation.build {
            mMedicineId = medicine1.mMedicineId
            mTimetableId = timetable2.mTimetableId
            mIsOneShot = IsOneShotType(false)
        })

        database.mediTimeRelationDao().delete(SqliteMediTimeRelation.build {
            mMedicineId = medicine1.mMedicineId
            mTimetableId = timetable3.mTimetableId
            mIsOneShot = IsOneShotType(false)
        })

        database.personMediRelationDao().delete(SqlitePersonMediRelation.build {
            mMedicineId = medicine1.mMedicineId
            mPersonId = person1.mPersonId
        })

        database.scheduleDao().delete(SqliteSchedule.build { mSchedule = schedule1 })
        database.scheduleDao().delete(SqliteSchedule.build { mSchedule = schedule2 })
        database.scheduleDao().delete(SqliteSchedule.build { mSchedule = schedule3 })
    }

    private val database = AppDatabase.getAppDatabase(RuntimeEnvironment.application.applicationContext)

    private val person1 = Person(
            mPersonId = PersonIdType("person01"),
            mPersonName = PersonNameType("自分"),
            mPersonDisplayOrder = PersonDisplayOrderType(10))

    private val medicineUnit1 = MedicineUnit(
            mMedicineUnitId = MedicineUnitIdType("unit01"),
            mMedicineUnitValue = MedicineUnitValueType(),
            mMedicineUnitDisplayOrder = MedicineUnitDisplayOrderType(2))

    private val medicine1 = Medicine(
            mMedicineId = MedicineIdType("medicine01"),
            mMedicineName = MedicineNameType("メルカゾール"),
            mMedicineUnit = medicineUnit1)

    private val timetable1 = Timetable(
            mTimetableId = TimetableIdType("timetable01"),
            mTimetableName = TimetableNameType("朝"),
            mTimetableTime = TimetableTimeType(CalendarNoSecond().calendar),
            mTimetableDisplayOrder = TimetableDisplayOrderType(20))

    private val timetable2 = Timetable(
            mTimetableId = TimetableIdType("timetable02"),
            mTimetableName = TimetableNameType("昼"),
            mTimetableTime = TimetableTimeType(12, 0),
            mTimetableDisplayOrder = TimetableDisplayOrderType(20))

    private val timetable3 = Timetable(
            mTimetableId = TimetableIdType("timetable03"),
            mTimetableName = TimetableNameType("夜"),
            mTimetableTime = TimetableTimeType(19, 0),
            mTimetableDisplayOrder = TimetableDisplayOrderType(20))

    private val schedule1 = Schedule(
            mMedicineId = medicine1.mMedicineId,
            mTimetableId = timetable1.mTimetableId,
            mSchedulePlanDate = SchedulePlanDateType(CalendarNoSecond().calendar),
            mScheduleNeedAlarm = ScheduleNeedAlarmType(true),
            mScheduleIsTake = ScheduleIsTakeType(false))

    private val schedule2 = Schedule(
            mMedicineId = medicine1.mMedicineId,
            mTimetableId = timetable2.mTimetableId,
            mSchedulePlanDate = SchedulePlanDateType(CalendarNoSecond().calendar),
            mScheduleNeedAlarm = ScheduleNeedAlarmType(true),
            mScheduleIsTake = ScheduleIsTakeType(false))

    private val schedule3 = Schedule(
            mMedicineId = medicine1.mMedicineId,
            mTimetableId = timetable3.mTimetableId,
            mSchedulePlanDate = SchedulePlanDateType(CalendarNoSecond().calendar),
            mScheduleNeedAlarm = ScheduleNeedAlarmType(true),
            mScheduleIsTake = ScheduleIsTakeType(false))
}