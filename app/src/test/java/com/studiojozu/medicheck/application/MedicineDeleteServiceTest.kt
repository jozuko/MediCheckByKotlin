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
import org.junit.After
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
class MedicineDeleteServiceTest : ATestParent() {

    @Inject
    lateinit var database: AppDatabase

    @Inject
    lateinit var medicineDeleteService: MedicineDeleteService

    private val medicineUnit1 = MedicineUnit(
            medicineUnitId = MedicineUnitIdType("unit01"),
            medicineUnitValue = MedicineUnitValueType("錠"),
            medicineUnitDisplayOrder = MedicineUnitDisplayOrderType(2))

    private val medicine1 = Medicine(
            medicineId = MedicineIdType("medicine01"),
            medicineName = MedicineNameType("メルカゾール"),
            medicineUnit = medicineUnit1)

    private val medicine2 = Medicine(
            medicineId = MedicineIdType("medicine02"),
            medicineName = MedicineNameType("チラーヂン"),
            medicineUnit = medicineUnit1)

    private val person1 = Person(
            personId = PersonIdType("person01"),
            personName = PersonNameType("自分"),
            personDisplayOrder = PersonDisplayOrderType(2))

    private val timetable1 = Timetable(
            timetableId = TimetableIdType("timetable01"),
            timetableName = TimetableNameType("朝"),
            timetableTime = TimetableTimeType(CalendarNoSecond().calendar),
            timetableDisplayOrder = TimetableDisplayOrderType(20))

    private val timetable2 = Timetable(
            timetableId = TimetableIdType("timetable02"),
            timetableName = TimetableNameType("昼"),
            timetableTime = TimetableTimeType(CalendarNoSecond().calendar),
            timetableDisplayOrder = TimetableDisplayOrderType(21))

    private val timetable3 = Timetable(
            timetableId = TimetableIdType("timetable03"),
            timetableName = TimetableNameType("夜"),
            timetableTime = TimetableTimeType(CalendarNoSecond().calendar),
            timetableDisplayOrder = TimetableDisplayOrderType(22))

    private val schedule1 = Schedule(
            medicineId = medicine1.medicineId,
            timetableId = timetable1.timetableId,
            schedulePlanDate = SchedulePlanDateType(CalendarNoSecond().calendar),
            scheduleNeedAlarm = ScheduleNeedAlarmType(true),
            scheduleIsTake = ScheduleIsTakeType(true))

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

    private val schedule4 = schedule1.copy(medicineId = medicine2.medicineId)

    @Before
    fun setUp() {
        (RuntimeEnvironment.application as MediCheckTestApplication).mAppTestComponent.inject(this)

        // 薬の単位
        database.medicineUnitDao().insert(SqliteMedicineUnit.build { medicineUnit = medicineUnit1 })

        // 薬を2つ登録
        database.medicineDao().insert(SqliteMedicine.build { medicine = medicine1 })
        database.medicineDao().insert(SqliteMedicine.build { medicine = medicine2 })

        // 人を1人登録
        database.personDao().insert(SqlitePerson.build { person = person1 })

        // 2つのくすりを1人に登録
        database.personMediRelationDao().insert(SqlitePersonMediRelation.build {
            personId = person1.personId
            medicineId = medicine1.medicineId
        })
        database.personMediRelationDao().insert(SqlitePersonMediRelation.build {
            personId = person1.personId
            medicineId = medicine2.medicineId
        })

        // タイムテーブルを3つ登録
        database.timetableDao().insert(SqliteTimetable.build { timetable = timetable1 })
        database.timetableDao().insert(SqliteTimetable.build { timetable = timetable2 })
        database.timetableDao().insert(SqliteTimetable.build { timetable = timetable3 })

        // 薬1にタイムテーブルを3つ登録
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

        // 薬2にタイムテーブルを1つ登録
        database.mediTimeRelationDao().insert(SqliteMediTimeRelation.build {
            medicineId = medicine2.medicineId
            timetableId = timetable1.timetableId
            oneShot = OneShotType(false)
        })

        // スケジュールを登録
        database.scheduleDao().insert(SqliteSchedule.build { schedule = schedule1 })
        database.scheduleDao().insert(SqliteSchedule.build { schedule = schedule2 })
        database.scheduleDao().insert(SqliteSchedule.build { schedule = schedule3 })
        database.scheduleDao().insert(SqliteSchedule.build { schedule = schedule4 })
    }

    @After
    fun after() {
        // delete data
        database.medicineUnitDao().delete(SqliteMedicineUnit.build { medicineUnit = medicineUnit1 })
        database.medicineDao().delete(SqliteMedicine.build { medicine = medicine1 })
        database.medicineDao().delete(SqliteMedicine.build { medicine = medicine2 })
        database.personDao().delete(SqlitePerson.build { person = person1 })
        database.personMediRelationDao().delete(SqlitePersonMediRelation.build {
            personId = person1.personId
            medicineId = medicine1.medicineId
        })
        database.personMediRelationDao().delete(SqlitePersonMediRelation.build {
            personId = person1.personId
            medicineId = medicine2.medicineId
        })

        database.timetableDao().delete(SqliteTimetable.build { timetable = timetable1 })
        database.timetableDao().delete(SqliteTimetable.build { timetable = timetable2 })
        database.timetableDao().delete(SqliteTimetable.build { timetable = timetable3 })

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
        database.mediTimeRelationDao().delete(SqliteMediTimeRelation.build {
            medicineId = medicine2.medicineId
            timetableId = timetable1.timetableId
            oneShot = OneShotType(false)
        })

        database.scheduleDao().delete(SqliteSchedule.build { schedule = schedule1 })
        database.scheduleDao().delete(SqliteSchedule.build { schedule = schedule2 })
        database.scheduleDao().delete(SqliteSchedule.build { schedule = schedule3 })
        database.scheduleDao().delete(SqliteSchedule.build { schedule = schedule4 })
    }

    @Test
    @Throws(Exception::class)
    fun deleteMedicine() {
        // before method call
        assertEquals(2, database.medicineDao().findAll().size)
        assertEquals(2, database.medicineUnitDao().findAll().size)
        assertEquals(2, database.personDao().findAll().size)
        assertEquals(2, database.personMediRelationDao().findAll().size)
        assertEquals(15, database.timetableDao().findAll().size)
        assertEquals(4, database.mediTimeRelationDao().findAll().size)
        assertEquals(4, database.scheduleDao().findAll().size)

        // call target method
        medicineDeleteService.deleteMedicine(medicine1)

        // after method call
        assertEquals(1, database.medicineDao().findAll().size)
        assertEquals(2, database.medicineUnitDao().findAll().size)
        assertEquals(2, database.personDao().findAll().size)
        assertEquals(1, database.personMediRelationDao().findAll().size)
        assertEquals(15, database.timetableDao().findAll().size)
        assertEquals(1, database.mediTimeRelationDao().findAll().size)
        assertEquals(1, database.scheduleDao().findAll().size)
    }
}
