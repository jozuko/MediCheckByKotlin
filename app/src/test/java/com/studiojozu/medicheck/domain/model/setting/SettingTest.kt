package com.studiojozu.medicheck.domain.model.setting

import com.studiojozu.common.domain.model.general.TestDateType
import com.studiojozu.common.domain.model.general.TestTimeType
import junit.framework.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.powermock.core.classloader.annotations.PowerMockIgnore
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import java.util.*

@Suppress("FunctionName")
@RunWith(RobolectricTestRunner::class)
@Config(manifest = "src/main/AndroidManifest.xml")
@PowerMockIgnore("org.mockito.*", "org.robolectric.*", "android.*")
class SettingTest : ATestParent() {

    private val mUseReminderProperty = findProperty(Setting::class, "mUseReminder")
    private fun getUseReminder(entity: Setting): UseReminderType = mUseReminderProperty.call(entity) as UseReminderType

    private val mRemindIntervalProperty = findProperty(Setting::class, "mRemindInterval")
    private fun getRemindInterval(entity: Setting): RemindIntervalType = mRemindIntervalProperty.call(entity) as RemindIntervalType

    private val mRemindTimeoutProperty = findProperty(Setting::class, "mRemindTimeout")
    private fun getRemindTimeout(entity: Setting): RemindTimeoutType = mRemindTimeoutProperty.call(entity) as RemindTimeoutType

    @Test
    @Throws(Exception::class)
    fun constructor_NoParameter() {
        val settingEntity = Setting()
        assertEquals(true, getUseReminder(settingEntity).isTrue)
        assertEquals(5, getRemindInterval(settingEntity).dbValue)
        assertEquals(60 * 24, getRemindTimeout(settingEntity).dbValue)
    }

    @Test
    @Throws(Exception::class)
    fun constructor_WithParameter() {
        var settingEntity = Setting(mUseReminder = UseReminderType(false))
        assertEquals(false, getUseReminder(settingEntity).isTrue)
        assertEquals(5, getRemindInterval(settingEntity).dbValue)
        assertEquals(60 * 24, getRemindTimeout(settingEntity).dbValue)

        settingEntity = Setting(mRemindInterval = RemindIntervalType(RemindIntervalType.RemindIntervalPattern.MINUTE_30))
        assertEquals(true, getUseReminder(settingEntity).isTrue)
        assertEquals(30, getRemindInterval(settingEntity).dbValue)
        assertEquals(60 * 24, getRemindTimeout(settingEntity).dbValue)

        settingEntity = Setting(mRemindTimeout = RemindTimeoutType(RemindTimeoutType.RemindTimeoutPattern.MINUTE_30))
        assertEquals(true, getUseReminder(settingEntity).isTrue)
        assertEquals(5, getRemindInterval(settingEntity).dbValue)
        assertEquals(30, getRemindTimeout(settingEntity).dbValue)
    }

    @Test
    @Throws(Exception::class)
    fun setUseReminder() {
        val settingEntity = Setting(mUseReminder = UseReminderType(false))
        assertEquals(false, getUseReminder(settingEntity).isTrue)

        settingEntity.setUseReminder(true)
        assertEquals(true, getUseReminder(settingEntity).isTrue)
        assertEquals(5, getRemindInterval(settingEntity).dbValue)
        assertEquals(60 * 24, getRemindTimeout(settingEntity).dbValue)
    }

    @Test
    @Throws(Exception::class)
    fun setRemindInterval() {
        val settingEntity = Setting(mRemindInterval = RemindIntervalType(RemindIntervalType.RemindIntervalPattern.MINUTE_30))
        assertEquals(30, getRemindInterval(settingEntity).dbValue)

        settingEntity.setRemindInterval(60)
        assertEquals(true, getUseReminder(settingEntity).isTrue)
        assertEquals(60, getRemindInterval(settingEntity).dbValue)
        assertEquals(60 * 24, getRemindTimeout(settingEntity).dbValue)
    }

