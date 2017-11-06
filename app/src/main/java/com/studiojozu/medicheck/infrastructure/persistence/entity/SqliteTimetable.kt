package com.studiojozu.medicheck.infrastructure.persistence.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters
import com.studiojozu.medicheck.infrastructure.persistence.converter.SqliteConverters
import java.util.*

@Entity(tableName = "timetable")
class SqliteTimetable(timetableId: String) {

    /** タイムテーブルID */
    @PrimaryKey
    @ColumnInfo(name = "timetable_id")
    var mTimetableId: String = timetableId

    /** 服用タイミング名 */
    @ColumnInfo(name = "timetable_name")
    var mTimetableName: String = ""

    /** 予定時刻 */
    @ColumnInfo(name = "timetable_time")
    @TypeConverters(SqliteConverters::class)
    var mTimetableTime: Calendar = Calendar.getInstance()

    /** 表示順 */
    @ColumnInfo(name = "timetable_display_order")
    var mTimetableDisplayOrder: Long = 0L
}