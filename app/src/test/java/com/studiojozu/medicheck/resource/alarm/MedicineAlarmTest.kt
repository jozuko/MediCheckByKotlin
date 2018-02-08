package com.studiojozu.medicheck.resource.alarm

import android.annotation.TargetApi
import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.studiojozu.common.domain.model.CalendarNoSecond
import com.studiojozu.medicheck.R
import com.studiojozu.medicheck.application.AlarmScheduleService
import com.studiojozu.medicheck.di.MediCheckTestApplication
import com.studiojozu.medicheck.di.module.ServiceTestModule
import com.studiojozu.medicheck.domain.model.alarm.AlarmSchedule
import com.studiojozu.medicheck.domain.model.medicine.*
import com.studiojozu.medicheck.domain.model.person.Person
import com.studiojozu.medicheck.domain.model.person.PersonDisplayOrderType
import com.studiojozu.medicheck.domain.model.person.PersonIdType
import com.studiojozu.medicheck.domain.model.person.PersonNameType
import com.studiojozu.medicheck.domain.model.schedule.Schedule
import com.studiojozu.medicheck.domain.model.schedule.ScheduleIsTakeType
import com.studiojozu.medicheck.domain.model.schedule.ScheduleNeedAlarmType
import com.studiojozu.medicheck.domain.model.schedule.SchedulePlanDateType
import com.studiojozu.medicheck.domain.model.setting.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.Shadows
import org.robolectric.annotation.Config
import javax.inject.Inject

@RunWith(RobolectricTestRunner::class)
@Config(manifest = "src/main/AndroidManifest.xml", application = MediCheckTestApplication::class)
class MedicineAlarmTest : ATestParent() {

    @Inject
    lateinit var alarmScheduleService: AlarmScheduleService

    @Before
    fun setUp() = (RuntimeEnvironment.application as MediCheckTestApplication).mAppTestComponent.inject(this)

    @Test
    @Throws(Exception::class)
    @TargetApi(Build.VERSION_CODES.M)
    @Config(sdk = intArrayOf(Build.VERSION_CODES.M))
    @Suppress("DEPRECATION")
    fun showNotification() {
        // create mock
        val application = RuntimeEnvironment.application as MediCheckTestApplication
        val resources = application.resources
        val serviceModule = ServiceTestModule()

        val alarmSchedules: List<AlarmSchedule> = createNeedAlarmScheduleList()
        val alarmServiceModuleMock = Mockito.spy(alarmScheduleService)
        Mockito.`when`(alarmServiceModuleMock.getNeedAlarmSchedules()).thenReturn(alarmSchedules)

        serviceModule.mAlarmScheduleService = alarmServiceModuleMock
        application.mServiceModule = serviceModule

        // create shadow
        val shadowNotificationManager = Shadows.shadowOf(RuntimeEnvironment.application.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)

        // before method call
        assertTrue(shadowNotificationManager.allNotifications.isEmpty())

        // test
        val targetService = MedicineAlarm(RuntimeEnvironment.application)
        targetService.showNotification()

        // after method call
        assertEquals(1, shadowNotificationManager.allNotifications.size)

        val actualNotification = shadowNotificationManager.allNotifications[0]
        val shadowNotification = Shadows.shadowOf(actualNotification)

        assertEquals(resources.getDrawable(R.mipmap.notification_icon), actualNotification.smallIcon.loadDrawable(application))
        assertNotNull(actualNotification.getLargeIcon())
        assertEquals(resources.getString(R.string.notification_medicine_title), actualNotification.tickerText)
        assertEquals(resources.getString(R.string.notification_medicine_title), shadowNotification.contentTitle)
        assertEquals("自分 メルカゾール\n自分 チラーヂン", shadowNotification.contentText)
        assertTrue(actualNotification.defaults and Notification.DEFAULT_LIGHTS == Notification.DEFAULT_LIGHTS)
        assertTrue(actualNotification.defaults and Notification.DEFAULT_SOUND == Notification.DEFAULT_SOUND)
        assertTrue(actualNotification.defaults and Notification.DEFAULT_VIBRATE == Notification.DEFAULT_VIBRATE)
    }

