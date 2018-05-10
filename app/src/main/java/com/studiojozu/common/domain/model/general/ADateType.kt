package com.studiojozu.common.domain.model.general

import com.studiojozu.common.domain.model.AValueObject
import com.studiojozu.common.domain.model.CalendarNoSecond
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

abstract class ADateType<out C : ADateType<C>> : AValueObject<Long, C>, Comparable<ADateType<*>> {
    companion object {
        const val serialVersionUID = 5505479830648039872L
    }

    protected val value: Calendar

    override val dbValue: Long
        get() = value.timeInMillis

    override val displayValue: String
        get() {
            val format = SimpleDateFormat("yy/MM/dd", Locale.JAPAN)
            return format.format(value.time)
        }

    protected constructor(millisecond: Any) {
        val timeInMillis = when (millisecond) {
            is Long -> millisecond
            is Calendar -> millisecond.timeInMillis
            is ADateType<*> -> millisecond.dbValue
            is ADatetimeType<*> -> millisecond.dbValue
            else -> throw IllegalArgumentException("unknown type.")
        }

        value = CalendarNoSecond(timeInMillis).calendar
        value.set(Calendar.HOUR_OF_DAY, 0)
        value.set(Calendar.MINUTE, 0)
    }

    protected constructor(year: Int, month: Int, day: Int) {
        value = CalendarNoSecond(year, month - 1, day, 0, 0).calendar
    }

    override fun compareTo(other: ADateType<*>): Int = dbValue.compareTo(other.dbValue)

    @Suppress("UNCHECKED_CAST")
    override fun clone(): C = this.javaClass.getConstructor(Any::class.java).newInstance(value.clone()) as C

    /**
     * フィールド値と引数の年月日を比較する。
     *
     * @param other 比較する年月日
     * @return 一致する場合はtrueを返却する
     */
    fun sameDate(other: Calendar): Boolean {
        if (value.get(Calendar.YEAR) != other.get(Calendar.YEAR)) return false
        if (value.get(Calendar.MONTH) != other.get(Calendar.MONTH)) return false
        if (value.get(Calendar.DAY_OF_MONTH) != other.get(Calendar.DAY_OF_MONTH)) return false
        return true
    }

    fun addDay(days: Int): C {
        val dateType = clone()
        dateType.value.add(Calendar.DAY_OF_MONTH, days)

        return dateType
    }

}