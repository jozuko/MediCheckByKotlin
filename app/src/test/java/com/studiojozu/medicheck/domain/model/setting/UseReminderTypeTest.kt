package com.studiojozu.medicheck.domain.model.setting

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Suppress("FunctionName")
@RunWith(RobolectricTestRunner::class)
@Config(manifest = "src/main/AndroidManifest.xml")
class UseReminderTypeTest : ATestParent() {
    @Test
    @Throws(Exception::class)
    fun constructor() {
        Assert.assertTrue(UseReminderType(true).isTrue)
        Assert.assertFalse(UseReminderType(false).isTrue)
        Assert.assertTrue(UseReminderType(1).isTrue)
    }
}