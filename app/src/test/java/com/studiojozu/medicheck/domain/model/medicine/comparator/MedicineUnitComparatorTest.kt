package com.studiojozu.medicheck.domain.model.medicine.comparator

import com.studiojozu.medicheck.domain.model.medicine.MedicineUnit
import com.studiojozu.medicheck.domain.model.medicine.MedicineUnitDisplayOrderType
import com.studiojozu.medicheck.domain.model.medicine.MedicineUnitIdType
import com.studiojozu.medicheck.domain.model.medicine.MedicineUnitValueType
import com.studiojozu.medicheck.domain.model.setting.ATestParent
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.powermock.core.classloader.annotations.PowerMockIgnore
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Suppress("FunctionName")
@RunWith(RobolectricTestRunner::class)
@Config(manifest = "src/main/AndroidManifest.xml")
@PowerMockIgnore("org.mockito.*", "org.robolectric.*", "android.*")
class MedicineUnitComparatorTest : ATestParent() {
    private val entity1 = MedicineUnit(
            mMedicineUnitId = MedicineUnitIdType("11111111"),
            mMedicineUnitValue = MedicineUnitValueType("1111"),
            mMedicineUnitDisplayOrder = MedicineUnitDisplayOrderType(1))
    private val entity2 = MedicineUnit(
            mMedicineUnitId = MedicineUnitIdType("11111111"),
            mMedicineUnitValue = MedicineUnitValueType("2222"),
            mMedicineUnitDisplayOrder = MedicineUnitDisplayOrderType(1))
    private val entity3 = MedicineUnit(
            mMedicineUnitId = MedicineUnitIdType("11111111"),
            mMedicineUnitValue = MedicineUnitValueType("1111"),
            mMedicineUnitDisplayOrder = MedicineUnitDisplayOrderType(2))
    private val entity4 = MedicineUnit(
            mMedicineUnitId = MedicineUnitIdType("11111111"),
            mMedicineUnitValue = MedicineUnitValueType("1111"),
            mMedicineUnitDisplayOrder = MedicineUnitDisplayOrderType(1))

    @Test
    @Throws(Exception::class)
    fun compareDisplayValue() {
        val comparator = MedicineUnitComparator(MedicineUnitComparator.ComparePattern.DisplayValue)

        assertTrue(comparator.compare(entity1, entity1) == 0)
        assertTrue(comparator.compare(entity1, entity4) == 0)

        assertTrue(comparator.compare(entity1, entity2) < 0)
        assertTrue(comparator.compare(entity2, entity1) > 0)

        assertTrue(comparator.compare(entity1, entity3) < 0)
    }

    @Test
    @Throws(Exception::class)
    fun compareDisplayOrder() {
        val comparator = MedicineUnitComparator(MedicineUnitComparator.ComparePattern.DisplayOrder)

        assertTrue(comparator.compare(entity1, entity1) == 0)
        assertTrue(comparator.compare(entity1, entity4) == 0)

        assertTrue(comparator.compare(entity1, entity3) < 0)
        assertTrue(comparator.compare(entity3, entity1) > 0)

        assertTrue(comparator.compare(entity1, entity2) < 0)
    }
}