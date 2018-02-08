package com.studiojozu.medicheck.domain.model.medicine

import com.studiojozu.medicheck.domain.model.setting.ATestParent
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Suppress("FunctionName")
@RunWith(RobolectricTestRunner::class)
@Config(manifest = "src/main/AndroidManifest.xml")
class OneShotTypeTest : ATestParent() {

    @Test
    @Throws(Exception::class)
    fun constructor() {
        Assert.assertFalse(OneShotType().isTrue)
        Assert.assertTrue(OneShotType(true).isTrue)
        Assert.assertFalse(OneShotType(false).isTrue)
        Assert.assertTrue(OneShotType(1).isTrue)
    }
}