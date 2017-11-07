package com.studiojozu.medicheck.infrastructure.persistence.dao

import com.studiojozu.medicheck.domain.model.medicine.*
import com.studiojozu.medicheck.domain.model.setting.ATestParent
import com.studiojozu.medicheck.infrastructure.persistence.database.AppDatabase
import com.studiojozu.medicheck.infrastructure.persistence.entity.SqliteMedicine
import com.studiojozu.medicheck.infrastructure.persistence.entity.SqliteMedicineMedicineUnit
import com.studiojozu.medicheck.infrastructure.persistence.entity.SqliteMedicineUnit
import org.junit.Assert
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
class SqliteMedicineMedicineUnitRepositoryTest : ATestParent() {

    @Test
    @Throws(Exception::class)
    fun findAll() {
        val database = AppDatabase.getAppDatabase(RuntimeEnvironment.application.applicationContext)
        val dao = database.medicineViewDao()

        // addData
        val medicineUnit = setSqliteMedicineUnit(getMedicineUnit("錠"))
        val medicine = setSqliteMedicine(getMedicine("メルカゾール", medicineUnit.mMedicineUnitId.dbValue))
        database.medicineDao().insert(medicine)
        database.medicineUnitDao().insert(medicineUnit)

        // findAll
        val medicines = dao.findAll()
        assertEquals(1, medicines.size)
        assert(medicine, medicineUnit, medicines[0])

        // delete data
        database.medicineDao().delete(medicine)
        database.medicineUnitDao().delete(medicineUnit)
    }

    @Test
    @Throws(Exception::class)
    fun findByMedicineId() {
        val database = AppDatabase.getAppDatabase(RuntimeEnvironment.application.applicationContext)
        val dao = database.medicineViewDao()

        // select not data
        var medicineView = dao.findByMedicineId("")
        assertNull(medicineView)

        // addData
        val medicineUnit = setSqliteMedicineUnit(getMedicineUnit("錠"))
        val medicine = setSqliteMedicine(getMedicine("メルカゾール", medicineUnit.mMedicineUnitId.dbValue))
        database.medicineDao().insert(medicine)
        database.medicineUnitDao().insert(medicineUnit)

        // findByMedicineId
        medicineView = dao.findByMedicineId(medicine.mMedicineId.dbValue)
        assertNotNull(medicineView)
        assert(medicine, medicineUnit, medicineView!!)

        // select no data
        medicineView = dao.findByMedicineId("unknown id")
        assertNull(medicineView)

        // delete data
        database.medicineDao().delete(medicine)
        database.medicineUnitDao().delete(medicineUnit)
    }

    private fun setSqliteMedicine(medicineEntity: Medicine): SqliteMedicine {
        val medicine = SqliteMedicine(medicineId = medicineEntity.mMedicineId)
        medicine.mMedicineName = medicineEntity.mMedicineName
        medicine.mMedicineTakeNumber = medicineEntity.mMedicineTakeNumber
        medicine.mMedicineUnitId = medicineEntity.mMedicineUnit.mMedicineUnitId
        medicine.mMedicineDateNumber = medicineEntity.mMedicineDateNumber
        medicine.mMedicineStartDatetime = medicineEntity.mMedicineStartDatetime
        medicine.mMedicineInterval = medicineEntity.mMedicineInterval
        medicine.mMedicineIntervalMode = medicineEntity.mMedicineIntervalMode
        medicine.mMedicinePhoto = medicineEntity.mMedicinePhoto
        medicine.mMedicineNeedAlarm = medicineEntity.mMedicineNeedAlarm
        medicine.mMedicineDeleteFlag = medicineEntity.mMedicineDeleteFlag

        return medicine
    }

    private fun setSqliteMedicineUnit(medicineUnit: MedicineUnit): SqliteMedicineUnit {
        val sqliteMedicineUnit = SqliteMedicineUnit(medicineUnitId = medicineUnit.mMedicineUnitId)
        sqliteMedicineUnit.mMedicineUnitValue = medicineUnit.mMedicineUnitValue
        sqliteMedicineUnit.mMedicineUnitDisplayOrder = medicineUnit.mMedicineUnitDisplayOrder

        return sqliteMedicineUnit
    }

    private fun assert(expectMedicine: SqliteMedicine, expectMedicineUnit: SqliteMedicineUnit, actual: SqliteMedicineMedicineUnit) {
        Assert.assertEquals(expectMedicine.mMedicineId, actual.mMedicineId)
        Assert.assertEquals(expectMedicine.mMedicineName, actual.mMedicineName)
        Assert.assertEquals(expectMedicine.mMedicineTakeNumber, actual.mMedicineTakeNumber)
        Assert.assertEquals(expectMedicine.mMedicineUnitId, actual.mMedicineUnitId)
        Assert.assertEquals(expectMedicine.mMedicineDateNumber, actual.mMedicineDateNumber)
        Assert.assertEquals(expectMedicine.mMedicineStartDatetime, actual.mMedicineStartDatetime)
        Assert.assertEquals(expectMedicine.mMedicineInterval, actual.mMedicineInterval)
        Assert.assertEquals(expectMedicine.mMedicineIntervalMode, actual.mMedicineIntervalMode)
        Assert.assertEquals(expectMedicine.mMedicinePhoto, actual.mMedicinePhoto)
        Assert.assertEquals(expectMedicine.mMedicineNeedAlarm, actual.mMedicineNeedAlarm)
        Assert.assertEquals(expectMedicine.mMedicineDeleteFlag, actual.mMedicineDeleteFlag)
        Assert.assertEquals(expectMedicineUnit.mMedicineUnitValue, actual.mMedicineUnitValue)
        Assert.assertEquals(expectMedicineUnit.mMedicineUnitDisplayOrder, actual.mMedicineUnitDisplayOrder)
    }

    private fun getMedicine(name: String = "", unitId: String = MedicineUnitIdType().dbValue): Medicine =
            Medicine(mMedicineName = MedicineNameType(name),
                    mMedicineUnit = MedicineUnit(mMedicineUnitId = MedicineUnitIdType(unitId)))

    private fun getMedicineUnit(value: String = ""): MedicineUnit =
            MedicineUnit(mMedicineUnitValue = MedicineUnitValueType(value))
}