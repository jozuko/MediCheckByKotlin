package com.studiojozu.medicheck.domain.model.medicine.repository

import com.studiojozu.medicheck.di.MediCheckTestApplication
import com.studiojozu.medicheck.domain.model.medicine.MedicineUnit
import com.studiojozu.medicheck.domain.model.medicine.MedicineUnitDisplayOrderType
import com.studiojozu.medicheck.domain.model.medicine.MedicineUnitIdType
import com.studiojozu.medicheck.domain.model.medicine.MedicineUnitValueType
import com.studiojozu.medicheck.domain.model.setting.ATestParent
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import javax.inject.Inject

@Suppress("FunctionName")
@RunWith(RobolectricTestRunner::class)
@Config(manifest = "src/main/AndroidManifest.xml", application = MediCheckTestApplication::class)
class MedicineUnitRepositoryTest : ATestParent() {

    @Inject
    lateinit var medicineUnitRepository: MedicineUnitRepository

    @Before
    fun setUp() = (RuntimeEnvironment.application as MediCheckTestApplication).mAppTestComponent.inject(this)

    @Test
    @Throws(Exception::class)
    fun crud() {
        // select no data
        var entities = medicineUnitRepository.findAll()
        Assert.assertNotNull(entities)
        Assert.assertEquals(1, entities.size)

        // insert
        val insertMedicineUnit = MedicineUnit(
                medicineUnitId = MedicineUnitIdType("12345678"),
                medicineUnitValue = MedicineUnitValueType("錠"),
                medicineUnitDisplayOrder = MedicineUnitDisplayOrderType(2))
        medicineUnitRepository.insert(insertMedicineUnit)
        entities = medicineUnitRepository.findAll()
        Assert.assertEquals(2, entities.size)
        assertIgnoreId(MedicineUnit(medicineUnitValue = MedicineUnitValueType("shot"), medicineUnitDisplayOrder = MedicineUnitDisplayOrderType(1)), entities[0])
        assert(insertMedicineUnit, entities[1])

        // update
        val updateMedicineUnit = insertMedicineUnit.copy(medicineUnitValue = MedicineUnitValueType("個"))
        medicineUnitRepository.insert(updateMedicineUnit)
        entities = medicineUnitRepository.findAll()
        Assert.assertEquals(2, entities.size)
        assertIgnoreId(MedicineUnit(medicineUnitValue = MedicineUnitValueType("shot"), medicineUnitDisplayOrder = MedicineUnitDisplayOrderType(1)), entities[0])
        assert(updateMedicineUnit, entities[1])

        // delete
        val deleteMedicineUnit = insertMedicineUnit.copy()
        medicineUnitRepository.delete(deleteMedicineUnit)
        entities = medicineUnitRepository.findAll()
        Assert.assertEquals(1, entities.size)
        assertIgnoreId(MedicineUnit(medicineUnitValue = MedicineUnitValueType("shot"), medicineUnitDisplayOrder = MedicineUnitDisplayOrderType(1)), entities[0])
    }

    @Test
    @Throws(Exception::class)
    fun findById() {
        // insert
        val insertMedicineUnit = MedicineUnit(
                medicineUnitId = MedicineUnitIdType("12345678"),
                medicineUnitValue = MedicineUnitValueType("錠"),
                medicineUnitDisplayOrder = MedicineUnitDisplayOrderType(2))
        medicineUnitRepository.insert(insertMedicineUnit)

        // findById - exists
        var entity = medicineUnitRepository.findById(insertMedicineUnit.medicineUnitId)
        assert(insertMedicineUnit, entity!!)

        // findById - not exists
        entity = medicineUnitRepository.findById(MedicineUnitIdType("unknown id"))
        Assert.assertNull(entity)

        // delete
        val deleteMedicineEntity = insertMedicineUnit.copy()
        medicineUnitRepository.delete(deleteMedicineEntity)
    }

    @Test
    @Throws(Exception::class)
    fun maxDisplayOrder() {
        assertEquals(1L, medicineUnitRepository.maxDisplayOrder())

        // insert
        val insertMedicineUnit = MedicineUnit(
                medicineUnitId = MedicineUnitIdType("12345678"),
                medicineUnitValue = MedicineUnitValueType("錠"),
                medicineUnitDisplayOrder = MedicineUnitDisplayOrderType(3))
        medicineUnitRepository.insert(insertMedicineUnit)

        assertEquals(3L, medicineUnitRepository.maxDisplayOrder())
    }

    private fun assert(expect: MedicineUnit, actual: MedicineUnit) {
        Assert.assertEquals(expect.medicineUnitId, actual.medicineUnitId)
        Assert.assertEquals(expect.medicineUnitValue, actual.medicineUnitValue)
        Assert.assertEquals(expect.medicineUnitDisplayOrder, actual.medicineUnitDisplayOrder)
    }

    private fun assertIgnoreId(expect: MedicineUnit, actual: MedicineUnit) {
        Assert.assertEquals(expect.medicineUnitValue, actual.medicineUnitValue)
        Assert.assertEquals(expect.medicineUnitDisplayOrder, actual.medicineUnitDisplayOrder)
    }
}