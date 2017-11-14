package com.studiojozu.medicheck.infrastructure.persistence.dao

import com.studiojozu.medicheck.domain.model.medicine.Medicine
import com.studiojozu.medicheck.domain.model.medicine.MedicineNameType
import com.studiojozu.medicheck.domain.model.setting.ATestParent
import com.studiojozu.medicheck.infrastructure.persistence.database.AppDatabase
import com.studiojozu.medicheck.infrastructure.persistence.entity.SqliteMedicine
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
}