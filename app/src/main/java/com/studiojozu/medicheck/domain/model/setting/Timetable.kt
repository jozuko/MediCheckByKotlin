package com.studiojozu.medicheck.domain.model.setting

import com.studiojozu.common.domain.model.general.ADatetimeType
import com.studiojozu.medicheck.domain.model.schedule.PlanDate
import java.io.Serializable

/**
 * タイムテーブルのデータを管理するクラス
 *
 * @property timetableId タイムテーブルID
 * @property timetableName 名称
 * @property timetableDisplayOrder 表示順
 * @property timetableTime 時刻
 * @property timetableNameWithTime タイムテーブルの名称＋(時刻)
 */
data class Timetable(val timetableId: TimetableIdType = TimetableIdType(),
                     val timetableName: TimetableNameType = TimetableNameType(),
                     val timetableTime: TimetableTimeType = TimetableTimeType(),
                     val timetableDisplayOrder: TimetableDisplayOrderType = TimetableDisplayOrderType()) : Serializable {

    companion object {
        const val serialVersionUID = -6460483527021583480L
    }

    val timetableNameWithTime: String
        get() {
            val name = timetableName.displayValue
            val time = timetableTime.displayValue
            return "$name($time)"
        }

    /**
     * 日付を表すパラメータを使用して予定日時を作成する
     * 日付を表すパラメータを使用して予定日時を作成する
     *
     * @param datetimeType 日付
     * @return 予定日時（パラメータの日付 + [.timetableTime]の時分）
     */
    fun getPlanDateTime(datetimeType: ADatetimeType<*>): PlanDate {
        val planDatetime = timetableTime.replaceHourMinute(datetimeType)
        return PlanDate(planDatetime, timetableId)
    }

    /**
     * タイムテーブルの時刻を最優先として比較を行う
     * @return 比較結果(優先順位：時刻、表示順、名称、ID)
     */
    fun compareToTimePriority(other: Timetable): Int {
        val timeResult = this.timetableTime.compareTo(other.timetableTime)
        if (timeResult != 0) return timeResult

        val displayOrderResult = this.timetableDisplayOrder.compareTo(other.timetableDisplayOrder)
        if (displayOrderResult != 0) return displayOrderResult

        val nameResult = this.timetableName.compareTo(other.timetableName)
        if (nameResult != 0) return nameResult

        return this.timetableId.compareTo(other.timetableId)
    }

    /**
     * タイムテーブルの時刻を最優先として比較を行う
     * @return 比較結果(優先順位：表示順、時刻、名称、ID)
     */
    fun compareToDisplayOrderPriority(other: Timetable): Int {
        val displayOrderResult = this.timetableDisplayOrder.compareTo(other.timetableDisplayOrder)
        if (displayOrderResult != 0) return displayOrderResult

        val timeResult = this.timetableTime.compareTo(other.timetableTime)
        if (timeResult != 0) return timeResult

        val nameResult = this.timetableName.compareTo(other.timetableName)
        if (nameResult != 0) return nameResult

        return this.timetableId.compareTo(other.timetableId)
    }
}
