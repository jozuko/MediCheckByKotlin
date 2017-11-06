package com.studiojozu.medicheck.domain.model.schedule

import com.studiojozu.medicheck.domain.model.setting.ATestParent
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.powermock.core.classloader.annotations.PowerMockIgnore
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.util.*

@Suppress("FunctionName")
@RunWith(RobolectricTestRunner::class)
@Config(manifest = "src/main/AndroidManifest.xml")
@PowerMockIgnore("org.mockito.*", "org.robolectric.*", "android.*")
class PlanDateTypeTest : ATestParent() {

    @Test
    @Throws(Exception::class)
    fun constructor() {
        assertTrue(0 < PlanDateType().dbValue.timeInMillis)

        val now = Calendar.getInstance()
        val expect = now.clone() as Calendar
        expect.set(Calendar.HOUR_OF_DAY, 0)
        expect.set(Calendar.MINUTE, 0)
        expect.set(Calendar.SECOND, 0)
        expect.set(Calendar.MILLISECOND, 0)
        assertEquals(expect.timeInMillis, PlanDateType(now).dbValue.timeInMillis)

        expect.set(2017, 0, 2, 0, 0, 0)
        assertEquals(expect.timeInMillis, PlanDateType(2017, 1, 2).dbValue.timeInMillis)
    }
}