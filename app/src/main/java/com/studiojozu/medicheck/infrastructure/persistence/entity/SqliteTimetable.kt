package com.studiojozu.medicheck.infrastructure.persistence.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.studiojozu.medicheck.domain.model.setting.TimetableDisplayOrderType
import com.studiojozu.medicheck.domain.model.setting.TimetableIdType
import com.studiojozu.medicheck.domain.model.setting.TimetableNameType
import com.studiojozu.medicheck.domain.model.setting.TimetableTimeType

@Entity(tableName = "timetable")
class SqliteTimetable(timetableId: TimetableIdType) {

    /** タイムテーブルID */
    @PrimaryKey
    @ColumnInfo(name = "timetable_id")
    var mTimetableId: TimetableIdType = timetableId

    /** 服用タイミング名 */
    @ColumnInfo(name = "timetable_name")
    var mTimetableName: TimetableNameType = TimetableNameType()

    /** 予定時刻 */
    @ColumnInfo(name = "timetable_time")
    var mTimetableTime: TimetableTimeType = TimetableTimeType()

    /** 表示順 */
    @ColumnInfo(name = "timetable_display_order")
    var mTimetableDisplayOrder: TimetableDisplayOrderType = TimetableDisplayOrderType()
}