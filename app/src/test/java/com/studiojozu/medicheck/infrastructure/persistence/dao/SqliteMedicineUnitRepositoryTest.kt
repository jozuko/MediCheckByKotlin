package com.studiojozu.medicheck.infrastructure.persistence.dao

import com.studiojozu.medicheck.domain.model.medicine.MedicineUnit
import com.studiojozu.medicheck.domain.model.medicine.MedicineUnitDisplayOrderType
import com.studiojozu.medicheck.domain.model.medicine.MedicineUnitIdType
import com.studiojozu.medicheck.domain.model.medicine.MedicineUnitValueType
import com.studiojozu.medicheck.domain.model.setting.ATestParent
import com.studiojozu.medicheck.infrastructure.persistence.database.AppDatabase
import com.studiojozu.medicheck.infrastructure.persistence.entity.SqliteMedicineUnit
import org.junit.Assert
import org.junit.Assert.assertNull
import org.junit.Test
import org.junit.runner.RunWith
import org.powermock.core.classloader.annotations.PowerMockIgnore
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = "src/main/AndroidManifest.xml")
@PowerMockIgnore("org.mockito.*", "org.robolectric.*", "android.*")
class SqliteMedicineUnitRepositoryTest : ATestParent() {

    @Test
    @Throws(Exception::class)
    fun crud() {
        val database = AppDatabase.getAppDatabase(RuntimeEnvironment.application.applicationContext)
        val dao = database.medicineUnitDao()

        // select no data
        var entities = dao.findAll()
        Assert.assertNotNull(entities)
        Assert.assertEquals(1, entities.size)

        // insert
        val insertMedicineUnit = MedicineUnit(
                mMedicineUnitId = MedicineUnitIdType("12345678"),
                mMedicineUnitValue = MedicineUnitValueType("錠"),
                mMedicineUnitDisplayOrder = MedicineUnitDisplayOrderType(2))
        dao.insert(setSqliteMedicineUnit(insertMedicineUnit))
        entities = dao.findAll()
        Assert.assertEquals(2, entities.size)
        assertIgnoreId(MedicineUnit(mMedicineUnitValue = MedicineUnitValueType("shot"), mMedicineUnitDisplayOrder = MedicineUnitDisplayOrderType(1)), entities[0])
        assert(insertMedicineUnit, entities[1])

        // update
        val updateMedicineUnit = insertMedicineUnit.copy(mMedicineUnitValue = MedicineUnitValueType("個"))
        dao.insert(setSqliteMedicineUnit(updateMedicineUnit))
        entities = dao.findAll()
        Assert.assertEquals(2, entities.size)
        assertIgnoreId(MedicineUnit(mMedicineUnitValue = MedicineUnitValueType("shot"), mMedicineUnitDisplayOrder = MedicineUnitDisplayOrderType(1)), entities[0])
        assert(updateMedicineUnit, entities[1])

        // delete
        val deleteMedicineUnit = insertMedicineUnit.copy()
        dao.delete(setSqliteMedicineUnit(deleteMedicineUnit))
        entities = dao.findAll()
        Assert.assertEquals(1, entities.size)
        assertIgnoreId(MedicineUnit(mMedicineUnitValue = MedicineUnitValueType("shot"), mMedicineUnitDisplayOrder = MedicineUnitDisplayOrderType(1)), entities[0])
    }

    @Test
    @Throws(Exception::class)
    fun findById() {
        val database = AppDatabase.getAppDatabase(RuntimeEnvironment.application.applicationContext)
        val dao = database.medicineUnitDao()

        // insert
        val insertMedicineUnit = MedicineUnit(
                mMedicineUnitId = MedicineUnitIdType("12345678"),
                mMedicineUnitValue = MedicineUnitValueType("錠"),
                mMedicineUnitDisplayOrder = MedicineUnitDisplayOrderType(2))
        dao.insert(setSqliteMedicineUnit(insertMedicineUnit))

        // findById - exists
        var entity = dao.findById(insertMedicineUnit.mMedicineUnitId.dbValue)
        assert(insertMedicineUnit, entity!!)

        // findById - not exists
        entity = dao.findById("unknown id")
        assertNull(entity)
    }

    private fun setSqliteMedicineUnit(medicineUnit: MedicineUnit): SqliteMedicineUnit {
        val sqliteMedicineUnit = SqliteMedicineUnit(medicineUnitId = medicineUnit.mMedicineUnitId.dbValue)
        sqliteMedicineUnit.mMedicineUnitValue = medicineUnit.mMedicineUnitValue.dbValue
        sqliteMedicineUnit.mMedicineUnitDisplayOrder = medicineUnit.mMedicineUnitDisplayOrder.dbValue

        return sqliteMedicineUnit
    }

    private fun assert(expect: MedicineUnit, actual: SqliteMedicineUnit) {
        Assert.assertEquals(expect.mMedicineUnitId.dbValue, actual.mMedicineUnitId)
        Assert.assertEquals(expect.mMedicineUnitValue.dbValue, actual.mMedicineUnitValue)
        Assert.assertEquals(expect.mMedicineUnitDisplayOrder.dbValue, actual.mMedicineUnitDisplayOrder)
    }

    private fun assertIgnoreId(expect: MedicineUnit, actual: SqliteMedicineUnit) {
        Assert.assertEquals(expect.mMedicineUnitValue.dbValue, actual.mMedicineUnitValue)
        Assert.assertEquals(expect.mMedicineUnitDisplayOrder.dbValue, actual.mMedicineUnitDisplayOrder)
    }
}