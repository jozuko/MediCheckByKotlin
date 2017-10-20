package com.studiojozu.common.domain.model.general

import android.content.ContentValues
import com.studiojozu.common.domain.model.ADbType
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * 時間を表す型クラス
 */
abstract class DatetimeType<C : DatetimeType<C>> : ADbType<Long, C>, Comparable<DatetimeType<*>> {
    companion object {
        private val serialVersionUID = 3758376193729504494L
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
            is DatetimeType<*> -> millisecond.dbValue
            else -> throw IllegalArgumentException("unknown type.")
        }

        mValue = Calendar.getInstance()
        mValue.timeInMillis = timeInMillis
        mValue.set(Calendar.SECOND, 0)
        mValue.set(Calendar.MILLISECOND, 0)
    }

    protected constructor(year: Int, month: Int, date: Int, hourOfDay: Int, minute: Int) {
        mValue = Calendar.getInstance()
        mValue.set(year, month - 1, date, hourOfDay, minute, 0)
        mValue.set(Calendar.MILLISECOND, 0)
    }

    protected constructor(dateModel: DateType<*>, timeModel: TimeType<*>) {
        val dateCalendar = Calendar.getInstance()
        dateCalendar.timeInMillis = dateModel.dbValue

        val timeCalendar = Calendar.getInstance()
        timeCalendar.timeInMillis = timeModel.dbValue

        mValue = Calendar.getInstance()
        mValue.set(dateCalendar.get(Calendar.YEAR), dateCalendar.get(Calendar.MONTH), dateCalendar.get(Calendar.DAY_OF_MONTH), timeCalendar.get(Calendar.HOUR_OF_DAY), timeCalendar.get(Calendar.MINUTE), 0)
        mValue.set(Calendar.MILLISECOND, 0)
    }

    override fun setContentValue(columnName: String, contentValue: ContentValues) {
        contentValue.put(columnName, dbValue)
    }

    override fun compareTo(other: DatetimeType<*>): Int = dbValue.compareTo(other.dbValue)

    /**
     * フィールドが保持する日時とパラメータの示す日時の差分を分単位で返却する
     *
     * @param other 差分を求める日時
     * @return 差分
     */
    fun diffMinutes(other: DatetimeType<*>): Long {
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
    fun setHourMinute(hourOfDay: Int, minute: Int): DatetimeType<*> {
        val datetimeType = clone()
        datetimeType.mValue.set(Calendar.HOUR_OF_DAY, hourOfDay)
        datetimeType.mValue.set(Calendar.MINUTE, minute)

        return datetimeType
    }

    abstract fun addMinute(minute: Int): C

    abstract fun addDay(day: Int): C

    abstract fun addMonth(month: Int): C
}
