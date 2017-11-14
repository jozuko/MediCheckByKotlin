package com.studiojozu.medicheck.application

import com.studiojozu.common.domain.model.CalendarNoSecond
import com.studiojozu.medicheck.di.MediCheckTestApplication
import com.studiojozu.medicheck.di.module.PersistenceTestModule
import com.studiojozu.medicheck.domain.model.medicine.*
import com.studiojozu.medicheck.domain.model.medicine.repository.MedicineViewRepository
import com.studiojozu.medicheck.domain.model.person.Person
import com.studiojozu.medicheck.domain.model.person.PersonDisplayOrderType
import com.studiojozu.medicheck.domain.model.person.PersonIdType
import com.studiojozu.medicheck.domain.model.person.PersonNameType
import com.studiojozu.medicheck.domain.model.schedule.Schedule
import com.studiojozu.medicheck.domain.model.schedule.ScheduleIsTakeType
import com.studiojozu.medicheck.domain.model.schedule.ScheduleNeedAlarmType
import com.studiojozu.medicheck.domain.model.schedule.SchedulePlanDateType
import com.studiojozu.medicheck.domain.model.setting.*
import com.studiojozu.medicheck.infrastructure.persistence.dao.SqlitePersonMediRelationRepository
import com.studiojozu.medicheck.infrastructure.persistence.database.AppDatabase
import com.studiojozu.medicheck.infrastructure.persistence.entity.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
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

    private val medicineUnit = MedicineUnit(
            mMedicineUnitId = MedicineUnitIdType("unit01"),
            mMedicineUnitValue = MedicineUnitValueType("錠"),
            mMedicineUnitDisplayOrder = MedicineUnitDisplayOrderType(2))

    private val medicine1 = Medicine(
            mMedicineId = MedicineIdType("medicine01"),
            mMedicineName = MedicineNameType("メルカゾール"),
            mMedicineUnit = medicineUnit)

    private val medicine2 = Medicine(
            mMedicineId = MedicineIdType("medicine02"),
            mMedicineName = MedicineNameType("チラーヂン"),
            mMedicineUnit = medicineUnit)

    private val person1 = Person(
            mPersonId = PersonIdType("person01"),
            mPersonName = PersonNameType("自分"),
            mPersonDisplayOrder = PersonDisplayOrderType(2))

    private val timetable1 = Timetable(
            mTimetableId = TimetableIdType("timetable01"),
            mTimetableName = TimetableNameType("朝"),
            mTimetableTime = TimetableTimeType(CalendarNoSecond().calendar),
            mTimetableDisplayOrder = TimetableDisplayOrderType(20))

    private val timetable2 = Timetable(
            mTimetableId = TimetableIdType("timetable02"),
            mTimetableName = TimetableNameType("昼"),
            mTimetableTime = TimetableTimeType(CalendarNoSecond().calendar),
            mTimetableDisplayOrder = TimetableDisplayOrderType(21))

    private val timetable3 = Timetable(
            mTimetableId = TimetableIdType("timetable03"),
            mTimetableName = TimetableNameType("夜"),
            mTimetableTime = TimetableTimeType(CalendarNoSecond().calendar),
            mTimetableDisplayOrder = TimetableDisplayOrderType(22))

    private val schedule1 = Schedule(
            mMedicineId = medicine1.mMedicineId,
            mTimetableId = timetable1.mTimetableId,
            mSchedulePlanDate = SchedulePlanDateType(CalendarNoSecond().calendar),
            mScheduleNeedAlarm = ScheduleNeedAlarmType(true),
            mScheduleIsTake = ScheduleIsTakeType(true))

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

    private val schedule4 = schedule1.copy(mMedicineId = medicine2.mMedicineId)

    @Before
    fun setUp() {
        (RuntimeEnvironment.application as MediCheckTestApplication).mAppTestComponent.inject(this)

        // 薬の単位
        database.medicineUnitDao().insert(SqliteMedicineUnit.build { mMedicineUnit = medicineUnit })

        // 薬を2つ登録
        database.medicineDao().insert(SqliteMedicine.build { mMedicine = medicine1 })
        database.medicineDao().insert(SqliteMedicine.build { mMedicine = medicine2 })

        // 人を1人登録
        database.personDao().insert(SqlitePerson.build { mPerson = person1 })

        // 2つのくすりを1人に登録
        database.personMediRelationDao().insert(SqlitePersonMediRelation.build {
            mPersonId = person1.mPersonId
            mMedicineId = medicine1.mMedicineId
        })
        database.personMediRelationDao().insert(SqlitePersonMediRelation.build {
            mPersonId = person1.mPersonId
            mMedicineId = medicine2.mMedicineId
        })

        // タイムテーブルを3つ登録
        database.timetableDao().insert(SqliteTimetable.build { mTimetable = timetable1 })
        database.timetableDao().insert(SqliteTimetable.build { mTimetable = timetable2 })
        database.timetableDao().insert(SqliteTimetable.build { mTimetable = timetable3 })

        // 薬1にタイムテーブルを3つ登録
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

        // 薬2にタイムテーブルを1つ登録
        database.mediTimeRelationDao().insert(SqliteMediTimeRelation.build {
            mMedicineId = medicine2.mMedicineId
            mTimetableId = timetable1.mTimetableId
            mIsOneShot = IsOneShotType(false)
        })

        // スケジュールを登録
        database.scheduleDao().insert(SqliteSchedule.build { mSchedule = schedule1 })
        database.scheduleDao().insert(SqliteSchedule.build { mSchedule = schedule2 })
        database.scheduleDao().insert(SqliteSchedule.build { mSchedule = schedule3 })
        database.scheduleDao().insert(SqliteSchedule.build { mSchedule = schedule4 })
    }

    @After
    fun after() {
        // delete data
        database.medicineUnitDao().delete(SqliteMedicineUnit.build { mMedicineUnit = medicineUnit })
        database.medicineDao().delete(SqliteMedicine.build { mMedicine = medicine1 })
        database.medicineDao().delete(SqliteMedicine.build { mMedicine = medicine2 })
        database.personDao().delete(SqlitePerson.build { mPerson = person1 })
        database.personMediRelationDao().delete(SqlitePersonMediRelation.build {
            mPersonId = person1.mPersonId
            mMedicineId = medicine1.mMedicineId
        })
        database.personMediRelationDao().delete(SqlitePersonMediRelation.build {
            mPersonId = person1.mPersonId
            mMedicineId = medicine2.mMedicineId
        })

        database.timetableDao().delete(SqliteTimetable.build { mTimetable = timetable1 })
        database.timetableDao().delete(SqliteTimetable.build { mTimetable = timetable2 })
        database.timetableDao().delete(SqliteTimetable.build { mTimetable = timetable3 })

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
        database.mediTimeRelationDao().delete(SqliteMediTimeRelation.build {
            mMedicineId = medicine2.mMedicineId
            mTimetableId = timetable1.mTimetableId
            mIsOneShot = IsOneShotType(false)
        })

        database.scheduleDao().delete(SqliteSchedule.build { mSchedule = schedule1 })
        database.scheduleDao().delete(SqliteSchedule.build { mSchedule = schedule2 })
        database.scheduleDao().delete(SqliteSchedule.build { mSchedule = schedule3 })
        database.scheduleDao().delete(SqliteSchedule.build { mSchedule = schedule4 })
    }

    @Test
    @Throws(Exception::class)
    fun deleteMedicineRollback() {
        // create mock
        val application = RuntimeEnvironment.application as MediCheckTestApplication
        val persistenceModule = PersistenceTestModule()
        val sqlitePersonMediRelationRepositoryMock = Mockito.mock(SqlitePersonMediRelationRepository::class.java)
        Mockito.`when`(sqlitePersonMediRelationRepositoryMock.deleteByMedicineId(medicine1.mMedicineId.dbValue)).thenThrow(RuntimeException("error!!"))
        persistenceModule.mMedicineViewRepository = MedicineViewRepository(
                sqliteMedicineRepository = database.medicineDao(),
                sqliteMedicineUnitRepository = database.medicineUnitDao(),
                sqliteMedicineMedicineUnitRepository = database.medicineViewDao(),
                sqlitePersonMediRelationRepository = sqlitePersonMediRelationRepositoryMock,
                sqliteMediTimeRelationRepository = database.mediTimeRelationDao(),
                sqliteScheduleRepository = database.scheduleDao())
        application.mPersistenceModule = persistenceModule

        // before method call
        assertEquals(2, database.medicineDao().findAll().size)
        assertEquals(2, database.medicineUnitDao().findAll().size)
        assertEquals(2, database.personDao().findAll().size)
        assertEquals(2, database.personMediRelationDao().findAll().size)
        assertEquals(15, database.timetableDao().findAll().size)
        assertEquals(4, database.mediTimeRelationDao().findAll().size)
        assertEquals(4, database.scheduleDao().findAll().size)

        // call target method
        try {
            val medicineDeleteService = MedicineDeleteService(application)
            medicineDeleteService.deleteMedicine(medicine1.mMedicineId)
            fail("don't work exception mock...")
        } catch (exception: Exception) {
            assertEquals("error!!", exception.message)
        }

        // after method call
        assertEquals(2, database.medicineDao().findAll().size)
        assertEquals(2, database.medicineUnitDao().findAll().size)
        assertEquals(2, database.personDao().findAll().size)
        assertEquals(2, database.personMediRelationDao().findAll().size)
        assertEquals(15, database.timetableDao().findAll().size)
        assertEquals(4, database.mediTimeRelationDao().findAll().size)
        assertEquals(4, database.scheduleDao().findAll().size)
    }

    @Test
    @Throws(Exception::class)
    fun deleteMedicineCommit() {
        // before method call
        assertEquals(2, database.medicineDao().findAll().size)
        assertEquals(2, database.medicineUnitDao().findAll().size)
        assertEquals(2, database.personDao().findAll().size)
        assertEquals(2, database.personMediRelationDao().findAll().size)
        assertEquals(15, database.timetableDao().findAll().size)
        assertEquals(4, database.mediTimeRelationDao().findAll().size)
        assertEquals(4, database.scheduleDao().findAll().size)

        // call target method
        medicineDeleteService.deleteMedicine(medicine1.mMedicineId)

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
