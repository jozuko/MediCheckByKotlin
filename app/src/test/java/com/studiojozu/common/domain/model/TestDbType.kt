package com.studiojozu.common.domain.model

import android.content.ContentValues

class TestDbType(value: Int) : ADbType<Int, TestDbType>() {

    private val mValue: Int = value

    override val dbValue: Int
        get() = mValue

    override val displayValue: String
        get() = mValue.toString()

    override fun setContentValue(columnName: String, contentValue: ContentValues) = Unit
}
