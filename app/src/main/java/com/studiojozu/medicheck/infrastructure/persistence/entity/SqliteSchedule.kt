package com.studiojozu.medicheck.infrastructure.persistence.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.TypeConverters
import com.studiojozu.medicheck.infrastructure.persistence.converter.SqliteConverters
import java.util.*

@Entity(tableName = "schedule", primaryKeys = arrayOf("medicine_id", "schedule_plan_date", "schedule_need_alert"))
class SqliteSchedule(medicineId: String, schedulePlanDate: Calendar, timetableId: String) {

    /** 薬ID */
    @ColumnInfo(name = "medicine_id")
    var mMedicineId: String = medicineId

    /** 服用予定日付 */
    @ColumnInfo(name = "schedule_plan_date")
    @TypeConverters(SqliteConverters::class)
    var mSchedulePlanDate: Calendar = schedulePlanDate

    /** 服用予定タイムテーブルID */
    @ColumnInfo(name = "timetable_id")
    var mTimetableId: String = timetableId

    /** Alertいる？ */
    @ColumnInfo(name = "schedule_need_alert")
    var mScheduleNeedAlert: Boolean = true

    /** 服用した？ */
    @ColumnInfo(name = "schedule_is_take")
    var mScheduleIsTake: Boolean = false

    /** 服用した日時 */
    @ColumnInfo(name = "schedule_took_datetime")
    @TypeConverters(SqliteConverters::class)
    var mScheduleTookDatetime: Calendar = Calendar.getInstance()
}