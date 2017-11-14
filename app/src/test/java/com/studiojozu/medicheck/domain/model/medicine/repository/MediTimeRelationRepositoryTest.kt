package com.studiojozu.medicheck.domain.model.medicine.repository

import com.studiojozu.medicheck.di.MediCheckTestApplication
import com.studiojozu.medicheck.domain.model.medicine.MedicineIdType
import com.studiojozu.medicheck.domain.model.medicine.MedicineTimetableList
import com.studiojozu.medicheck.domain.model.setting.ATestParent
import com.studiojozu.medicheck.domain.model.setting.Timetable
import com.studiojozu.medicheck.infrastructure.persistence.database.AppDatabase
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import javax.inject.Inject

@Suppress("FunctionName")
@RunWith(RobolectricTestRunner::class)
@Config(manifest = "src/main/AndroidManifest.xml", application = MediCheckTestApplication::class)
class MediTimeRelationRepositoryTest : ATestParent() {

    @Inject
    lateinit var dao: MediTimeRelationRepository

    @Before
    fun setUp() = (RuntimeEnvironment.application as MediCheckTestApplication).mAppTestComponent.inject(this)

    @Test
    @Throws(Exception::class)
    fun insertTimetable() {
        val database = AppDatabase.getAppDatabase(RuntimeEnvironment.application.applicationContext)
        val timetables = database.timetableDao().findAll()

        // insert - no timetable
        val medicineIdType = MedicineIdType("12345678")
        var timetableList = MedicineTimetableList()
        dao.insertTimetable(medicineIdType, timetableList)

        var entities = dao.findTimetableByMedicineId(medicineIdType)
        Assert.assertEquals(0, entities.size)

        // insert - 3 timetable
        timetableList = MedicineTimetableList(
                mutableListOf(
                        timetables[0].toTimetable(),
                        timetables[1].toTimetable(),
                        timetables[2].toTimetable()))
        dao.insertTimetable(medicineIdType, timetableList)

        entities = dao.findTimetableByMedicineId(medicineIdType)
        Assert.assertEquals(3, entities.size)
        assert(timetables[0].toTimetable(), entities[0])
        assert(timetables[1].toTimetable(), entities[1])
        assert(timetables[2].toTimetable(), entities[2])

        // delete
        dao.deleteByMedicineId(medicineIdType)
        entities = dao.findTimetableByMedicineId(medicineIdType)
        Assert.assertEquals(0, entities.size)
    }

    @Test
    @Throws(Exception::class)
    fun insertOneShot() {
        val medicineIdType = MedicineIdType("12345678")
        dao.insertOneShot(medicineIdType)

        val entities = dao.findTimetableByMedicineId(medicineIdType)
        assertEquals(0, entities.size)
    }

    fun assert(expect: Timetable, actual: Timetable) {
        assertEquals(expect.mTimetableId, actual.mTimetableId)
        assertEquals(expect.mTimetableName, actual.mTimetableName)
        assertEquals(expect.mTimetableTime, actual.mTimetableTime)
        assertEquals(expect.mTimetableDisplayOrder, actual.mTimetableDisplayOrder)
    }

}