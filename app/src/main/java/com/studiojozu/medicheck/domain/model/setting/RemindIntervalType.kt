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
class RemindIntervalType @JvmOverloads constructor(mIntervalMinutes: Any = RemindIntervalType.RemindIntervalPattern.MINUTE_5) : ADbType<Int, RemindIntervalType>(), Comparable<RemindIntervalType> {
    private val mValue: RemindIntervalPattern

    override val dbValue: Int
        get() = RemindIntervalPattern.valueOfIntervalMinutes(mValue)

    override val displayValue: String
        get() = throw RuntimeException("you need to call getDisplayValue(TakeIntervalModeType).")

    init {
        mValue = when (mIntervalMinutes) {
            is RemindIntervalPattern -> mIntervalMinutes
            is Long -> RemindIntervalPattern.typeOfIntervalMinute(mIntervalMinutes.toInt())
            is Int -> RemindIntervalPattern.typeOfIntervalMinute(mIntervalMinutes)
            else -> throw IllegalArgumentException("unknown type")
        }
    }

    override fun setContentValue(columnName: String, contentValue: ContentValues) = contentValue.put(columnName, dbValue)

    override fun compareTo(other: RemindIntervalType): Int = mValue.compareTo(other.mValue)

    fun getDisplayValue(resources: Resources): String {
        val stringRes = RemindIntervalPattern.valueOfStringRes(mValue)
        return if (stringRes == 0) "" else resources.getString(stringRes, RemindIntervalPattern.valueOfDisplayValue(mValue))
    }

    enum class RemindIntervalPattern(internal val mIntervalMinutes: Int, private val mDisplayValue: Int, @param:StringRes @field:StringRes private val mStringRes: Int) {
        MINUTE_1(1, 1, R.string.interval_minute),
        MINUTE_5(5, 5, R.string.interval_minutes),
        MINUTE_10(10, 10, R.string.interval_minutes),
        MINUTE_15(15, 15, R.string.interval_minutes),
        MINUTE_30(30, 30, R.string.interval_minutes),
        HOUR_1(60, 1, R.string.interval_hour);

        companion object {
            internal fun valueOfIntervalMinutes(type: RemindIntervalPattern): Int = values().firstOrNull { it == type }?.mIntervalMinutes ?: 0
            internal fun valueOfDisplayValue(type: RemindIntervalPattern): String = values().firstOrNull { it == type }?.mDisplayValue?.toString() ?: "0"
            @StringRes
            internal fun valueOfStringRes(type: RemindIntervalPattern): Int = values().firstOrNull { it == type }?.mStringRes ?: 0

            internal fun typeOfIntervalMinute(intervalMinutes: Int): RemindIntervalPattern = values().firstOrNull { it.mIntervalMinutes == intervalMinutes } ?: RemindIntervalPattern.MINUTE_5
        }
    }

    companion object {

        const val serialVersionUID = -2200953636883165844L

        /**
         * 選択肢のIDとなる分とそれに対応する表示文字列をMapで返却する
         *
         * @param context アプリケーションコンテキスト
         * @return 選択肢のIDとなる分とそれに対応する表示文字列のMap
         */
        fun getAllValues(context: Context): TreeMap<Int, String> {
            val values = TreeMap<Int, String>()
            for (remindIntervalPattern in RemindIntervalPattern.values()) {
                val stringRes = RemindIntervalPattern.valueOfStringRes(remindIntervalPattern)
                val stringResParam = RemindIntervalPattern.valueOfDisplayValue(remindIntervalPattern)
                values.put(remindIntervalPattern.mIntervalMinutes, context.resources.getString(stringRes, stringResParam))
            }

            return values
        }
    }
}
