package com.studiojozu.medicheck.domain.model.schedule

import com.studiojozu.medicheck.domain.model.setting.ATestParent
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.powermock.core.classloader.annotations.PowerMockIgnore
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Suppress("FunctionName")
@RunWith(RobolectricTestRunner::class)
@Config(manifest = "src/main/AndroidManifest.xml")
@PowerMockIgnore("org.mockito.*", "org.robolectric.*", "android.*")
class ScheduleIsTakeTypeTest : ATestParent() {

    @Test
    @Throws(Exception::class)
    fun constructor() {
        assertFalse(ScheduleIsTakeType().isTrue)
        assertTrue(ScheduleIsTakeType(true).isTrue)
        assertFalse(ScheduleIsTakeType(false).isTrue)
        assertTrue(ScheduleIsTakeType(1).isTrue)
    }
}