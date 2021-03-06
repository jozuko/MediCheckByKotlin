package com.studiojozu.medicheck.domain.model.schedule

import com.studiojozu.common.domain.model.general.ADatetimeType
import com.studiojozu.medicheck.domain.model.setting.TimetableIdType

import java.io.Serializable

data class PlanDate(
        val planDatetime: ADatetimeType<*>,
        val timetableId: TimetableIdType = TimetableIdType()) : Serializable {

    companion object {
        const val serialVersionUID = 1510995332489280524L
    }

    val schedulePlanDate: SchedulePlanDateType
        get() = SchedulePlanDateType(planDatetime)

    /**
     * フィールドが保持する日時に、パラメータのdayを日数として追加した値を返却する。
     *
     * @param days 加算する日数
     * @return パラメータ値加算後の値を保持するインスタンス
     */
    fun addDay(days: Int): PlanDate = copy(planDatetime = planDatetime.addDay(days))
}
