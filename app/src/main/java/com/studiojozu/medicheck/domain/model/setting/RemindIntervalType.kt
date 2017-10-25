package com.studiojozu.medicheck.domain.model.setting

import android.content.ContentValues
import android.content.Context
import android.content.res.Resources
import android.support.annotation.StringRes
import com.studiojozu.common.domain.model.ADbType
import com.studiojozu.medicheck.R
import java.util.*

/**
 * 通知の繰り返し間隔を表す型クラス
 */
class RemindIntervalType @JvmOverloads constructor(intervalMinutes: Any = RemindIntervalType.RemindIntervalPattern.MINUTE_5) : ADbType<Int, RemindIntervalType>(), Comparable<RemindIntervalType> {
    companion object {
        const val serialVersionUID = -2200953636883165844L

        fun getAllValues(context: Context): TreeMap<Int, String> {
            val values = TreeMap<Int, String>()
            val resources = context.resources

            RemindIntervalPattern.values().forEach { it -> values.put(it.mIntervalMinutes, it.getDisplayValue(resources)) }
            return values
        }
    }

    private val mValue: RemindIntervalPattern

    init {
        mValue = when (intervalMinutes) {
            is RemindIntervalType -> intervalMinutes.mValue
            is RemindIntervalPattern -> intervalMinutes
            is Long -> RemindIntervalPattern.typeOfIntervalMinute(intervalMinutes.toInt())
            is Int -> RemindIntervalPattern.typeOfIntervalMinute(intervalMinutes)
            else -> throw IllegalArgumentException("unknown type")
        }
    }

    override val dbValue: Int
        get() = mValue.mIntervalMinutes

    override val displayValue: String
        get() = throw RuntimeException("you need to call getDisplayValue(Resources).")

    fun getDisplayValue(resources: Resources): String = mValue.getDisplayValue(resources)

    override fun setContentValue(columnName: String, contentValue: ContentValues) = contentValue.put(columnName, dbValue)

    override fun compareTo(other: RemindIntervalType): Int = mValue.compareTo(other.mValue)

    enum class RemindIntervalPattern(internal val mIntervalMinutes: Int, private val mDisplayValue: String, @param:StringRes private val mStringRes: Int) {
        MINUTE_1(1, "1", R.string.interval_minute),
        MINUTE_5(5, "5", R.string.interval_minutes),
        MINUTE_10(10, "10", R.string.interval_minutes),
        MINUTE_15(15, "15", R.string.interval_minutes),
        MINUTE_30(30, "30", R.string.interval_minutes),
        HOUR_1(60, "1", R.string.interval_hour);

        companion object {
            internal fun typeOfIntervalMinute(intervalMinutes: Int): RemindIntervalPattern = values().firstOrNull { it.mIntervalMinutes == intervalMinutes } ?: RemindIntervalPattern.MINUTE_5
        }

        fun getDisplayValue(resources: Resources): String = resources.getString(mStringRes, mDisplayValue)
    }
}
