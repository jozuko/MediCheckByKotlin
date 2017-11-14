package com.studiojozu.medicheck.domain.model.setting

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Suppress("FunctionName")
@RunWith(RobolectricTestRunner::class)
@Config(manifest = "src/main/AndroidManifest.xml")
class TimetableIdTypeTest : ATestParent() {
    @Test
    @Throws(Exception::class)
    fun constructor() {
        Assert.assertNotNull(TimetableIdType().dbValue)
        Assert.assertEquals("test", TimetableIdType("test").dbValue)
    }
}