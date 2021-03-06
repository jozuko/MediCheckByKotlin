package com.studiojozu.medicheck.domain.model.schedule

import com.studiojozu.common.domain.model.general.TestDatetimeType
import com.studiojozu.medicheck.domain.model.setting.ATestParent
import com.studiojozu.medicheck.domain.model.setting.TimetableIdType
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotSame
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Suppress("FunctionName")
@RunWith(RobolectricTestRunner::class)
@Config(manifest = "src/main/AndroidManifest.xml")
class PlanDateTest : ATestParent() {

    @Test
    @Throws(Exception::class)
    fun constructor_WithParameter() {
        var entity = PlanDate(planDatetime = TestDatetimeType(2017, 1, 2, 3, 4))
        assertNotSame("", entity.timetableId.dbValue)
        assertEquals("17/01/02", entity.schedulePlanDate.displayValue)
        assertEquals("17/01/02 3:04", entity.planDatetime.displayValue)

        entity = PlanDate(planDatetime = TestDatetimeType(2017, 1, 2, 3, 4), timetableId = TimetableIdType("12345678"))
        assertEquals("12345678", entity.timetableId.dbValue)
        assertEquals("17/01/02", entity.schedulePlanDate.displayValue)
        assertEquals("17/01/02 3:04", entity.planDatetime.displayValue)
    }

}