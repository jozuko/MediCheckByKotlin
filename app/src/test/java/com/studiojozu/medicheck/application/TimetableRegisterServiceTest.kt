package com.studiojozu.medicheck.application

import com.studiojozu.medicheck.di.MediCheckTestApplication
import com.studiojozu.medicheck.domain.model.medicine.*
import com.studiojozu.medicheck.domain.model.medicine.repository.MediTimeRelationRepository
import com.studiojozu.medicheck.domain.model.medicine.repository.MedicineUnitRepository
import com.studiojozu.medicheck.domain.model.medicine.repository.MedicineViewRepository
import com.studiojozu.medicheck.domain.model.setting.*
import com.studiojozu.medicheck.domain.model.setting.repository.TimetableRepository
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import javax.inject.Inject

@RunWith(RobolectricTestRunner::class)
@Config(manifest = "src/main/AndroidManifest.xml", application = MediCheckTestApplication::class)
class TimetableRegisterServiceTest : ATestParent() {
    @Inject
    lateinit var timetableResisterService: TimetableRegisterService

    @Inject
    lateinit var timetableRepository: TimetableRepository

    @Inject
    lateinit var mediTimeRelationRepository: MediTimeRelationRepository

    @Inject
    lateinit var medicineViewRepository: MedicineViewRepository

    @Inject
    lateinit var medicineUnitRepository: MedicineUnitRepository

    @Before
    fun setUp() = (RuntimeEnvironment.application as MediCheckTestApplication).mAppTestComponent.inject(this)

    @Test
    @Config(qualifiers = "ja")
    @Throws(Exception::class)
    fun saveOneModel() {
        assertEquals(12, timetableRepository.findAll().size)

        val newModel = Timetable(
                timetableId = TimetableIdType(),
                timetableName = TimetableNameType("服用時間名-1"),
                timetableTime = TimetableTimeType(15, 0),
                timetableDisplayOrder = TimetableDisplayOrderType(0L)
        )
        timetableResisterService.save(newModel)

        val timetableList = timetableRepository.findAll()
        assertEquals(13, timetableList.size)

        var index = 0
        assertEquals("朝", timetableList[index].timetableName.dbValue)
        assertEquals("7:00", timetableList[index].timetableTime.displayValue)
        assertEquals((index + 1).toLong(), timetableList[index].timetableDisplayOrder.dbValue)

        index = 1
        assertEquals("昼", timetableList[index].timetableName.dbValue)
        assertEquals("12:00", timetableList[index].timetableTime.displayValue)
        assertEquals((index + 1).toLong(), timetableList[index].timetableDisplayOrder.dbValue)

        index = 2
        assertEquals("夜", timetableList[index].timetableName.dbValue)
        assertEquals("19:00", timetableList[index].timetableTime.displayValue)
        assertEquals((index + 1).toLong(), timetableList[index].timetableDisplayOrder.dbValue)

        index = 3
        assertEquals("就寝前", timetableList[index].timetableName.dbValue)
        assertEquals("22:00", timetableList[index].timetableTime.displayValue)
        assertEquals((index + 1).toLong(), timetableList[index].timetableDisplayOrder.dbValue)

        index = 4
        assertEquals("朝食前", timetableList[index].timetableName.dbValue)
        assertEquals("6:30", timetableList[index].timetableTime.displayValue)
        assertEquals((index + 1).toLong(), timetableList[index].timetableDisplayOrder.dbValue)

        index = 5
        assertEquals("昼食前", timetableList[index].timetableName.dbValue)
        assertEquals("11:30", timetableList[index].timetableTime.displayValue)
        assertEquals((index + 1).toLong(), timetableList[index].timetableDisplayOrder.dbValue)

        index = 6
        assertEquals("夕食前", timetableList[index].timetableName.dbValue)
        assertEquals("18:30", timetableList[index].timetableTime.displayValue)
        assertEquals((index + 1).toLong(), timetableList[index].timetableDisplayOrder.dbValue)

        index = 7
        assertEquals("朝食後", timetableList[index].timetableName.dbValue)
        assertEquals("7:30", timetableList[index].timetableTime.displayValue)
        assertEquals((index + 1).toLong(), timetableList[index].timetableDisplayOrder.dbValue)

        index = 8
        assertEquals("昼食後", timetableList[index].timetableName.dbValue)
        assertEquals("12:30", timetableList[index].timetableTime.displayValue)
        assertEquals((index + 1).toLong(), timetableList[index].timetableDisplayOrder.dbValue)

        index = 9
        assertEquals("夕食後", timetableList[index].timetableName.dbValue)
        assertEquals("19:30", timetableList[index].timetableTime.displayValue)
        assertEquals((index + 1).toLong(), timetableList[index].timetableDisplayOrder.dbValue)

        index = 10
        assertEquals("食間(朝食 - 昼食)", timetableList[index].timetableName.dbValue)
        assertEquals("10:00", timetableList[index].timetableTime.displayValue)
        assertEquals((index + 1).toLong(), timetableList[index].timetableDisplayOrder.dbValue)

        index = 11
        assertEquals("食間(昼食 - 夕食)", timetableList[index].timetableName.dbValue)
        assertEquals("16:00", timetableList[index].timetableTime.displayValue)
        assertEquals((index + 1).toLong(), timetableList[index].timetableDisplayOrder.dbValue)

        index = 12
        assertEquals(newModel.timetableId, timetableList[index].timetableId)
        assertEquals(newModel.timetableName, timetableList[index].timetableName)
        assertEquals(newModel.timetableTime, timetableList[index].timetableTime)
        assertEquals((index + 1).toLong(), timetableList[index].timetableDisplayOrder.dbValue)
    }

