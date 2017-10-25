package com.studiojozu.medicheck.domain.model.setting

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.powermock.core.classloader.annotations.PowerMockIgnore
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Suppress("FunctionName")
@RunWith(RobolectricTestRunner::class)
@Config(manifest = "src/main/AndroidManifest.xml")
@PowerMockIgnore("org.mockito.*", "org.robolectric.*", "android.*")
class TimetableDisplayOrderTypeTest : ATestParent() {
    @Test
    @Throws(Exception::class)
    fun constructor() {
        assertEquals(0L, TimetableDisplayOrderType().dbValue)
        assertEquals(1L, TimetableDisplayOrderType(1L).dbValue)
    }
}