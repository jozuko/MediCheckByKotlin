package com.studiojozu.medicheck.domain.model.setting

import android.content.ContentValues
import android.content.Context
import android.content.res.Resources
import android.support.annotation.StringRes
import com.studiojozu.common.domain.model.ADbType
import com.studiojozu.common.domain.model.general.ADateType
import com.studiojozu.common.domain.model.general.ADatetimeType
import com.studiojozu.common.domain.model.general.ATimeType
import com.studiojozu.medicheck.R
import java.util.*

/**
 * 通知の繰り返しタイムアウトを表す型クラス
 */
class RemindTimeoutType @JvmOverloads constructor(timeoutMinute: Any = RemindTimeoutType.RemindTimeoutPattern.HOUR_24) : ADbType<Int, RemindTimeoutType>(), Comparable<RemindTimeoutType> {
    companion object {
        const val serialVersionUID = 2910275487868116214L

        fun getAllValues(context: Context): TreeMap<Int, String> {
            val values = TreeMap<Int, String>()
            val resources = context.resources

            RemindTimeoutPattern.values().forEach { it -> values.put(it.mTimeoutMinutes, it.getDisplayValue(resources)) }
            return values
        }
    }

    private val mValue: RemindTimeoutPattern

    init {
        mValue = when (timeoutMinute) {
            is RemindTimeoutType -> timeoutMinute.mValue
            is RemindTimeoutPattern -> timeoutMinute
            is Long -> RemindTimeoutPattern.typeOfTimeoutMinute(timeoutMinute.toInt())
            is Int -> RemindTimeoutPattern.typeOfTimeoutMinute(timeoutMinute)
            else -> throw IllegalArgumentException("unknown type")
        }
    }

    override val dbValue: Int
        get() = mValue.mTimeoutMinutes

    override val displayValue: String
        get() = throw RuntimeException("you need to call getDisplayValue(Resources).")

    fun getDisplayValue(resources: Resources): String = mValue.getDisplayValue(resources)

    override fun setContentValue(columnName: String, contentValue: ContentValues) = contentValue.put(columnName, dbValue)

    override fun compareTo(other: RemindTimeoutType): Int = mValue.compareTo(other.mValue)

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

    enum class RemindTimeoutPattern constructor(internal val mTimeoutMinutes: Int, private val mDisplayValue: String, @param:StringRes private val mStringRes: Int) {
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
            internal fun typeOfTimeoutMinute(timeoutMinutes: Int): RemindTimeoutPattern = values().firstOrNull { it.mTimeoutMinutes == timeoutMinutes } ?: RemindTimeoutPattern.HOUR_24
        }

        fun getDisplayValue(resources: Resources): String = resources.getString(mStringRes, mDisplayValue)
    }
}
