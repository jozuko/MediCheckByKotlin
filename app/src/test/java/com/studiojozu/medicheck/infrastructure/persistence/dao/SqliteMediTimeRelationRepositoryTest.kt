package com.studiojozu.medicheck.infrastructure.persistence.dao

import com.studiojozu.medicheck.domain.model.medicine.IsOneShotType
import com.studiojozu.medicheck.domain.model.medicine.MedicineIdType
import com.studiojozu.medicheck.domain.model.setting.ATestParent
import com.studiojozu.medicheck.domain.model.setting.TimetableIdType
import com.studiojozu.medicheck.infrastructure.persistence.database.AppDatabase
import com.studiojozu.medicheck.infrastructure.persistence.entity.SqliteMediTimeRelation
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.powermock.core.classloader.annotations.PowerMockIgnore
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = "src/main/AndroidManifest.xml")
@PowerMockIgnore("org.mockito.*", "org.robolectric.*", "android.*")
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
        val insertData = SqliteMediTimeRelation(MedicineIdType("12345678"), TimetableIdType())
        insertData.mIsOneShot = IsOneShotType(false)

        dao.insert(insertData)

        entities = dao.findAll()
        assertEquals(1, entities.size)
        assertEquals(insertData.mMedicineId, entities[0].mMedicineId)
        assertEquals(insertData.mTimetableId, entities[0].mTimetableId)
        assertEquals(insertData.mIsOneShot, entities[0].mIsOneShot)

        // update
        val updateData = SqliteMediTimeRelation(insertData.mMedicineId, insertData.mTimetableId)
        updateData.mIsOneShot = IsOneShotType(true)

        dao.insert(updateData)

        entities = dao.findAll()
        assertEquals(1, entities.size)
        assertEquals(updateData.mMedicineId, entities[0].mMedicineId)
        assertEquals(updateData.mTimetableId, entities[0].mTimetableId)
        assertEquals(updateData.mIsOneShot, entities[0].mIsOneShot)

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
        val insertData1 = SqliteMediTimeRelation(MedicineIdType(), TimetableIdType())
        insertData1.mIsOneShot = IsOneShotType(false)
        dao.insert(insertData1)

        val insertData2 = SqliteMediTimeRelation(MedicineIdType(), insertData1.mTimetableId)
        insertData2.mIsOneShot = IsOneShotType(false)
        dao.insert(insertData2)

        val insertData3 = SqliteMediTimeRelation(insertData1.mMedicineId, TimetableIdType(""))
        insertData3.mIsOneShot = IsOneShotType(true)
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
        val insertData1 = SqliteMediTimeRelation(medicineId1, timetables[0].mTimetableId)
        val insertData2 = SqliteMediTimeRelation(medicineId1, timetables[1].mTimetableId)
        val insertData3 = SqliteMediTimeRelation(medicineId1, timetables[2].mTimetableId)
        val insertData4 = SqliteMediTimeRelation(medicineId2, timetables[0].mTimetableId)
        val insertData5 = SqliteMediTimeRelation(medicineId2, timetables[3].mTimetableId)
        dao.insert(insertData1)
        dao.insert(insertData2)
        dao.insert(insertData3)
        dao.insert(insertData4)
        dao.insert(insertData5)

        // findTimetableByMedicineId
        val timetableArray = dao.findTimetableByMedicineId(medicineId1.dbValue)
        assertEquals(3, timetableArray.size)
        var index = 0
        assertEquals("朝", timetableArray[index].mTimetableName.dbValue)
        assertEquals("7:00", timetableArray[index].mTimetableTime.displayValue)
        assertEquals((index + 1).toLong(), timetableArray[index].mTimetableDisplayOrder.dbValue)

        index = 1
        assertEquals("昼", timetableArray[index].mTimetableName.dbValue)
        assertEquals("12:00", timetableArray[index].mTimetableTime.displayValue)
        assertEquals((index + 1).toLong(), timetableArray[index].mTimetableDisplayOrder.dbValue)

        index = 2
        assertEquals("夜", timetableArray[index].mTimetableName.dbValue)
        assertEquals("19:00", timetableArray[index].mTimetableTime.displayValue)
        assertEquals((index + 1).toLong(), timetableArray[index].mTimetableDisplayOrder.dbValue)

        // delete
        dao.delete(insertData1)
        dao.delete(insertData2)
        dao.delete(insertData3)
        dao.delete(insertData4)
        dao.delete(insertData5)
    }

}