    @Test
    @Throws(Exception::class)
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Config(sdk = intArrayOf(Build.VERSION_CODES.JELLY_BEAN))
    @Suppress("DEPRECATION")
    fun showNotificationICSMR1() {
        // create mock
        val application = RuntimeEnvironment.application as MediCheckTestApplication
        val resources = application.resources
        val serviceModule = ServiceTestModule()

        val alarmSchedules: List<AlarmSchedule> = createNeedAlarmScheduleList()
        val alarmServiceModuleMock = Mockito.spy(alarmScheduleService)
        Mockito.`when`(alarmServiceModuleMock.getNeedAlarmSchedules()).thenReturn(alarmSchedules)

        serviceModule.mAlarmScheduleService = alarmServiceModuleMock
        application.mServiceModule = serviceModule

        // create shadow
        val shadowNotificationManager = Shadows.shadowOf(RuntimeEnvironment.application.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)

        // before method call
        assertTrue(shadowNotificationManager.allNotifications.isEmpty())

        // test
        val targetService = MedicineAlarm(RuntimeEnvironment.application)
        targetService.showNotification()

        // after method call
        assertEquals(1, shadowNotificationManager.allNotifications.size)

        val actualNotification = shadowNotificationManager.allNotifications[0]
        val shadowNotification = Shadows.shadowOf(actualNotification)

        assertEquals(R.mipmap.notification_icon, actualNotification.icon)
        assertNotNull(actualNotification.largeIcon)
        assertEquals(resources.getString(R.string.notification_medicine_title), actualNotification.tickerText)
        assertEquals(resources.getString(R.string.notification_medicine_title), shadowNotification.contentTitle)
        assertEquals("自分 メルカゾール\n自分 チラーヂン", shadowNotification.contentText)
        assertTrue(actualNotification.defaults and Notification.DEFAULT_LIGHTS == Notification.DEFAULT_LIGHTS)
        assertTrue(actualNotification.defaults and Notification.DEFAULT_SOUND == Notification.DEFAULT_SOUND)
        assertTrue(actualNotification.defaults and Notification.DEFAULT_VIBRATE == Notification.DEFAULT_VIBRATE)
    }

    private val person1 = Person(
            personId = PersonIdType("person01"),
            personName = PersonNameType("自分"),
            personDisplayOrder = PersonDisplayOrderType(10))

    private val medicineUnit1 = MedicineUnit(
            medicineUnitId = MedicineUnitIdType("unit01"),
            medicineUnitValue = MedicineUnitValueType(),
            medicineUnitDisplayOrder = MedicineUnitDisplayOrderType(2))

    private val medicine1 = Medicine(
            medicineId = MedicineIdType("medicine01"),
            medicineName = MedicineNameType("メルカゾール"),
            medicineUnit = medicineUnit1)

    private val medicine2 = Medicine(
            medicineId = MedicineIdType("medicine02"),
            medicineName = MedicineNameType("チラーヂン"),
            medicineUnit = medicineUnit1)

    private val timetable1 = Timetable(
            timetableId = TimetableIdType("timetable01"),
            timetableName = TimetableNameType("朝"),
            timetableTime = TimetableTimeType(CalendarNoSecond().calendar),
            timetableDisplayOrder = TimetableDisplayOrderType(20))

    private val schedule1 = Schedule(
            medicineId = medicine1.medicineId,
            timetableId = timetable1.timetableId,
            schedulePlanDate = SchedulePlanDateType(CalendarNoSecond().calendar),
            scheduleNeedAlarm = ScheduleNeedAlarmType(true),
            scheduleIsTake = ScheduleIsTakeType(false))

    private val schedule4 = schedule1.copy(medicineId = medicine2.medicineId)

    private fun createNeedAlarmScheduleList(): List<AlarmSchedule> {
        val alarmSchedule1 = AlarmSchedule(
                schedule = schedule1,
                timetable = timetable1,
                medicine = medicine1,
                person = person1)

        val alarmSchedule2 = AlarmSchedule(
                schedule = schedule4,
                timetable = timetable1,
                medicine = medicine2,
                person = person1)

        return listOf(alarmSchedule1, alarmSchedule2)
    }
}