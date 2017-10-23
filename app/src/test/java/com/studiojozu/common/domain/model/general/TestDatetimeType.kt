package com.studiojozu.common.domain.model.general

class TestDatetimeType : ADatetimeType<TestDatetimeType> {
    constructor(millisecond: Any) : super(millisecond)
    constructor(year: Int, month: Int, date: Int, hourOfDay: Int, minute: Int) : super(year, month, date, hourOfDay, minute)
    constructor(dateModel: ADateType<*>, timeModel: ATimeType<*>) : super(dateModel, timeModel)
}