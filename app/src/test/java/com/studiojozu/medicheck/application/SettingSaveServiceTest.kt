package com.studiojozu.medicheck.application

import com.studiojozu.medicheck.di.MediCheckTestApplication
import com.studiojozu.medicheck.domain.model.setting.ATestParent
import com.studiojozu.medicheck.domain.model.setting.RemindIntervalType
import com.studiojozu.medicheck.domain.model.setting.RemindTimeoutType
import com.studiojozu.medicheck.domain.model.setting.UseReminderType
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
class SettingSaveServiceTest : ATestParent() {
    @Inject
    lateinit var settingSaveService: SettingSaveService

    @Inject
    lateinit var settingFinderService: SettingFinderService

    @Before
    fun setUp() = (RuntimeEnvironment.application as MediCheckTestApplication).mAppTestComponent.inject(this)

    @Test
    @Throws(Exception::class)
    fun saveUseReminder() {
        var setting = settingFinderService.findSetting()
        assertEquals(true, setting.useReminder())
        assertEquals(RemindIntervalType(RemindIntervalType.RemindIntervalPattern.MINUTE_5), setting.remindInterval)
        assertEquals(RemindTimeoutType(RemindTimeoutType.RemindTimeoutPattern.HOUR_24), setting.remindTimeout)

        // updateSetting
        settingSaveService.save(oldSetting = setting, useReminder = UseReminderType(false))

        // select update data
        setting = settingFinderService.findSetting()
        assertEquals(false, setting.useReminder())
        assertEquals(RemindIntervalType(RemindIntervalType.RemindIntervalPattern.MINUTE_5), setting.remindInterval)
        assertEquals(RemindTimeoutType(RemindTimeoutType.RemindTimeoutPattern.HOUR_24), setting.remindTimeout)
    }

    @Test
    @Throws(Exception::class)
    fun saveRemindInterval() {
        var setting = settingFinderService.findSetting()
        assertEquals(true, setting.useReminder())
        assertEquals(RemindIntervalType(RemindIntervalType.RemindIntervalPattern.MINUTE_5), setting.remindInterval)
        assertEquals(RemindTimeoutType(RemindTimeoutType.RemindTimeoutPattern.HOUR_24), setting.remindTimeout)

        // updateSetting
        settingSaveService.save(oldSetting = setting, remindInterval = RemindIntervalType(RemindIntervalType.RemindIntervalPattern.MINUTE_10))

        // select update data
        setting = settingFinderService.findSetting()
        assertEquals(true, setting.useReminder())
        assertEquals(RemindIntervalType(RemindIntervalType.RemindIntervalPattern.MINUTE_10), setting.remindInterval)
        assertEquals(RemindTimeoutType(RemindTimeoutType.RemindTimeoutPattern.HOUR_24), setting.remindTimeout)
    }

    @Test
    @Throws(Exception::class)
    fun saveRemindTimeout() {
        var setting = settingFinderService.findSetting()
        assertEquals(true, setting.useReminder())
        assertEquals(RemindIntervalType(RemindIntervalType.RemindIntervalPattern.MINUTE_5), setting.remindInterval)
        assertEquals(RemindTimeoutType(RemindTimeoutType.RemindTimeoutPattern.HOUR_24), setting.remindTimeout)

        // updateSetting
        settingSaveService.save(oldSetting = setting, remindTimeout = RemindTimeoutType(RemindTimeoutType.RemindTimeoutPattern.HOUR_1))

        // select update data
        setting = settingFinderService.findSetting()
        assertEquals(true, setting.useReminder())
        assertEquals(RemindIntervalType(RemindIntervalType.RemindIntervalPattern.MINUTE_5), setting.remindInterval)
        assertEquals(RemindTimeoutType(RemindTimeoutType.RemindTimeoutPattern.HOUR_1), setting.remindTimeout)
    }
}
