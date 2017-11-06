package com.studiojozu.medicheck.infrastructure.persistence.dao

import com.studiojozu.medicheck.domain.model.medicine.MedicineNameType
import com.studiojozu.medicheck.domain.model.setting.ATestParent
import com.studiojozu.medicheck.infrastructure.persistence.database.AppDatabase
import com.studiojozu.medicheck.infrastructure.persistence.entity.SqliteMedicine
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.powermock.core.classloader.annotations.PowerMockIgnore
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = "src/main/AndroidManifest.xml")
@PowerMockIgnore("org.mockito.*", "org.robolectric.*", "android.*")
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
        dao.insert(setRepositoryMedicine(insertMedicineEntity))
        medicines = dao.findAll()
        assertEquals(1, medicines.size)
        assert(insertMedicineEntity, medicines[0])

        // update
        val updateMedicineEntity = insertMedicineEntity.copy(mMedicineName = MedicineNameType("メルカゾール"))
        dao.insert(setRepositoryMedicine(updateMedicineEntity))
        medicines = dao.findAll()
        assertEquals(1, medicines.size)
        assert(updateMedicineEntity, medicines[0])

        // delete
        val deleteMedicineEntity = insertMedicineEntity.copy()
        dao.delete(setRepositoryMedicine(deleteMedicineEntity))
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
        dao.insert(setRepositoryMedicine(insertMedicineEntity))

        // findById
        val medicine1 = dao.findById(insertMedicineEntity.mMedicineId.dbValue)
        assert(insertMedicineEntity, medicine1!!)

        // findById
        val medicine2 = dao.findById("unknown id")
        assertNull(medicine2)

        // delete
        val deleteMedicineEntity = insertMedicineEntity.copy()
        dao.delete(setRepositoryMedicine(deleteMedicineEntity))
    }

    private fun setRepositoryMedicine(medicineEntity: com.studiojozu.medicheck.domain.model.medicine.Medicine): SqliteMedicine {
        val medicine = SqliteMedicine(medicineId = medicineEntity.mMedicineId.dbValue)
        medicine.mMedicineName = medicineEntity.mMedicineName.dbValue
        medicine.mMedicineTakeNumber = medicineEntity.mMedicineTakeNumber.dbValue
        medicine.mMedicineUnitId = medicineEntity.mMedicineUnit.mMedicineUnitId.dbValue
        medicine.mMedicineDateNumber = medicineEntity.mMedicineDateNumber.dbValue.toInt()
        medicine.mMedicineStartDatetime = medicineEntity.mMedicineStartDatetime.dbValue
        medicine.mMedicineInterval = medicineEntity.mMedicineInterval.dbValue.toInt()
        medicine.mMedicineIntervalMode = medicineEntity.mMedicineIntervalMode.dbValue
        medicine.mMedicinePhoto = medicineEntity.mMedicinePhoto.dbValue
        medicine.mMedicineNeedAlarm = medicineEntity.mMedicineNeedAlarm.isTrue
        medicine.mMedicineDeleteFlag = medicineEntity.mMedicineDeleteFlag.isTrue

        return medicine
    }

    private fun assert(expect: com.studiojozu.medicheck.domain.model.medicine.Medicine, actual: SqliteMedicine) {
        assertEquals(expect.mMedicineId.dbValue, actual.mMedicineId)
        assertEquals(expect.mMedicineName.dbValue, actual.mMedicineName)
        assertEquals(expect.mMedicineTakeNumber.dbValue, actual.mMedicineTakeNumber)
        assertEquals(expect.mMedicineUnit.mMedicineUnitId.dbValue, actual.mMedicineUnitId)
        assertEquals(expect.mMedicineDateNumber.dbValue.toInt(), actual.mMedicineDateNumber)
        assertEquals(expect.mMedicineStartDatetime.dbValue, actual.mMedicineStartDatetime)
        assertEquals(expect.mMedicineInterval.dbValue.toInt(), actual.mMedicineInterval)
        assertEquals(expect.mMedicineIntervalMode.dbValue, actual.mMedicineIntervalMode)
        assertEquals(expect.mMedicinePhoto.dbValue, actual.mMedicinePhoto)
        assertEquals(expect.mMedicineNeedAlarm.isTrue, actual.mMedicineNeedAlarm)
        assertEquals(expect.mMedicineDeleteFlag.isTrue, actual.mMedicineDeleteFlag)
    }
}