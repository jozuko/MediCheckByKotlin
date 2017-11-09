package com.studiojozu.medicheck.domain.model.medicine

import android.content.res.Resources
import com.studiojozu.common.domain.model.CalendarNoSecond
import com.studiojozu.common.domain.model.general.ADatetimeType
import com.studiojozu.common.domain.model.general.ALongType
import com.studiojozu.medicheck.R
import java.util.*

/**
 * 日付の間隔の型クラス.
 * 〇日おき or 毎月〇日の〇を表す
 */
class MedicineIntervalType : ALongType<MedicineIntervalType> {
    companion object {
        const val serialVersionUID = 1399242371014805824L
    }

    constructor() : super(0)
    constructor(value: Any) : super(value)

    override val displayValue: String
        get() = throw RuntimeException("you need to call getDisplayValue(Resources, MedicineIntervalModeType).")

    fun displayValue(resources: Resources, takeIntervalModeType: MedicineIntervalModeType): String
            = if (takeIntervalModeType.isDays) getDaysDisplayValue(resources) else getMonthDisplayValue(resources)

    private fun getDaysDisplayValue(resources: Resources): String
            = when (dbValue) {
        0L -> resources.getString(R.string.interval_every_day)
        1L -> resources.getString(R.string.interval_every_other_day)
        else -> resources.getString(R.string.interval_every_few_days, dbValue.toString())
    }

    private fun getMonthDisplayValue(resources: Resources): String {
        val daysValue = when (dbValue) {
            1L -> resources.getString(R.string.day_1)
            2L -> resources.getString(R.string.day_2)
            3L -> resources.getString(R.string.day_3)
            else -> resources.getString(R.string.day_4_over, dbValue.toString())
        }

        return resources.getString(R.string.interval_every_month, daysValue)
    }

    /**
     * Interval分の日数（or 月数）を加算する
     *
     * @param datetime         日時
     * @param takeIntervalMode 日数か月数を表す
     * @return 日数の場合：パラメータの日時にInterval+1分の日数を加算した値<br></br>
     * 月数の場合：パラメータの日時の日付をIntervalに置き換えて、1か月加算した値
     */
    fun addInterval(datetime: ADatetimeType<*>, takeIntervalMode: MedicineIntervalModeType): ADatetimeType<*> {
        if (takeIntervalMode.isDays) {
            return datetime.addDay(dbValue.toInt() + 1)
        }

        val calculateCalendar = CalendarNoSecond(datetime.dbValue).calendar
        calculateCalendar.add(Calendar.MONTH, 1)
        val maxDay = calculateCalendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        if (maxDay < dbValue)
            calculateCalendar.set(Calendar.DAY_OF_MONTH, maxDay)
        else
            calculateCalendar.set(Calendar.DAY_OF_MONTH, dbValue.toInt())

        return MedicineStartDatetimeType(calculateCalendar)
    }
}
