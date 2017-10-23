package com.studiojozu.common.domain.model.general

class TestDateType : ADateType<TestDateType> {
    constructor(millisecond: Any) : super(millisecond)
    constructor(year: Int, month: Int, day: Int) : super(year, month, day)
}
