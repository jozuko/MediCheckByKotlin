package com.studiojozu.medicheck.application

import android.app.AlarmManager
import android.content.Context
import android.os.Build
import com.studiojozu.common.domain.model.CalendarNoSecond
import com.studiojozu.medicheck.di.MediCheckTestApplication
import com.studiojozu.medicheck.domain.model.setting.ATestParent
import com.studiojozu.medicheck.resource.alarm.AlarmBroadcastReceiver
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.Shadows.shadowOf
import org.robolectric.annotation.Config
import java.util.*
import javax.inject.Inject

@RunWith(RobolectricTestRunner::class)
@Config(manifest = "src/main/AndroidManifest.xml", application = MediCheckTestApplication::class)
class AlarmServiceTest : ATestParent() {
    @Inject
    lateinit var alarmService: AlarmService

    @Before
    fun setUp() = (RuntimeEnvironment.application as MediCheckTestApplication).mAppTestComponent.inject(this)

    @Test
    @Throws(Exception::class)
    @Config(sdk = intArrayOf(Build.VERSION_CODES.LOLLIPOP_MR1))
    fun resetAlarmLollipop() = resetAlarmTest()

    @Test
    @Throws(Exception::class)
    @Config(sdk = intArrayOf(Build.VERSION_CODES.M))
    fun resetAlarmMarshmallow() = resetAlarmTest()

    private fun resetAlarmTest() {
        val alarmManager = RuntimeEnvironment.application.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val shadowAlarmManager = shadowOf(alarmManager)

        // before method call
        assertNull(shadowAlarmManager.nextScheduledAlarm)

        // target method call
        alarmService.resetAlarm()
        val expectTriggerAtTime = CalendarNoSecond().calendar
        expectTriggerAtTime.add(Calendar.MINUTE, 1)

        // after method call
        assertEquals(1, shadowAlarmManager.scheduledAlarms.size)

        val scheduledAlarm = shadowAlarmManager.nextScheduledAlarm
        assertNotNull(scheduledAlarm)
        assertEquals(0, scheduledAlarm.interval)
        assertEquals(AlarmManager.RTC_WAKEUP, scheduledAlarm.type)
        assertEquals(expectTriggerAtTime.timeInMillis, scheduledAlarm.triggerAtTime)

        val actualPendingIntent = shadowOf(scheduledAlarm.operation)
        assertNotNull(actualPendingIntent)
        assertTrue(actualPendingIntent.isBroadcastIntent)
        assertEquals(1, actualPendingIntent.requestCode)
        assertEquals(0, actualPendingIntent.flags)

        val actualIntent = shadowOf(actualPendingIntent.savedIntent)
        assertNotNull(actualIntent)
        assertEquals(AlarmBroadcastReceiver::class.java, actualIntent.intentClass)

        // target method call again
        alarmService.resetAlarm()

        // size not increment
        assertEquals(1, shadowAlarmManager.scheduledAlarms.size)
    }
}