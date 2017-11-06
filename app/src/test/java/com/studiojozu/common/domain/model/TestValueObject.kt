package com.studiojozu.common.domain.model

class TestValueObject(value: Int) : AValueObject<Int, TestValueObject>() {

    private val mValue: Int = value

    override val dbValue: Int
        get() = mValue

    override val displayValue: String
        get() = mValue.toString()
}
