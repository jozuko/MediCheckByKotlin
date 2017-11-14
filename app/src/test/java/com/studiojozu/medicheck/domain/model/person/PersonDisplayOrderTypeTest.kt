package com.studiojozu.medicheck.domain.model.person

import com.studiojozu.medicheck.domain.model.setting.ATestParent
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Suppress("FunctionName")
@RunWith(RobolectricTestRunner::class)
@Config(manifest = "src/main/AndroidManifest.xml")
class PersonDisplayOrderTypeTest : ATestParent() {
    @Test
    @Throws(Exception::class)
    fun constructor() {
        Assert.assertEquals(0L, PersonDisplayOrderType().dbValue)
        Assert.assertEquals(1L, PersonDisplayOrderType(1L).dbValue)
    }
}