package com.studiojozu.medicheck.domain.model.medicine.repository

import com.studiojozu.medicheck.di.MediCheckTestApplication
import com.studiojozu.medicheck.domain.model.medicine.*
import com.studiojozu.medicheck.domain.model.setting.ATestParent
import com.studiojozu.medicheck.domain.model.setting.Timetable
import com.studiojozu.medicheck.domain.model.setting.TimetableIdType
import com.studiojozu.medicheck.infrastructure.persistence.database.AppDatabase
import com.studiojozu.medicheck.infrastructure.persistence.entity.SqliteMediTimeRelation
import com.studiojozu.medicheck.infrastructure.persistence.entity.SqliteMedicine
import com.studiojozu.medicheck.infrastructure.persistence.entity.SqliteMedicineUnit
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import javax.inject.Inject

@Suppress("FunctionName")
@RunWith(RobolectricTestRunner::class)
@Config(manifest = "src/main/AndroidManifest.xml", application = MediCheckTestApplication::class)
class MediTimeRelationRepositoryTest : ATestParent() {

    @Inject
    lateinit var repository: MediTimeRelationRepository

    @Before
    fun setUp() = (RuntimeEnvironment.application as MediCheckTestApplication).mAppTestComponent.inject(this)

    @Test
    @Throws(Exception::class)
    fun insertTimetable() {
        val database = AppDatabase.getAppDatabase(RuntimeEnvironment.application.applicationContext)
        val timetables = database.timetableDao().findAll()

        // insert - no timetable
        val medicineIdType = MedicineIdType("12345678")
        var timetableList = MedicineTimetableList()
        repository.insertTimetable(medicineIdType, timetableList)

        var entities = repository.findTimetableByMedicineId(medicineIdType)
        Assert.assertEquals(0, entities.size)

        // insert - 3 timetable
        timetableList = MedicineTimetableList(
                mutableListOf(
                        timetables[0].toTimetable(),
                        timetables[1].toTimetable(),
                        timetables[2].toTimetable()))
        repository.insertTimetable(medicineIdType, timetableList)

        entities = repository.findTimetableByMedicineId(medicineIdType)
        Assert.assertEquals(3, entities.size)
        assert(timetables[0].toTimetable(), entities[0])
        assert(timetables[1].toTimetable(), entities[1])
        assert(timetables[2].toTimetable(), entities[2])

        // delete
        repository.deleteByMedicineId(medicineIdType)
        entities = repository.findTimetableByMedicineId(medicineIdType)
        Assert.assertEquals(0, entities.size)
    }

    @Test
    @Throws(Exception::class)
    fun insertOneShot() {
        val medicineIdType = MedicineIdType("12345678")
        repository.insertOneShot(medicineIdType)

        val entities = repository.findTimetableByMedicineId(medicineIdType)
        assertEquals(0, entities.size)
    }

    fun assert(expect: Timetable, actual: Timetable) {
        assertEquals(expect.timetableId, actual.timetableId)
        assertEquals(expect.timetableName, actual.timetableName)
        assertEquals(expect.timetableTime, actual.timetableTime)
        assertEquals(expect.timetableDisplayOrder, actual.timetableDisplayOrder)
    }

