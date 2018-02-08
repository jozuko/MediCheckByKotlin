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
        val updateMedicineEntity = insertMedicineEntity.copy(medicineName = MedicineNameType("メルカゾール"))
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
        val medicine1 = dao.findById(insertMedicineEntity.medicineId.dbValue)
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
        database.personDao().insert(SqlitePerson.build { person = person1 })
        // タイムテーブルを3つ登録
        database.timetableDao().insert(SqliteTimetable.build { timetable = timetable1 })
        database.timetableDao().insert(SqliteTimetable.build { timetable = timetable2 })
        database.timetableDao().insert(SqliteTimetable.build { timetable = timetable3 })

        // insert data
        val sqliteMedicineUnit = SqliteMedicineUnit.build { medicineUnit = medicineUnit1 }

        // 薬1
        val sqliteMedicine1 = SqliteMedicine.build { medicine = medicine1 }
        // 薬1を人1に連結
        val sqlitePersonMediRelationArray1 = arrayOf(SqlitePersonMediRelation.build {
            personId = person1.personId
            medicineId = medicine1.medicineId
        })
        // 薬1にタイムテーブルを3つ登録
        val sqliteMediTimeRelationArray1 = arrayOf(
                SqliteMediTimeRelation.build {
                    medicineId = medicine1.medicineId
                    timetableId = timetable1.timetableId
                    oneShot = OneShotType(false)
                },
                SqliteMediTimeRelation.build {
                    medicineId = medicine1.medicineId
                    timetableId = timetable2.timetableId
                    oneShot = OneShotType(false)
                },
                SqliteMediTimeRelation.build {
                    medicineId = medicine1.medicineId
                    timetableId = timetable3.timetableId
                    oneShot = OneShotType(false)
                })
        val sqliteScheduleArray1 = arrayOf(
                SqliteSchedule.build { schedule = schedule1 },
                SqliteSchedule.build { schedule = schedule2 },
                SqliteSchedule.build { schedule = schedule3 }
        )

        // 薬2
        val sqliteMedicine2 = SqliteMedicine.build { medicine = medicine2 }
        // 薬2に人1を連結
        val sqlitePersonMediRelationArray2 = arrayOf(SqlitePersonMediRelation.build {
            personId = person1.personId
            medicineId = medicine2.medicineId
        })
        // 薬2にタイムテーブルを1つ登録
        val sqliteMediTimeRelationArray2 = arrayOf(SqliteMediTimeRelation.build {
            medicineId = medicine2.medicineId
            timetableId = timetable1.timetableId
            oneShot = OneShotType(false)
        })
        // スケジュールを登録
        val sqliteScheduleArray2 = arrayOf(SqliteSchedule.build { schedule = schedule4 })

        // insert - medicine1
        dao.insertMedicine(sqliteMedicine = sqliteMedicine1,
                sqliteMedicineUnit = sqliteMedicineUnit,
                sqlitePersonMediRelationArray = sqlitePersonMediRelationArray1,
                sqliteMediTimeRelationArray = sqliteMediTimeRelationArray1,
                sqliteScheduleArray = sqliteScheduleArray1)

        // find
        val actualMedicine = dao.findById(medicine1.medicineId.dbValue)!!
        assertEquals(medicine1.medicineId, actualMedicine.medicineId)
        val actualMedicineUnit1 = database.medicineUnitDao().findById(medicine1.medicineUnitId.dbValue)!!
        assertEquals(medicine1.medicineUnitId, actualMedicineUnit1.medicineUnitId)
        val actualPerson = database.personMediRelationDao().findPersonByMedicineId(medicine1.medicineId.dbValue)!!
        assertEquals(person1.personId, actualPerson.personId)
        val actualTimetableArray = database.mediTimeRelationDao().findTimetableByMedicineId(medicine1.medicineId.dbValue)
        assertEquals(3, actualTimetableArray.size)
        assertEquals(timetable1.timetableId, actualTimetableArray[0].timetableId)
        assertEquals(timetable2.timetableId, actualTimetableArray[1].timetableId)
        assertEquals(timetable3.timetableId, actualTimetableArray[2].timetableId)
        val actualScheduleArray = database.scheduleDao().findByMedicineId(medicine1.medicineId.dbValue)
        assertEquals(3, actualScheduleArray.size)
        assertEquals(timetable1.timetableId, actualScheduleArray[0].timetableId)
        assertEquals(medicine1.medicineId, actualScheduleArray[0].medicineId)
        assertEquals(timetable2.timetableId, actualScheduleArray[1].timetableId)
        assertEquals(medicine1.medicineId, actualScheduleArray[1].medicineId)
        assertEquals(timetable3.timetableId, actualScheduleArray[2].timetableId)
        assertEquals(medicine1.medicineId, actualScheduleArray[2].medicineId)

        // insert - medicine2
        dao.insertMedicine(sqliteMedicine = sqliteMedicine2,
                sqliteMedicineUnit = sqliteMedicineUnit,
                sqlitePersonMediRelationArray = sqlitePersonMediRelationArray2,
                sqliteMediTimeRelationArray = sqliteMediTimeRelationArray2,
                sqliteScheduleArray = sqliteScheduleArray2)

        val actualMedicine2 = dao.findById(medicine2.medicineId.dbValue)!!
        assertEquals(medicine2.medicineId, actualMedicine2.medicineId)
        val actualMedicineUnit2 = database.medicineUnitDao().findById(medicine2.medicineUnitId.dbValue)!!
        assertEquals(medicine2.medicineUnitId, actualMedicineUnit2.medicineUnitId)
        val actualPerson2 = database.personMediRelationDao().findPersonByMedicineId(medicine2.medicineId.dbValue)!!
        assertEquals(person1.personId, actualPerson2.personId)
        val actualTimetableArray2 = database.mediTimeRelationDao().findTimetableByMedicineId(medicine2.medicineId.dbValue)
        assertEquals(1, actualTimetableArray2.size)
        assertEquals(timetable1.timetableId, actualTimetableArray2[0].timetableId)
        val actualScheduleArray2 = database.scheduleDao().findByMedicineId(medicine2.medicineId.dbValue)
        assertEquals(1, actualScheduleArray2.size)
        assertEquals(timetable1.timetableId, actualScheduleArray2[0].timetableId)
        assertEquals(medicine2.medicineId, actualScheduleArray2[0].medicineId)

        // delete
        dao.deleteMedicine(sqliteMedicine = sqliteMedicine1,
                sqlitePersonMediRelationArray = sqlitePersonMediRelationArray1,
                sqliteMediTimeRelationArray = sqliteMediTimeRelationArray1,
                sqliteScheduleArray = sqliteScheduleArray1)

        assertNull(dao.findById(medicine1.medicineId.dbValue))
        assertNotNull(dao.findById(medicine2.medicineId.dbValue))
        assertNull(database.personMediRelationDao().findPersonByMedicineId(medicine1.medicineId.dbValue))
        assertNotNull(database.personMediRelationDao().findPersonByMedicineId(medicine2.medicineId.dbValue))
        assertTrue(database.mediTimeRelationDao().findTimetableByMedicineId(medicine1.medicineId.dbValue).isEmpty())
        assertFalse(database.mediTimeRelationDao().findTimetableByMedicineId(medicine2.medicineId.dbValue).isEmpty())
        assertTrue(database.scheduleDao().findByMedicineId(medicine1.medicineId.dbValue).isEmpty())
        assertFalse(database.scheduleDao().findByMedicineId(medicine2.medicineId.dbValue).isEmpty())

        // delete
        dao.deleteMedicine(sqliteMedicine = sqliteMedicine2,
                sqlitePersonMediRelationArray = sqlitePersonMediRelationArray2,
                sqliteMediTimeRelationArray = sqliteMediTimeRelationArray2,
                sqliteScheduleArray = sqliteScheduleArray2)

        assertNull(dao.findById(medicine1.medicineId.dbValue))
        assertNull(dao.findById(medicine2.medicineId.dbValue))
        assertNull(database.personMediRelationDao().findPersonByMedicineId(medicine1.medicineId.dbValue))
        assertNull(database.personMediRelationDao().findPersonByMedicineId(medicine2.medicineId.dbValue))
        assertTrue(database.mediTimeRelationDao().findTimetableByMedicineId(medicine1.medicineId.dbValue).isEmpty())
        assertTrue(database.mediTimeRelationDao().findTimetableByMedicineId(medicine2.medicineId.dbValue).isEmpty())
        assertTrue(database.scheduleDao().findByMedicineId(medicine1.medicineId.dbValue).isEmpty())
        assertTrue(database.scheduleDao().findByMedicineId(medicine2.medicineId.dbValue).isEmpty())
    }

    private fun setSqliteMedicine(entity: Medicine) =
            SqliteMedicine.build { medicine = entity }

    private fun assert(expect: com.studiojozu.medicheck.domain.model.medicine.Medicine, actual: SqliteMedicine) {
        assertEquals(expect.medicineId, actual.medicineId)
        assertEquals(expect.medicineName, actual.medicineName)
        assertEquals(expect.medicineTakeNumber, actual.medicineTakeNumber)
        assertEquals(expect.medicineUnit.medicineUnitId, actual.medicineUnitId)
        assertEquals(expect.medicineDateNumber, actual.medicineDateNumber)
        assertEquals(expect.medicineStartDatetime, actual.medicineStartDatetime)
        assertEquals(expect.medicineInterval, actual.medicineInterval)
        assertEquals(expect.medicineIntervalMode, actual.medicineIntervalMode)
        assertEquals(expect.medicinePhoto, actual.medicinePhoto)
        assertEquals(expect.medicineNeedAlarm, actual.medicineNeedAlarm)
        assertEquals(expect.medicineDeleteFlag, actual.medicineDeleteFlag)
    }

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
}