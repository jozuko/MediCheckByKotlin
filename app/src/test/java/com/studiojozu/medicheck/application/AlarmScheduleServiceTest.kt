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
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import javax.inject.Inject

@RunWith(RobolectricTestRunner::class)
@Config(manifest = "src/main/AndroidManifest.xml", application = MediCheckTestApplication::class)
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
        assertEquals(medicine1.medicineName.displayValue, alarmSchedules[0].medicineName)
        assertEquals(person1.personName.displayValue, alarmSchedules[0].personName)

        deleteNeedAlarmData()
    }

    private fun createNeedAlarmData() {
        database.personDao().insert(SqlitePerson.build { person = person1 })
        database.medicineUnitDao().insert(SqliteMedicineUnit.build { medicineUnit = medicineUnit1 })
        database.timetableDao().insert(SqliteTimetable.build { timetable = timetable1 })
        database.timetableDao().insert(SqliteTimetable.build { timetable = timetable2 })
        database.timetableDao().insert(SqliteTimetable.build { timetable = timetable3 })
        database.medicineDao().insert(SqliteMedicine.build { medicine = medicine1 })

        database.mediTimeRelationDao().insert(SqliteMediTimeRelation.build {
            medicineId = medicine1.medicineId
            timetableId = timetable1.timetableId
            oneShot = OneShotType(false)
        })

        database.mediTimeRelationDao().insert(SqliteMediTimeRelation.build {
            medicineId = medicine1.medicineId
            timetableId = timetable2.timetableId
            oneShot = OneShotType(false)
        })

        database.mediTimeRelationDao().insert(SqliteMediTimeRelation.build {
            medicineId = medicine1.medicineId
            timetableId = timetable3.timetableId
            oneShot = OneShotType(false)
        })

        database.personMediRelationDao().insert(SqlitePersonMediRelation.build {
            medicineId = medicine1.medicineId
            personId = person1.personId
        })

        database.scheduleDao().insert(SqliteSchedule.build { schedule = schedule1 })
        database.scheduleDao().insert(SqliteSchedule.build { schedule = schedule2 })
        database.scheduleDao().insert(SqliteSchedule.build { schedule = schedule3 })
    }

    private fun deleteNeedAlarmData() {
        database.personDao().delete(SqlitePerson.build { person = person1 })
        database.medicineUnitDao().delete(SqliteMedicineUnit.build { medicineUnit = medicineUnit1 })
        database.timetableDao().delete(SqliteTimetable.build { timetable = timetable1 })
        database.timetableDao().delete(SqliteTimetable.build { timetable = timetable2 })
        database.timetableDao().delete(SqliteTimetable.build { timetable = timetable3 })
        database.medicineDao().delete(SqliteMedicine.build { medicine = medicine1 })

        database.mediTimeRelationDao().delete(SqliteMediTimeRelation.build {
            medicineId = medicine1.medicineId
            timetableId = timetable1.timetableId
            oneShot = OneShotType(false)
        })

        database.mediTimeRelationDao().delete(SqliteMediTimeRelation.build {
            medicineId = medicine1.medicineId
            timetableId = timetable2.timetableId
            oneShot = OneShotType(false)
        })

        database.mediTimeRelationDao().delete(SqliteMediTimeRelation.build {
            medicineId = medicine1.medicineId
            timetableId = timetable3.timetableId
            oneShot = OneShotType(false)
        })

        database.personMediRelationDao().delete(SqlitePersonMediRelation.build {
            medicineId = medicine1.medicineId
            personId = person1.personId
        })

        database.scheduleDao().delete(SqliteSchedule.build { schedule = schedule1 })
        database.scheduleDao().delete(SqliteSchedule.build { schedule = schedule2 })
        database.scheduleDao().delete(SqliteSchedule.build { schedule = schedule3 })
    }

    private val database = AppDatabase.getAppDatabase(RuntimeEnvironment.application.applicationContext)

    private val person1 = Person(
            personId = PersonIdType("person01"),
            personName = PersonNameType("自分"),
            personDisplayOrder = PersonDisplayOrderType(10))

    private val medicineUnit1 = MedicineUnit(
            medicineUnitId = MedicineUnitIdType("unit01"),
            medicineUnitValue = MedicineUnitValueType(),
            medicineUnitDisplayOrder = MedicineUnitDisplayOrderType(2))

    private val medicine1 = Medicine(
            medicineId = MedicineIdType("medicine01"),
            medicineName = MedicineNameType("メルカゾール"),
            medicineUnit = medicineUnit1)

    private val timetable1 = Timetable(
            timetableId = TimetableIdType("timetable01"),
            timetableName = TimetableNameType("朝"),
            timetableTime = TimetableTimeType(CalendarNoSecond().calendar),
            timetableDisplayOrder = TimetableDisplayOrderType(20))

    private val timetable2 = Timetable(
            timetableId = TimetableIdType("timetable02"),
            timetableName = TimetableNameType("昼"),
            timetableTime = TimetableTimeType(12, 0),
            timetableDisplayOrder = TimetableDisplayOrderType(20))

    private val timetable3 = Timetable(
            timetableId = TimetableIdType("timetable03"),
            timetableName = TimetableNameType("夜"),
            timetableTime = TimetableTimeType(19, 0),
            timetableDisplayOrder = TimetableDisplayOrderType(20))

    private val schedule1 = Schedule(
            medicineId = medicine1.medicineId,
            timetableId = timetable1.timetableId,
            schedulePlanDate = SchedulePlanDateType(CalendarNoSecond().calendar),
            scheduleNeedAlarm = ScheduleNeedAlarmType(true),
            scheduleIsTake = ScheduleIsTakeType(false))

    private val schedule2 = Schedule(
            medicineId = medicine1.medicineId,
            timetableId = timetable2.timetableId,
            schedulePlanDate = SchedulePlanDateType(CalendarNoSecond().calendar),
            scheduleNeedAlarm = ScheduleNeedAlarmType(true),
            scheduleIsTake = ScheduleIsTakeType(false))

    private val schedule3 = Schedule(
            medicineId = medicine1.medicineId,
            timetableId = timetable3.timetableId,
            schedulePlanDate = SchedulePlanDateType(CalendarNoSecond().calendar),
            scheduleNeedAlarm = ScheduleNeedAlarmType(true),
            scheduleIsTake = ScheduleIsTakeType(false))
}