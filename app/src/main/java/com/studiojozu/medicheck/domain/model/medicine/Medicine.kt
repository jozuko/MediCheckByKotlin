package com.studiojozu.medicheck.domain.model.medicine

import java.io.Serializable

/**
 * 薬を管理するクラス
 * @property mMedicineId 薬ID
 * @property mTimetableList タイムテーブルの一覧
 * @property mMedicineName 薬の名前
 * @property mMedicineTakeNumber 服用数
 * @property mMedicineUnit 薬の単位
 * @property mMedicineDateNumber 服用日数
 * @property mMedicineStartDatetime 服用開始日時
 * @property mMedicineInterval 服用間隔
 * @property mMedicineIntervalMode 服用間隔タイプ
 * @property mMedicinePhoto 薬の写真パス
 * @property mMedicineNeedAlarm アラーム要否フラグ
 * @property mMedicineDeleteFlag 削除フラグ
 */
data class Medicine(val mMedicineId: MedicineIdType = MedicineIdType(),
                    val mMedicineName: MedicineNameType = MedicineNameType(),
                    val mMedicineTakeNumber: MedicineTakeNumberType = MedicineTakeNumberType(),
                    val mMedicineUnit: MedicineUnit = MedicineUnit(),
                    val mMedicineDateNumber: MedicineDateNumberType = MedicineDateNumberType(),
                    val mMedicineStartDatetime: MedicineStartDatetimeType = MedicineStartDatetimeType(),
                    val mMedicineInterval: MedicineIntervalType = MedicineIntervalType(),
                    val mMedicineIntervalMode: MedicineIntervalModeType = MedicineIntervalModeType(),
                    val mMedicinePhoto: MedicinePhotoType = MedicinePhotoType(),
                    val mMedicineNeedAlarm: MedicineNeedAlarmType = MedicineNeedAlarmType(),
                    val mMedicineDeleteFlag: MedicineDeleteFlagType = MedicineDeleteFlagType(),
                    val mTimetableList: MedicineTimetableList = MedicineTimetableList()) : Serializable {

    companion object {
        private const val serialVersionUID = -3626443464441488492L
    }

    val medicineUnitId: MedicineUnitIdType
        get() = mMedicineUnit.mMedicineUnitId

    val isOneShowMedicine: Boolean
        get() = mTimetableList.isOneShotMedicine

    fun setOneShotMedicine(isOneShotMedicine: Boolean) {
        mTimetableList.isOneShotMedicine = isOneShotMedicine
    }
}
