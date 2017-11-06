package com.studiojozu.medicheck.domain.model.medicine

import android.annotation.SuppressLint
import com.studiojozu.medicheck.domain.model.setting.*
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.powermock.core.classloader.annotations.PowerMockIgnore
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Suppress("FunctionName")
@RunWith(RobolectricTestRunner::class)
@Config(manifest = "src/main/AndroidManifest.xml")
@PowerMockIgnore("org.mockito.*", "org.robolectric.*", "android.*")
class MedicineTest : ATestParent() {
    private val timetable1 = Timetable(
            mTimetableId = TimetableIdType("time0001"),
            mTimetableName = TimetableNameType("朝"),
            mTimetableTime = TimetableTimeType(7, 0),
            mTimetableDisplayOrder = TimetableDisplayOrderType(1))

    private val timetable2 = Timetable(
            mTimetableId = TimetableIdType("time0002"),
            mTimetableName = TimetableNameType("昼"),
            mTimetableTime = TimetableTimeType(12, 30),
            mTimetableDisplayOrder = TimetableDisplayOrderType(2))

    private val timetable3 = Timetable(
            mTimetableId = TimetableIdType("time0003"),
            mTimetableName = TimetableNameType("夜"),
            mTimetableTime = TimetableTimeType(19, 0),
            mTimetableDisplayOrder = TimetableDisplayOrderType(3))

    @Test
    @Throws(Exception::class)
    fun constructor_NoParameter() {
        val entity = Medicine()
        assertNotNull(entity.mMedicineId.dbValue)
        assertNotSame("", entity.mMedicineId.dbValue)
        assertEquals("", entity.mMedicineName.dbValue)
        assertEquals("0", entity.mMedicineTakeNumber.dbValue)
        assertEquals("", entity.mMedicineUnit.displayValue)
        assertEquals(0, entity.mMedicineDateNumber.dbValue)
        assertTrue(0 < entity.mMedicineStartDatetime.dbValue.timeInMillis)
        assertEquals(0, entity.mMedicineInterval.dbValue)
        assertEquals(0, entity.mMedicineIntervalMode.dbValue)
        assertEquals("", entity.mMedicinePhoto.dbValue)
        assertEquals(true, entity.mMedicineNeedAlarm.isTrue)
        assertEquals(false, entity.mMedicineDeleteFlag.isTrue)
        assertEquals(0, entity.mTimetableList.count)

        assertNotNull(entity.medicineUnitId.dbValue)
        assertNotSame("", entity.medicineUnitId.dbValue)

        assertEquals(false, entity.isOneShowMedicine)
    }

    @SuppressLint("SdCardPath")
    @Test
    @Throws(Exception::class)
    fun constructor_WithParameter() {
        var entity = Medicine(mMedicineId = MedicineIdType("1234"))
        assertEquals("1234", entity.mMedicineId.dbValue)

        entity = Medicine(mMedicineName = MedicineNameType("test"))
        assertEquals("test", entity.mMedicineName.dbValue)

        entity = Medicine(mMedicineTakeNumber = MedicineTakeNumberType("1"))
        assertEquals("1", entity.mMedicineTakeNumber.dbValue)

        entity = Medicine(
                mMedicineUnit = MedicineUnit(mMedicineUnitId = MedicineUnitIdType("5678"),
                        mMedicineUnitValue = MedicineUnitValueType("錠"),
                        mMedicineUnitDisplayOrder = MedicineUnitDisplayOrderType(1)))
        assertEquals("5678", entity.mMedicineUnit.mMedicineUnitId.dbValue)
        assertEquals("錠", entity.mMedicineUnit.mMedicineUnitValue.dbValue)
        assertEquals(1L, entity.mMedicineUnit.mMedicineUnitDisplayOrder.dbValue)

        entity = Medicine(mMedicineDateNumber = MedicineDateNumberType(1))
        assertEquals(1L, entity.mMedicineDateNumber.dbValue)

        entity = Medicine(mMedicineStartDatetime = MedicineStartDatetimeType(2017, 1, 2, 3, 4))
        assertEquals("17/01/02 3:04", entity.mMedicineStartDatetime.displayValue)

        entity = Medicine(mMedicineInterval = MedicineIntervalType(1))
        assertEquals(1L, entity.mMedicineInterval.dbValue)

        entity = Medicine(mMedicineIntervalMode = MedicineIntervalModeType(MedicineIntervalModeType.DateIntervalPattern.MONTH))
        assertEquals(false, entity.mMedicineIntervalMode.isDays)

        entity = Medicine(mMedicinePhoto = MedicinePhotoType("/sdcard/CAMERA/hogehoge.jpg"))
        assertEquals("/sdcard/CAMERA/hogehoge.jpg", entity.mMedicinePhoto.dbValue)

        entity = Medicine(mMedicineNeedAlarm = MedicineNeedAlarmType(false))
        assertEquals(false, entity.mMedicineNeedAlarm.isTrue)

        entity = Medicine(mMedicineDeleteFlag = MedicineDeleteFlagType(true))
        assertEquals(true, entity.mMedicineDeleteFlag.isTrue)

        val timetableList = MedicineTimetableList()
        timetableList.setTimetableList(mutableListOf(timetable1, timetable2, timetable3))

        entity = Medicine(mTimetableList = timetableList)
        assertEquals(3, entity.mTimetableList.count)
        assertEquals(timetable1, entity.mTimetableList.elementAt(0))
        assertEquals(timetable2, entity.mTimetableList.elementAt(1))
        assertEquals(timetable3, entity.mTimetableList.elementAt(2))
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
                mMedicineUnit = MedicineUnit(mMedicineUnitId = MedicineUnitIdType("5678"),
                        mMedicineUnitValue = MedicineUnitValueType("錠"),
                        mMedicineUnitDisplayOrder = MedicineUnitDisplayOrderType(1)))
        assertEquals("5678", entity.medicineUnitId.dbValue)
    }
}