    @Test
    @Config(qualifiers = "ja")
    @Throws(Exception::class)
    fun saveOneModelForUpdate() {
        val timetableInitList = timetableRepository.findAll()
        assertEquals(12, timetableInitList.size)

        val newModel = timetableInitList[11].copy(timetableDisplayOrder = TimetableDisplayOrderType(13L))
        timetableResisterService.save(newModel)

        val timetableList = timetableRepository.findAll()
        assertEquals(12, timetableList.size)

        var index = 0
        assertEquals("朝", timetableList[index].timetableName.dbValue)
        assertEquals("7:00", timetableList[index].timetableTime.displayValue)
        assertEquals((index + 1).toLong(), timetableList[index].timetableDisplayOrder.dbValue)

        index = 1
        assertEquals("昼", timetableList[index].timetableName.dbValue)
        assertEquals("12:00", timetableList[index].timetableTime.displayValue)
        assertEquals((index + 1).toLong(), timetableList[index].timetableDisplayOrder.dbValue)

        index = 2
        assertEquals("夜", timetableList[index].timetableName.dbValue)
        assertEquals("19:00", timetableList[index].timetableTime.displayValue)
        assertEquals((index + 1).toLong(), timetableList[index].timetableDisplayOrder.dbValue)

        index = 3
        assertEquals("就寝前", timetableList[index].timetableName.dbValue)
        assertEquals("22:00", timetableList[index].timetableTime.displayValue)
        assertEquals((index + 1).toLong(), timetableList[index].timetableDisplayOrder.dbValue)

        index = 4
        assertEquals("朝食前", timetableList[index].timetableName.dbValue)
        assertEquals("6:30", timetableList[index].timetableTime.displayValue)
        assertEquals((index + 1).toLong(), timetableList[index].timetableDisplayOrder.dbValue)

        index = 5
        assertEquals("昼食前", timetableList[index].timetableName.dbValue)
        assertEquals("11:30", timetableList[index].timetableTime.displayValue)
        assertEquals((index + 1).toLong(), timetableList[index].timetableDisplayOrder.dbValue)

        index = 6
        assertEquals("夕食前", timetableList[index].timetableName.dbValue)
        assertEquals("18:30", timetableList[index].timetableTime.displayValue)
        assertEquals((index + 1).toLong(), timetableList[index].timetableDisplayOrder.dbValue)

        index = 7
        assertEquals("朝食後", timetableList[index].timetableName.dbValue)
        assertEquals("7:30", timetableList[index].timetableTime.displayValue)
        assertEquals((index + 1).toLong(), timetableList[index].timetableDisplayOrder.dbValue)

        index = 8
        assertEquals("昼食後", timetableList[index].timetableName.dbValue)
        assertEquals("12:30", timetableList[index].timetableTime.displayValue)
        assertEquals((index + 1).toLong(), timetableList[index].timetableDisplayOrder.dbValue)

        index = 9
        assertEquals("夕食後", timetableList[index].timetableName.dbValue)
        assertEquals("19:30", timetableList[index].timetableTime.displayValue)
        assertEquals((index + 1).toLong(), timetableList[index].timetableDisplayOrder.dbValue)

        index = 10
        assertEquals("食間(朝食 - 昼食)", timetableList[index].timetableName.dbValue)
        assertEquals("10:00", timetableList[index].timetableTime.displayValue)
        assertEquals((index + 1).toLong(), timetableList[index].timetableDisplayOrder.dbValue)

        index = 11
        assertEquals("食間(昼食 - 夕食)", timetableList[index].timetableName.dbValue)
        assertEquals("16:00", timetableList[index].timetableTime.displayValue)
        assertEquals(13L, timetableList[index].timetableDisplayOrder.dbValue)
    }

