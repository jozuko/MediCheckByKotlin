package com.studiojozu.medicheck.infrastructure.persistence.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.studiojozu.medicheck.domain.model.setting.*

@Entity(tableName = "timetable")
class SqliteTimetable(timetableId: TimetableIdType) {
    class Builder {
        lateinit var mTimetable: Timetable

        fun build(): SqliteTimetable {
            val sqliteTimetable = SqliteTimetable(timetableId = mTimetable.mTimetableId)
            sqliteTimetable.mTimetableName = mTimetable.mTimetableName
            sqliteTimetable.mTimetableTime = mTimetable.mTimetableTime
            sqliteTimetable.mTimetableDisplayOrder = mTimetable.mTimetableDisplayOrder

            return sqliteTimetable
        }
    }

    companion object {
        fun build(f: Builder.() -> Unit): SqliteTimetable {
            val builder = Builder()
            builder.f()
            return builder.build()
        }
    }

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

    fun toTimetable(): Timetable =
            Timetable(
                    mTimetableId = mTimetableId,
                    mTimetableName = mTimetableName,
                    mTimetableTime = mTimetableTime,
                    mTimetableDisplayOrder = mTimetableDisplayOrder)
}