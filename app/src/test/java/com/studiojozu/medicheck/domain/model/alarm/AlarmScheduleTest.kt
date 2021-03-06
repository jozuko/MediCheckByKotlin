package com.studiojozu.medicheck.domain.model.alarm

import com.studiojozu.medicheck.domain.model.medicine.Medicine
import com.studiojozu.medicheck.domain.model.medicine.MedicineIdType
import com.studiojozu.medicheck.domain.model.medicine.MedicineNameType
import com.studiojozu.medicheck.domain.model.person.Person
import com.studiojozu.medicheck.domain.model.person.PersonNameType
import com.studiojozu.medicheck.domain.model.schedule.Schedule
import com.studiojozu.medicheck.domain.model.schedule.SchedulePlanDateType
import com.studiojozu.medicheck.domain.model.setting.*
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.util.*

@Suppress("FunctionName")
@RunWith(RobolectricTestRunner::class)
@Config(manifest = "src/main/AndroidManifest.xml")
class AlarmScheduleTest : ATestParent() {

    @Test
    @Throws(Exception::class)
    fun medicineName() {
        val entity = AlarmSchedule(
                schedule = Schedule(medicineId = MedicineIdType(), schedulePlanDate = SchedulePlanDateType(), timetableId = TimetableIdType()),
                timetable = Timetable(),
                medicine = Medicine(medicineName = MedicineNameType("テスト薬名")),
                person = Person())

        assertEquals("テスト薬名", entity.medicineName)
    }

    @Test
    @Throws(Exception::class)
    fun personName() {
        val entity = AlarmSchedule(
                schedule = Schedule(medicineId = MedicineIdType(), schedulePlanDate = SchedulePlanDateType(), timetableId = TimetableIdType()),
                timetable = Timetable(),
                medicine = Medicine(medicineName = MedicineNameType("テスト薬名")),
                person = Person(personName = PersonNameType("サンプル さん")))

        assertEquals("サンプル さん", entity.personName)
    }

    @Test
    @Throws(Exception::class)
    fun isNeedAlarm_SameDateTime() {
        val now = Calendar.getInstance()
        now.set(2017, 0, 2, 3, 4, 0)
        now.set(Calendar.MILLISECOND, 0)

        val timetable = Timetable(timetableTime = TimetableTimeType(3, 4))
        val schedule = Schedule(
                medicineId = MedicineIdType(),
                schedulePlanDate = SchedulePlanDateType(2017, 1, 2),
                timetableId = timetable.timetableId)
        val entity = AlarmSchedule(
                schedule = schedule,
                timetable = timetable,
                medicine = Medicine(),
                person = Person())

        assertTrue(entity.isNeedAlarm(now, Setting(useReminder = UseReminderType(false))))
        assertTrue(entity.isNeedAlarm(now, Setting(useReminder = UseReminderType(true))))
    }

    @Test
    @Throws(Exception::class)
    fun isNeedAlarm_NoUseReminder() {
        val now = Calendar.getInstance()
        now.set(2017, 0, 2, 3, 4, 0)
        now.set(Calendar.MILLISECOND, 0)

        // nowより1時間早くする
        val timetable = Timetable(timetableTime = TimetableTimeType(2, 4))
        val schedule = Schedule(
                medicineId = MedicineIdType(),
                schedulePlanDate = SchedulePlanDateType(2017, 1, 2),
                timetableId = timetable.timetableId)
        val entity = AlarmSchedule(
                schedule = schedule,
                timetable = timetable,
                medicine = Medicine(),
                person = Person())

        assertFalse(entity.isNeedAlarm(now, Setting(useReminder = UseReminderType(false))))
    }

    @Test
    @Throws(Exception::class)
    fun isNeedAlarm_RemindTimeout() {
        val now = Calendar.getInstance()
        now.set(2017, 0, 2, 3, 4, 0)
        now.set(Calendar.MILLISECOND, 0)

        // nowより1時間早くする
        val timetable = Timetable(timetableTime = TimetableTimeType(2, 4))
        val schedule = Schedule(
                medicineId = MedicineIdType(),
                schedulePlanDate = SchedulePlanDateType(2017, 1, 2),
                timetableId = timetable.timetableId)
        val entity = AlarmSchedule(
                schedule = schedule,
                timetable = timetable,
                medicine = Medicine(),
                person = Person())

        val setting = Setting(
                useReminder = UseReminderType(true),
                remindTimeout = RemindTimeoutType(RemindTimeoutType.RemindTimeoutPattern.MINUTE_30))

        assertFalse(entity.isNeedAlarm(now, setting))
    }

