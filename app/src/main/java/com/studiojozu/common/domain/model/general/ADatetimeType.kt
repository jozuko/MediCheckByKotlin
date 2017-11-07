package com.studiojozu.common.domain.model.general

import com.studiojozu.common.domain.model.AValueObject
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

abstract class ADatetimeType<out C : ADatetimeType<C>> : AValueObject<Long, C>, Comparable<ADatetimeType<*>> {
    companion object {
        const val serialVersionUID = 3758376193729504494L
    }

    protected val mValue: Calendar

    override val dbValue: Long
        get() = mValue.timeInMillis

    override val displayValue: String
        get() {
            val format = SimpleDateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT)
            return format.format(mValue.time)
        }

    val year: Int
        get() = mValue.get(Calendar.YEAR)

    val month: Int
        get() = mValue.get(Calendar.MONTH)

    val dayOfMonth: Int
        get() = mValue.get(Calendar.DAY_OF_MONTH)

    val hourOfDay: Int
        get() = mValue.get(Calendar.HOUR_OF_DAY)

    val minute: Int
        get() = mValue.get(Calendar.MINUTE)

    protected constructor(millisecond: Any) {
        val timeInMillis: Long = when (millisecond) {
            is Long -> millisecond
            is Calendar -> millisecond.timeInMillis
            is ADatetimeType<*> -> millisecond.dbValue
            else -> throw IllegalArgumentException("unknown type.")
        }

        mValue = Calendar.getInstance()
        mValue.timeInMillis = timeInMillis
        mValue.set(Calendar.SECOND, 0)
        mValue.set(Calendar.MILLISECOND, 0)
    }

    /**
     * @param year 年
     * @param month 月(1-12)
     * @param day 日(1-31)
     * @param hourOfDay 時間(0-23)
     * @param minute 分(0-59)
     */
    protected constructor(year: Int, month: Int, day: Int, hourOfDay: Int, minute: Int) {
        mValue = Calendar.getInstance()
        mValue.set(year, month - 1, day, hourOfDay, minute, 0)
        mValue.set(Calendar.MILLISECOND, 0)
    }

    protected constructor(dateModel: ADateType<*>, timeModel: ATimeType<*>) {
        val dateCalendar = Calendar.getInstance()
        dateCalendar.timeInMillis = dateModel.dbValue

        val timeCalendar = Calendar.getInstance()
        timeCalendar.timeInMillis = timeModel.dbValue

        mValue = Calendar.getInstance()
        mValue.set(dateCalendar.get(Calendar.YEAR), dateCalendar.get(Calendar.MONTH), dateCalendar.get(Calendar.DAY_OF_MONTH), timeCalendar.get(Calendar.HOUR_OF_DAY), timeCalendar.get(Calendar.MINUTE), 0)
        mValue.set(Calendar.MILLISECOND, 0)
    }

    override fun compareTo(other: ADatetimeType<*>): Int = dbValue.compareTo(other.dbValue)

    @Suppress("UNCHECKED_CAST")
    override fun clone(): C = this.javaClass.getConstructor(Any::class.java).newInstance(mValue.clone()) as C

    /**
     * フィールドが保持する日時とパラメータの示す日時の差分を分単位で返却する
     *
     * @param other 差分を求める日時
     * @return 差分
     */
    fun diffMinutes(other: ADatetimeType<*>): Long {
        val targetCalendar = Calendar.getInstance()
        targetCalendar.timeInMillis = other.dbValue

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
        datetimeType.mValue.set(Calendar.HOUR_OF_DAY, hourOfDay)
        datetimeType.mValue.set(Calendar.MINUTE, minute)

        return datetimeType
    }

    fun addMinute(minutes: Int): C {
        val datetimeType = clone()
        datetimeType.mValue.add(Calendar.MINUTE, minutes)

        return datetimeType
    }

    fun addDay(days: Int): C {
        val datetimeType = clone()
        datetimeType.mValue.add(Calendar.DAY_OF_MONTH, days)

        return datetimeType
    }

    fun addMonth(months: Int): C {
        val datetimeType = clone()
        datetimeType.mValue.add(Calendar.MONTH, months)

        return datetimeType
    }
}
