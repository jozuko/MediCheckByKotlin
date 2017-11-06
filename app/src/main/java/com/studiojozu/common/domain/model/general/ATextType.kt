package com.studiojozu.common.domain.model.general

import com.studiojozu.common.domain.model.AValueObject

abstract class ATextType<out C : ATextType<C>> : AValueObject<String, C>, Comparable<ATextType<*>> {
    companion object {
        const val serialVersionUID = -4630676994700703038L
    }

    protected val mValue: String

    override val dbValue: String
        get() = mValue

    override val displayValue: String
        get() = mValue

    protected constructor() {
        mValue = ""
    }

    protected constructor(value: Any?) {
        mValue = when (value) {
            null -> ""
            is String -> value
            is ATextType<*> -> value.mValue
            else -> throw IllegalArgumentException("unknown type.")
        }
    }

    override fun compareTo(other: ATextType<*>): Int = dbValue.compareTo(other.dbValue)
}
