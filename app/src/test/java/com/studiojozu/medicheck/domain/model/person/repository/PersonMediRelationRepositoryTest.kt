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
    private val medicine1 = setSqliteMedicine(getMedicine("メルカゾール", medicineUnit1.medicineUnitId.dbValue))
    private val medicine2 = setSqliteMedicine(getMedicine("Previcox", medicineUnit1.medicineUnitId.dbValue))
    private val person1 = setSqlitePerson(getPerson("Jozuko Dev"))
    private val person2 = setSqlitePerson(getPerson("Luke"))

    @Test
    @Throws(Exception::class)
    fun crud() {
        val database = AppDatabase.getAppDatabase(RuntimeEnvironment.application.applicationContext)
        addData(database)

        // insert
        dao.insert(person1.personId, medicine1.medicineId)
        var person = dao.findPersonByMedicineId(medicine1.medicineId)!!
        var medicines = dao.findMedicineByPersonId(person.personId)
        assertEquals(person1.personId, person.personId)
        assertEquals(1, medicines.size)
        assertEquals(medicine1.medicineId, medicines[0].medicineId)

        // update
        dao.insert(person1.personId, medicine1.medicineId)
        person = dao.findPersonByMedicineId(medicine1.medicineId)!!
        medicines = dao.findMedicineByPersonId(person.personId)
        assertEquals(person1.personId, person.personId)
        assertEquals(1, medicines.size)
        assertEquals(medicine1.medicineId, medicines[0].medicineId)

        // delete
        dao.deleteByMedicineId(medicine1.medicineId)
        assertNull(dao.findPersonByMedicineId(medicine1.medicineId))

        removeData(database)
    }

    @Test
    @Throws(Exception::class)
    fun existByPersonIdMedicineId() {
        val database = AppDatabase.getAppDatabase(RuntimeEnvironment.application.applicationContext)
        addData(database)

        // insert
        dao.insert(person1.personId, medicine1.medicineId)

        // existByPersonIdMedicineId
        assertTrue(dao.existByPersonIdMedicineId(person1.personId, medicine1.medicineId))
        assertFalse(dao.existByPersonIdMedicineId(person1.personId, medicine2.medicineId))
        assertFalse(dao.existByPersonIdMedicineId(person2.personId, medicine2.medicineId))

        removeData(database)
    }

    private fun getMedicineUnit(value: String = ""): MedicineUnit =
            MedicineUnit(medicineUnitValue = MedicineUnitValueType(value))

    private fun getMedicine(name: String = "", unitId: String = MedicineUnitIdType().dbValue): Medicine =
            Medicine(
                    medicineName = MedicineNameType(name),
                    medicineUnit = MedicineUnit(medicineUnitId = MedicineUnitIdType(unitId)))

    private fun getPerson(name: String = ""): Person =
            Person(personName = PersonNameType(name))

    private fun setSqliteMedicine(entity: Medicine): SqliteMedicine =
            SqliteMedicine.build { medicine = entity }

    private fun setSqliteMedicineUnit(entity: MedicineUnit): SqliteMedicineUnit =
            SqliteMedicineUnit.build { medicineUnit = entity }

    private fun setSqlitePerson(entity: Person): SqlitePerson =
            SqlitePerson.build { person = entity }

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