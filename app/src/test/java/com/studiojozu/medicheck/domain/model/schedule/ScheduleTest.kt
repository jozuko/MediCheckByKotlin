package com.studiojozu.medicheck.domain.model.schedule

import com.studiojozu.medicheck.domain.model.medicine.MedicineIdType
import com.studiojozu.medicheck.domain.model.setting.ATestParent
import com.studiojozu.medicheck.domain.model.setting.TimetableIdType
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.util.*

@Suppress("FunctionName")
@RunWith(RobolectricTestRunner::class)
@Config(manifest = "src/main/AndroidManifest.xml")
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
                medicineId = MedicineIdType("1234"),
                schedulePlanDate = SchedulePlanDateType(2017, 1, 2),
                timetableId = TimetableIdType("4567"))
        assertEquals("1234", entity.medicineId.dbValue)
        assertEquals("17/01/02", entity.schedulePlanDate.displayValue)
        assertEquals("4567", entity.timetableId.dbValue)
        assertTrue(entity.scheduleNeedAlarm.isTrue)
        assertFalse(entity.scheduleIsTake.isTrue)
        assertEquals("$year/$month/$day $hour:$minute", entity.scheduleTookDatetime.displayValue)
    }

    @Test
    @Throws(Exception::class)
    fun constructor_withParameter() {
        val entity = Schedule(
                medicineId = MedicineIdType("1234"),
                schedulePlanDate = SchedulePlanDateType(2017, 1, 2),
                timetableId = TimetableIdType("4567"),
                scheduleNeedAlarm = ScheduleNeedAlarmType(false),
                scheduleIsTake = ScheduleIsTakeType(true),
                scheduleTookDatetime = ScheduleTookDatetimeType(2017, 1, 2, 3, 4)
        )
        assertEquals("1234", entity.medicineId.dbValue)
        assertEquals("17/01/02", entity.schedulePlanDate.displayValue)
        assertEquals("4567", entity.timetableId.dbValue)
        assertFalse(entity.scheduleNeedAlarm.isTrue)
        assertTrue(entity.scheduleIsTake.isTrue)
        assertEquals("17/01/02 3:04", entity.scheduleTookDatetime.displayValue)
    }
}
