package com.studiojozu.medicheck.domain.model.medicine

import java.io.Serializable

/**
 * 薬を管理するクラス
 * @property medicineId 薬ID
 * @property timetableList タイムテーブルの一覧
 * @property medicineName 薬の名前
 * @property medicineTakeNumber 服用数
 * @property medicineUnit 薬の単位
 * @property medicineDateNumber 服用日数
 * @property medicineStartDatetime 服用開始日時
 * @property medicineInterval 服用間隔
 * @property medicineIntervalMode 服用間隔タイプ
 * @property medicinePhoto 薬の写真パス
 * @property medicineNeedAlarm アラーム要否フラグ
 * @property medicineDeleteFlag 削除フラグ
 */
data class Medicine(val medicineId: MedicineIdType = MedicineIdType(),
                    val medicineName: MedicineNameType = MedicineNameType(),
                    val medicineTakeNumber: MedicineTakeNumberType = MedicineTakeNumberType(),
                    val medicineUnit: MedicineUnit = MedicineUnit(),
                    val medicineDateNumber: MedicineDateNumberType = MedicineDateNumberType(),
                    val medicineStartDatetime: MedicineStartDatetimeType = MedicineStartDatetimeType(),
                    val medicineInterval: MedicineIntervalType = MedicineIntervalType(),
                    val medicineIntervalMode: MedicineIntervalModeType = MedicineIntervalModeType(),
                    val medicinePhoto: MedicinePhotoType = MedicinePhotoType(),
                    val medicineNeedAlarm: MedicineNeedAlarmType = MedicineNeedAlarmType(),
                    val medicineDeleteFlag: MedicineDeleteFlagType = MedicineDeleteFlagType(),
                    val timetableList: MedicineTimetableList = MedicineTimetableList()) : Serializable {

    companion object {
        private const val serialVersionUID = -3626443464441488492L
    }

    val medicineUnitId: MedicineUnitIdType
        get() = medicineUnit.medicineUnitId

    val isOneShowMedicine: Boolean
        get() = timetableList.isOneShotMedicine

    fun setOneShotMedicine(isOneShotMedicine: Boolean) {
        timetableList.isOneShotMedicine = isOneShotMedicine
    }
}
