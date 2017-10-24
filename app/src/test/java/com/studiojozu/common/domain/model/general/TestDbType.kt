package com.studiojozu.common.domain.model.general

import android.content.ContentValues
import com.studiojozu.common.domain.model.ADbType

class TestDbType(value: Int) : ADbType<Int, TestDbType>() {

    private val mValue: Int = value

    override val dbValue: Int
        get() = mValue

    override val displayValue: String
        get() = mValue.toString()

    override fun setContentValue(columnName: String, contentValue: ContentValues) = Unit
}
