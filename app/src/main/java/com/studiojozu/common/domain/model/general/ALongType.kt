package com.studiojozu.common.domain.model.general

import com.studiojozu.common.domain.model.AValueObject

abstract class ALongType<out C : ALongType<C>> @JvmOverloads protected constructor(value: Any = 0L) : AValueObject<Long, C>(), Comparable<ALongType<*>> {
    companion object {
        const val serialVersionUID = 7610652992905703415L
    }

    protected val mValue: Long = when (value) {
        is Long -> value
        is Int -> value.toLong()
        is ALongType<*> -> value.mValue
        else -> -1L
    }

    override val dbValue: Long
        get() = mValue

    override val displayValue: String
        get() = dbValue.toString()


    override fun compareTo(other: ALongType<*>): Int = dbValue.compareTo(other.dbValue)
}
