package com.studiojozu.common.domain.model.general

import android.content.ContentValues
import com.studiojozu.common.domain.model.ADbType
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * 時間を表す型クラス
 */
abstract class TimeType<C : TimeType<C>> : ADbType<Long, C>, Comparable<TimeType<*>> {
    companion object {
        private val serialVersionUID = 2498883479425278479L
    }

    protected val mValue: Calendar

    override val dbValue: Long
        get() = mValue.timeInMillis

    override val displayValue: String
        get() {
            val format = SimpleDateFormat.getTimeInstance(DateFormat.SHORT)
            return format.format(mValue.time)
        }

    protected constructor(millisecond: Any) {
        val timeInMillis = when (millisecond) {
            is Long -> millisecond
            is Calendar -> millisecond.timeInMillis
            is TimeType<*> -> millisecond.dbValue
            is DatetimeType<*> -> millisecond.dbValue
            else -> throw IllegalArgumentException("unknown type.")
        }

        mValue = Calendar.getInstance()
        mValue.timeInMillis = timeInMillis
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

    override fun setContentValue(columnName: String, contentValue: ContentValues) {
        contentValue.put(columnName, dbValue)
    }

    override fun compareTo(other: TimeType<*>): Int = dbValue.compareTo(other.dbValue)

    /**
     * フィールド値と引数の時分を比較する。
     *
     * @param target 比較する時分
     * @return 一致する場合はtrueを返却する
     */
    fun equalsTime(target: Calendar): Boolean =if (mValue.get(Calendar.HOUR_OF_DAY) != target.get(Calendar.HOUR_OF_DAY)) false else mValue.get(Calendar.MINUTE) == target.get(Calendar.MINUTE)
}
