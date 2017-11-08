package com.studiojozu.medicheck.infrastructure.persistence.dao

import com.studiojozu.medicheck.domain.model.setting.*
import com.studiojozu.medicheck.infrastructure.persistence.database.AppDatabase
import com.studiojozu.medicheck.infrastructure.persistence.entity.SqliteSetting
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
        var sqliteSetting = dao.find()!!
        assertEquals(true, sqliteSetting.mUseReminder.isTrue)
        assertEquals(RemindIntervalType(RemindIntervalType.RemindIntervalPattern.MINUTE_5), sqliteSetting.mRemindInterval)
        assertEquals(RemindTimeoutType(RemindTimeoutType.RemindTimeoutPattern.HOUR_24), sqliteSetting.mRemindTimeout)

        // update
        val updateSetting = setSqliteSetting(Setting(
                mUseReminder = UseReminderType(false),
                mRemindInterval = RemindIntervalType(RemindIntervalType.RemindIntervalPattern.MINUTE_30),
                mRemindTimeout = RemindTimeoutType(RemindTimeoutType.RemindTimeoutPattern.HOUR_12)
        ))
        dao.insert(updateSetting)
        sqliteSetting = dao.find()!!
        assertEquals(false, sqliteSetting.mUseReminder.isTrue)
        assertEquals(RemindIntervalType(RemindIntervalType.RemindIntervalPattern.MINUTE_30), sqliteSetting.mRemindInterval)
        assertEquals(RemindTimeoutType(RemindTimeoutType.RemindTimeoutPattern.HOUR_12), sqliteSetting.mRemindTimeout)

        // delete
        dao.delete()
        assertNull(dao.find())
    }

    private fun setSqliteSetting(entity: Setting) =
            SqliteSetting.build {
                mSetting = entity
            }
}