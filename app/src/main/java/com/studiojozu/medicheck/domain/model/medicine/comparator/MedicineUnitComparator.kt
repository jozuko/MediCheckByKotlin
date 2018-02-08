package com.studiojozu.medicheck.domain.model.medicine.comparator

import com.studiojozu.medicheck.domain.model.medicine.MedicineUnit
import java.util.*

class MedicineUnitComparator(private val mComparePattern: MedicineUnitComparator.ComparePattern) : Comparator<MedicineUnit> {

    override fun compare(medicineUnit1: MedicineUnit, medicineUnit2: MedicineUnit): Int = when (mComparePattern) {
        ComparePattern.DISPLAY_VALUE -> medicineUnit1.compareToDisplayValuePriority(medicineUnit2)
        ComparePattern.DISPLAY_ORDER -> medicineUnit1.compareToDisplayOrderPriority(medicineUnit2)
    }

    enum class ComparePattern {
        DISPLAY_VALUE,
        DISPLAY_ORDER
    }
}
