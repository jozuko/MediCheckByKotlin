package com.studiojozu.medicheck.domain.model.medicine

import com.studiojozu.common.domain.model.AValueObject

class MedicineIntervalModeType(initValue: Any = DateIntervalPattern.DAYS) : AValueObject<Int, MedicineIntervalModeType>(), Comparable<MedicineIntervalModeType> {
    companion object {
        const val serialVersionUID = -469466334517497620L
    }

    private val mValue: DateIntervalPattern

    init {
        mValue = when (initValue) {
            is MedicineIntervalModeType -> initValue.mValue
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

    override fun compareTo(other: MedicineIntervalModeType): Int = dbValue.compareTo(other.dbValue)

    enum class DateIntervalPattern {
        DAYS,
        MONTH;

        companion object {
            internal fun typeOf(dbValue: Int): DateIntervalPattern = values().firstOrNull { it.ordinal == dbValue } ?: DateIntervalPattern.DAYS
        }
    }

}
