package com.studiojozu.medicheck.domain.model.medicine

import com.studiojozu.medicheck.domain.model.setting.ATestParent
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.powermock.core.classloader.annotations.PowerMockIgnore
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Suppress("FunctionName")
@RunWith(RobolectricTestRunner::class)
@Config(manifest = "src/main/AndroidManifest.xml")
@PowerMockIgnore("org.mockito.*", "org.robolectric.*", "android.*")
class DeleteFlagTypeTest : ATestParent() {

    @Test
    @Throws(Exception::class)
    fun constructor() {
        Assert.assertFalse(DeleteFlagType().isTrue)
        Assert.assertTrue(DeleteFlagType(true).isTrue)
        Assert.assertFalse(DeleteFlagType(false).isTrue)
        Assert.assertTrue(DeleteFlagType(1).isTrue)
    }
}