    @Test
    @Throws(Exception::class)
    @Config(qualifiers = "ja")
    fun findTimetableByMedicineId() {
        val database = AppDatabase.getAppDatabase(RuntimeEnvironment.application.applicationContext)
        val dao = database.mediTimeRelationDao()
        val timetables = database.timetableDao().findAll()

        // insert
        val medicineId1 = MedicineIdType()
        val medicineId2 = MedicineIdType()
        val insertData1 = setSqliteMediTimeRelation(medicineId1, timetables[0].timetableId)
        val insertData2 = setSqliteMediTimeRelation(medicineId1, timetables[1].timetableId)
        val insertData3 = setSqliteMediTimeRelation(medicineId1, timetables[2].timetableId)
        val insertData4 = setSqliteMediTimeRelation(medicineId2, timetables[0].timetableId)
        val insertData5 = setSqliteMediTimeRelation(medicineId2, timetables[3].timetableId)
        dao.insert(insertData1)
        dao.insert(insertData2)
        dao.insert(insertData3)
        dao.insert(insertData4)
        dao.insert(insertData5)

        // findTimetableByMedicineId
        val timetableArray = repository.findTimetableByMedicineId(medicineId1)
        assertEquals(3, timetableArray.size)
        var index = 0
        assertEquals("朝", timetableArray[index].timetableName.dbValue)
        assertEquals("7:00", timetableArray[index].timetableTime.displayValue)
        assertEquals((index + 1).toLong(), timetableArray[index].timetableDisplayOrder.dbValue)

        index = 1
        assertEquals("昼", timetableArray[index].timetableName.dbValue)
        assertEquals("12:00", timetableArray[index].timetableTime.displayValue)
        assertEquals((index + 1).toLong(), timetableArray[index].timetableDisplayOrder.dbValue)

        index = 2
        assertEquals("夜", timetableArray[index].timetableName.dbValue)
        assertEquals("19:00", timetableArray[index].timetableTime.displayValue)
        assertEquals((index + 1).toLong(), timetableArray[index].timetableDisplayOrder.dbValue)

        // delete
        dao.delete(insertData1)
        dao.delete(insertData2)
        dao.delete(insertData3)
        dao.delete(insertData4)
        dao.delete(insertData5)
    }

    @Test
    @Throws(Exception::class)
    fun findMedicineByTimetableId() {
        val database = AppDatabase.getAppDatabase(RuntimeEnvironment.application.applicationContext)
        val timetables = database.timetableDao().findAll()

        // 薬を登録
        database.medicineUnitDao().insert(setSqliteMedicineUnit(medicineUnit1))
        database.medicineDao().insert(setSqliteMedicine(medicine1))
        database.medicineDao().insert(setSqliteMedicine(medicine2))

        // insert
        val insertData1 = setSqliteMediTimeRelation(medicine1.medicineId, timetables[0].timetableId)
        val insertData2 = setSqliteMediTimeRelation(medicine1.medicineId, timetables[1].timetableId)
        val insertData3 = setSqliteMediTimeRelation(medicine1.medicineId, timetables[2].timetableId)
        val insertData4 = setSqliteMediTimeRelation(medicine2.medicineId, timetables[0].timetableId)
        val insertData5 = setSqliteMediTimeRelation(medicine2.medicineId, timetables[3].timetableId)
        database.mediTimeRelationDao().insert(insertData1)
        database.mediTimeRelationDao().insert(insertData2)
        database.mediTimeRelationDao().insert(insertData3)
        database.mediTimeRelationDao().insert(insertData4)
        database.mediTimeRelationDao().insert(insertData5)

        // findTimetableByTimetableId
        val timetableArray = repository.findMedicineByTimetableId(timetables[0].timetableId)

        // assert
        assertEquals(2, timetableArray.size)
        assertEquals(medicine1.medicineId.dbValue, timetableArray[0].medicineId.dbValue)
        assertEquals(medicine2.medicineId.dbValue, timetableArray[1].medicineId.dbValue)

        // delete
        database.mediTimeRelationDao().delete(insertData1)
        database.mediTimeRelationDao().delete(insertData2)
        database.mediTimeRelationDao().delete(insertData3)
        database.mediTimeRelationDao().delete(insertData4)
        database.mediTimeRelationDao().delete(insertData5)
    }

    private fun setSqliteMediTimeRelation(medicineIdType: MedicineIdType, timetableIdType: TimetableIdType): SqliteMediTimeRelation =
            SqliteMediTimeRelation.build {
                medicineId = medicineIdType
                timetableId = timetableIdType
                oneShot = OneShotType(true)
            }

    private fun setSqliteMedicine(entity: Medicine) =
            SqliteMedicine.build { medicine = entity }

    private fun setSqliteMedicineUnit(entity: MedicineUnit): SqliteMedicineUnit =
            SqliteMedicineUnit.build { medicineUnit = entity }

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
}