package com.studiojozu.medicheck.infrastructure.persistence.dao

import com.studiojozu.medicheck.domain.model.setting.ATestParent
import com.studiojozu.medicheck.domain.model.setting.RemindIntervalType
import com.studiojozu.medicheck.domain.model.setting.RemindTimeoutType
import com.studiojozu.medicheck.infrastructure.persistence.database.AppDatabase
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import org.junit.runner.RunWith
import org.powermock.core.classloader.annotations.PowerMockIgnore
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = "src/main/AndroidManifest.xml")
@PowerMockIgnore("org.mockito.*", "org.robolectric.*", "android.*")
class SqliteSettingRepositoryTest : ATestParent() {

    @Test
    @Throws(Exception::class)
    fun crud() {
        val database = AppDatabase.getAppDatabase(RuntimeEnvironment.application.applicationContext)
        val dao = database.settingDao()

        // select no data
        var setting = dao.find()!!
        assertEquals(true, setting.mUseReminder)
        assertEquals(RemindIntervalType(RemindIntervalType.RemindIntervalPattern.MINUTE_5).dbValue, setting.mRemindInterval)
        assertEquals(RemindTimeoutType(RemindTimeoutType.RemindTimeoutPattern.HOUR_24).dbValue, setting.mRemindTimeout)

        // update
        setting.mUseReminder = false
        setting.mRemindInterval = RemindIntervalType(RemindIntervalType.RemindIntervalPattern.MINUTE_30).dbValue
        setting.mRemindTimeout = RemindTimeoutType(RemindTimeoutType.RemindTimeoutPattern.HOUR_12).dbValue
        dao.insert(setting)
        setting = dao.find()!!
        assertEquals(false, setting.mUseReminder)
        assertEquals(RemindIntervalType(RemindIntervalType.RemindIntervalPattern.MINUTE_30).dbValue, setting.mRemindInterval)
        assertEquals(RemindTimeoutType(RemindTimeoutType.RemindTimeoutPattern.HOUR_12).dbValue, setting.mRemindTimeout)

        // delete
        dao.delete()
        assertNull(dao.find())
    }
}