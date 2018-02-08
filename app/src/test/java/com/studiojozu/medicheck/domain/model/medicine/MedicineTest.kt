package com.studiojozu.medicheck.domain.model.medicine

import android.annotation.SuppressLint
import com.studiojozu.medicheck.domain.model.setting.*
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Suppress("FunctionName")
@RunWith(RobolectricTestRunner::class)
@Config(manifest = "src/main/AndroidManifest.xml")
class MedicineTest : ATestParent() {
    private val timetable1 = Timetable(
            timetableId = TimetableIdType("time0001"),
            timetableName = TimetableNameType("朝"),
            timetableTime = TimetableTimeType(7, 0),
            timetableDisplayOrder = TimetableDisplayOrderType(1))

    private val timetable2 = Timetable(
            timetableId = TimetableIdType("time0002"),
            timetableName = TimetableNameType("昼"),
            timetableTime = TimetableTimeType(12, 30),
            timetableDisplayOrder = TimetableDisplayOrderType(2))

    private val timetable3 = Timetable(
            timetableId = TimetableIdType("time0003"),
            timetableName = TimetableNameType("夜"),
            timetableTime = TimetableTimeType(19, 0),
            timetableDisplayOrder = TimetableDisplayOrderType(3))

    @Test
    @Throws(Exception::class)
    fun constructor_NoParameter() {
        val entity = Medicine()
        assertNotNull(entity.medicineId.dbValue)
        assertNotSame("", entity.medicineId.dbValue)
        assertEquals("", entity.medicineName.dbValue)
        assertEquals("0", entity.medicineTakeNumber.dbValue)
        assertEquals("", entity.medicineUnit.displayValue)
        assertEquals(0, entity.medicineDateNumber.dbValue)
        assertTrue(0 < entity.medicineStartDatetime.dbValue)
        assertEquals(0, entity.medicineInterval.dbValue)
        assertEquals(0, entity.medicineIntervalMode.dbValue)
        assertEquals("", entity.medicinePhoto.dbValue)
        assertEquals(true, entity.medicineNeedAlarm.isTrue)
        assertEquals(false, entity.medicineDeleteFlag.isTrue)
        assertEquals(0, entity.timetableList.count)

        assertNotNull(entity.medicineUnitId.dbValue)
        assertNotSame("", entity.medicineUnitId.dbValue)

        assertEquals(false, entity.isOneShowMedicine)
    }

    @SuppressLint("SdCardPath")
    @Test
    @Throws(Exception::class)
    fun constructor_WithParameter() {
        var entity = Medicine(medicineId = MedicineIdType("1234"))
        assertEquals("1234", entity.medicineId.dbValue)

        entity = Medicine(medicineName = MedicineNameType("test"))
        assertEquals("test", entity.medicineName.dbValue)

        entity = Medicine(medicineTakeNumber = MedicineTakeNumberType("1"))
        assertEquals("1", entity.medicineTakeNumber.dbValue)

        entity = Medicine(
                medicineUnit = MedicineUnit(medicineUnitId = MedicineUnitIdType("5678"),
                        medicineUnitValue = MedicineUnitValueType("錠"),
                        medicineUnitDisplayOrder = MedicineUnitDisplayOrderType(1)))
        assertEquals("5678", entity.medicineUnit.medicineUnitId.dbValue)
        assertEquals("錠", entity.medicineUnit.medicineUnitValue.dbValue)
        assertEquals(1L, entity.medicineUnit.medicineUnitDisplayOrder.dbValue)

        entity = Medicine(medicineDateNumber = MedicineDateNumberType(1))
        assertEquals(1L, entity.medicineDateNumber.dbValue)

        entity = Medicine(medicineStartDatetime = MedicineStartDatetimeType(2017, 1, 2, 3, 4))
        assertEquals("17/01/02 3:04", entity.medicineStartDatetime.displayValue)

        entity = Medicine(medicineInterval = MedicineIntervalType(1))
        assertEquals(1L, entity.medicineInterval.dbValue)

        entity = Medicine(medicineIntervalMode = MedicineIntervalModeType(MedicineIntervalModeType.DateIntervalPattern.MONTH))
        assertEquals(false, entity.medicineIntervalMode.isDays)

        entity = Medicine(medicinePhoto = MedicinePhotoType("/sdcard/CAMERA/hogehoge.jpg"))
        assertEquals("/sdcard/CAMERA/hogehoge.jpg", entity.medicinePhoto.dbValue)

        entity = Medicine(medicineNeedAlarm = MedicineNeedAlarmType(false))
        assertEquals(false, entity.medicineNeedAlarm.isTrue)

        entity = Medicine(medicineDeleteFlag = MedicineDeleteFlagType(true))
        assertEquals(true, entity.medicineDeleteFlag.isTrue)

        val timetableList = MedicineTimetableList()
        timetableList.setTimetableList(mutableListOf(timetable1, timetable2, timetable3))

        entity = Medicine(timetableList = timetableList)
        assertEquals(3, entity.timetableList.count)
        assertEquals(timetable1, entity.timetableList.elementAt(0))
        assertEquals(timetable2, entity.timetableList.elementAt(1))
        assertEquals(timetable3, entity.timetableList.elementAt(2))
    }

    @Test
    @Throws(Exception::class)
    fun setOneShotMedicine() {
        val entity = Medicine()
        entity.setOneShotMedicine(true)
        assertTrue(entity.isOneShowMedicine)

        entity.setOneShotMedicine(false)
        assertFalse(entity.isOneShowMedicine)
    }

    @Test
    @Throws(Exception::class)
    fun medicineUnitId() {
        val entity = Medicine(
                medicineUnit = MedicineUnit(medicineUnitId = MedicineUnitIdType("5678"),
                        medicineUnitValue = MedicineUnitValueType("錠"),
                        medicineUnitDisplayOrder = MedicineUnitDisplayOrderType(1)))
        assertEquals("5678", entity.medicineUnitId.dbValue)
    }
}