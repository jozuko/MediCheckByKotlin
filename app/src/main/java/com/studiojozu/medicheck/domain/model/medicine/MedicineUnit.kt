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

    fun compareToDisplayOrderPriority(other: MedicineUnit): Int {
        val displayOrderResult = this.mMedicineUnitDisplayOrder.dbValue.compareTo(other.mMedicineUnitDisplayOrder.dbValue)
        if (displayOrderResult != 0) return displayOrderResult

        val displayValueResult = this.displayValue.compareTo(other.displayValue)
        if (displayValueResult != 0) return displayValueResult

        return this.mMedicineUnitId.dbValue.compareTo(other.mMedicineUnitId.dbValue)
    }

    fun compareToDisplayValuePriority(other: MedicineUnit): Int {
        val displayValueResult = this.displayValue.compareTo(other.displayValue)
        if (displayValueResult != 0) return displayValueResult

        val displayOrderResult = this.mMedicineUnitDisplayOrder.dbValue.compareTo(other.mMedicineUnitDisplayOrder.dbValue)
        if (displayOrderResult != 0) return displayOrderResult

        return this.mMedicineUnitId.dbValue.compareTo(other.mMedicineUnitId.dbValue)
    }
}