    @Test
    @Config(qualifiers = "ja")
    @Throws(Exception::class)
    fun saveList() {
        val timetableInitList = timetableRepository.findAll()
        assertEquals(12, timetableInitList.size)

        timetableResisterService.save(timetableInitList.map { timetable -> timetable.copy(timetableDisplayOrder = TimetableDisplayOrderType(timetable.timetableDisplayOrder.dbValue + 100)) })

        val timetableList = timetableRepository.findAll()
        assertEquals(12, timetableList.size)

        var index = 0
        assertEquals("朝", timetableList[index].timetableName.dbValue)
        assertEquals("7:00", timetableList[index].timetableTime.displayValue)
        assertEquals((index + 101).toLong(), timetableList[index].timetableDisplayOrder.dbValue)

        index = 1
        assertEquals("昼", timetableList[index].timetableName.dbValue)
        assertEquals("12:00", timetableList[index].timetableTime.displayValue)
        assertEquals((index + 101).toLong(), timetableList[index].timetableDisplayOrder.dbValue)

        index = 2
        assertEquals("夜", timetableList[index].timetableName.dbValue)
        assertEquals("19:00", timetableList[index].timetableTime.displayValue)
        assertEquals((index + 101).toLong(), timetableList[index].timetableDisplayOrder.dbValue)

        index = 3
        assertEquals("就寝前", timetableList[index].timetableName.dbValue)
        assertEquals("22:00", timetableList[index].timetableTime.displayValue)
        assertEquals((index + 101).toLong(), timetableList[index].timetableDisplayOrder.dbValue)

        index = 4
        assertEquals("朝食前", timetableList[index].timetableName.dbValue)
        assertEquals("6:30", timetableList[index].timetableTime.displayValue)
        assertEquals((index + 101).toLong(), timetableList[index].timetableDisplayOrder.dbValue)

        index = 5
        assertEquals("昼食前", timetableList[index].timetableName.dbValue)
        assertEquals("11:30", timetableList[index].timetableTime.displayValue)
        assertEquals((index + 101).toLong(), timetableList[index].timetableDisplayOrder.dbValue)

        index = 6
        assertEquals("夕食前", timetableList[index].timetableName.dbValue)
        assertEquals("18:30", timetableList[index].timetableTime.displayValue)
        assertEquals((index + 101).toLong(), timetableList[index].timetableDisplayOrder.dbValue)

        index = 7
        assertEquals("朝食後", timetableList[index].timetableName.dbValue)
        assertEquals("7:30", timetableList[index].timetableTime.displayValue)
        assertEquals((index + 101).toLong(), timetableList[index].timetableDisplayOrder.dbValue)

        index = 8
        assertEquals("昼食後", timetableList[index].timetableName.dbValue)
        assertEquals("12:30", timetableList[index].timetableTime.displayValue)
        assertEquals((index + 101).toLong(), timetableList[index].timetableDisplayOrder.dbValue)

        index = 9
        assertEquals("夕食後", timetableList[index].timetableName.dbValue)
        assertEquals("19:30", timetableList[index].timetableTime.displayValue)
        assertEquals((index + 101).toLong(), timetableList[index].timetableDisplayOrder.dbValue)

        index = 10
        assertEquals("食間(朝食 - 昼食)", timetableList[index].timetableName.dbValue)
        assertEquals("10:00", timetableList[index].timetableTime.displayValue)
        assertEquals((index + 101).toLong(), timetableList[index].timetableDisplayOrder.dbValue)

        index = 11
        assertEquals("食間(昼食 - 夕食)", timetableList[index].timetableName.dbValue)
        assertEquals("16:00", timetableList[index].timetableTime.displayValue)
        assertEquals((index + 101).toLong(), timetableList[index].timetableDisplayOrder.dbValue)
    }

