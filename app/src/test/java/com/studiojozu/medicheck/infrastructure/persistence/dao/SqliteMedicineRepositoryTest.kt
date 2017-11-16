package com.studiojozu.medicheck.infrastructure.persistence.dao

import com.studiojozu.common.domain.model.CalendarNoSecond
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
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = "src/main/AndroidManifest.xml")
class SqliteMedicineRepositoryTest : ATestParent() {

    @Test
    @Throws(Exception::class)
    fun crud() {
        val database = AppDatabase.getAppDatabase(RuntimeEnvironment.application.applicationContext)
        val dao = database.medicineDao()

        // select no data
        var medicines = dao.findAll()
        assertNotNull(medicines)
        assertEquals(0, medicines.size)

        // insert
        val insertMedicineEntity = com.studiojozu.medicheck.domain.model.medicine.Medicine()
        dao.insert(setSqliteMedicine(insertMedicineEntity))
        medicines = dao.findAll()
        assertEquals(1, medicines.size)
        assert(insertMedicineEntity, medicines[0])

        // update
        val updateMedicineEntity = insertMedicineEntity.copy(mMedicineName = MedicineNameType("メルカゾール"))
        dao.insert(setSqliteMedicine(updateMedicineEntity))
        medicines = dao.findAll()
        assertEquals(1, medicines.size)
        assert(updateMedicineEntity, medicines[0])

        // delete
        val deleteMedicineEntity = insertMedicineEntity.copy()
        dao.delete(setSqliteMedicine(deleteMedicineEntity))
        medicines = dao.findAll()
        assertEquals(0, medicines.size)
    }

    @Test
    @Throws(Exception::class)
    fun findById() {
        val database = AppDatabase.getAppDatabase(RuntimeEnvironment.application.applicationContext)
        val dao = database.medicineDao()

        // insert
        val insertMedicineEntity = com.studiojozu.medicheck.domain.model.medicine.Medicine()
        dao.insert(setSqliteMedicine(insertMedicineEntity))

        // findById
        val medicine1 = dao.findById(insertMedicineEntity.mMedicineId.dbValue)
        assert(insertMedicineEntity, medicine1!!)

        // findById
        val medicine2 = dao.findById("unknown id")
        assertNull(medicine2)

        // delete
        val deleteMedicineEntity = insertMedicineEntity.copy()
        dao.delete(setSqliteMedicine(deleteMedicineEntity))
    }

