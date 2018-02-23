package com.studiojozu.medicheck.domain.model.setting

import android.content.Context
import android.content.res.Resources
import android.support.annotation.StringRes
import com.studiojozu.common.domain.model.AValueObject
import com.studiojozu.medicheck.R

/**
 * 通知の繰り返し間隔を表す型クラス
 */
class RemindIntervalType @JvmOverloads constructor(intervalMinutes: Any = RemindIntervalType.RemindIntervalPattern.MINUTE_5) : AValueObject<Int, RemindIntervalType>(), Comparable<RemindIntervalType> {
    companion object {
        const val serialVersionUID = -2200953636883165844L

        fun getAllValues(context: Context): MutableMap<Int, String> {
            val values = mutableMapOf<Int, String>()
            val resources = context.resources

            RemindIntervalPattern.values().forEach { it -> values.put(it.intervalMinutes, it.getDisplayValue(resources)) }
            return values
        }
    }

    private val value: RemindIntervalPattern

    init {
        value = when (intervalMinutes) {
            is RemindIntervalType -> intervalMinutes.value
            is RemindIntervalPattern -> intervalMinutes
            is Long -> RemindIntervalPattern.typeOfIntervalMinute(intervalMinutes.toInt())
            is Int -> RemindIntervalPattern.typeOfIntervalMinute(intervalMinutes)
            else -> throw IllegalArgumentException("unknown type")
        }
    }

    override val dbValue: Int
        get() = value.intervalMinutes

    override val displayValue: String
        get() = throw RuntimeException("you need to call getDisplayValue(Resources).")

    fun getDisplayValue(resources: Resources): String = value.getDisplayValue(resources)

    override fun compareTo(other: RemindIntervalType): Int = value.compareTo(other.value)

    enum class RemindIntervalPattern(internal val intervalMinutes: Int, private val displayValue: String, @param:StringRes private val stringRes: Int) {
        MINUTE_1(1, "1", R.string.interval_minute),
        MINUTE_5(5, "5", R.string.interval_minutes),
        MINUTE_10(10, "10", R.string.interval_minutes),
        MINUTE_15(15, "15", R.string.interval_minutes),
        MINUTE_30(30, "30", R.string.interval_minutes),
        HOUR_1(60, "1", R.string.interval_hour);

        companion object {
            fun typeOf(rowIndex: Int): RemindIntervalPattern {
                if (rowIndex >= 0 && rowIndex < RemindIntervalPattern.values().size) return RemindIntervalPattern.values()[rowIndex]
                return RemindIntervalPattern.MINUTE_5
            }

            internal fun typeOfIntervalMinute(intervalMinutes: Int): RemindIntervalPattern = values().firstOrNull { it.intervalMinutes == intervalMinutes } ?: RemindIntervalPattern.MINUTE_5
        }

        fun getDisplayValue(resources: Resources): String = resources.getString(stringRes, displayValue)
    }
}
