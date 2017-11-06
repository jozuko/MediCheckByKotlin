package com.studiojozu.common.domain.model.general

import com.studiojozu.common.domain.model.AValueObject
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

abstract class ATimeType<out C : ATimeType<C>> : AValueObject<Calendar, C>, Comparable<ATimeType<*>> {
    companion object {
        const val serialVersionUID = 2498883479425278479L
    }

    protected val mValue: Calendar

    override val dbValue: Calendar
        get() = mValue

    override val displayValue: String
        get() {
            val format = SimpleDateFormat.getTimeInstance(DateFormat.SHORT)
            return format.format(mValue.time)
        }

    protected constructor(calendarTime: Any) {
        val calendar: Calendar = when (calendarTime) {
            is Calendar -> calendarTime
            is ATimeType<*> -> calendarTime.dbValue
            is ADatetimeType<*> -> calendarTime.dbValue
            else -> throw IllegalArgumentException("unknown type.")
        }

        mValue = calendar.clone() as Calendar
        mValue.set(Calendar.YEAR, 2000)
        mValue.set(Calendar.MONTH, 0)
        mValue.set(Calendar.DAY_OF_MONTH, 1)
        mValue.set(Calendar.SECOND, 0)
        mValue.set(Calendar.MILLISECOND, 0)
    }

    protected constructor(hourOfDay: Int, minute: Int) {
        mValue = Calendar.getInstance()
        mValue.set(Calendar.YEAR, 2000)
        mValue.set(Calendar.MONTH, 0)
        mValue.set(Calendar.DAY_OF_MONTH, 1)
        mValue.set(Calendar.HOUR_OF_DAY, hourOfDay)
        mValue.set(Calendar.MINUTE, minute)
        mValue.set(Calendar.SECOND, 0)
        mValue.set(Calendar.MILLISECOND, 0)
    }

    override fun compareTo(other: ATimeType<*>): Int = dbValue.compareTo(other.dbValue)

    @Suppress("UNCHECKED_CAST")
    override fun clone(): C = this.javaClass.getConstructor(Any::class.java).newInstance(mValue.clone()) as C

    /**
     * フィールド値と引数の時分を比較する。
     *
     * @param target 比較する時分
     * @return 一致する場合はtrueを返却する
     */
    fun sameTime(target: Calendar): Boolean = if (mValue.get(Calendar.HOUR_OF_DAY) != target.get(Calendar.HOUR_OF_DAY)) false else mValue.get(Calendar.MINUTE) == target.get(Calendar.MINUTE)
}
