package com.studiojozu.common.domain.model.general

import android.content.ContentValues

import com.studiojozu.common.domain.model.ADbType

/**
 * longの型クラス
 */
class LongType<C : LongType<C>> @JvmOverloads protected constructor(value: Any = 0L) : ADbType<Long, C>(), Comparable<LongType<*>> {
    companion object {
        private val serialVersionUID = 7610652992905703415L
    }

    private val mValue : Long = when (value) {
        is Long -> value
        is Int -> value.toLong()
        else -> -1L
    }

    override val dbValue: Long
        get() = mValue

    override val displayValue: String
        get() = dbValue.toString()

    override fun setContentValue(columnName: String, contentValue: ContentValues) {
        contentValue.put(columnName, dbValue)
    }

    override fun compareTo(other: LongType<*>): Int = dbValue.compareTo(other.dbValue)
}
