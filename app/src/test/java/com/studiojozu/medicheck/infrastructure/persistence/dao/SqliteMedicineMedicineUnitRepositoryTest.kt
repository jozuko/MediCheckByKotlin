package com.studiojozu.medicheck.infrastructure.persistence.dao

import com.studiojozu.medicheck.domain.model.medicine.*
import com.studiojozu.medicheck.domain.model.setting.ATestParent
import com.studiojozu.medicheck.infrastructure.persistence.database.AppDatabase
import com.studiojozu.medicheck.infrastructure.persistence.entity.SqliteMedicine
import com.studiojozu.medicheck.infrastructure.persistence.entity.SqliteMedicineUnit
import com.studiojozu.medicheck.infrastructure.persistence.entity.SqliteMedicineView
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
        val medicineUnit = setSqliteMedicineUnit(getMedicineUnit("12345678", "錠"))
        val medicine = setSqliteMedicine(getMedicine("12345678", "メルカゾール", medicineUnit.mMedicineUnitId))
        database.medicineDao().insert(medicine)
        database.medicineUnitDao().insert(medicineUnit)

        // select no data
        val medicines = dao.findAll()
        assertEquals(1, medicines.size)
        assert(medicine, medicineUnit, medicines[0])
    }

    @Test
    @Throws(Exception::class)
    fun findByMedicineId() {
        val database = AppDatabase.getAppDatabase(RuntimeEnvironment.application.applicationContext)
        val dao = database.medicineViewDao()

        // addData
        val medicineUnit = setSqliteMedicineUnit(getMedicineUnit("12345678", "錠"))
        val medicine = setSqliteMedicine(getMedicine("12345678", "メルカゾール", medicineUnit.mMedicineUnitId))
        database.medicineDao().insert(medicine)
        database.medicineUnitDao().insert(medicineUnit)

        // findByMedicineId
        var medicineView = dao.findByMedicineId(medicine.mMedicineId)
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

    private fun setSqliteMedicineUnit(medicineUnit: MedicineUnit): SqliteMedicineUnit {
        val sqliteMedicineUnit = SqliteMedicineUnit(medicineUnitId = medicineUnit.mMedicineUnitId.dbValue)
        sqliteMedicineUnit.mMedicineUnitValue = medicineUnit.mMedicineUnitValue.dbValue
        sqliteMedicineUnit.mMedicineUnitDisplayOrder = medicineUnit.mMedicineUnitDisplayOrder.dbValue

        return sqliteMedicineUnit
    }

    private fun assert(expectMedicine: SqliteMedicine, expectMedicineUnit: SqliteMedicineUnit, actual: SqliteMedicineView) {
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

    private fun getMedicine(id: String = "", name: String = "", unitId: String = ""): Medicine {
        var medicineId = id
        if (medicineId.isEmpty()) medicineId = MedicineIdType().dbValue

        var medicineUnitId = unitId
        if (medicineUnitId.isEmpty()) medicineUnitId = MedicineUnitIdType().dbValue

        return Medicine(
                mMedicineId = MedicineIdType(medicineId),
                mMedicineName = MedicineNameType(name),
                mMedicineUnit = MedicineUnit(mMedicineUnitId = MedicineUnitIdType(medicineUnitId)))
    }

    private fun getMedicineUnit(id: String = "", value: String = ""): MedicineUnit {
        var medicineUnitId = id
        if (medicineUnitId.isEmpty()) medicineUnitId = MedicineUnitIdType().dbValue
        return MedicineUnit(mMedicineUnitId = MedicineUnitIdType(medicineUnitId), mMedicineUnitValue = MedicineUnitValueType(value))
    }
}