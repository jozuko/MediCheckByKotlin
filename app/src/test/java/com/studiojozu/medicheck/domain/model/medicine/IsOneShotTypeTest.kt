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
class IsOneShotTypeTest : ATestParent() {

    @Test
    @Throws(Exception::class)
    fun constructor() {
        Assert.assertFalse(IsOneShotType().isTrue)
        Assert.assertTrue(IsOneShotType(true).isTrue)
        Assert.assertFalse(IsOneShotType(false).isTrue)
        Assert.assertTrue(IsOneShotType(1).isTrue)
    }
}