package com.studiojozu.common.domain.model.general

import android.content.ContentValues

import com.studiojozu.common.domain.model.ADbType

/**
 * booleanの型クラス
 */
abstract class BooleanType<C : BooleanType<C>> protected constructor(value: Any) : ADbType<Int, C>(), Comparable<BooleanType<*>> {
    companion object {
        private val serialVersionUID = -2292450454310417909L
        private val TRUE_VALUE = 1
        private val FALSE_VALUE = 0
    }

    private val isTrue: Boolean = when (value) {
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

    override fun compareTo(other: BooleanType<*>): Int = dbValue.compareTo(other.dbValue)
}
