package com.studiojozu.medicheck.domain.model.alarm

import com.studiojozu.medicheck.domain.model.medicine.Medicine
import com.studiojozu.medicheck.domain.model.medicine.MedicineIdType
import com.studiojozu.medicheck.domain.model.medicine.MedicineNameType
import com.studiojozu.medicheck.domain.model.person.Person
import com.studiojozu.medicheck.domain.model.person.PersonNameType
import com.studiojozu.medicheck.domain.model.schedule.PlanDateType
import com.studiojozu.medicheck.domain.model.schedule.Schedule
import com.studiojozu.medicheck.domain.model.setting.*
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.powermock.core.classloader.annotations.PowerMockIgnore
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.util.*

@Suppress("FunctionName")
@RunWith(RobolectricTestRunner::class)
@Config(manifest = "src/main/AndroidManifest.xml")
@PowerMockIgnore("org.mockito.*", "org.robolectric.*", "android.*")
class AlarmScheduleTest : ATestParent() {

    @Test
    @Throws(Exception::class)
    fun medicineName() {
        val entity = AlarmSchedule(
                mSchedule = Schedule(mMedicineId = MedicineIdType(), mPlanDate = PlanDateType(), mTimetableId = TimetableIdType()),
                mTimetable = Timetable(),
                mMedicine = Medicine(mMedicineName = MedicineNameType("テスト薬名")),
                mPerson = Person())

        assertEquals("テスト薬名", entity.medicineName)
    }

    @Test
    @Throws(Exception::class)
    fun personName() {
        val entity = AlarmSchedule(
                mSchedule = Schedule(mMedicineId = MedicineIdType(), mPlanDate = PlanDateType(), mTimetableId = TimetableIdType()),
                mTimetable = Timetable(),
                mMedicine = Medicine(mMedicineName = MedicineNameType("テスト薬名")),
                mPerson = Person(mPersonName = PersonNameType("サンプル さん")))

        assertEquals("サンプル さん", entity.personName)
    }

    @Test
    @Throws(Exception::class)
    fun isNeedAlarm_SameDateTime() {
        val now = Calendar.getInstance()
        now.set(2017, 0, 2, 3, 4, 0)
        now.set(Calendar.MILLISECOND, 0)

        val timetable = Timetable(mTimetableTime = TimetableTimeType(3, 4))
        val schedule = Schedule(
                mMedicineId = MedicineIdType(),
                mPlanDate = PlanDateType(2017, 1, 2),
                mTimetableId = timetable.mTimetableId)
        val entity = AlarmSchedule(
                mSchedule = schedule,
                mTimetable = timetable,
                mMedicine = Medicine(),
                mPerson = Person())

        assertTrue(entity.isNeedAlarm(now, Setting(mUseReminder = UseReminderType(false))))
        assertTrue(entity.isNeedAlarm(now, Setting(mUseReminder = UseReminderType(true))))
    }

    @Test
    @Throws(Exception::class)
    fun isNeedAlarm_NoUseReminder() {
        val now = Calendar.getInstance()
        now.set(2017, 0, 2, 3, 4, 0)
        now.set(Calendar.MILLISECOND, 0)

        // nowより1時間早くする
        val timetable = Timetable(mTimetableTime = TimetableTimeType(2, 4))
        val schedule = Schedule(
                mMedicineId = MedicineIdType(),
                mPlanDate = PlanDateType(2017, 1, 2),
                mTimetableId = timetable.mTimetableId)
        val entity = AlarmSchedule(
                mSchedule = schedule,
                mTimetable = timetable,
                mMedicine = Medicine(),
                mPerson = Person())

        assertFalse(entity.isNeedAlarm(now, Setting(mUseReminder = UseReminderType(false))))
    }

    @Test
    @Throws(Exception::class)
    fun isNeedAlarm_RemindTimeout() {
        val now = Calendar.getInstance()
        now.set(2017, 0, 2, 3, 4, 0)
        now.set(Calendar.MILLISECOND, 0)

        // nowより1時間早くする
        val timetable = Timetable(mTimetableTime = TimetableTimeType(2, 4))
        val schedule = Schedule(
                mMedicineId = MedicineIdType(),
                mPlanDate = PlanDateType(2017, 1, 2),
                mTimetableId = timetable.mTimetableId)
        val entity = AlarmSchedule(
                mSchedule = schedule,
                mTimetable = timetable,
                mMedicine = Medicine(),
                mPerson = Person())

        val setting = Setting(
                mUseReminder = UseReminderType(true),
                mRemindTimeout = RemindTimeoutType(RemindTimeoutType.RemindTimeoutPattern.MINUTE_30))

        assertFalse(entity.isNeedAlarm(now, setting))
    }

    @Test
    @Throws(Exception::class)
    fun isNeedAlarm_NotRemindTiming() {
        val timetable = Timetable(mTimetableTime = TimetableTimeType(3, 4))
        val schedule = Schedule(
                mMedicineId = MedicineIdType(),
                mPlanDate = PlanDateType(2017, 1, 2),
                mTimetableId = timetable.mTimetableId)
        val entity = AlarmSchedule(
                mSchedule = schedule,
                mTimetable = timetable,
                mMedicine = Medicine(),
                mPerson = Person())

        val setting = Setting(
                mUseReminder = UseReminderType(true),
                mRemindTimeout = RemindTimeoutType(RemindTimeoutType.RemindTimeoutPattern.HOUR_1),
                mRemindInterval = RemindIntervalType(RemindIntervalType.RemindIntervalPattern.MINUTE_5))

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
        val schedule1 = Schedule(mPlanDate = PlanDateType(2017, 1, 2), mMedicineId = MedicineIdType(), mTimetableId = TimetableIdType())
        val entity1 = AlarmSchedule(mSchedule = schedule1, mTimetable = Timetable(), mMedicine = Medicine(), mPerson = Person())

        val schedule2 = schedule1.copy()
        val entity2 = AlarmSchedule(mSchedule = schedule2, mTimetable = Timetable(), mMedicine = Medicine(), mPerson = Person())
        assertTrue(entity1.compareDate(entity2) == 0)

        val schedule3 = Schedule(mPlanDate = PlanDateType(2017, 1, 3), mMedicineId = MedicineIdType(), mTimetableId = TimetableIdType())
        val entity3 = AlarmSchedule(mSchedule = schedule3, mTimetable = Timetable(), mMedicine = Medicine(), mPerson = Person())
        assertTrue(entity1.compareDate(entity3) < 0)
        assertTrue(entity3.compareDate(entity1) > 0)
    }

    @Test
    @Throws(Exception::class)
    fun compareTime() {
        val schedule = Schedule(mPlanDate = PlanDateType(2017, 1, 2), mMedicineId = MedicineIdType(), mTimetableId = TimetableIdType())

        val timetable1 = Timetable(mTimetableTime = TimetableTimeType(3, 4))
        val entity1 = AlarmSchedule(mTimetable = timetable1, mSchedule = schedule, mMedicine = Medicine(), mPerson = Person())

        val timetable2 = timetable1.copy()
        val entity2 = AlarmSchedule(mTimetable = timetable2, mSchedule = schedule, mMedicine = Medicine(), mPerson = Person())
        assertTrue(entity1.compareTime(entity2) == 0)

        val timetable3 = Timetable(mTimetableTime = TimetableTimeType(15, 4))
        val entity3 = AlarmSchedule(mTimetable = timetable3, mSchedule = schedule, mMedicine = Medicine(), mPerson = Person())
        assertTrue(entity1.compareTime(entity3) < 0)
        assertTrue(entity3.compareTime(entity1) > 0)
    }
}