    @Test
    @Throws(Exception::class)
    fun deleteInsertMedicine() {
        val database = AppDatabase.getAppDatabase(RuntimeEnvironment.application.applicationContext)
        val dao = database.medicineDao()

        // prepare
        // 人を1人登録
        database.personDao().insert(SqlitePerson.build { mPerson = person1 })
        // タイムテーブルを3つ登録
        database.timetableDao().insert(SqliteTimetable.build { mTimetable = timetable1 })
        database.timetableDao().insert(SqliteTimetable.build { mTimetable = timetable2 })
        database.timetableDao().insert(SqliteTimetable.build { mTimetable = timetable3 })

        // insert data
        val sqliteMedicineUnit = SqliteMedicineUnit.build { mMedicineUnit = medicineUnit }

        // 薬1
        val sqliteMedicine1 = SqliteMedicine.build { mMedicine = medicine1 }
        // 薬1を人1に連結
        val sqlitePersonMediRelationArray1 = arrayOf(SqlitePersonMediRelation.build {
            mPersonId = person1.mPersonId
            mMedicineId = medicine1.mMedicineId
        })
        // 薬1にタイムテーブルを3つ登録
        val sqliteMediTimeRelationArray1 = arrayOf(
                SqliteMediTimeRelation.build {
                    mMedicineId = medicine1.mMedicineId
                    mTimetableId = timetable1.mTimetableId
                    mIsOneShot = IsOneShotType(false)
                },
                SqliteMediTimeRelation.build {
                    mMedicineId = medicine1.mMedicineId
                    mTimetableId = timetable2.mTimetableId
                    mIsOneShot = IsOneShotType(false)
                },
                SqliteMediTimeRelation.build {
                    mMedicineId = medicine1.mMedicineId
                    mTimetableId = timetable3.mTimetableId
                    mIsOneShot = IsOneShotType(false)
                })
        val sqliteScheduleArray1 = arrayOf(
                SqliteSchedule.build { mSchedule = schedule1 },
                SqliteSchedule.build { mSchedule = schedule2 },
                SqliteSchedule.build { mSchedule = schedule3 }
        )

        // 薬2
        val sqliteMedicine2 = SqliteMedicine.build { mMedicine = medicine2 }
        // 薬2に人1を連結
        val sqlitePersonMediRelationArray2 = arrayOf(SqlitePersonMediRelation.build {
            mPersonId = person1.mPersonId
            mMedicineId = medicine2.mMedicineId
        })
        // 薬2にタイムテーブルを1つ登録
        val sqliteMediTimeRelationArray2 = arrayOf(SqliteMediTimeRelation.build {
            mMedicineId = medicine2.mMedicineId
            mTimetableId = timetable1.mTimetableId
            mIsOneShot = IsOneShotType(false)
        })
        // スケジュールを登録
        val sqliteScheduleArray2 = arrayOf(SqliteSchedule.build { mSchedule = schedule4 })

        // insert - medicine1
        dao.insertMedicine(sqliteMedicine = sqliteMedicine1,
                sqliteMedicineUnit = sqliteMedicineUnit,
                sqlitePersonMediRelationArray = sqlitePersonMediRelationArray1,
                sqliteMediTimeRelationArray = sqliteMediTimeRelationArray1,
                sqliteScheduleArray = sqliteScheduleArray1)

        // find
        val actualMedicine = dao.findById(medicine1.mMedicineId.dbValue)!!
        assertEquals(medicine1.mMedicineId, actualMedicine.mMedicineId)
        val actualMedicineUnit1 = database.medicineUnitDao().findById(medicine1.medicineUnitId.dbValue)!!
        assertEquals(medicine1.medicineUnitId, actualMedicineUnit1.mMedicineUnitId)
        val actualPerson = database.personMediRelationDao().findPersonByMedicineId(medicine1.mMedicineId.dbValue)!!
        assertEquals(person1.mPersonId, actualPerson.mPersonId)
        val actualTimetableArray = database.mediTimeRelationDao().findTimetableByMedicineId(medicine1.mMedicineId.dbValue)
        assertEquals(3, actualTimetableArray.size)
        assertEquals(timetable1.mTimetableId, actualTimetableArray[0].mTimetableId)
        assertEquals(timetable2.mTimetableId, actualTimetableArray[1].mTimetableId)
        assertEquals(timetable3.mTimetableId, actualTimetableArray[2].mTimetableId)
        val actualScheduleArray = database.scheduleDao().findByMedicineId(medicine1.mMedicineId.dbValue)
        assertEquals(3, actualScheduleArray.size)
        assertEquals(timetable1.mTimetableId, actualScheduleArray[0].mTimetableId)
        assertEquals(medicine1.mMedicineId, actualScheduleArray[0].mMedicineId)
        assertEquals(timetable2.mTimetableId, actualScheduleArray[1].mTimetableId)
        assertEquals(medicine1.mMedicineId, actualScheduleArray[1].mMedicineId)
        assertEquals(timetable3.mTimetableId, actualScheduleArray[2].mTimetableId)
        assertEquals(medicine1.mMedicineId, actualScheduleArray[2].mMedicineId)

        // insert - medicine2
        dao.insertMedicine(sqliteMedicine = sqliteMedicine2,
                sqliteMedicineUnit = sqliteMedicineUnit,
                sqlitePersonMediRelationArray = sqlitePersonMediRelationArray2,
                sqliteMediTimeRelationArray = sqliteMediTimeRelationArray2,
                sqliteScheduleArray = sqliteScheduleArray2)

        val actualMedicine2 = dao.findById(medicine2.mMedicineId.dbValue)!!
        assertEquals(medicine2.mMedicineId, actualMedicine2.mMedicineId)
        val actualMedicineUnit2 = database.medicineUnitDao().findById(medicine2.medicineUnitId.dbValue)!!
        assertEquals(medicine2.medicineUnitId, actualMedicineUnit2.mMedicineUnitId)
        val actualPerson2 = database.personMediRelationDao().findPersonByMedicineId(medicine2.mMedicineId.dbValue)!!
        assertEquals(person1.mPersonId, actualPerson2.mPersonId)
        val actualTimetableArray2 = database.mediTimeRelationDao().findTimetableByMedicineId(medicine2.mMedicineId.dbValue)
        assertEquals(1, actualTimetableArray2.size)
        assertEquals(timetable1.mTimetableId, actualTimetableArray2[0].mTimetableId)
        val actualScheduleArray2 = database.scheduleDao().findByMedicineId(medicine2.mMedicineId.dbValue)
        assertEquals(1, actualScheduleArray2.size)
        assertEquals(timetable1.mTimetableId, actualScheduleArray2[0].mTimetableId)
        assertEquals(medicine2.mMedicineId, actualScheduleArray2[0].mMedicineId)

        // delete
        dao.deleteMedicine(sqliteMedicine = sqliteMedicine1,
                sqlitePersonMediRelationArray = sqlitePersonMediRelationArray1,
                sqliteMediTimeRelationArray = sqliteMediTimeRelationArray1,
                sqliteScheduleArray = sqliteScheduleArray1)

        assertNull(dao.findById(medicine1.mMedicineId.dbValue))
        assertNotNull(dao.findById(medicine2.mMedicineId.dbValue))
        assertNull(database.personMediRelationDao().findPersonByMedicineId(medicine1.mMedicineId.dbValue))
        assertNotNull(database.personMediRelationDao().findPersonByMedicineId(medicine2.mMedicineId.dbValue))
        assertTrue(database.mediTimeRelationDao().findTimetableByMedicineId(medicine1.mMedicineId.dbValue).isEmpty())
        assertFalse(database.mediTimeRelationDao().findTimetableByMedicineId(medicine2.mMedicineId.dbValue).isEmpty())
        assertTrue(database.scheduleDao().findByMedicineId(medicine1.mMedicineId.dbValue).isEmpty())
        assertFalse(database.scheduleDao().findByMedicineId(medicine2.mMedicineId.dbValue).isEmpty())

        // delete
        dao.deleteMedicine(sqliteMedicine = sqliteMedicine2,
                sqlitePersonMediRelationArray = sqlitePersonMediRelationArray2,
                sqliteMediTimeRelationArray = sqliteMediTimeRelationArray2,
                sqliteScheduleArray = sqliteScheduleArray2)

        assertNull(dao.findById(medicine1.mMedicineId.dbValue))
        assertNull(dao.findById(medicine2.mMedicineId.dbValue))
        assertNull(database.personMediRelationDao().findPersonByMedicineId(medicine1.mMedicineId.dbValue))
        assertNull(database.personMediRelationDao().findPersonByMedicineId(medicine2.mMedicineId.dbValue))
        assertTrue(database.mediTimeRelationDao().findTimetableByMedicineId(medicine1.mMedicineId.dbValue).isEmpty())
        assertTrue(database.mediTimeRelationDao().findTimetableByMedicineId(medicine2.mMedicineId.dbValue).isEmpty())
        assertTrue(database.scheduleDao().findByMedicineId(medicine1.mMedicineId.dbValue).isEmpty())
        assertTrue(database.scheduleDao().findByMedicineId(medicine2.mMedicineId.dbValue).isEmpty())
    }

    private fun setSqliteMedicine(entity: Medicine) =
            SqliteMedicine.build { mMedicine = entity }

    private fun assert(expect: com.studiojozu.medicheck.domain.model.medicine.Medicine, actual: SqliteMedicine) {
        assertEquals(expect.mMedicineId, actual.mMedicineId)
        assertEquals(expect.mMedicineName, actual.mMedicineName)
        assertEquals(expect.mMedicineTakeNumber, actual.mMedicineTakeNumber)
        assertEquals(expect.mMedicineUnit.mMedicineUnitId, actual.mMedicineUnitId)
        assertEquals(expect.mMedicineDateNumber, actual.mMedicineDateNumber)
        assertEquals(expect.mMedicineStartDatetime, actual.mMedicineStartDatetime)
        assertEquals(expect.mMedicineInterval, actual.mMedicineInterval)
        assertEquals(expect.mMedicineIntervalMode, actual.mMedicineIntervalMode)
        assertEquals(expect.mMedicinePhoto, actual.mMedicinePhoto)
        assertEquals(expect.mMedicineNeedAlarm, actual.mMedicineNeedAlarm)
        assertEquals(expect.mMedicineDeleteFlag, actual.mMedicineDeleteFlag)
    }

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
}