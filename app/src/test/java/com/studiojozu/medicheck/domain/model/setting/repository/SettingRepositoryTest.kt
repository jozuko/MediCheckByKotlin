package com.studiojozu.medicheck.domain.model.setting.repository

import com.studiojozu.medicheck.di.MediCheckTestApplication
import com.studiojozu.medicheck.domain.model.setting.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.powermock.core.classloader.annotations.PowerMockIgnore
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import javax.inject.Inject

@RunWith(RobolectricTestRunner::class)
@Config(manifest = "src/main/AndroidManifest.xml", application = MediCheckTestApplication::class)
@PowerMockIgnore("org.mockito.*", "org.robolectric.*", "android.*")
class SettingRepositoryTest : ATestParent() {

    @Inject
    lateinit var settingRepository: SettingRepository

    @Before
    fun setUp() = (RuntimeEnvironment.application as MediCheckTestApplication).mAppTestComponent.inject(this)

    @Test
    @Throws(Exception::class)
    fun crud() {
        // select init data
        var setting = settingRepository.find()
        assertEquals(true, setting.useReminder())
        assertEquals(RemindIntervalType(RemindIntervalType.RemindIntervalPattern.MINUTE_5), setting.mRemindInterval)
        assertEquals(RemindTimeoutType(RemindTimeoutType.RemindTimeoutPattern.HOUR_24), setting.mRemindTimeout)

        // updateSetting
        val updateSetting = Setting(
                mUseReminder = UseReminderType(false),
                mRemindInterval = RemindIntervalType(RemindIntervalType.RemindIntervalPattern.HOUR_1),
                mRemindTimeout = RemindTimeoutType(RemindTimeoutType.RemindTimeoutPattern.MINUTE_10))
        settingRepository.insert(updateSetting)

        // select update data
        setting = settingRepository.find()
        assertEquals(false, setting.useReminder())
        assertEquals(RemindIntervalType(RemindIntervalType.RemindIntervalPattern.HOUR_1), setting.mRemindInterval)
        assertEquals(RemindTimeoutType(RemindTimeoutType.RemindTimeoutPattern.MINUTE_10), setting.mRemindTimeout)

        // delete setting
        settingRepository.delete()
        setting = settingRepository.find()
        assertEquals(Setting().mUseReminder, setting.mUseReminder)
        assertEquals(Setting().mRemindInterval, setting.mRemindInterval)
        assertEquals(Setting().mRemindTimeout, setting.mRemindTimeout)
    }
}