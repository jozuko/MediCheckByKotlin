package com.studiojozu.medicheck.domain.model.medicine.repository

import com.studiojozu.common.domain.model.CalendarNoSecond
import com.studiojozu.medicheck.di.MediCheckTestApplication
import com.studiojozu.medicheck.domain.model.medicine.*
import com.studiojozu.medicheck.domain.model.person.Person
import com.studiojozu.medicheck.domain.model.person.PersonDisplayOrderType
import com.studiojozu.medicheck.domain.model.person.PersonIdType
import com.studiojozu.medicheck.domain.model.person.PersonNameType
import com.studiojozu.medicheck.domain.model.schedule.Schedule
import com.studiojozu.medicheck.domain.model.schedule.ScheduleIsTakeType
import com.studiojozu.medicheck.domain.model.schedule.ScheduleNeedAlarmType
import com.studiojozu.medicheck.domain.model.schedule.SchedulePlanDateType
import com.studiojozu.medicheck.domain.model.setting.*
import com.studiojozu.medicheck.infrastructure.persistence.database.AppDatabase
import com.studiojozu.medicheck.infrastructure.persistence.entity.SqlitePerson
import com.studiojozu.medicheck.infrastructure.persistence.entity.SqliteTimetable
import org.junit.Assert
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
class MedicineViewRepositoryTest : ATestParent() {

    @Inject
    lateinit var medicineViewRepository: MedicineViewRepository

    @Before
    fun setUp() = (RuntimeEnvironment.application as MediCheckTestApplication).mAppTestComponent.inject(this)

    @Test
    @Throws(Exception::class)
    fun findAll() {
        // addData
        val medicineUnit = getMedicineUnit("錠")
        val medicine = getMedicine("メルカゾール", medicineUnit.medicineUnitId.dbValue)
        medicineViewRepository.insert(medicine)

        // findAll
        val medicines = medicineViewRepository.findAllNoTimetable()
        Assert.assertEquals(1, medicines.size)
        assert(medicine, medicines[0])

        // delete data
        medicineViewRepository.delete(medicine)

        // findAll
        Assert.assertEquals(0, medicineViewRepository.findAllNoTimetable().size)
    }

    @Test
    @Throws(Exception::class)
    fun findByMedicineId() {
        // addData
        val medicineUnit = getMedicineUnit("錠")
        val medicine = getMedicine("メルカゾール", medicineUnit.medicineUnitId.dbValue)
        medicineViewRepository.insert(medicine)

        // findByMedicineId
        val actual = medicineViewRepository.findByMedicineId(medicine.medicineId)!!
        assert(medicine, actual)

        // select no data
        Assert.assertNull(medicineViewRepository.findByMedicineId(medicineId = MedicineIdType("unknown id")))

        // delete data
        medicineViewRepository.delete(medicine)
    }