    @Test
    @Throws(Exception::class)
    fun isNeedAlarm_NotRemindTiming() {
        val timetable = Timetable(timetableTime = TimetableTimeType(3, 4))
        val schedule = Schedule(
                medicineId = MedicineIdType(),
                schedulePlanDate = SchedulePlanDateType(2017, 1, 2),
                timetableId = timetable.timetableId)
        val entity = AlarmSchedule(
                schedule = schedule,
                timetable = timetable,
                medicine = Medicine(),
                person = Person())

        val setting = Setting(
                useReminder = UseReminderType(true),
                remindTimeout = RemindTimeoutType(RemindTimeoutType.RemindTimeoutPattern.HOUR_1),
                remindInterval = RemindIntervalType(RemindIntervalType.RemindIntervalPattern.MINUTE_5))

        val now = Calendar.getInstance()
        now.set(Calendar.MILLISECOND, 0)

        // スケジュール時刻 + 1分
        now.set(2017, 0, 2, 3, 5, 0)
        assertFalse(entity.isNeedAlarm(now, setting))

        // スケジュール時刻 + 3分
        now.set(2017, 0, 2, 3, 7, 0)
        assertFalse(entity.isNeedAlarm(now, setting))

        // スケジュール時刻 + 5分
        now.set(2017, 0, 2, 3, 9, 0)
        assertTrue(entity.isNeedAlarm(now, setting))

        // スケジュール時刻 + 55分
        now.set(2017, 0, 2, 3, 59, 0)
        assertTrue(entity.isNeedAlarm(now, setting))

        // スケジュール時刻 + 1時間
        now.set(2017, 0, 2, 4, 4, 0)
        assertTrue(entity.isNeedAlarm(now, setting))

        // スケジュール時刻 + 1時間5分
        now.set(2017, 0, 2, 4, 9, 0)
        assertFalse(entity.isNeedAlarm(now, setting))
    }

    @Test
    @Throws(Exception::class)
    fun compareDate() {
        val schedule1 = Schedule(schedulePlanDate = SchedulePlanDateType(2017, 1, 2), medicineId = MedicineIdType(), timetableId = TimetableIdType())
        val entity1 = AlarmSchedule(schedule = schedule1, timetable = Timetable(), medicine = Medicine(), person = Person())

        val schedule2 = schedule1.copy()
        val entity2 = AlarmSchedule(schedule = schedule2, timetable = Timetable(), medicine = Medicine(), person = Person())
        assertTrue(entity1.compareDate(entity2) == 0)

        val schedule3 = Schedule(schedulePlanDate = SchedulePlanDateType(2017, 1, 3), medicineId = MedicineIdType(), timetableId = TimetableIdType())
        val entity3 = AlarmSchedule(schedule = schedule3, timetable = Timetable(), medicine = Medicine(), person = Person())
        assertTrue(entity1.compareDate(entity3) < 0)
        assertTrue(entity3.compareDate(entity1) > 0)
    }

    @Test
    @Throws(Exception::class)
    fun compareTime() {
        val schedule = Schedule(schedulePlanDate = SchedulePlanDateType(2017, 1, 2), medicineId = MedicineIdType(), timetableId = TimetableIdType())

        val timetable1 = Timetable(timetableTime = TimetableTimeType(3, 4))
        val entity1 = AlarmSchedule(timetable = timetable1, schedule = schedule, medicine = Medicine(), person = Person())

        val timetable2 = timetable1.copy()
        val entity2 = AlarmSchedule(timetable = timetable2, schedule = schedule, medicine = Medicine(), person = Person())
        assertTrue(entity1.compareTime(entity2) == 0)

        val timetable3 = Timetable(timetableTime = TimetableTimeType(15, 4))
        val entity3 = AlarmSchedule(timetable = timetable3, schedule = schedule, medicine = Medicine(), person = Person())
        assertTrue(entity1.compareTime(entity3) < 0)
        assertTrue(entity3.compareTime(entity1) > 0)
    }
}