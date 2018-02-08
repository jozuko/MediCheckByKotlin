package com.studiojozu.medicheck.domain.model.medicine

import com.studiojozu.medicheck.domain.model.setting.ATestParent
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Suppress("FunctionName")
@RunWith(RobolectricTestRunner::class)
@Config(manifest = "src/main/AndroidManifest.xml")
class MedicineUnitTest : ATestParent() {

    @Test
    @Throws(Exception::class)
    fun constructor_NoParameter() {
        val entity = MedicineUnit()
        assertNotNull(entity.medicineUnitId.dbValue)
        assertNotSame("", entity.medicineUnitId.dbValue)
        assertEquals("", entity.medicineUnitValue.dbValue)
        assertEquals(0L, entity.medicineUnitDisplayOrder.dbValue)
    }

    @Test
    @Throws(Exception::class)
    fun constructor_WithParameter() {
        var entity = MedicineUnit(medicineUnitId = MedicineUnitIdType("1234"))
        assertEquals("1234", entity.medicineUnitId.dbValue)
        assertEquals("", entity.medicineUnitValue.dbValue)
        assertEquals(0L, entity.medicineUnitDisplayOrder.dbValue)

        entity = MedicineUnit(medicineUnitValue = MedicineUnitValueType("錠"))
        assertNotNull(entity.medicineUnitId.dbValue)
        assertNotSame("", entity.medicineUnitId.dbValue)
        assertEquals("錠", entity.medicineUnitValue.dbValue)
        assertEquals(0L, entity.medicineUnitDisplayOrder.dbValue)

        entity = MedicineUnit(medicineUnitDisplayOrder = MedicineUnitDisplayOrderType(Long.MAX_VALUE))
        assertNotNull(entity.medicineUnitId.dbValue)
        assertNotSame("", entity.medicineUnitId.dbValue)
        assertEquals("", entity.medicineUnitValue.dbValue)
        assertEquals(Long.MAX_VALUE, entity.medicineUnitDisplayOrder.dbValue)
    }

    @Test
    @Throws(Exception::class)
    fun displayValue() {
        var entity = MedicineUnit()
        assertEquals("", entity.displayValue)

        entity = MedicineUnit(medicineUnitValue = MedicineUnitValueType("錠"))
        assertEquals("錠", entity.displayValue)
    }
}