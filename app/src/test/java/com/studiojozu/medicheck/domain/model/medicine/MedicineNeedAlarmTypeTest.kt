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
class MedicineNeedAlarmTypeTest : ATestParent() {

    @Test
    @Throws(Exception::class)
    fun constructor() {
        Assert.assertTrue(MedicineNeedAlarmType().isTrue)
        Assert.assertTrue(MedicineNeedAlarmType(true).isTrue)
        Assert.assertFalse(MedicineNeedAlarmType(false).isTrue)
        Assert.assertTrue(MedicineNeedAlarmType(1).isTrue)
    }
}