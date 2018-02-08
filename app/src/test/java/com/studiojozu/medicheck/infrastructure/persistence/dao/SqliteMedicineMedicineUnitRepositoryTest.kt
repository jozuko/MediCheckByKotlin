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
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = "src/main/AndroidManifest.xml")
class SqliteMedicineMedicineUnitRepositoryTest : ATestParent() {

    @Test
    @Throws(Exception::class)
    fun findAll() {
        val database = AppDatabase.getAppDatabase(RuntimeEnvironment.application.applicationContext)
        val dao = database.medicineViewDao()

        // addData
        val medicineUnit = setSqliteMedicineUnit(getMedicineUnit("錠"))
        val medicine = setSqliteMedicine(getMedicine("メルカゾール", medicineUnit.medicineUnitId.dbValue))
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
        val medicine = setSqliteMedicine(getMedicine("メルカゾール", medicineUnit.medicineUnitId.dbValue))
        database.medicineDao().insert(medicine)
        database.medicineUnitDao().insert(medicineUnit)

        // findByMedicineId
        medicineView = dao.findByMedicineId(medicine.medicineId.dbValue)
        assertNotNull(medicineView)
        assert(medicine, medicineUnit, medicineView!!)

        // select no data
        medicineView = dao.findByMedicineId("unknown id")
        assertNull(medicineView)

        // delete data
        database.medicineDao().delete(medicine)
        database.medicineUnitDao().delete(medicineUnit)
    }

    private fun setSqliteMedicine(entity: Medicine) =
            SqliteMedicine.build { medicine = entity }

    private fun setSqliteMedicineUnit(entity: MedicineUnit): SqliteMedicineUnit =
            SqliteMedicineUnit.build { medicineUnit = entity }

    private fun assert(expectMedicine: SqliteMedicine, expectMedicineUnit: SqliteMedicineUnit, actual: SqliteMedicineMedicineUnit) {
        Assert.assertEquals(expectMedicine.medicineId, actual.medicineId)
        Assert.assertEquals(expectMedicine.medicineName, actual.medicineName)
        Assert.assertEquals(expectMedicine.medicineTakeNumber, actual.medicineTakeNumber)
        Assert.assertEquals(expectMedicine.medicineUnitId, actual.medicineUnitId)
        Assert.assertEquals(expectMedicine.medicineDateNumber, actual.medicineDateNumber)
        Assert.assertEquals(expectMedicine.medicineStartDatetime, actual.medicineStartDatetime)
        Assert.assertEquals(expectMedicine.medicineInterval, actual.medicineInterval)
        Assert.assertEquals(expectMedicine.medicineIntervalMode, actual.medicineIntervalMode)
        Assert.assertEquals(expectMedicine.medicinePhoto, actual.medicinePhoto)
        Assert.assertEquals(expectMedicine.medicineNeedAlarm, actual.medicineNeedAlarm)
        Assert.assertEquals(expectMedicine.medicineDeleteFlag, actual.medicineDeleteFlag)
        Assert.assertEquals(expectMedicineUnit.medicineUnitValue, actual.medicineUnitValue)
        Assert.assertEquals(expectMedicineUnit.medicineUnitDisplayOrder, actual.medicineUnitDisplayOrder)
    }

    private fun getMedicine(name: String = "", unitId: String = MedicineUnitIdType().dbValue): Medicine =
            Medicine(medicineName = MedicineNameType(name),
                    medicineUnit = MedicineUnit(medicineUnitId = MedicineUnitIdType(unitId)))

    private fun getMedicineUnit(value: String = ""): MedicineUnit =
            MedicineUnit(medicineUnitValue = MedicineUnitValueType(value))
}