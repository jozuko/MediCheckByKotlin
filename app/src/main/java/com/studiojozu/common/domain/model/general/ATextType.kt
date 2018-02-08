package com.studiojozu.common.domain.model.general

import com.studiojozu.common.domain.model.AValueObject

abstract class ATextType<out C : ATextType<C>> : AValueObject<String, C>, Comparable<ATextType<*>> {
    companion object {
        const val serialVersionUID = -4630676994700703038L
    }

    protected val value: String

    override val dbValue: String
        get() = value

    override val displayValue: String
        get() = value

    protected constructor() {
        value = ""
    }

    protected constructor(value: Any?) {
        this.value = when (value) {
            null -> ""
            is String -> value
            is ATextType<*> -> value.value
            else -> throw IllegalArgumentException("unknown type.")
        }
    }

    override fun compareTo(other: ATextType<*>): Int = dbValue.compareTo(other.dbValue)
}
