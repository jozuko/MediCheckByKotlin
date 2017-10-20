package com.studiojozu.common.domain.model.general

import android.content.ContentValues

import com.studiojozu.common.domain.model.ADbType
import java.text.DateFormat
import java.text.SimpleDateFormat

/**
 * intの型クラス
 */
abstract class TextType<C : TextType<C>> : ADbType<String, C>, Comparable<TextType<*>> {
    companion object {
        private val serialVersionUID = -4630676994700703038L
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
        mValue = when(value) {
            value === null -> ""
            is String -> value
            else -> throw IllegalArgumentException("unknown type.")
        }
    }

    override fun setContentValue(columnName: String, contentValue: ContentValues) {
        contentValue.put(columnName, dbValue)
    }

    override fun compareTo(other: TextType<*>): Int =dbValue.compareTo(other.dbValue)
}
