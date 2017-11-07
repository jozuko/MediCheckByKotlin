package com.studiojozu.medicheck.infrastructure.persistence.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.studiojozu.medicheck.domain.model.medicine.*

@Entity(tableName = "medicine")
open class SqliteMedicine(medicineId: MedicineIdType) {
    /** ID  */
    @PrimaryKey
    @ColumnInfo(name = "medicine_id")
    var mMedicineId: MedicineIdType = medicineId

    /** 名前  */
    @ColumnInfo(name = "medicine_name")
    var mMedicineName: MedicineNameType = MedicineNameType()

    /** 服用数  */
    @ColumnInfo(name = "medicine_take_number")
    var mMedicineTakeNumber: MedicineTakeNumberType = MedicineTakeNumberType()

    /** 服用数 単位  */
    @ColumnInfo(name = "medicine_unit_id")
    var mMedicineUnitId: MedicineUnitIdType = MedicineUnitIdType()

    /** 服用日数  */
    @ColumnInfo(name = "medicine_date_number")
    var mMedicineDateNumber: MedicineDateNumberType = MedicineDateNumberType()

    /** 服用開始日時  */
    @ColumnInfo(name = "medicine_start_datetime")
    var mMedicineStartDatetime: MedicineStartDatetimeType = MedicineStartDatetimeType()

    /** 服用間隔  */
    @ColumnInfo(name = "medicine_interval")
    var mMedicineInterval: MedicineIntervalType = MedicineIntervalType()

    /** 服用間隔タイプ  */
    @ColumnInfo(name = "medicine_interval_mode")
    var mMedicineIntervalMode: MedicineIntervalModeType = MedicineIntervalModeType()

    /** 薬の写真  */
    @ColumnInfo(name = "medicine_photo")
    var mMedicinePhoto: MedicinePhotoType = MedicinePhotoType()

    /** アラーム要否  */
    @ColumnInfo(name = "medicine_need_alarm")
    var mMedicineNeedAlarm: MedicineNeedAlarmType = MedicineNeedAlarmType()

    /** 削除フラグ  */
    @ColumnInfo(name = "medicine_delete_flag")
    var mMedicineDeleteFlag: MedicineDeleteFlagType = MedicineDeleteFlagType()
}
