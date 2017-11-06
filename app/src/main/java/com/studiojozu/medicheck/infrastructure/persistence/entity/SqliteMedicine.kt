package com.studiojozu.medicheck.infrastructure.persistence.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters
import com.studiojozu.medicheck.infrastructure.persistence.converter.SqliteConverters
import java.util.*

@Entity(tableName = "medicine")
class SqliteMedicine(medicineId: String) {
    /** ID  */
    @PrimaryKey
    @ColumnInfo(name = "medicine_id")
    var mMedicineId: String = medicineId

    /** 名前  */
    @ColumnInfo(name = "medicine_name")
    var mMedicineName: String = ""

    /** 服用数  */
    @ColumnInfo(name = "medicine_take_number")
    var mMedicineTakeNumber: String = ""

    /** 服用数 単位  */
    @ColumnInfo(name = "medicine_unit_id")
    var mMedicineUnitId: String = ""

    /** 服用日数  */
    @ColumnInfo(name = "medicine_date_number")
    var mMedicineDateNumber: Int = 0

    /** 服用開始日時  */
    @ColumnInfo(name = "medicine_start_datetime")
    @TypeConverters(SqliteConverters::class)
    var mMedicineStartDatetime: Calendar = Calendar.getInstance()

    /** 服用間隔  */
    @ColumnInfo(name = "medicine_interval")
    var mMedicineInterval: Int = 0

    /** 服用間隔タイプ  */
    @ColumnInfo(name = "medicine_interval_mode")
    var mMedicineIntervalMode: Int = 0

    /** 薬の写真  */
    @ColumnInfo(name = "medicine_photo")
    var mMedicinePhoto: String = ""

    /** アラーム要否  */
    @ColumnInfo(name = "medicine_need_alarm")
    var mMedicineNeedAlarm: Boolean = true

    /** 削除フラグ  */
    @ColumnInfo(name = "medicine_delete_flag")
    var mMedicineDeleteFlag: Boolean = false
}
