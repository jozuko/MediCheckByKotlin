package com.studiojozu.medicheck.infrastructure.persistence.dao

import com.studiojozu.medicheck.domain.model.medicine.MedicineIdType
import com.studiojozu.medicheck.domain.model.person.PersonIdType
import com.studiojozu.medicheck.domain.model.setting.ATestParent
import com.studiojozu.medicheck.infrastructure.persistence.database.AppDatabase
import com.studiojozu.medicheck.infrastructure.persistence.entity.SqlitePersonMediRelation
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.powermock.core.classloader.annotations.PowerMockIgnore
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = "src/main/AndroidManifest.xml")
@PowerMockIgnore("org.mockito.*", "org.robolectric.*", "android.*")
class SqlitePersonMediRelationRepositoryTest : ATestParent() {

    @Test
    @Throws(Exception::class)
    fun crud() {
        val database = AppDatabase.getAppDatabase(RuntimeEnvironment.application.applicationContext)
        val dao = database.personMediRelationDao()

        // select no data
        var entities = dao.findAll()
        Assert.assertNotNull(entities)
        Assert.assertEquals(0, entities.size)

        // insert
        val insertData = SqlitePersonMediRelation(
                medicineId = MedicineIdType().dbValue,
                personId = PersonIdType().dbValue)
        dao.insert(insertData)

        entities = dao.findAll()
        Assert.assertEquals(1, entities.size)
        Assert.assertEquals(insertData.mMedicineId, entities[0].mMedicineId)
        Assert.assertEquals(insertData.mPersonId, entities[0].mPersonId)

        // update
        val updateData = SqlitePersonMediRelation(
                medicineId = insertData.mMedicineId,
                personId = insertData.mPersonId)
        dao.insert(updateData)

        entities = dao.findAll()
        Assert.assertEquals(1, entities.size)
        Assert.assertEquals(updateData.mMedicineId, entities[0].mMedicineId)
        Assert.assertEquals(updateData.mPersonId, entities[0].mPersonId)

        // delete
        dao.delete(insertData)
        entities = dao.findAll()
        Assert.assertEquals(0, entities.size)
    }

    @Test
    @Throws(Exception::class)
    fun insertThreeRecords() {
        val database = AppDatabase.getAppDatabase(RuntimeEnvironment.application.applicationContext)
        val dao = database.personMediRelationDao()

        // select no data
        Assert.assertEquals(0, dao.findAll().size)

        // insert
        val insertData1 = SqlitePersonMediRelation(
                medicineId = MedicineIdType().dbValue,
                personId = PersonIdType().dbValue)
        dao.insert(insertData1)

        val insertData2 = SqlitePersonMediRelation(
                medicineId = MedicineIdType().dbValue,
                personId = insertData1.mPersonId)
        dao.insert(insertData2)

        val insertData3 = SqlitePersonMediRelation(
                medicineId = insertData1.mMedicineId,
                personId = PersonIdType().dbValue)
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