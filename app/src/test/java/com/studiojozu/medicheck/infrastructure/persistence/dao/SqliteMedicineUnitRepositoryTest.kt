package com.studiojozu.medicheck.infrastructure.persistence.dao

import com.studiojozu.medicheck.domain.model.medicine.MedicineUnit
import com.studiojozu.medicheck.domain.model.medicine.MedicineUnitDisplayOrderType
import com.studiojozu.medicheck.domain.model.medicine.MedicineUnitIdType
import com.studiojozu.medicheck.domain.model.medicine.MedicineUnitValueType
import com.studiojozu.medicheck.domain.model.setting.ATestParent
import com.studiojozu.medicheck.infrastructure.persistence.database.AppDatabase
import com.studiojozu.medicheck.infrastructure.persistence.entity.SqliteMedicineUnit
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = "src/main/AndroidManifest.xml")
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
                medicineUnitId = MedicineUnitIdType("12345678"),
                medicineUnitValue = MedicineUnitValueType("錠"),
                medicineUnitDisplayOrder = MedicineUnitDisplayOrderType(2))
        dao.insert(setSqliteMedicineUnit(insertMedicineUnit))
        entities = dao.findAll()
        Assert.assertEquals(2, entities.size)
        assertIgnoreId(MedicineUnit(medicineUnitValue = MedicineUnitValueType("shot"), medicineUnitDisplayOrder = MedicineUnitDisplayOrderType(1)), entities[0])
        assert(insertMedicineUnit, entities[1])

        // update
        val updateMedicineUnit = insertMedicineUnit.copy(medicineUnitValue = MedicineUnitValueType("個"))
        dao.insert(setSqliteMedicineUnit(updateMedicineUnit))
        entities = dao.findAll()
        Assert.assertEquals(2, entities.size)
        assertIgnoreId(MedicineUnit(medicineUnitValue = MedicineUnitValueType("shot"), medicineUnitDisplayOrder = MedicineUnitDisplayOrderType(1)), entities[0])
        assert(updateMedicineUnit, entities[1])

        // delete
        val deleteMedicineUnit = insertMedicineUnit.copy()
        dao.delete(setSqliteMedicineUnit(deleteMedicineUnit))
        entities = dao.findAll()
        Assert.assertEquals(1, entities.size)
        assertIgnoreId(MedicineUnit(medicineUnitValue = MedicineUnitValueType("shot"), medicineUnitDisplayOrder = MedicineUnitDisplayOrderType(1)), entities[0])
    }

    @Test
    @Throws(Exception::class)
    fun findById() {
        val database = AppDatabase.getAppDatabase(RuntimeEnvironment.application.applicationContext)
        val dao = database.medicineUnitDao()

        // insert
        val insertMedicineUnit = MedicineUnit(
                medicineUnitId = MedicineUnitIdType("12345678"),
                medicineUnitValue = MedicineUnitValueType("錠"),
                medicineUnitDisplayOrder = MedicineUnitDisplayOrderType(2))
        dao.insert(setSqliteMedicineUnit(insertMedicineUnit))

        // findById - exists
        var entity = dao.findById(insertMedicineUnit.medicineUnitId.dbValue)
        assert(insertMedicineUnit, entity!!)

        // findById - not exists
        entity = dao.findById("unknown id")
        assertNull(entity)

        // delete
        val deleteMedicineEntity = insertMedicineUnit.copy()
        dao.delete(setSqliteMedicineUnit(deleteMedicineEntity))
    }

    @Test
    @Throws(Exception::class)
    fun maxDisplayOrder() {
        val database = AppDatabase.getAppDatabase(RuntimeEnvironment.application.applicationContext)
        val dao = database.medicineUnitDao()

        assertEquals(1L, dao.maxDisplayOrder())

        // insert
        val insertMedicineUnit = MedicineUnit(
                medicineUnitId = MedicineUnitIdType("12345678"),
                medicineUnitValue = MedicineUnitValueType("錠"),
                medicineUnitDisplayOrder = MedicineUnitDisplayOrderType(3))
        dao.insert(setSqliteMedicineUnit(insertMedicineUnit))

        assertEquals(3L, dao.maxDisplayOrder())
    }

    private fun setSqliteMedicineUnit(entity: MedicineUnit): SqliteMedicineUnit =
            SqliteMedicineUnit.build { medicineUnit = entity }

    private fun assert(expect: MedicineUnit, actual: SqliteMedicineUnit) {
        Assert.assertEquals(expect.medicineUnitId, actual.medicineUnitId)
        Assert.assertEquals(expect.medicineUnitValue, actual.medicineUnitValue)
        Assert.assertEquals(expect.medicineUnitDisplayOrder, actual.medicineUnitDisplayOrder)
    }

    private fun assertIgnoreId(expect: MedicineUnit, actual: SqliteMedicineUnit) {
        Assert.assertEquals(expect.medicineUnitValue, actual.medicineUnitValue)
        Assert.assertEquals(expect.medicineUnitDisplayOrder, actual.medicineUnitDisplayOrder)
    }
}