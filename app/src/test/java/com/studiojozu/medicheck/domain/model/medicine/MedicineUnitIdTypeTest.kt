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
class MedicineUnitIdTypeTest : ATestParent() {
    @Test
    @Throws(Exception::class)
    fun constructor() {
        Assert.assertNotNull(MedicineUnitIdType().dbValue)
        Assert.assertNotSame("", MedicineUnitIdType().dbValue)
        Assert.assertEquals("test", MedicineUnitIdType("test").dbValue)
    }
}