package com.studiojozu.common.domain.model.general

import com.studiojozu.common.domain.model.AValueObject
import com.studiojozu.common.domain.model.CalendarNoSecond
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

abstract class ATimeType<out C : ATimeType<C>> : AValueObject<Long, C>, Comparable<ATimeType<*>> {
    companion object {
        const val serialVersionUID = 2498883479425278479L
    }

    protected val value: Calendar

    override val dbValue: Long
        get() = value.timeInMillis

    override val displayValue: String
        get() {
            val format = SimpleDateFormat("H:mm", Locale.JAPAN)
            return format.format(value.time)
        }

    val hourOfDay: Int
        get() = value.get(Calendar.HOUR_OF_DAY)

    val minute: Int
        get() = value.get(Calendar.MINUTE)

    protected constructor(millisecond: Any) {
        val timeInMillis = when (millisecond) {
            is Long -> millisecond
            is Calendar -> millisecond.timeInMillis
            is ATimeType<*> -> millisecond.dbValue
            is ADatetimeType<*> -> millisecond.dbValue
            else -> throw IllegalArgumentException("unknown type.")
        }

        value = CalendarNoSecond(timeInMillis).calendar
        value.set(Calendar.YEAR, 2000)
        value.set(Calendar.MONTH, 0)
        value.set(Calendar.DAY_OF_MONTH, 1)
    }

    protected constructor(hourOfDay: Int, minute: Int) {
        value = CalendarNoSecond(
                year = 2000,
                month = 0,
                dayOfMonth = 1,
                hourOfDay = hourOfDay,
                minute = minute).calendar
    }

    override fun compareTo(other: ATimeType<*>): Int = dbValue.compareTo(other.dbValue)

    @Suppress("UNCHECKED_CAST")
    override fun clone(): C = this.javaClass.getConstructor(Any::class.java).newInstance(value.clone()) as C

    /**
     * フィールド値と引数の時分を比較する。
     *
     * @param target 比較する時分
     * @return 一致する場合はtrueを返却する
     */
    fun sameTime(target: Calendar): Boolean = if (value.get(Calendar.HOUR_OF_DAY) != target.get(Calendar.HOUR_OF_DAY)) false else value.get(Calendar.MINUTE) == target.get(Calendar.MINUTE)
}
