package com.studiojozu.medicheck.domain.model.setting

import android.content.Context
import android.content.res.Resources
import android.support.annotation.StringRes
import com.studiojozu.common.domain.model.AValueObject
import com.studiojozu.common.domain.model.general.ADateType
import com.studiojozu.common.domain.model.general.ADatetimeType
import com.studiojozu.common.domain.model.general.ATimeType
import com.studiojozu.medicheck.R
import java.util.*

/**
 * 通知の繰り返しタイムアウトを表す型クラス
 */
class RemindTimeoutType @JvmOverloads constructor(timeoutMinute: Any = RemindTimeoutType.RemindTimeoutPattern.HOUR_24) : AValueObject<Int, RemindTimeoutType>(), Comparable<RemindTimeoutType> {
    companion object {
        const val serialVersionUID = 2910275487868116214L

        fun getAllValues(context: Context): TreeMap<Int, String> {
            val values = TreeMap<Int, String>()
            val resources = context.resources

            RemindTimeoutPattern.values().forEach { it -> values.put(it.timeoutMinutes, it.getDisplayValue(resources)) }
            return values
        }
    }

    private val value: RemindTimeoutPattern

    init {
        value = when (timeoutMinute) {
            is RemindTimeoutType -> timeoutMinute.value
            is RemindTimeoutPattern -> timeoutMinute
            is Long -> RemindTimeoutPattern.typeOfTimeoutMinute(timeoutMinute.toInt())
            is Int -> RemindTimeoutPattern.typeOfTimeoutMinute(timeoutMinute)
            else -> throw IllegalArgumentException("unknown type")
        }
    }

    override val dbValue: Int
        get() = value.timeoutMinutes

    override val displayValue: String
        get() = throw RuntimeException("you need to call getDisplayValue(Resources).")

    fun getDisplayValue(resources: Resources): String = value.getDisplayValue(resources)

    override fun compareTo(other: RemindTimeoutType): Int = value.compareTo(other.value)

    /**
     * パラメータnowに指名した時刻が、リマインド機能の限界時間を超えているか？
     *
     * @param now          現在日時
     * @param scheduleDate アラーム予定日付
     * @param scheduleTime アラーム予定時刻
     * @return リマインド機能の限界時間を超えている場合はtrueを返却する
     */
    fun isTimeout(now: ADatetimeType<*>, scheduleDate: ADateType<*>, scheduleTime: ATimeType<*>): Boolean {
        val reminderDateTime = ReminderDatetimeType(scheduleDate, scheduleTime).addMinute(dbValue)
        return reminderDateTime < now
    }

    enum class RemindTimeoutPattern constructor(internal val timeoutMinutes: Int, private val displayValue: String, @param:StringRes private val stringRes: Int) {
        MINUTE_1(1, "1", R.string.interval_minute),
        MINUTE_5(5, "5", R.string.interval_minutes),
        MINUTE_10(10, "10", R.string.interval_minutes),
        MINUTE_15(15, "15", R.string.interval_minutes),
        MINUTE_30(30, "30", R.string.interval_minutes),
        HOUR_1(60, "1", R.string.interval_hour),
        HOUR_2(60 * 2, "2", R.string.interval_hours),
        HOUR_6(60 * 6, "6", R.string.interval_hours),
        HOUR_9(60 * 9, "9", R.string.interval_hours),
        HOUR_12(60 * 12, "12", R.string.interval_hours),
        HOUR_24(60 * 24, "24", R.string.interval_hours);

        companion object {
            fun typeOf(rowIndex: Int): RemindTimeoutPattern {
                if (rowIndex >= 0 && rowIndex < RemindTimeoutPattern.values().size) return RemindTimeoutPattern.values()[rowIndex]
                return RemindTimeoutPattern.HOUR_24
            }

            internal fun typeOfTimeoutMinute(timeoutMinutes: Int): RemindTimeoutPattern = values().firstOrNull { it.timeoutMinutes == timeoutMinutes } ?: RemindTimeoutPattern.HOUR_24
        }

        fun getDisplayValue(resources: Resources): String = resources.getString(stringRes, displayValue)
    }
}