    @Test
    @Throws(Exception::class)
    fun deleteInsertMedicine() {
        val database = AppDatabase.getAppDatabase(RuntimeEnvironment.application.applicationContext)

        // prepare
        // 人を1人登録
        database.personDao().insert(SqlitePerson.build { person = person1 })
        // タイムテーブルを3つ登録
        database.timetableDao().insert(SqliteTimetable.build { timetable = timetable1 })
        database.timetableDao().insert(SqliteTimetable.build { timetable = timetable2 })
        database.timetableDao().insert(SqliteTimetable.build { timetable = timetable3 })

        // insert data
        val timetableArray1 = arrayOf(timetable1, timetable2, timetable3)
        val scheduleArray1 = arrayOf(schedule1, schedule2, schedule3)
        val timetableArray2 = arrayOf(timetable1)
        val scheduleArray2 = arrayOf(schedule4)

        // insert - medicine1
        medicineViewRepository.insert(medicine = medicine1,
                person = person1,
                timetableArray = timetableArray1,
                scheduleArray = scheduleArray1)

        // find
        val actualMedicine = medicineViewRepository.findByMedicineId(medicine1.medicineId)!!
        Assert.assertEquals(medicine1.medicineId, actualMedicine.medicineId)
        val actualMedicineUnit1 = database.medicineUnitDao().findById(medicine1.medicineUnitId.dbValue)!!
        Assert.assertEquals(medicine1.medicineUnitId, actualMedicineUnit1.medicineUnitId)
        val actualPerson = database.personMediRelationDao().findPersonByMedicineId(medicine1.medicineId.dbValue)!!
        Assert.assertEquals(person1.personId, actualPerson.personId)
        val actualTimetableArray = database.mediTimeRelationDao().findTimetableByMedicineId(medicine1.medicineId.dbValue)
        Assert.assertEquals(3, actualTimetableArray.size)
        Assert.assertEquals(timetable1.timetableId, actualTimetableArray[0].timetableId)
        Assert.assertEquals(timetable2.timetableId, actualTimetableArray[1].timetableId)
        Assert.assertEquals(timetable3.timetableId, actualTimetableArray[2].timetableId)
        val actualScheduleArray = database.scheduleDao().findByMedicineId(medicine1.medicineId.dbValue)
        Assert.assertEquals(3, actualScheduleArray.size)
        Assert.assertEquals(timetable1.timetableId, actualScheduleArray[0].timetableId)
        Assert.assertEquals(medicine1.medicineId, actualScheduleArray[0].medicineId)
        Assert.assertEquals(timetable2.timetableId, actualScheduleArray[1].timetableId)
        Assert.assertEquals(medicine1.medicineId, actualScheduleArray[1].medicineId)
        Assert.assertEquals(timetable3.timetableId, actualScheduleArray[2].timetableId)
        Assert.assertEquals(medicine1.medicineId, actualScheduleArray[2].medicineId)

        // insert - medicine2
        medicineViewRepository.insert(medicine = medicine2,
                person = person1,
                timetableArray = timetableArray2,
                scheduleArray = scheduleArray2)

        val actualMedicine2 = medicineViewRepository.findByMedicineId(medicine2.medicineId)!!
        Assert.assertEquals(medicine2.medicineId, actualMedicine2.medicineId)
        val actualMedicineUnit2 = database.medicineUnitDao().findById(medicine2.medicineUnitId.dbValue)!!
        Assert.assertEquals(medicine2.medicineUnitId, actualMedicineUnit2.medicineUnitId)
        val actualPerson2 = database.personMediRelationDao().findPersonByMedicineId(medicine2.medicineId.dbValue)!!
        Assert.assertEquals(person1.personId, actualPerson2.personId)
        val actualTimetableArray2 = database.mediTimeRelationDao().findTimetableByMedicineId(medicine2.medicineId.dbValue)
        Assert.assertEquals(1, actualTimetableArray2.size)
        Assert.assertEquals(timetable1.timetableId, actualTimetableArray2[0].timetableId)
        val actualScheduleArray2 = database.scheduleDao().findByMedicineId(medicine2.medicineId.dbValue)
        Assert.assertEquals(1, actualScheduleArray2.size)
        Assert.assertEquals(timetable1.timetableId, actualScheduleArray2[0].timetableId)
        Assert.assertEquals(medicine2.medicineId, actualScheduleArray2[0].medicineId)

        // delete
        medicineViewRepository.delete(medicine = medicine1,
                person = person1,
                timetableArray = timetableArray1,
                scheduleArray = scheduleArray1)

        Assert.assertNull(medicineViewRepository.findByMedicineId(medicine1.medicineId))
        Assert.assertNotNull(medicineViewRepository.findByMedicineId(medicine2.medicineId))
        Assert.assertNull(database.personMediRelationDao().findPersonByMedicineId(medicine1.medicineId.dbValue))
        Assert.assertNotNull(database.personMediRelationDao().findPersonByMedicineId(medicine2.medicineId.dbValue))
        Assert.assertTrue(database.mediTimeRelationDao().findTimetableByMedicineId(medicine1.medicineId.dbValue).isEmpty())
        Assert.assertFalse(database.mediTimeRelationDao().findTimetableByMedicineId(medicine2.medicineId.dbValue).isEmpty())
        Assert.assertTrue(database.scheduleDao().findByMedicineId(medicine1.medicineId.dbValue).isEmpty())
        Assert.assertFalse(database.scheduleDao().findByMedicineId(medicine2.medicineId.dbValue).isEmpty())

        // delete
        medicineViewRepository.delete(medicine = medicine2,
                person = person1,
                timetableArray = timetableArray2,
                scheduleArray = scheduleArray2)

        Assert.assertNull(medicineViewRepository.findByMedicineId(medicine1.medicineId))
        Assert.assertNull(medicineViewRepository.findByMedicineId(medicine2.medicineId))
        Assert.assertNull(database.personMediRelationDao().findPersonByMedicineId(medicine1.medicineId.dbValue))
        Assert.assertNull(database.personMediRelationDao().findPersonByMedicineId(medicine2.medicineId.dbValue))
        Assert.assertTrue(database.mediTimeRelationDao().findTimetableByMedicineId(medicine1.medicineId.dbValue).isEmpty())
        Assert.assertTrue(database.mediTimeRelationDao().findTimetableByMedicineId(medicine2.medicineId.dbValue).isEmpty())
        Assert.assertTrue(database.scheduleDao().findByMedicineId(medicine1.medicineId.dbValue).isEmpty())
        Assert.assertTrue(database.scheduleDao().findByMedicineId(medicine2.medicineId.dbValue).isEmpty())
    }

