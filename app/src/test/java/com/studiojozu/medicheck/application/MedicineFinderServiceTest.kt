package com.studiojozu.medicheck.application

import com.studiojozu.medicheck.R
import com.studiojozu.medicheck.di.MediCheckTestApplication
import com.studiojozu.medicheck.domain.model.medicine.*
import com.studiojozu.medicheck.domain.model.medicine.repository.MedicineUnitRepository
import com.studiojozu.medicheck.domain.model.medicine.repository.MedicineViewRepository
import com.studiojozu.medicheck.domain.model.setting.ATestParent
import com.studiojozu.medicheck.domain.model.setting.repository.TimetableRepository
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.powermock.core.classloader.annotations.PowerMockIgnore
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import javax.inject.Inject

@RunWith(RobolectricTestRunner::class)
@Config(manifest = "src/main/AndroidManifest.xml", application = MediCheckTestApplication::class)
@PowerMockIgnore("org.mockito.*", "org.robolectric.*", "android.*")
class MedicineFinderServiceTest : ATestParent() {
    @Inject
    lateinit var medicineFinderService: MedicineFinderService

    @Inject
    lateinit var medicineViewRepository: MedicineViewRepository

    @Inject
    lateinit var medicineUnitRepository: MedicineUnitRepository

    @Inject
    lateinit var timetableRepository: TimetableRepository

    @Before
    fun setUp() = (RuntimeEnvironment.application as MediCheckTestApplication).mAppTestComponent.inject(this)

    @Test
    @Throws(Exception::class)
    fun existMedicine() {
        // existMedicine - not exist
        assertFalse(medicineFinderService.existMedicine())

        // insert
        medicineViewRepository.insert(medicine = Medicine(mMedicineId = MedicineIdType("12345678")))

        // existMedicine - exists
        assertTrue(medicineFinderService.existMedicine())

        // delete
        medicineViewRepository.delete(medicine = Medicine(mMedicineId = MedicineIdType("12345678")))
    }

    @Test
    @Throws(Exception::class)
    fun findByMedicineId() {
        val medicine = Medicine(
                mMedicineId = MedicineIdType("12345678"),
                mMedicineName = MedicineNameType("メルカゾール"))

        // findByMedicineId - not found
        var entity = medicineFinderService.findByMedicineId(medicine.mMedicineId)
        assertNotNull(entity)
        assertEquals(medicine.mMedicineId, entity.mMedicineId)
        assertEquals(MedicineNameType(""), entity.mMedicineName)

        // insert
        medicineViewRepository.insert(medicine = medicine)

        // findByMedicineId - found
        entity = medicineFinderService.findByMedicineId(medicine.mMedicineId)
        assertNotNull(entity)
        assertEquals(medicine.mMedicineId, entity.mMedicineId)
        assertEquals(medicine.mMedicineName, entity.mMedicineName)

        // delete
        medicineViewRepository.delete(medicine = medicine)
    }

    @Test
    @Throws(Exception::class)
    @Config(qualifiers = "ja")
    fun defaultMedicineUnit() {
        val defaultUnitValue = RuntimeEnvironment.application.resources.getString(R.string.medicine_unit_1)

        // existMedicine - not exist
        var entity = medicineFinderService.defaultMedicineUnit
        assertEquals(defaultUnitValue, entity.displayValue)

        // delete
        medicineUnitRepository.delete(entity)

        // existMedicine - exists
        entity = medicineFinderService.defaultMedicineUnit
        assertEquals("", entity.displayValue)

        // insert
        medicineUnitRepository.insert(medicineUnit = MedicineUnit(
                mMedicineUnitId = MedicineUnitIdType(),
                mMedicineUnitValue = MedicineUnitValueType(defaultUnitValue),
                mMedicineUnitDisplayOrder = MedicineUnitDisplayOrderType(1))
        )
    }

    @Test
    @Throws(Exception::class)
    @Config(qualifiers = "ja")
    fun defaultTimetable() {
        val timetables = timetableRepository.findAll()

        // existMedicine - not exist
        val entity = medicineFinderService.defaultTimetable!!
        assertEquals(timetables[0].mTimetableId, entity.mTimetableId)
        assertEquals(timetables[0].mTimetableName, entity.mTimetableName)
        assertEquals(timetables[0].mTimetableTime, entity.mTimetableTime)
        assertEquals(timetables[0].mTimetableDisplayOrder, entity.mTimetableDisplayOrder)

        // delete
        timetables.forEach { it ->
            timetableRepository.delete(it)
        }

        // existMedicine - exists
        assertNull(medicineFinderService.defaultTimetable)

        // insert
        timetables.forEach { it ->
            timetableRepository.insert(it)
        }
    }
}
