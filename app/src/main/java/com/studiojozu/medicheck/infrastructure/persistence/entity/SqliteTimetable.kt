package com.studiojozu.medicheck.infrastructure.persistence.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.studiojozu.medicheck.domain.model.setting.*

@Entity(tableName = "timetable")
class SqliteTimetable(timetableId: TimetableIdType) {
    class Builder {
        lateinit var timetable: Timetable

        fun build(): SqliteTimetable {
            val sqliteTimetable = SqliteTimetable(timetableId = timetable.timetableId)
            sqliteTimetable.timetableName = timetable.timetableName
            sqliteTimetable.timetableTime = timetable.timetableTime
            sqliteTimetable.timetableDisplayOrder = timetable.timetableDisplayOrder

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
    @Suppress("CanBePrimaryConstructorProperty")
    @PrimaryKey
    @ColumnInfo(name = "timetable_id")
    var timetableId: TimetableIdType = timetableId

    /** 服用タイミング名 */
    @ColumnInfo(name = "timetable_name")
    var timetableName: TimetableNameType = TimetableNameType()

    /** 予定時刻 */
    @ColumnInfo(name = "timetable_time")
    var timetableTime: TimetableTimeType = TimetableTimeType()

    /** 表示順 */
    @ColumnInfo(name = "timetable_display_order")
    var timetableDisplayOrder: TimetableDisplayOrderType = TimetableDisplayOrderType()

    fun toTimetable(): Timetable =
            Timetable(
                    timetableId = timetableId,
                    timetableName = timetableName,
                    timetableTime = timetableTime,
                    timetableDisplayOrder = timetableDisplayOrder)
}