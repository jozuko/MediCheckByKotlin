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
        val medicine = getMedicine("メルカゾール", medicineUnit.mMedicineUnitId.dbValue)
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
        val medicine = getMedicine("メルカゾール", medicineUnit.mMedicineUnitId.dbValue)
        medicineViewRepository.insert(medicine)

        // findByMedicineId
        val actual = medicineViewRepository.findByMedicineId(medicine.mMedicineId)!!
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
        database.personDao().insert(SqlitePerson.build { mPerson = person1 })
        // タイムテーブルを3つ登録
        database.timetableDao().insert(SqliteTimetable.build { mTimetable = timetable1 })
        database.timetableDao().insert(SqliteTimetable.build { mTimetable = timetable2 })
        database.timetableDao().insert(SqliteTimetable.build { mTimetable = timetable3 })

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
        val actualMedicine = medicineViewRepository.findByMedicineId(medicine1.mMedicineId)!!
        Assert.assertEquals(medicine1.mMedicineId, actualMedicine.mMedicineId)
        val actualMedicineUnit1 = database.medicineUnitDao().findById(medicine1.medicineUnitId.dbValue)!!
        Assert.assertEquals(medicine1.medicineUnitId, actualMedicineUnit1.mMedicineUnitId)
        val actualPerson = database.personMediRelationDao().findPersonByMedicineId(medicine1.mMedicineId.dbValue)!!
        Assert.assertEquals(person1.mPersonId, actualPerson.mPersonId)
        val actualTimetableArray = database.mediTimeRelationDao().findTimetableByMedicineId(medicine1.mMedicineId.dbValue)
        Assert.assertEquals(3, actualTimetableArray.size)
        Assert.assertEquals(timetable1.mTimetableId, actualTimetableArray[0].mTimetableId)
        Assert.assertEquals(timetable2.mTimetableId, actualTimetableArray[1].mTimetableId)
        Assert.assertEquals(timetable3.mTimetableId, actualTimetableArray[2].mTimetableId)
        val actualScheduleArray = database.scheduleDao().findByMedicineId(medicine1.mMedicineId.dbValue)
        Assert.assertEquals(3, actualScheduleArray.size)
        Assert.assertEquals(timetable1.mTimetableId, actualScheduleArray[0].mTimetableId)
        Assert.assertEquals(medicine1.mMedicineId, actualScheduleArray[0].mMedicineId)
        Assert.assertEquals(timetable2.mTimetableId, actualScheduleArray[1].mTimetableId)
        Assert.assertEquals(medicine1.mMedicineId, actualScheduleArray[1].mMedicineId)
        Assert.assertEquals(timetable3.mTimetableId, actualScheduleArray[2].mTimetableId)
        Assert.assertEquals(medicine1.mMedicineId, actualScheduleArray[2].mMedicineId)

        // insert - medicine2
        medicineViewRepository.insert(medicine = medicine2,
                person = person1,
                timetableArray = timetableArray2,
                scheduleArray = scheduleArray2)

        val actualMedicine2 = medicineViewRepository.findByMedicineId(medicine2.mMedicineId)!!
        Assert.assertEquals(medicine2.mMedicineId, actualMedicine2.mMedicineId)
        val actualMedicineUnit2 = database.medicineUnitDao().findById(medicine2.medicineUnitId.dbValue)!!
        Assert.assertEquals(medicine2.medicineUnitId, actualMedicineUnit2.mMedicineUnitId)
        val actualPerson2 = database.personMediRelationDao().findPersonByMedicineId(medicine2.mMedicineId.dbValue)!!
        Assert.assertEquals(person1.mPersonId, actualPerson2.mPersonId)
        val actualTimetableArray2 = database.mediTimeRelationDao().findTimetableByMedicineId(medicine2.mMedicineId.dbValue)
        Assert.assertEquals(1, actualTimetableArray2.size)
        Assert.assertEquals(timetable1.mTimetableId, actualTimetableArray2[0].mTimetableId)
        val actualScheduleArray2 = database.scheduleDao().findByMedicineId(medicine2.mMedicineId.dbValue)
        Assert.assertEquals(1, actualScheduleArray2.size)
        Assert.assertEquals(timetable1.mTimetableId, actualScheduleArray2[0].mTimetableId)
        Assert.assertEquals(medicine2.mMedicineId, actualScheduleArray2[0].mMedicineId)

        // delete
        medicineViewRepository.delete(medicine = medicine1,
                person = person1,
                timetableArray = timetableArray1,
                scheduleArray = scheduleArray1)

        Assert.assertNull(medicineViewRepository.findByMedicineId(medicine1.mMedicineId))
        Assert.assertNotNull(medicineViewRepository.findByMedicineId(medicine2.mMedicineId))
        Assert.assertNull(database.personMediRelationDao().findPersonByMedicineId(medicine1.mMedicineId.dbValue))
        Assert.assertNotNull(database.personMediRelationDao().findPersonByMedicineId(medicine2.mMedicineId.dbValue))
        Assert.assertTrue(database.mediTimeRelationDao().findTimetableByMedicineId(medicine1.mMedicineId.dbValue).isEmpty())
        Assert.assertFalse(database.mediTimeRelationDao().findTimetableByMedicineId(medicine2.mMedicineId.dbValue).isEmpty())
        Assert.assertTrue(database.scheduleDao().findByMedicineId(medicine1.mMedicineId.dbValue).isEmpty())
        Assert.assertFalse(database.scheduleDao().findByMedicineId(medicine2.mMedicineId.dbValue).isEmpty())

        // delete
        medicineViewRepository.delete(medicine = medicine2,
                person = person1,
                timetableArray = timetableArray2,
                scheduleArray = scheduleArray2)

        Assert.assertNull(medicineViewRepository.findByMedicineId(medicine1.mMedicineId))
        Assert.assertNull(medicineViewRepository.findByMedicineId(medicine2.mMedicineId))
        Assert.assertNull(database.personMediRelationDao().findPersonByMedicineId(medicine1.mMedicineId.dbValue))
        Assert.assertNull(database.personMediRelationDao().findPersonByMedicineId(medicine2.mMedicineId.dbValue))
        Assert.assertTrue(database.mediTimeRelationDao().findTimetableByMedicineId(medicine1.mMedicineId.dbValue).isEmpty())
        Assert.assertTrue(database.mediTimeRelationDao().findTimetableByMedicineId(medicine2.mMedicineId.dbValue).isEmpty())
        Assert.assertTrue(database.scheduleDao().findByMedicineId(medicine1.mMedicineId.dbValue).isEmpty())
        Assert.assertTrue(database.scheduleDao().findByMedicineId(medicine2.mMedicineId.dbValue).isEmpty())
    }

    private fun getMedicine(name: String = "", unitId: String = MedicineUnitIdType().dbValue): Medicine =
            Medicine(mMedicineName = MedicineNameType(name),
                    mMedicineUnit = MedicineUnit(mMedicineUnitId = MedicineUnitIdType(unitId)))

    private fun getMedicineUnit(value: String = ""): MedicineUnit =
            MedicineUnit(mMedicineUnitValue = MedicineUnitValueType(value))

    private fun assert(expectMedicine: Medicine, actual: Medicine) {
        Assert.assertEquals(expectMedicine.mMedicineId, actual.mMedicineId)
        Assert.assertEquals(expectMedicine.mMedicineName, actual.mMedicineName)
        Assert.assertEquals(expectMedicine.mMedicineTakeNumber, actual.mMedicineTakeNumber)
        Assert.assertEquals(expectMedicine.mMedicineDateNumber, actual.mMedicineDateNumber)
        Assert.assertEquals(expectMedicine.mMedicineStartDatetime, actual.mMedicineStartDatetime)
        Assert.assertEquals(expectMedicine.mMedicineInterval, actual.mMedicineInterval)
        Assert.assertEquals(expectMedicine.mMedicineIntervalMode, actual.mMedicineIntervalMode)
        Assert.assertEquals(expectMedicine.mMedicinePhoto, actual.mMedicinePhoto)
        Assert.assertEquals(expectMedicine.mMedicineNeedAlarm, actual.mMedicineNeedAlarm)
        Assert.assertEquals(expectMedicine.mMedicineDeleteFlag, actual.mMedicineDeleteFlag)

        Assert.assertEquals(expectMedicine.mMedicineUnit.mMedicineUnitId, actual.mMedicineUnit.mMedicineUnitId)
        Assert.assertEquals(expectMedicine.mMedicineUnit.mMedicineUnitValue, expectMedicine.mMedicineUnit.mMedicineUnitValue)
        Assert.assertEquals(expectMedicine.mMedicineUnit.mMedicineUnitDisplayOrder, expectMedicine.mMedicineUnit.mMedicineUnitDisplayOrder)
    }

    private val medicineUnit = MedicineUnit(
            mMedicineUnitId = MedicineUnitIdType("unit01"),
            mMedicineUnitValue = MedicineUnitValueType("錠"),
            mMedicineUnitDisplayOrder = MedicineUnitDisplayOrderType(2))

    private val medicine1 = Medicine(
            mMedicineId = MedicineIdType("medicine01"),
            mMedicineName = MedicineNameType("メルカゾール"),
            mMedicineUnit = medicineUnit)

    private val medicine2 = Medicine(
            mMedicineId = MedicineIdType("medicine02"),
            mMedicineName = MedicineNameType("チラーヂン"),
            mMedicineUnit = medicineUnit)

    private val person1 = Person(
            mPersonId = PersonIdType("person01"),
            mPersonName = PersonNameType("自分"),
            mPersonDisplayOrder = PersonDisplayOrderType(2))

    private val timetable1 = Timetable(
            mTimetableId = TimetableIdType("timetable01"),
            mTimetableName = TimetableNameType("朝"),
            mTimetableTime = TimetableTimeType(CalendarNoSecond().calendar),
            mTimetableDisplayOrder = TimetableDisplayOrderType(20))

    private val timetable2 = Timetable(
            mTimetableId = TimetableIdType("timetable02"),
            mTimetableName = TimetableNameType("昼"),
            mTimetableTime = TimetableTimeType(CalendarNoSecond().calendar),
            mTimetableDisplayOrder = TimetableDisplayOrderType(21))

    private val timetable3 = Timetable(
            mTimetableId = TimetableIdType("timetable03"),
            mTimetableName = TimetableNameType("夜"),
            mTimetableTime = TimetableTimeType(CalendarNoSecond().calendar),
            mTimetableDisplayOrder = TimetableDisplayOrderType(22))

    private val schedule1 = Schedule(
            mMedicineId = medicine1.mMedicineId,
            mTimetableId = timetable1.mTimetableId,
            mSchedulePlanDate = SchedulePlanDateType(CalendarNoSecond().calendar),
            mScheduleNeedAlarm = ScheduleNeedAlarmType(true),
            mScheduleIsTake = ScheduleIsTakeType(true))

    private val schedule2 = Schedule(
            mMedicineId = medicine1.mMedicineId,
            mTimetableId = timetable2.mTimetableId,
            mSchedulePlanDate = SchedulePlanDateType(CalendarNoSecond().calendar),
            mScheduleNeedAlarm = ScheduleNeedAlarmType(true),
            mScheduleIsTake = ScheduleIsTakeType(false))

    private val schedule3 = Schedule(
            mMedicineId = medicine1.mMedicineId,
            mTimetableId = timetable3.mTimetableId,
            mSchedulePlanDate = SchedulePlanDateType(CalendarNoSecond().calendar),
            mScheduleNeedAlarm = ScheduleNeedAlarmType(true),
            mScheduleIsTake = ScheduleIsTakeType(false))

    private val schedule4 = schedule1.copy(mMedicineId = medicine2.mMedicineId)
}