    @Test
    @Config(qualifiers = "ja")
    @Throws(Exception::class)
    fun delete() {
        val timetables = timetableRepository.findAll()
        assertEquals(12, timetables.size)

        val medicineIdType = MedicineIdType("12345678")
        val timetableList = MedicineTimetableList(mutableListOf(timetables[0], timetables[1]))
        mediTimeRelationRepository.insertTimetable(medicineIdType, timetableList)

        var index = 0
        assertEquals(TimetableRegisterService.ErrorType.NO_ERROR, timetableResisterService.delete(timetables[index]))
        assertEquals(11 - index, timetableRepository.findAll().size)

        index = 1
        assertEquals(TimetableRegisterService.ErrorType.NO_ERROR, timetableResisterService.delete(timetables[index]))
        assertEquals(11 - index, timetableRepository.findAll().size)

        index = 2
        assertEquals(TimetableRegisterService.ErrorType.NO_ERROR, timetableResisterService.delete(timetables[index]))
        assertEquals(11 - index, timetableRepository.findAll().size)

        index = 3
        assertEquals(TimetableRegisterService.ErrorType.NO_ERROR, timetableResisterService.delete(timetables[index]))
        assertEquals(11 - index, timetableRepository.findAll().size)

        index = 4
        assertEquals(TimetableRegisterService.ErrorType.NO_ERROR, timetableResisterService.delete(timetables[index]))
        assertEquals(11 - index, timetableRepository.findAll().size)

        index = 5
        assertEquals(TimetableRegisterService.ErrorType.NO_ERROR, timetableResisterService.delete(timetables[index]))
        assertEquals(11 - index, timetableRepository.findAll().size)

        index = 6
        assertEquals(TimetableRegisterService.ErrorType.NO_ERROR, timetableResisterService.delete(timetables[index]))
        assertEquals(11 - index, timetableRepository.findAll().size)

        index = 7
        assertEquals(TimetableRegisterService.ErrorType.NO_ERROR, timetableResisterService.delete(timetables[index]))
        assertEquals(11 - index, timetableRepository.findAll().size)

        index = 8
        assertEquals(TimetableRegisterService.ErrorType.NO_ERROR, timetableResisterService.delete(timetables[index]))
        assertEquals(11 - index, timetableRepository.findAll().size)

        index = 9
        assertEquals(TimetableRegisterService.ErrorType.NO_ERROR, timetableResisterService.delete(timetables[index]))
        assertEquals(11 - index, timetableRepository.findAll().size)

        index = 10
        assertEquals(TimetableRegisterService.ErrorType.NO_ERROR, timetableResisterService.delete(timetables[index]))
        assertEquals(11 - index, timetableRepository.findAll().size)

        index = 11
        assertEquals(TimetableRegisterService.ErrorType.LAST_ONE, timetableResisterService.delete(timetables[index]))
        assertEquals(1, timetableRepository.findAll().size)
    }

    @Test
    @Config(qualifiers = "ja")
    @Throws(Exception::class)
    fun deleteExistRelationOther() {
        val timetables = timetableRepository.findAll()
        assertEquals(12, timetables.size)

        // 薬データを投入
        val medicineUnit = getMedicineUnit("錠")
        medicineUnitRepository.insert(medicineUnit)
        val medicine = getMedicine("メルカゾール", medicineUnit.medicineUnitId.dbValue)
        medicineViewRepository.insert(medicine)
        val timetableList = MedicineTimetableList(mutableListOf(timetables[0], timetables[1]))
        mediTimeRelationRepository.insertTimetable(medicine.medicineId, timetableList)

        val result = timetableResisterService.delete(timetables[0])
        assertEquals(TimetableRegisterService.ErrorType.EXIST_RELATION_OTHER, result)

        assertEquals(12, timetableRepository.findAll().size)
    }

    private fun getMedicine(name: String = "", unitId: String = MedicineUnitIdType().dbValue): Medicine =
            Medicine(medicineName = MedicineNameType(name),
                    medicineUnit = MedicineUnit(medicineUnitId = MedicineUnitIdType(unitId)))

    private fun getMedicineUnit(value: String = ""): MedicineUnit =
            MedicineUnit(medicineUnitValue = MedicineUnitValueType(value))
}