    @Test
    @Throws(Exception::class)
    fun setRemindTimeout() {
        val settingEntity = Setting(mRemindTimeout = RemindTimeoutType(RemindTimeoutType.RemindTimeoutPattern.MINUTE_30))
        assertEquals(30, getRemindTimeout(settingEntity).dbValue)

        settingEntity.setRemindTimeout(60)
        assertEquals(true, getUseReminder(settingEntity).isTrue)
        assertEquals(5, getRemindInterval(settingEntity).dbValue)
        assertEquals(60, getRemindTimeout(settingEntity).dbValue)
    }

    @Test
    @Throws(Exception::class)
    fun useReminder() {
        val settingEntity = Setting(mUseReminder = UseReminderType(false))
        assertEquals(false, settingEntity.useReminder())

        settingEntity.setUseReminder(true)
        assertEquals(true, settingEntity.useReminder())
    }

    @Test
    @Throws(Exception::class)
    fun isRemindTimeout() {
        val now = Calendar.getInstance()
        now.set(2017, 0, 2, 3, 4, 0)
        now.set(Calendar.MILLISECOND, 0)

        val dateType = TestDateType(now)
        val timeType = TestTimeType(now)
        val model = Setting(mRemindTimeout = RemindTimeoutType(RemindTimeoutType.RemindTimeoutPattern.MINUTE_1))

        // same time
        assertFalse(model.isRemindTimeout(now, dateType, timeType))

        // max range(1 minute)
        now.set(2017, 0, 2, 3, 5, 0)
        assertFalse(model.isRemindTimeout(now, dateType, timeType))

        // out of max range(1 minute) time(1 minute over)
        now.set(2017, 0, 2, 3, 6, 0)
        assertTrue(model.isRemindTimeout(now, dateType, timeType))
    }

    @Test
    @Throws(Exception::class)
    fun isRemindTiming() {
        val now = Calendar.getInstance()
        now.set(2017, 0, 2, 3, 5, 0)
        now.set(Calendar.MILLISECOND, 0)

        val dateType = TestDateType(now)
        val timeType = TestTimeType(now)
        val model = Setting(mRemindInterval = RemindIntervalType(RemindIntervalType.RemindIntervalPattern.MINUTE_5))

        // -5 minute
        now.set(2017, 0, 2, 3, 0, 0)
        assertFalse(model.isRemindTiming(now, dateType, timeType))

        // same time
        now.set(2017, 0, 2, 3, 5, 0)
        assertFalse(model.isRemindTiming(now, dateType, timeType))

        // +5 minute
        now.set(2017, 0, 2, 3, 10, 0)
        assertTrue(model.isRemindTiming(now, dateType, timeType))

        // +8 minute
        now.set(2017, 0, 2, 3, 13, 0)
        assertFalse(model.isRemindTiming(now, dateType, timeType))

        // +10 minute
        now.set(2017, 0, 2, 3, 15, 0)
        assertTrue(model.isRemindTiming(now, dateType, timeType))
    }

    @Test
    @Throws(Exception::class)
    @Config(qualifiers = "ja")
    fun getRemindTimeoutMap() {
        val model = Setting()
        val valueMap = model.getRemindTimeoutMap(RuntimeEnvironment.application.applicationContext)

        val expectMap = TreeMap<Int, String>()
        expectMap.put(1, "1分")
        expectMap.put(5, "5分")
        expectMap.put(10, "10分")
        expectMap.put(15, "15分")
        expectMap.put(30, "30分")
        expectMap.put(1 * 60, "1時間")
        expectMap.put(2 * 60, "2時間")
        expectMap.put(6 * 60, "6時間")
        expectMap.put(9 * 60, "9時間")
        expectMap.put(12 * 60, "12時間")
        expectMap.put(24 * 60, "24時間")

        valueMap.entries.forEach { (k, v) ->
            assertEquals(expectMap[k], v)
        }
    }

    @Test
    @Throws(Exception::class)
    @Config(qualifiers = "ja")
    fun getRemindIntervalMap() {
        val model = Setting()
        val valueMap = model.getRemindIntervalMap(RuntimeEnvironment.application.applicationContext)

        val expectMap = TreeMap<Int, String>()
        expectMap.put(1, "1分")
        expectMap.put(5, "5分")
        expectMap.put(10, "10分")
        expectMap.put(15, "15分")
        expectMap.put(30, "30分")
        expectMap.put(60, "1時間")

        valueMap.entries.forEach { (k, v) ->
            assertEquals(expectMap[k], v)
        }
    }
}