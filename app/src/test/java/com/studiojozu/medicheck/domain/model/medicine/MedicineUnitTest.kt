package com.studiojozu.medicheck.domain.model.medicine

import com.studiojozu.medicheck.domain.model.setting.ATestParent
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.powermock.core.classloader.annotations.PowerMockIgnore
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Suppress("FunctionName")
@RunWith(RobolectricTestRunner::class)
@Config(manifest = "src/main/AndroidManifest.xml")
@PowerMockIgnore("org.mockito.*", "org.robolectric.*", "android.*")
class MedicineUnitTest : ATestParent() {

    @Test
    @Throws(Exception::class)
    fun constructor_NoParameter() {
        val entity = MedicineUnit()
        assertNotNull(entity.mMedicineUnitId.dbValue)
        assertNotSame("", entity.mMedicineUnitId.dbValue)
        assertEquals("", entity.mMedicineUnitValue.dbValue)
        assertEquals(0L, entity.mMedicineUnitDisplayOrder.dbValue)
    }

    @Test
    @Throws(Exception::class)
    fun constructor_WithParameter() {
        var entity = MedicineUnit(mMedicineUnitId = MedicineUnitIdType("1234"))
        assertEquals("1234", entity.mMedicineUnitId.dbValue)
        assertEquals("", entity.mMedicineUnitValue.dbValue)
        assertEquals(0L, entity.mMedicineUnitDisplayOrder.dbValue)

        entity = MedicineUnit(mMedicineUnitValue = MedicineUnitValueType("錠"))
        assertNotNull(entity.mMedicineUnitId.dbValue)
        assertNotSame("", entity.mMedicineUnitId.dbValue)
        assertEquals("錠", entity.mMedicineUnitValue.dbValue)
        assertEquals(0L, entity.mMedicineUnitDisplayOrder.dbValue)

        entity = MedicineUnit(mMedicineUnitDisplayOrder = MedicineUnitDisplayOrderType(Long.MAX_VALUE))
        assertNotNull(entity.mMedicineUnitId.dbValue)
        assertNotSame("", entity.mMedicineUnitId.dbValue)
        assertEquals("", entity.mMedicineUnitValue.dbValue)
        assertEquals(Long.MAX_VALUE, entity.mMedicineUnitDisplayOrder.dbValue)
    }

    @Test
    @Throws(Exception::class)
    fun displayValue() {
        var entity = MedicineUnit()
        assertEquals("", entity.displayValue)

        entity = MedicineUnit(mMedicineUnitValue = MedicineUnitValueType("錠"))
        assertEquals("錠", entity.displayValue)
    }
}