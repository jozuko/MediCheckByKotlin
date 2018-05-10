package com.studiojozu.common.domain.model.general

import com.studiojozu.common.domain.model.AValueObject
import com.studiojozu.common.domain.model.CalendarNoSecond
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

abstract class ADatetimeType<out C : ADatetimeType<C>> : AValueObject<Long, C>, Comparable<ADatetimeType<*>> {
    companion object {
        const val serialVersionUID = 3758376193729504494L
    }

    protected val value: Calendar

    override val dbValue: Long
        get() = value.timeInMillis

    override val displayValue: String
        get() {
            val format = SimpleDateFormat("yy/MM/dd H:mm", Locale.JAPAN)
            return format.format(value.time)
        }

    val year: Int
        get() = value.get(Calendar.YEAR)

    val month: Int
        get() = value.get(Calendar.MONTH)

    val dayOfMonth: Int
        get() = value.get(Calendar.DAY_OF_MONTH)

    val hourOfDay: Int
        get() = value.get(Calendar.HOUR_OF_DAY)

    val minute: Int
        get() = value.get(Calendar.MINUTE)

    protected constructor(millisecond: Any) {
        val timeInMillis: Long = when (millisecond) {
            is Long -> millisecond
            is Calendar -> millisecond.timeInMillis
            is ADatetimeType<*> -> millisecond.dbValue
            else -> throw IllegalArgumentException("unknown type.")
        }

        value = CalendarNoSecond(timeInMillis).calendar
    }

    /**
     * @param year 年
     * @param month 月(1-12)
     * @param day 日(1-31)
     * @param hourOfDay 時間(0-23)
     * @param minute 分(0-59)
     */
    protected constructor(year: Int, month: Int, day: Int, hourOfDay: Int, minute: Int) {
        value = CalendarNoSecond(year, month - 1, day, hourOfDay, minute).calendar
    }

    protected constructor(dateModel: ADateType<*>, timeModel: ATimeType<*>) {
        val dateCalendar = CalendarNoSecond(dateModel.dbValue).calendar
        val timeCalendar = CalendarNoSecond(timeModel.dbValue).calendar

        value = CalendarNoSecond(
                year = dateCalendar.get(Calendar.YEAR),
                month = dateCalendar.get(Calendar.MONTH),
                dayOfMonth = dateCalendar.get(Calendar.DAY_OF_MONTH),
                hourOfDay = timeCalendar.get(Calendar.HOUR_OF_DAY),
                minute = timeCalendar.get(Calendar.MINUTE)).calendar
    }

    override fun compareTo(other: ADatetimeType<*>): Int = dbValue.compareTo(other.dbValue)

    @Suppress("UNCHECKED_CAST")
    override fun clone(): C = this.javaClass.getConstructor(Any::class.java).newInstance(value.clone()) as C

    /**
     * フィールドが保持する日時とパラメータの示す日時の差分を分単位で返却する
     *
     * @param other 差分を求める日時
     * @return 差分
     */
    fun diffMinutes(other: ADatetimeType<*>): Long {
        val diff = dbValue - other.dbValue
        return diff / (60 * 1000)
    }

    /**
     * クローンを生成し、時と分のみを置き換える
     *
     * @param hourOfDay 時
     * @param minute    分
     * @return 時分を置き換えたクローンインスタンス
     */
    fun setHourMinute(hourOfDay: Int, minute: Int): ADatetimeType<*> {
        val datetimeType = clone()
        datetimeType.value.set(Calendar.HOUR_OF_DAY, hourOfDay)
        datetimeType.value.set(Calendar.MINUTE, minute)

        return datetimeType
    }

    fun addMinute(minutes: Int): C {
        val datetimeType = clone()
        datetimeType.value.add(Calendar.MINUTE, minutes)

        return datetimeType
    }

    fun addDay(days: Int): C {
        val datetimeType = clone()
        datetimeType.value.add(Calendar.DAY_OF_MONTH, days)

        return datetimeType
    }

    fun addMonth(months: Int): C {
        val datetimeType = clone()
        datetimeType.value.add(Calendar.MONTH, months)

        return datetimeType
    }
}
