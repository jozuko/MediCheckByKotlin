package com.studiojozu.medicheck.domain.model.medicine

import java.io.Serializable

data class MedicineUnit(val mMedicineUnitId: MedicineUnitIdType = MedicineUnitIdType(),
                        val mMedicineUnitValue: MedicineUnitValueType = MedicineUnitValueType(),
                        val mMedicineUnitDisplayOrder: MedicineUnitDisplayOrderType = MedicineUnitDisplayOrderType()) : Serializable {
    companion object {
        private const val serialVersionUID = 1450309381235440899L
    }

    val displayValue: String
        get() = mMedicineUnitValue.displayValue
}
