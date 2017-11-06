package com.studiojozu.common.domain.model.general

import com.studiojozu.common.domain.model.AValueObject
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

abstract class ADateType<out C : ADateType<C>> : AValueObject<Calendar, C>, Comparable<ADateType<*>> {
    companion object {
        const val serialVersionUID = 5505479830648039872L
    }

    protected val mValue: Calendar

    override val dbValue: Calendar
        get() = mValue

    override val displayValue: String
        get() {
            val format = SimpleDateFormat.getDateInstance(DateFormat.SHORT)
            return format.format(mValue.time)
        }

    protected constructor(calendarDate: Any) {
        val calendar: Calendar = when (calendarDate) {
            is Calendar -> calendarDate
            is ADateType<*> -> calendarDate.dbValue
            is ADatetimeType<*> -> calendarDate.dbValue
            else -> throw IllegalArgumentException("unknown type.")
        }

        mValue = calendar.clone() as Calendar
        mValue.set(Calendar.HOUR_OF_DAY, 0)
        mValue.set(Calendar.MINUTE, 0)
        mValue.set(Calendar.SECOND, 0)
        mValue.set(Calendar.MILLISECOND, 0)
    }

    protected constructor(year: Int, month: Int, day: Int) {
        mValue = Calendar.getInstance()
        mValue.set(year, month - 1, day, 0, 0, 0)
        mValue.set(Calendar.MILLISECOND, 0)
    }

    override fun compareTo(other: ADateType<*>): Int = dbValue.compareTo(other.dbValue)

    @Suppress("UNCHECKED_CAST")
    override fun clone(): C = this.javaClass.getConstructor(Any::class.java).newInstance(mValue.clone()) as C

    /**
     * フィールド値と引数の年月日を比較する。
     *
     * @param other 比較する年月日
     * @return 一致する場合はtrueを返却する
     */
    fun sameDate(other: Calendar): Boolean {
        if (mValue.get(Calendar.YEAR) != other.get(Calendar.YEAR)) return false
        if (mValue.get(Calendar.MONTH) != other.get(Calendar.MONTH)) return false
        if (mValue.get(Calendar.DAY_OF_MONTH) != other.get(Calendar.DAY_OF_MONTH)) return false
        return true
    }

    fun addDay(days: Int): C {
        val dateType = clone()
        dateType.mValue.add(Calendar.DAY_OF_MONTH, days)

        return dateType
    }

}
