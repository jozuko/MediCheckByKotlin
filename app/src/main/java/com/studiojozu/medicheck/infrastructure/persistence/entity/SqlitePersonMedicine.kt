package com.studiojozu.medicheck.infrastructure.persistence.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.TypeConverters
import com.studiojozu.medicheck.infrastructure.persistence.converter.SqliteConverters
import java.util.*

class SqlitePersonMedicine(personId: String) : SqlitePerson(personId) {

    /** 薬.ID  */
    @ColumnInfo(name = "medicine_id")
    var mMedicineId: String = ""

    /** 薬.名前  */
    @ColumnInfo(name = "medicine_name")
    var mMedicineName: String = ""

    /** 薬.服用数  */
    @ColumnInfo(name = "medicine_take_number")
    var mMedicineTakeNumber: String = ""

    /** 薬.服用数 単位  */
    @ColumnInfo(name = "medicine_unit_id")
    var mMedicineUnitId: String = ""

    /** 薬.服用日数  */
    @ColumnInfo(name = "medicine_date_number")
    var mMedicineDateNumber: Int = 0

    /** 薬.服用開始日時  */
    @ColumnInfo(name = "medicine_start_datetime")
    @TypeConverters(SqliteConverters::class)
    var mMedicineStartDatetime: Calendar = Calendar.getInstance()

    /** 薬.服用間隔  */
    @ColumnInfo(name = "medicine_interval")
    var mMedicineInterval: Int = 0

    /** 薬.服用間隔タイプ  */
    @ColumnInfo(name = "medicine_interval_mode")
    var mMedicineIntervalMode: Int = 0

    /** 薬.写真  */
    @ColumnInfo(name = "medicine_photo")
    var mMedicinePhoto: String = ""

    /** 薬.アラーム要否  */
    @ColumnInfo(name = "medicine_need_alarm")
    var mMedicineNeedAlarm: Boolean = true

    /** 薬.削除フラグ  */
    @ColumnInfo(name = "medicine_delete_flag")
    var mMedicineDeleteFlag: Boolean = false

    /** 薬単位.表示文字列  */
    @ColumnInfo(name = "medicine_unit_value")
    var mMedicineUnitValue: String = ""
}