@file:Suppress("CanBePrimaryConstructorProperty")

package com.studiojozu.medicheck.infrastructure.persistence.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import com.studiojozu.medicheck.domain.model.medicine.MedicineIdType
import com.studiojozu.medicheck.domain.model.schedule.*
import com.studiojozu.medicheck.domain.model.setting.TimetableIdType

@Entity(tableName = "schedule", primaryKeys = arrayOf("medicine_id", "schedule_plan_date", "timetable_id"))
class SqliteSchedule(medicineId: MedicineIdType, schedulePlanDate: SchedulePlanDateType, timetableId: TimetableIdType) {
    class Builder {
        lateinit var schedule: Schedule

        fun build(): SqliteSchedule {
            val sqliteSchedule = SqliteSchedule(medicineId = schedule.medicineId,
                    schedulePlanDate = schedule.schedulePlanDate,
                    timetableId = schedule.timetableId)
            sqliteSchedule.scheduleNeedAlarm = schedule.scheduleNeedAlarm
            sqliteSchedule.scheduleIsTake = schedule.scheduleIsTake
            sqliteSchedule.scheduleTookDatetime = schedule.scheduleTookDatetime

            return sqliteSchedule
        }
    }

    companion object {
        fun build(f: Builder.() -> Unit): SqliteSchedule {
            val builder = Builder()
            builder.f()
            return builder.build()
        }
    }

    /** 薬ID */
    @ColumnInfo(name = "medicine_id")
    var medicineId: MedicineIdType = medicineId

    /** 服用予定日付 */
    @ColumnInfo(name = "schedule_plan_date")
    var schedulePlanDate: SchedulePlanDateType = schedulePlanDate

    /** 服用予定タイムテーブルID */
    @ColumnInfo(name = "timetable_id")
    var timetableId: TimetableIdType = timetableId

    /** Alertいる？ */
    @ColumnInfo(name = "schedule_need_alarm")
    var scheduleNeedAlarm: ScheduleNeedAlarmType = ScheduleNeedAlarmType()

    /** 服用した？ */
    @ColumnInfo(name = "schedule_is_take")
    var scheduleIsTake: ScheduleIsTakeType = ScheduleIsTakeType()

    /** 服用した日時 */
    @ColumnInfo(name = "schedule_took_datetime")
    var scheduleTookDatetime: ScheduleTookDatetimeType = ScheduleTookDatetimeType()

    fun toSchedule(): Schedule =
            Schedule(medicineId = medicineId,
                    schedulePlanDate = schedulePlanDate,
                    timetableId = timetableId,
                    scheduleNeedAlarm = scheduleNeedAlarm,
                    scheduleIsTake = scheduleIsTake,
                    scheduleTookDatetime = scheduleTookDatetime)
}