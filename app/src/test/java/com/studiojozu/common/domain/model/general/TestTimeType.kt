package com.studiojozu.common.domain.model.general

class TestTimeType : ATimeType<TestTimeType> {
    constructor(millisecond: Any) : super(millisecond)
    constructor(hourOfDay: Int, minute: Int) : super(hourOfDay, minute)
}