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
class IsTakeTypeTest : ATestParent() {

    @Test
    @Throws(Exception::class)
    fun constructor() {
        assertFalse(IsTakeType().isTrue)
        assertTrue(IsTakeType(true).isTrue)
        assertFalse(IsTakeType(false).isTrue)
        assertTrue(IsTakeType(1).isTrue)
    }
}