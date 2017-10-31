package com.studiojozu.medicheck.domain.model.schedule

import com.studiojozu.medicheck.domain.model.medicine.MedicineIdType
import com.studiojozu.medicheck.domain.model.setting.ATestParent
import com.studiojozu.medicheck.domain.model.setting.TimetableIdType
import junit.framework.Assert.*
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
class ScheduleTest : ATestParent() {

    @Test
    @Throws(Exception::class)
    fun constructor_requireParameterOnly() {
        val now = Calendar.getInstance()
        val year = String.format("%02d", now.get(Calendar.YEAR) % 2000)
        val month = String.format("%02d", now.get(Calendar.MONTH) + 1)
        val day = String.format("%02d", now.get(Calendar.DAY_OF_MONTH))
        val hour = String.format("%d", now.get(Calendar.HOUR_OF_DAY))
        val minute = String.format("%02d", now.get(Calendar.MINUTE))

        val entity = Schedule(
                mMedicineId = MedicineIdType("1234"),
                mPlanDate = PlanDateType(2017, 1, 2),
                mTimetableId = TimetableIdType("4567"))
        assertEquals("1234", entity.mMedicineId.dbValue)
        assertEquals("17/01/02", entity.mPlanDate.displayValue)
        assertEquals("4567", entity.mTimetableId.dbValue)
        assertTrue(entity.mNeedAlarm.isTrue)
        assertFalse(entity.mIsTake.isTrue)
        assertEquals("$year/$month/$day $hour:$minute", entity.mTookDatetime.displayValue)
    }

    @Test
    @Throws(Exception::class)
    fun constructor_withParameter() {
        val entity = Schedule(
                mMedicineId = MedicineIdType("1234"),
                mPlanDate = PlanDateType(2017, 1, 2),
                mTimetableId = TimetableIdType("4567"),
                mNeedAlarm = ScheduleNeedAlarmType(false),
                mIsTake = IsTakeType(true),
                mTookDatetime = TookDatetimeType(2017, 1, 2, 3, 4)
        )
        assertEquals("1234", entity.mMedicineId.dbValue)
        assertEquals("17/01/02", entity.mPlanDate.displayValue)
        assertEquals("4567", entity.mTimetableId.dbValue)
        assertFalse(entity.mNeedAlarm.isTrue)
        assertTrue(entity.mIsTake.isTrue)
        assertEquals("17/01/02 3:04", entity.mTookDatetime.displayValue)
    }
}