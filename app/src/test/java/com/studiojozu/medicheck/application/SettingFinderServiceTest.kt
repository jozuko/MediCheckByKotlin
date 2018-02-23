package com.studiojozu.medicheck.application

import com.studiojozu.medicheck.di.MediCheckTestApplication
import com.studiojozu.medicheck.domain.model.setting.*
import com.studiojozu.medicheck.domain.model.setting.repository.SettingRepository
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import javax.inject.Inject

@RunWith(RobolectricTestRunner::class)
@Config(manifest = "src/main/AndroidManifest.xml", application = MediCheckTestApplication::class)
class SettingFinderServiceTest : ATestParent() {
    @Inject
    lateinit var settingFinderService: SettingFinderService

    @Inject
    lateinit var settingRepository: SettingRepository

    @Before
    fun setUp() = (RuntimeEnvironment.application as MediCheckTestApplication).mAppTestComponent.inject(this)

    @Test
    @Throws(Exception::class)
    fun findSetting() {
        var setting = settingFinderService.findSetting()
        assertEquals(true, setting.useReminder())
        assertEquals(RemindIntervalType(RemindIntervalType.RemindIntervalPattern.MINUTE_5), setting.remindInterval)
        assertEquals(RemindTimeoutType(RemindTimeoutType.RemindTimeoutPattern.HOUR_24), setting.remindTimeout)

        // updateSetting
        val updateSetting = Setting(
                useReminder = UseReminderType(false),
                remindInterval = RemindIntervalType(RemindIntervalType.RemindIntervalPattern.HOUR_1),
                remindTimeout = RemindTimeoutType(RemindTimeoutType.RemindTimeoutPattern.MINUTE_10))
        settingRepository.updateData(updateSetting)

        // select update data
        setting = settingFinderService.findSetting()
        assertEquals(false, setting.useReminder())
        assertEquals(RemindIntervalType(RemindIntervalType.RemindIntervalPattern.HOUR_1), setting.remindInterval)
        assertEquals(RemindTimeoutType(RemindTimeoutType.RemindTimeoutPattern.MINUTE_10), setting.remindTimeout)
    }
}
