package com.studiojozu.medicheck.domain.model.setting

import com.studiojozu.common.domain.model.general.ADatetimeType
import com.studiojozu.medicheck.domain.model.schedule.PlanDate
import java.io.Serializable

/**
 * タイムテーブルのデータを管理するクラス
 *
 * @property mTimetableId タイムテーブルID
 * @property mTimetableName 名称
 * @property mTimetableDisplayOrder 表示順
 * @property mTimetableTime 時刻
 * @property timetableNameWithTime タイムテーブルの名称＋(時刻)
 */
data class Timetable(val mTimetableId: TimetableIdType = TimetableIdType(),
                     private var mTimetableName: TimetableNameType = TimetableNameType(),
                     private var mTimetableTime: TimetableTimeType = TimetableTimeType(),
                     private var mTimetableDisplayOrder: TimetableDisplayOrderType = TimetableDisplayOrderType()) : Serializable {

    companion object {
        const val serialVersionUID = -6460483527021583480L
    }

    val timetableNameWithTime: String
        get() {
            val name = mTimetableName.displayValue
            val time = mTimetableTime.displayValue
            return "$name($time)"
        }

    fun getTimetableTime(): TimetableTimeType = mTimetableTime

    fun setTimetableTime(hourOfDay: Int, minute: Int) {
        mTimetableTime = TimetableTimeType(hourOfDay, minute)
    }

    fun setTimetableName(timetableName: String) {
        mTimetableName = TimetableNameType(timetableName)
    }

    /**
     * 日付を表すパラメータを使用して予定日時を作成する
     *
     * @param datetimeType 日付
     * @return 予定日時（パラメータの日付 + [.mTimetableTime]の時分）
     */
    fun getPlanDateTime(datetimeType: ADatetimeType<*>): PlanDate {
        val planDatetime = mTimetableTime.replaceHourMinute(datetimeType)
        return PlanDate(planDatetime, mTimetableId)
    }

    /**
     * タイムテーブルの時刻を最優先として比較を行う
     * @return 比較結果(優先順位：時刻、表示順、名称、ID)
     */
    fun compareToTimePriority(other: Timetable): Int {
        val timeResult = this.mTimetableTime.compareTo(other.mTimetableTime)
        if (timeResult != 0) return timeResult

        val displayOrderResult = this.mTimetableDisplayOrder.compareTo(other.mTimetableDisplayOrder)
        if (displayOrderResult != 0) return displayOrderResult

        val nameResult = this.mTimetableName.compareTo(other.mTimetableName)
        if (nameResult != 0) return nameResult

        return this.mTimetableId.compareTo(other.mTimetableId)
    }

    /**
     * タイムテーブルの時刻を最優先として比較を行う
     * @return 比較結果(優先順位：表示順、時刻、名称、ID)
     */
    fun compareToDisplayOrderPriority(other: Timetable): Int {
        val displayOrderResult = this.mTimetableDisplayOrder.compareTo(other.mTimetableDisplayOrder)
        if (displayOrderResult != 0) return displayOrderResult

        val timeResult = this.mTimetableTime.compareTo(other.mTimetableTime)
        if (timeResult != 0) return timeResult

        val nameResult = this.mTimetableName.compareTo(other.mTimetableName)
        if (nameResult != 0) return nameResult

        return this.mTimetableId.compareTo(other.mTimetableId)
    }
}
