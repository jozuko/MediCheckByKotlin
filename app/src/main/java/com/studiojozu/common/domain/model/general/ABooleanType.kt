package com.studiojozu.common.domain.model.general

import com.studiojozu.common.domain.model.AValueObject

abstract class ABooleanType<out C : ABooleanType<C>> protected constructor(value: Any) : AValueObject<Int, C>(), Comparable<ABooleanType<*>> {
    companion object {
        const val serialVersionUID = -2292450454310417909L
        private val TRUE_VALUE = 1
        private val FALSE_VALUE = 0
    }

    val isTrue: Boolean = when (value) {
        is Boolean -> value
        is Long -> value.toInt() == TRUE_VALUE
        is Int -> value == TRUE_VALUE
        is ABooleanType<*> -> value.isTrue
        else -> throw IllegalArgumentException("unknown type.")
    }

    override val dbValue: Int
        get() = if (isTrue) TRUE_VALUE else FALSE_VALUE

    override val displayValue: String
        get() = if (isTrue) "true" else "false"

    override fun compareTo(other: ABooleanType<*>): Int = dbValue.compareTo(other.dbValue)
}
