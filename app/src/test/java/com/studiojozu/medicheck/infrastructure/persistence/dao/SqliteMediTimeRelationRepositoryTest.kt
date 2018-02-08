package com.studiojozu.medicheck.infrastructure.persistence.dao

import com.studiojozu.medicheck.domain.model.medicine.MedicineIdType
import com.studiojozu.medicheck.domain.model.medicine.OneShotType
import com.studiojozu.medicheck.domain.model.setting.ATestParent
import com.studiojozu.medicheck.domain.model.setting.TimetableIdType
import com.studiojozu.medicheck.infrastructure.persistence.database.AppDatabase
import com.studiojozu.medicheck.infrastructure.persistence.entity.SqliteMediTimeRelation
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = "src/main/AndroidManifest.xml")
class SqliteMediTimeRelationRepositoryTest : ATestParent() {

    @Test
    @Throws(Exception::class)
    fun crud() {
        val database = AppDatabase.getAppDatabase(RuntimeEnvironment.application.applicationContext)
        val dao = database.mediTimeRelationDao()

        // select no data
        var entities = dao.findAll()
        assertNotNull(entities)
        assertEquals(0, entities.size)

        // insert
        val insertData = setSqliteMediTimeRelation(MedicineIdType("12345678"), TimetableIdType())
        insertData.oneShot = OneShotType(false)

        dao.insert(insertData)

        entities = dao.findAll()
        assertEquals(1, entities.size)
        assertEquals(insertData.medicineId, entities[0].medicineId)
        assertEquals(insertData.timetableId, entities[0].timetableId)
        assertEquals(insertData.oneShot, entities[0].oneShot)

        // update
        val updateData = setSqliteMediTimeRelation(insertData.medicineId, insertData.timetableId)
        updateData.oneShot = OneShotType(true)

        dao.insert(updateData)

        entities = dao.findAll()
        assertEquals(1, entities.size)
        assertEquals(updateData.medicineId, entities[0].medicineId)
        assertEquals(updateData.timetableId, entities[0].timetableId)
        assertEquals(updateData.oneShot, entities[0].oneShot)

        // delete
        dao.delete(insertData)
        entities = dao.findAll()
        Assert.assertEquals(0, entities.size)
    }

    @Test
    @Throws(Exception::class)
    fun insertThreeRecords() {
        val database = AppDatabase.getAppDatabase(RuntimeEnvironment.application.applicationContext)
        val dao = database.mediTimeRelationDao()

        // select no data
        assertEquals(0, dao.findAll().size)

        // insert
        val insertData1 = setSqliteMediTimeRelation(MedicineIdType(), TimetableIdType())
        insertData1.oneShot = OneShotType(false)
        dao.insert(insertData1)

        val insertData2 = setSqliteMediTimeRelation(MedicineIdType(), insertData1.timetableId)
        insertData2.oneShot = OneShotType(false)
        dao.insert(insertData2)

        val insertData3 = setSqliteMediTimeRelation(insertData1.medicineId, TimetableIdType(""))
        insertData3.oneShot = OneShotType(true)
        dao.insert(insertData3)

        Assert.assertEquals(3, dao.findAll().size)

        // delete
        dao.delete(insertData1)
        Assert.assertEquals(2, dao.findAll().size)

        dao.delete(insertData2)
        Assert.assertEquals(1, dao.findAll().size)

        dao.delete(insertData3)
        Assert.assertEquals(0, dao.findAll().size)
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
        val timetableArray = dao.findTimetableByMedicineId(medicineId1.dbValue)
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
    @Config(qualifiers = "ja")
    fun deleteByMedicineId() {
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

        // findAll
        assertEquals(5, dao.findAll().size)

        // deleteByMedicineId
        dao.deleteByMedicineId(medicineId1.dbValue)

        // findAll
        assertEquals(2, dao.findAll().size)

        // delete
        dao.delete(insertData1)
        dao.delete(insertData2)
        dao.delete(insertData3)
        dao.delete(insertData4)
        dao.delete(insertData5)
    }

    private fun setSqliteMediTimeRelation(medicineIdType: MedicineIdType, timetableIdType: TimetableIdType): SqliteMediTimeRelation =
            SqliteMediTimeRelation.build {
                medicineId = medicineIdType
                timetableId = timetableIdType
                oneShot = OneShotType(true)
            }
}