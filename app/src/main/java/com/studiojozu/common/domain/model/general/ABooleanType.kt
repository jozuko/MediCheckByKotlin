package com.studiojozu.common.domain.model.general

import android.content.ContentValues

import com.studiojozu.common.domain.model.ADbType

abstract class ABooleanType<out C : ABooleanType<C>> protected constructor(value: Any) : ADbType<Int, C>(), Comparable<ABooleanType<*>> {
    companion object {
        private val serialVersionUID = -2292450454310417909L
        private val TRUE_VALUE = 1
        private val FALSE_VALUE = 0
    }

    val isTrue: Boolean = when (value) {
        is Boolean -> value
        is Long -> value.toInt() == TRUE_VALUE
        is Int -> value == TRUE_VALUE
        else -> throw IllegalArgumentException("unknown type.")
    }

    override val dbValue: Int
        get() = if (isTrue) TRUE_VALUE else FALSE_VALUE

    override val displayValue: String
        get() = if (isTrue) "true" else "false"

    override fun setContentValue(columnName: String, contentValue: ContentValues) {
        contentValue.put(columnName, dbValue)
    }

    override fun compareTo(other: ABooleanType<*>): Int = dbValue.compareTo(other.dbValue)
}
