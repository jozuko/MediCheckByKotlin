package com.studiojozu.medicheck.domain.model.medicine

import java.io.Serializable

/**
 * 薬を管理するクラス
 * @property mMedicineId 薬ID
 * @property mTimetableList タイムテーブルの一覧
 * @property mMedicineName 薬の名前
 * @property mTakeNumber 服用数
 * @property mMedicineUnit 薬の単位
 * @property mDateNumber 服用日数
 * @property mStartDatetime 服用開始日時
 * @property mTakeInterval 服用間隔
 * @property mTakeIntervalMode 服用間隔タイプ
 * @property mMedicinePhoto 薬の写真パス
 * @property mNeedAlarm アラーム要否フラグ
 * @property mDeleteFlagType 削除フラグ
 */
data class Medicine(val mMedicineId: MedicineIdType = MedicineIdType(),
                    val mMedicineName: MedicineNameType = MedicineNameType(),
                    val mTakeNumber: TakeNumberType = TakeNumberType(),
                    val mMedicineUnit: MedicineUnit = MedicineUnit(),
                    val mDateNumber: MedicineDateNumberType = MedicineDateNumberType(),
                    val mStartDatetime: StartDatetimeType = StartDatetimeType(),
                    val mTakeInterval: TakeIntervalType = TakeIntervalType(),
                    val mTakeIntervalMode: TakeIntervalModeType = TakeIntervalModeType(),
                    val mMedicinePhoto: MedicinePhotoType = MedicinePhotoType(),
                    val mNeedAlarm: MedicineNeedAlarmType = MedicineNeedAlarmType(),
                    val mDeleteFlagType: DeleteFlagType = DeleteFlagType(),
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
