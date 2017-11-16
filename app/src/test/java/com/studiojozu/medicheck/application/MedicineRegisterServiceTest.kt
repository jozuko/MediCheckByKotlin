package com.studiojozu.medicheck.application

import com.studiojozu.common.domain.model.CalendarNoSecond
import com.studiojozu.medicheck.di.MediCheckTestApplication
import com.studiojozu.medicheck.domain.model.medicine.*
import com.studiojozu.medicheck.domain.model.person.Person
import com.studiojozu.medicheck.domain.model.person.PersonDisplayOrderType
import com.studiojozu.medicheck.domain.model.person.PersonIdType
import com.studiojozu.medicheck.domain.model.person.PersonNameType
import com.studiojozu.medicheck.domain.model.setting.*
import com.studiojozu.medicheck.infrastructure.persistence.database.AppDatabase
import com.studiojozu.medicheck.infrastructure.persistence.entity.SqliteMedicine
import com.studiojozu.medicheck.infrastructure.persistence.entity.SqliteMedicineUnit
import com.studiojozu.medicheck.infrastructure.persistence.entity.SqlitePerson
import com.studiojozu.medicheck.infrastructure.persistence.entity.SqliteTimetable
import org.junit.After
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
class MedicineRegisterServiceTest : ATestParent() {

    @Inject
    lateinit var database: AppDatabase

    @Inject
    lateinit var medicineRegisterService: MedicineRegisterService

    private val medicineUnit = MedicineUnit(
            mMedicineUnitId = MedicineUnitIdType("unit01"),
            mMedicineUnitValue = MedicineUnitValueType("錠"),
            mMedicineUnitDisplayOrder = MedicineUnitDisplayOrderType(2))

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

    private val medicine1 = Medicine(
            mMedicineId = MedicineIdType("medicine01"),
            mMedicineName = MedicineNameType("メルカゾール"),
            mMedicineUnit = medicineUnit,
            mMedicineStartDatetime = MedicineStartDatetimeType(2017, 1, 2, 3, 4),
            mMedicineDateNumber = MedicineDateNumberType(1),
            mTimetableList = MedicineTimetableList(mutableListOf(timetable1, timetable2, timetable3)))

    private val medicine2 = Medicine(
            mMedicineId = MedicineIdType("medicine02"),
            mMedicineName = MedicineNameType("チラーヂン"),
            mMedicineUnit = medicineUnit,
            mMedicineStartDatetime = MedicineStartDatetimeType(2017, 1, 2, 3, 4),
            mMedicineDateNumber = MedicineDateNumberType(1),
            mTimetableList = MedicineTimetableList(mutableListOf(timetable1)))

    @Before
    fun setUp() {
        (RuntimeEnvironment.application as MediCheckTestApplication).mAppTestComponent.inject(this)

        // 人を1人登録
        database.personDao().insert(SqlitePerson.build { mPerson = person1 })

        // タイムテーブルを3つ登録
        database.timetableDao().insert(SqliteTimetable.build { mTimetable = timetable1 })
        database.timetableDao().insert(SqliteTimetable.build { mTimetable = timetable2 })
        database.timetableDao().insert(SqliteTimetable.build { mTimetable = timetable3 })
    }

    @After
    fun after() {
        // delete data
        database.personDao().delete(SqlitePerson.build { mPerson = person1 })

        database.timetableDao().delete(SqliteTimetable.build { mTimetable = timetable1 })
        database.timetableDao().delete(SqliteTimetable.build { mTimetable = timetable2 })
        database.timetableDao().delete(SqliteTimetable.build { mTimetable = timetable3 })

        database.medicineDao().delete(SqliteMedicine.build { mMedicine = medicine1 })
        database.medicineDao().delete(SqliteMedicine.build { mMedicine = medicine2 })
        database.medicineUnitDao().delete(SqliteMedicineUnit.build { mMedicineUnit = medicineUnit })

        database.personMediRelationDao().deleteByMedicineId(medicine1.mMedicineId.dbValue)
        database.personMediRelationDao().deleteByMedicineId(medicine2.mMedicineId.dbValue)

        database.mediTimeRelationDao().deleteByMedicineId(medicine1.mMedicineId.dbValue)
        database.mediTimeRelationDao().deleteByMedicineId(medicine2.mMedicineId.dbValue)

        database.scheduleDao().deleteAllByMedicineId(medicine1.mMedicineId.dbValue)
        database.scheduleDao().deleteAllByMedicineId(medicine2.mMedicineId.dbValue)
    }

    @Test
    @Throws(Exception::class)
    fun deleteMedicine() {
        // before method call
        assertEquals(2, database.personDao().findAll().size)
        assertEquals(15, database.timetableDao().findAll().size)
        assertEquals(0, database.medicineDao().findAll().size)
        assertEquals(1, database.medicineUnitDao().findAll().size)
        assertEquals(0, database.personMediRelationDao().findAll().size)
        assertEquals(0, database.mediTimeRelationDao().findAll().size)
        assertEquals(0, database.scheduleDao().findAll().size)

        // call target method
        medicineRegisterService.registerMedicine(medicine = medicine1, personIdType = person1.mPersonId)

        // after method call
        assertEquals(2, database.personDao().findAll().size)
        assertEquals(15, database.timetableDao().findAll().size)
        assertEquals(1, database.medicineDao().findAll().size)
        assertEquals(2, database.medicineUnitDao().findAll().size)
        assertEquals(1, database.personMediRelationDao().findAll().size)
        assertEquals(3, database.mediTimeRelationDao().findAll().size)
        assertEquals(3, database.scheduleDao().findAll().size)

        // call target method
        medicineRegisterService.registerMedicine(medicine = medicine2, personIdType = person1.mPersonId)

        // after method call
        assertEquals(2, database.personDao().findAll().size)
        assertEquals(15, database.timetableDao().findAll().size)
        assertEquals(2, database.medicineDao().findAll().size)
        assertEquals(2, database.medicineUnitDao().findAll().size)
        assertEquals(2, database.personMediRelationDao().findAll().size)
        assertEquals(4, database.mediTimeRelationDao().findAll().size)
        assertEquals(4, database.scheduleDao().findAll().size)
    }
}
