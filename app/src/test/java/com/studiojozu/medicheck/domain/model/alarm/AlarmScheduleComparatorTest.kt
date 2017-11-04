package com.studiojozu.medicheck.domain.model.alarm

import com.studiojozu.medicheck.domain.model.medicine.Medicine
import com.studiojozu.medicheck.domain.model.medicine.MedicineIdType
import com.studiojozu.medicheck.domain.model.person.Person
import com.studiojozu.medicheck.domain.model.schedule.PlanDateType
import com.studiojozu.medicheck.domain.model.schedule.Schedule
import com.studiojozu.medicheck.domain.model.setting.ATestParent
import com.studiojozu.medicheck.domain.model.setting.Timetable
import com.studiojozu.medicheck.domain.model.setting.TimetableIdType
import com.studiojozu.medicheck.domain.model.setting.TimetableTimeType
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.powermock.core.classloader.annotations.PowerMockIgnore
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Suppress("FunctionName")
@RunWith(RobolectricTestRunner::class)
@Config(manifest = "src/main/AndroidManifest.xml")
@PowerMockIgnore("org.mockito.*", "org.robolectric.*", "android.*")
class AlarmScheduleComparatorTest : ATestParent() {

    @Test
    @Throws(Exception::class)
    fun compare_Same() {
        val comparator = AlarmScheduleComparator()

        val schedule1 = Schedule(mPlanDate = PlanDateType(2017, 1, 2), mMedicineId = MedicineIdType(), mTimetableId = TimetableIdType())
        val timetable1 = Timetable(mTimetableTime = TimetableTimeType(3, 4))
        val entity1 = AlarmSchedule(mSchedule = schedule1, mTimetable = timetable1, mMedicine = Medicine(), mPerson = Person())

        val schedule2 = schedule1.copy()
        val timetable2 = timetable1.copy()
        val entity2 = AlarmSchedule(mSchedule = schedule2, mTimetable = timetable2, mMedicine = Medicine(), mPerson = Person())

        Assert.assertTrue(comparator.compare(entity1, entity2) == 0)
    }

    @Test
    @Throws(Exception::class)
    fun compare_DiffTime() {
        val comparator = AlarmScheduleComparator()

        val schedule1 = Schedule(mPlanDate = PlanDateType(2017, 1, 2), mMedicineId = MedicineIdType(), mTimetableId = TimetableIdType())
        val timetable1 = Timetable(mTimetableTime = TimetableTimeType(3, 4))
        val entity1 = AlarmSchedule(mSchedule = schedule1, mTimetable = timetable1, mMedicine = Medicine(), mPerson = Person())

        val schedule2 = schedule1.copy()
        val timetable2 = Timetable(mTimetableTime = TimetableTimeType(15, 4))
        val entity2 = AlarmSchedule(mSchedule = schedule2, mTimetable = timetable2, mMedicine = Medicine(), mPerson = Person())

        Assert.assertTrue(comparator.compare(entity1, entity2) < 0)
        Assert.assertTrue(comparator.compare(entity2, entity1) > 0)
    }

    @Test
    @Throws(Exception::class)
    fun compare_DiffDate() {
        val comparator = AlarmScheduleComparator()

        val schedule1 = Schedule(mPlanDate = PlanDateType(2017, 1, 2), mMedicineId = MedicineIdType(), mTimetableId = TimetableIdType())
        val timetable1 = Timetable(mTimetableTime = TimetableTimeType(3, 4))
        val entity1 = AlarmSchedule(mSchedule = schedule1, mTimetable = timetable1, mMedicine = Medicine(), mPerson = Person())

        val schedule2 = Schedule(mPlanDate = PlanDateType(2017, 1, 3), mMedicineId = MedicineIdType(), mTimetableId = TimetableIdType())
        val timetable2 = timetable1.copy()
        val entity2 = AlarmSchedule(mSchedule = schedule2, mTimetable = timetable2, mMedicine = Medicine(), mPerson = Person())

        Assert.assertTrue(comparator.compare(entity1, entity2) < 0)
        Assert.assertTrue(comparator.compare(entity2, entity1) > 0)
    }
}