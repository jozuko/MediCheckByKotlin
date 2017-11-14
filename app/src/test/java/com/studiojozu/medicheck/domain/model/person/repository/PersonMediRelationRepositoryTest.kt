package com.studiojozu.medicheck.domain.model.person.repository

import com.studiojozu.medicheck.di.MediCheckTestApplication
import com.studiojozu.medicheck.domain.model.medicine.*
import com.studiojozu.medicheck.domain.model.person.Person
import com.studiojozu.medicheck.domain.model.person.PersonNameType
import com.studiojozu.medicheck.domain.model.setting.ATestParent
import com.studiojozu.medicheck.infrastructure.persistence.database.AppDatabase
import com.studiojozu.medicheck.infrastructure.persistence.entity.SqliteMedicine
import com.studiojozu.medicheck.infrastructure.persistence.entity.SqliteMedicineUnit
import com.studiojozu.medicheck.infrastructure.persistence.entity.SqlitePerson
import org.junit.Assert.*
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
class PersonMediRelationRepositoryTest : ATestParent() {

    @Inject
    lateinit var dao: PersonMediRelationRepository

    @Before
    fun setUp() = (RuntimeEnvironment.application as MediCheckTestApplication).mAppTestComponent.inject(this)

    private val medicineUnit1 = setSqliteMedicineUnit(getMedicineUnit("錠"))
    private val medicine1 = setSqliteMedicine(getMedicine("メルカゾール", medicineUnit1.mMedicineUnitId.dbValue))
    private val medicine2 = setSqliteMedicine(getMedicine("Previcox", medicineUnit1.mMedicineUnitId.dbValue))
    private val person1 = setSqlitePerson(getPerson("Jozuko Dev"))
    private val person2 = setSqlitePerson(getPerson("Luke"))

    @Test
    @Throws(Exception::class)
    fun crud() {
        val database = AppDatabase.getAppDatabase(RuntimeEnvironment.application.applicationContext)
        addData(database)

        // insert
        dao.insert(person1.mPersonId, medicine1.mMedicineId)
        var person = dao.findPersonByMedicineId(medicine1.mMedicineId)!!
        var medicines = dao.findMedicineByPersonId(person.mPersonId)
        assertEquals(person1.mPersonId, person.mPersonId)
        assertEquals(1, medicines.size)
        assertEquals(medicine1.mMedicineId, medicines[0].mMedicineId)

        // update
        dao.insert(person1.mPersonId, medicine1.mMedicineId)
        person = dao.findPersonByMedicineId(medicine1.mMedicineId)!!
        medicines = dao.findMedicineByPersonId(person.mPersonId)
        assertEquals(person1.mPersonId, person.mPersonId)
        assertEquals(1, medicines.size)
        assertEquals(medicine1.mMedicineId, medicines[0].mMedicineId)

        // delete
        dao.deleteByMedicineId(medicine1.mMedicineId)
        assertNull(dao.findPersonByMedicineId(medicine1.mMedicineId))

        removeData(database)
    }

    @Test
    @Throws(Exception::class)
    fun existByPersonIdMedicineId() {
        val database = AppDatabase.getAppDatabase(RuntimeEnvironment.application.applicationContext)
        addData(database)

        // insert
        dao.insert(person1.mPersonId, medicine1.mMedicineId)

        // existByPersonIdMedicineId
        assertTrue(dao.existByPersonIdMedicineId(person1.mPersonId, medicine1.mMedicineId))
        assertFalse(dao.existByPersonIdMedicineId(person1.mPersonId, medicine2.mMedicineId))
        assertFalse(dao.existByPersonIdMedicineId(person2.mPersonId, medicine2.mMedicineId))

        removeData(database)
    }

    private fun getMedicineUnit(value: String = ""): MedicineUnit =
            MedicineUnit(mMedicineUnitValue = MedicineUnitValueType(value))

    private fun getMedicine(name: String = "", unitId: String = MedicineUnitIdType().dbValue): Medicine =
            Medicine(
                    mMedicineName = MedicineNameType(name),
                    mMedicineUnit = MedicineUnit(mMedicineUnitId = MedicineUnitIdType(unitId)))

    private fun getPerson(name: String = ""): Person =
            Person(mPersonName = PersonNameType(name))

    private fun setSqliteMedicine(entity: Medicine): SqliteMedicine =
            SqliteMedicine.build { mMedicine = entity }

    private fun setSqliteMedicineUnit(entity: MedicineUnit): SqliteMedicineUnit =
            SqliteMedicineUnit.build { mMedicineUnit = entity }

    private fun setSqlitePerson(entity: Person): SqlitePerson =
            SqlitePerson.build { mPerson = entity }

    private fun addData(database: AppDatabase) {
        database.medicineDao().insert(medicine1)
        database.medicineDao().insert(medicine2)

        database.medicineUnitDao().insert(medicineUnit1)

        database.personDao().insert(person1)
        database.personDao().insert(person2)
    }

    private fun removeData(database: AppDatabase) {
        database.medicineDao().delete(medicine1)
        database.medicineDao().delete(medicine2)

        database.medicineUnitDao().delete(medicineUnit1)

        database.personDao().delete(person1)
        database.personDao().delete(person2)
    }
}