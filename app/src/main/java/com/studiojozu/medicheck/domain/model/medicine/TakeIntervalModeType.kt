package com.studiojozu.medicheck.domain.model.medicine

import android.content.ContentValues

import com.studiojozu.common.domain.model.ADbType

class TakeIntervalModeType(initValue: Any = DateIntervalPattern.DAYS) : ADbType<Int, TakeIntervalModeType>(), Comparable<TakeIntervalModeType> {
    companion object {
        const val serialVersionUID = -469466334517497620L
    }

    private val mValue: DateIntervalPattern

    init {
        mValue = when (initValue) {
            is TakeIntervalModeType -> initValue.mValue
            is DateIntervalPattern -> initValue
            is Long -> DateIntervalPattern.typeOf(initValue.toInt())
            is Int -> DateIntervalPattern.typeOf(initValue)
            else -> throw IllegalArgumentException("unknown type.")
        }
    }

    override val dbValue: Int
        get() = mValue.ordinal

    override val displayValue: String
        get() = mValue.toString()

    val isDays: Boolean
        get() = mValue == DateIntervalPattern.DAYS

    override fun setContentValue(columnName: String, contentValue: ContentValues) = contentValue.put(columnName, dbValue)

    override fun compareTo(other: TakeIntervalModeType): Int = dbValue.compareTo(other.dbValue)

    enum class DateIntervalPattern {
        DAYS,
        MONTH;

        companion object {
            internal fun typeOf(dbValue: Int): DateIntervalPattern = values().firstOrNull { it.ordinal == dbValue } ?: DateIntervalPattern.DAYS
        }
    }

}
