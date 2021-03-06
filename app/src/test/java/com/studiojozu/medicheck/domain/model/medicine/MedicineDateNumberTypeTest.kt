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
class MedicineDateNumberTypeTest : ATestParent() {
    @Test
    @Throws(Exception::class)
    fun constructor() {
        Assert.assertEquals(0L, MedicineDateNumberType().dbValue)
        Assert.assertEquals(1L, MedicineDateNumberType(1).dbValue)
        Assert.assertEquals(Int.MAX_VALUE.toLong(), MedicineDateNumberType(Int.MAX_VALUE).dbValue)
        Assert.assertEquals(Long.MAX_VALUE, MedicineDateNumberType(Long.MAX_VALUE).dbValue)
    }
}