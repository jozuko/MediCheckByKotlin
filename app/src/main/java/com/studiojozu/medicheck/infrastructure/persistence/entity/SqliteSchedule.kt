package com.studiojozu.medicheck.infrastructure.persistence.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import com.studiojozu.medicheck.domain.model.medicine.MedicineIdType
import com.studiojozu.medicheck.domain.model.schedule.ScheduleIsTakeType
import com.studiojozu.medicheck.domain.model.schedule.ScheduleNeedAlarmType
import com.studiojozu.medicheck.domain.model.schedule.SchedulePlanDateType
import com.studiojozu.medicheck.domain.model.schedule.ScheduleTookDatetimeType
import com.studiojozu.medicheck.domain.model.setting.TimetableIdType

@Entity(tableName = "schedule", primaryKeys = arrayOf("medicine_id", "schedule_plan_date", "schedule_need_alarm"))
class SqliteSchedule(medicineId: MedicineIdType, schedulePlanDate: SchedulePlanDateType, timetableId: TimetableIdType) {

    /** 薬ID */
    @ColumnInfo(name = "medicine_id")
    var mMedicineId: MedicineIdType = medicineId

    /** 服用予定日付 */
    @ColumnInfo(name = "schedule_plan_date")
    var mSchedulePlanDate: SchedulePlanDateType = schedulePlanDate

    /** 服用予定タイムテーブルID */
    @ColumnInfo(name = "timetable_id")
    var mTimetableId: TimetableIdType = timetableId

    /** Alertいる？ */
    @ColumnInfo(name = "schedule_need_alarm")
    var mScheduleNeedAlarm: ScheduleNeedAlarmType = ScheduleNeedAlarmType()

    /** 服用した？ */
    @ColumnInfo(name = "schedule_is_take")
    var mScheduleIsTake: ScheduleIsTakeType = ScheduleIsTakeType()

    /** 服用した日時 */
    @ColumnInfo(name = "schedule_took_datetime")
    var mScheduleTookDatetime: ScheduleTookDatetimeType = ScheduleTookDatetimeType()
}