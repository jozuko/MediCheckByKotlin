package com.studiojozu.medicheck.domain.model.medicine

import java.io.Serializable

data class MedicineUnit(val medicineUnitId: MedicineUnitIdType = MedicineUnitIdType(),
                        val medicineUnitValue: MedicineUnitValueType = MedicineUnitValueType(),
                        val medicineUnitDisplayOrder: MedicineUnitDisplayOrderType = MedicineUnitDisplayOrderType()) : Serializable {
    companion object {
        private const val serialVersionUID = 1450309381235440899L
    }

    val displayValue: String
        get() = medicineUnitValue.displayValue

    fun compareToDisplayOrderPriority(other: MedicineUnit): Int {
        val displayOrderResult = this.medicineUnitDisplayOrder.dbValue.compareTo(other.medicineUnitDisplayOrder.dbValue)
        if (displayOrderResult != 0) return displayOrderResult

        val displayValueResult = this.displayValue.compareTo(other.displayValue)
        if (displayValueResult != 0) return displayValueResult

        return this.medicineUnitId.dbValue.compareTo(other.medicineUnitId.dbValue)
    }

    fun compareToDisplayValuePriority(other: MedicineUnit): Int {
        val displayValueResult = this.displayValue.compareTo(other.displayValue)
        if (displayValueResult != 0) return displayValueResult

        val displayOrderResult = this.medicineUnitDisplayOrder.dbValue.compareTo(other.medicineUnitDisplayOrder.dbValue)
        if (displayOrderResult != 0) return displayOrderResult

        return this.medicineUnitId.dbValue.compareTo(other.medicineUnitId.dbValue)
    }
}
