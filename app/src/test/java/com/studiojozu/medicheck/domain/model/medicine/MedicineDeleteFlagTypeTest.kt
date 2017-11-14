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
class MedicineDeleteFlagTypeTest : ATestParent() {

    @Test
    @Throws(Exception::class)
    fun constructor() {
        Assert.assertFalse(MedicineDeleteFlagType().isTrue)
        Assert.assertTrue(MedicineDeleteFlagType(true).isTrue)
        Assert.assertFalse(MedicineDeleteFlagType(false).isTrue)
        Assert.assertTrue(MedicineDeleteFlagType(1).isTrue)
    }
}