    private fun getMedicine(name: String = "", unitId: String = MedicineUnitIdType().dbValue): Medicine =
            Medicine(medicineName = MedicineNameType(name),
                    medicineUnit = MedicineUnit(medicineUnitId = MedicineUnitIdType(unitId)))

    private fun getMedicineUnit(value: String = ""): MedicineUnit =
            MedicineUnit(medicineUnitValue = MedicineUnitValueType(value))

    private fun assert(expectMedicine: Medicine, actual: Medicine) {
        Assert.assertEquals(expectMedicine.medicineId, actual.medicineId)
        Assert.assertEquals(expectMedicine.medicineName, actual.medicineName)
        Assert.assertEquals(expectMedicine.medicineTakeNumber, actual.medicineTakeNumber)
        Assert.assertEquals(expectMedicine.medicineDateNumber, actual.medicineDateNumber)
        Assert.assertEquals(expectMedicine.medicineStartDatetime, actual.medicineStartDatetime)
        Assert.assertEquals(expectMedicine.medicineInterval, actual.medicineInterval)
        Assert.assertEquals(expectMedicine.medicineIntervalMode, actual.medicineIntervalMode)
        Assert.assertEquals(expectMedicine.medicinePhoto, actual.medicinePhoto)
        Assert.assertEquals(expectMedicine.medicineNeedAlarm, actual.medicineNeedAlarm)
        Assert.assertEquals(expectMedicine.medicineDeleteFlag, actual.medicineDeleteFlag)

        Assert.assertEquals(expectMedicine.medicineUnit.medicineUnitId, actual.medicineUnit.medicineUnitId)
        Assert.assertEquals(expectMedicine.medicineUnit.medicineUnitValue, expectMedicine.medicineUnit.medicineUnitValue)
        Assert.assertEquals(expectMedicine.medicineUnit.medicineUnitDisplayOrder, expectMedicine.medicineUnit.medicineUnitDisplayOrder)
    }

    private val medicineUnit = MedicineUnit(
            medicineUnitId = MedicineUnitIdType("unit01"),
            medicineUnitValue = MedicineUnitValueType("錠"),
            medicineUnitDisplayOrder = MedicineUnitDisplayOrderType(2))

    private val medicine1 = Medicine(
            medicineId = MedicineIdType("medicine01"),
            medicineName = MedicineNameType("メルカゾール"),
            medicineUnit = medicineUnit)

    private val medicine2 = Medicine(
            medicineId = MedicineIdType("medicine02"),
            medicineName = MedicineNameType("チラーヂン"),
            medicineUnit = medicineUnit)

    private val person1 = Person(
            personId = PersonIdType("person01"),
            personName = PersonNameType("自分"),
            personDisplayOrder = PersonDisplayOrderType(2))

    private val timetable1 = Timetable(
            timetableId = TimetableIdType("timetable01"),
            timetableName = TimetableNameType("朝"),
            timetableTime = TimetableTimeType(CalendarNoSecond().calendar),
            timetableDisplayOrder = TimetableDisplayOrderType(20))

    private val timetable2 = Timetable(
            timetableId = TimetableIdType("timetable02"),
            timetableName = TimetableNameType("昼"),
            timetableTime = TimetableTimeType(CalendarNoSecond().calendar),
            timetableDisplayOrder = TimetableDisplayOrderType(21))

    private val timetable3 = Timetable(
            timetableId = TimetableIdType("timetable03"),
            timetableName = TimetableNameType("夜"),
            timetableTime = TimetableTimeType(CalendarNoSecond().calendar),
            timetableDisplayOrder = TimetableDisplayOrderType(22))

    private val schedule1 = Schedule(
            medicineId = medicine1.medicineId,
            timetableId = timetable1.timetableId,
            schedulePlanDate = SchedulePlanDateType(CalendarNoSecond().calendar),
            scheduleNeedAlarm = ScheduleNeedAlarmType(true),
            scheduleIsTake = ScheduleIsTakeType(true))

    private val schedule2 = Schedule(
            medicineId = medicine1.medicineId,
            timetableId = timetable2.timetableId,
            schedulePlanDate = SchedulePlanDateType(CalendarNoSecond().calendar),
            scheduleNeedAlarm = ScheduleNeedAlarmType(true),
            scheduleIsTake = ScheduleIsTakeType(false))

    private val schedule3 = Schedule(
            medicineId = medicine1.medicineId,
            timetableId = timetable3.timetableId,
            schedulePlanDate = SchedulePlanDateType(CalendarNoSecond().calendar),
            scheduleNeedAlarm = ScheduleNeedAlarmType(true),
            scheduleIsTake = ScheduleIsTakeType(false))

    private val schedule4 = schedule1.copy(medicineId = medicine2.medicineId)
}