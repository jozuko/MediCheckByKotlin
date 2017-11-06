package com.studiojozu.medicheck.infrastructure.persistence.dao

import com.studiojozu.medicheck.domain.model.medicine.MedicineIdType
import com.studiojozu.medicheck.domain.model.setting.ATestParent
import com.studiojozu.medicheck.domain.model.setting.Timetable
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
        val insertData = SqliteMediTimeRelation("12345678")
        insertData.mTimetableId = Timetable().mTimetableId.dbValue
        insertData.mIsOneShot = false

        dao.insert(insertData)

        entities = dao.findAll()
        assertEquals(1, entities.size)
        assertEquals(insertData.mMedicineId, entities[0].mMedicineId)
        assertEquals(insertData.mTimetableId, entities[0].mTimetableId)
        assertEquals(insertData.mIsOneShot, entities[0].mIsOneShot)

        // update
        val updateData = SqliteMediTimeRelation(insertData.mMedicineId)
        updateData.mTimetableId = insertData.mTimetableId
        updateData.mIsOneShot = true

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
        val insertData1 = SqliteMediTimeRelation(MedicineIdType().dbValue)
        insertData1.mTimetableId = TimetableIdType().dbValue
        insertData1.mIsOneShot = false
        dao.insert(insertData1)

        val insertData2 = SqliteMediTimeRelation(MedicineIdType().dbValue)
        insertData2.mTimetableId = TimetableIdType().dbValue
        insertData2.mIsOneShot = false
        dao.insert(insertData2)

        val insertData3 = SqliteMediTimeRelation(MedicineIdType().dbValue)
        insertData3.mTimetableId = ""
        insertData3.mIsOneShot = true
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
}