package com.studiojozu.medicheck.infrastructure.persistence.dao

import com.studiojozu.medicheck.domain.model.medicine.*
import com.studiojozu.medicheck.domain.model.person.Person
import com.studiojozu.medicheck.domain.model.person.PersonIdType
import com.studiojozu.medicheck.domain.model.person.PersonNameType
import com.studiojozu.medicheck.domain.model.setting.ATestParent
import com.studiojozu.medicheck.infrastructure.persistence.database.AppDatabase
import com.studiojozu.medicheck.infrastructure.persistence.entity.*
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.powermock.core.classloader.annotations.PowerMockIgnore
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = "src/main/AndroidManifest.xml")
@PowerMockIgnore("org.mockito.*", "org.robolectric.*", "android.*")
class SqlitePersonMediRelationRepositoryTest : ATestParent() {
    private val medicineUnit1 = setSqliteMedicineUnit(getMedicineUnit("錠"))

    private val medicine1 = setSqliteMedicine(getMedicine("メルカゾール", medicineUnit1.mMedicineUnitId.dbValue))
    private val medicine2 = setSqliteMedicine(getMedicine("Previcox", medicineUnit1.mMedicineUnitId.dbValue))
    private val medicine3 = setSqliteMedicine(getMedicine("ビオイムバスター", medicineUnit1.mMedicineUnitId.dbValue))
    private val medicine4 = setSqliteMedicine(getMedicine("フィラリア-1", medicineUnit1.mMedicineUnitId.dbValue))
    private val medicine5 = setSqliteMedicine(getMedicine("フィラリア-2", medicineUnit1.mMedicineUnitId.dbValue))
    private val medicine6 = setSqliteMedicine(getMedicine("フィラリア-3", medicineUnit1.mMedicineUnitId.dbValue))

    private val person1 = setSqlitePerson(getPerson("Jozuko Dev"))
    private val person2 = setSqlitePerson(getPerson("Luke"))
    private val person3 = setSqlitePerson(getPerson("Chase"))
    private val person4 = setSqlitePerson(getPerson("Arron"))

    private val relation1 = setSqlitePersonMediRelation(person = person1, medicine = medicine1)
    private val relation2 = setSqlitePersonMediRelation(person = person2, medicine = medicine2)
    private val relation3 = setSqlitePersonMediRelation(person = person2, medicine = medicine3)
    private val relation4 = setSqlitePersonMediRelation(person = person2, medicine = medicine4)
    private val relation5 = setSqlitePersonMediRelation(person = person3, medicine = medicine5)
    private val relation6 = setSqlitePersonMediRelation(person = person4, medicine = medicine6)

    @Test
    @Throws(Exception::class)
    fun crud() {
        val database = AppDatabase.getAppDatabase(RuntimeEnvironment.application.applicationContext)
        val dao = database.personMediRelationDao()

        // select no data
        var entities = dao.findAll()
        assertNotNull(entities)
        assertEquals(0, entities.size)

        // insert
        val insertData = SqlitePersonMediRelation(
                medicineId = medicine1.mMedicineId,
                personId = person1.mPersonId)
        dao.insert(insertData)

        entities = dao.findAll()
        assertEquals(1, entities.size)
        assertEquals(medicine1.mMedicineId, entities[0].mMedicineId)
        assertEquals(person1.mPersonId, entities[0].mPersonId)

        // update
        val updateData = SqlitePersonMediRelation(
                medicineId = insertData.mMedicineId,
                personId = insertData.mPersonId)
        dao.insert(updateData)

        entities = dao.findAll()
        assertEquals(1, entities.size)
        assertEquals(updateData.mMedicineId, entities[0].mMedicineId)
        assertEquals(updateData.mPersonId, entities[0].mPersonId)

        // delete
        dao.delete(insertData)
        entities = dao.findAll()
        assertEquals(0, entities.size)
    }

    @Test
    @Throws(Exception::class)
    fun insertThreeRecords() {
        val database = AppDatabase.getAppDatabase(RuntimeEnvironment.application.applicationContext)
        val dao = database.personMediRelationDao()

        // select no data
        assertEquals(0, dao.findAll().size)

        // insert
        val insertData1 = SqlitePersonMediRelation(
                medicineId = MedicineIdType(),
                personId = PersonIdType())
        dao.insert(insertData1)

        val insertData2 = SqlitePersonMediRelation(
                medicineId = MedicineIdType(),
                personId = insertData1.mPersonId)
        dao.insert(insertData2)

        val insertData3 = SqlitePersonMediRelation(
                medicineId = insertData1.mMedicineId,
                personId = PersonIdType())
        dao.insert(insertData3)

        assertEquals(3, dao.findAll().size)

        // delete
        dao.delete(insertData1)
        assertEquals(2, dao.findAll().size)

        dao.delete(insertData2)
        assertEquals(1, dao.findAll().size)

        dao.delete(insertData3)
        assertEquals(0, dao.findAll().size)
    }

    @Test
    @Throws(Exception::class)
    fun findPersonByMedicineId() {
        val database = AppDatabase.getAppDatabase(RuntimeEnvironment.application.applicationContext)
        val dao = database.personMediRelationDao()

        // select not data
        var personMedicine = dao.findPersonByMedicineId("")
        assertNull(personMedicine)

        // addData
        addData(database)

        // findByMedicineId
        personMedicine = dao.findPersonByMedicineId(medicine1.mMedicineId.dbValue)!!
        assertPerson(person1, personMedicine)

        // findByMedicineId
        personMedicine = dao.findPersonByMedicineId(medicine2.mMedicineId.dbValue)!!
        assertPerson(person2, personMedicine)

        // delete data
        removeData(database)
    }

    @Test
    @Throws(Exception::class)
    fun findMedicineByPersonId() {
        val database = AppDatabase.getAppDatabase(RuntimeEnvironment.application.applicationContext)
        val dao = database.personMediRelationDao()

        // select not data
        var medicineArray = dao.findMedicineByPersonId("")
        assertEquals(0, medicineArray.size)

        // addData
        addData(database)

        // findByPersonId
        medicineArray = dao.findMedicineByPersonId(person1.mPersonId.dbValue)
        assertEquals(1, medicineArray.size)
        assertMedicine(medicine1, medicineUnit1, medicineArray[0])

        // findByPersonId
        medicineArray = dao.findMedicineByPersonId(person2.mPersonId.dbValue)
        assertEquals(3, medicineArray.size)
        assertMedicine(medicine2, medicineUnit1, medicineArray[0])
        assertMedicine(medicine3, medicineUnit1, medicineArray[1])
        assertMedicine(medicine4, medicineUnit1, medicineArray[2])

        // delete data
        removeData(database)
    }

    @Test
    @Throws(Exception::class)
    fun deleteByMedicineId() {
        val database = AppDatabase.getAppDatabase(RuntimeEnvironment.application.applicationContext)
        val dao = database.personMediRelationDao()

        // addData
        addData(database)

        // deleteByMedicineId
        assertEquals(6, dao.findAll().size)
        dao.deleteByMedicineId("")
        assertEquals(6, dao.findAll().size)

        dao.deleteByMedicineId(medicine6.mMedicineId.dbValue)
        val relationArray = dao.findAll()
        assertEquals(5, relationArray.size)
        assertEquals(medicine1.mMedicineId, relationArray[0].mMedicineId)
        assertEquals(person1.mPersonId, relationArray[0].mPersonId)

        assertEquals(medicine2.mMedicineId, relationArray[1].mMedicineId)
        assertEquals(person2.mPersonId, relationArray[1].mPersonId)

        assertEquals(medicine3.mMedicineId, relationArray[2].mMedicineId)
        assertEquals(person2.mPersonId, relationArray[2].mPersonId)

        assertEquals(medicine4.mMedicineId, relationArray[3].mMedicineId)
        assertEquals(person2.mPersonId, relationArray[3].mPersonId)

        assertEquals(medicine5.mMedicineId, relationArray[4].mMedicineId)
        assertEquals(person3.mPersonId, relationArray[4].mPersonId)

        // delete data
        removeData(database)
    }

    private fun setSqliteMedicine(entity: Medicine): SqliteMedicine =
            SqliteMedicine.build { mMedicine = entity }

    private fun setSqliteMedicineUnit(entity: MedicineUnit): SqliteMedicineUnit =
            SqliteMedicineUnit.build { mMedicineUnit = entity }

    private fun setSqlitePerson(entity: Person): SqlitePerson =
            SqlitePerson.build { mPerson = entity }

    private fun setSqlitePersonMediRelation(person: SqlitePerson, medicine: SqliteMedicine): SqlitePersonMediRelation =
            SqlitePersonMediRelation.build {
                mPersonId = person.mPersonId
                mMedicineId = medicine.mMedicineId
            }

    private fun assertMedicine(expectMedicine: SqliteMedicine, expectMedicineUnit: SqliteMedicineUnit, actual: SqliteMedicineMedicineUnit) {
        assertEquals(expectMedicine.mMedicineId, actual.mMedicineId)
        assertEquals(expectMedicine.mMedicineName, actual.mMedicineName)
        assertEquals(expectMedicine.mMedicineTakeNumber, actual.mMedicineTakeNumber)
        assertEquals(expectMedicine.mMedicineUnitId, actual.mMedicineUnitId)
        assertEquals(expectMedicine.mMedicineDateNumber, actual.mMedicineDateNumber)
        assertEquals(expectMedicine.mMedicineStartDatetime, actual.mMedicineStartDatetime)
        assertEquals(expectMedicine.mMedicineInterval, actual.mMedicineInterval)
        assertEquals(expectMedicine.mMedicineIntervalMode, actual.mMedicineIntervalMode)
        assertEquals(expectMedicine.mMedicinePhoto, actual.mMedicinePhoto)
        assertEquals(expectMedicine.mMedicineNeedAlarm, actual.mMedicineNeedAlarm)
        assertEquals(expectMedicine.mMedicineDeleteFlag, actual.mMedicineDeleteFlag)

        assertEquals(expectMedicineUnit.mMedicineUnitValue, actual.mMedicineUnitValue)
        assertEquals(expectMedicineUnit.mMedicineUnitDisplayOrder, actual.mMedicineUnitDisplayOrder)
    }

    private fun assertPerson(expectPerson: SqlitePerson, actual: SqlitePerson) {
        assertEquals(expectPerson.mPersonId, actual.mPersonId)
        assertEquals(expectPerson.mPersonName, actual.mPersonName)
        assertEquals(expectPerson.mPersonPhoto, actual.mPersonPhoto)
        assertEquals(expectPerson.mPersonDisplayOrder, actual.mPersonDisplayOrder)
    }

    private fun getMedicineUnit(value: String = ""): MedicineUnit =
            MedicineUnit(mMedicineUnitValue = MedicineUnitValueType(value))

    private fun getMedicine(name: String = "", unitId: String = MedicineUnitIdType().dbValue): Medicine =
            Medicine(
                    mMedicineName = MedicineNameType(name),
                    mMedicineUnit = MedicineUnit(mMedicineUnitId = MedicineUnitIdType(unitId)))

    private fun getPerson(name: String = ""): Person =
            Person(mPersonName = PersonNameType(name))

    private fun addData(database: AppDatabase) {
        database.medicineDao().insert(medicine1)
        database.medicineDao().insert(medicine2)
        database.medicineDao().insert(medicine3)
        database.medicineDao().insert(medicine4)
        database.medicineDao().insert(medicine5)
        database.medicineDao().insert(medicine6)

        database.medicineUnitDao().insert(medicineUnit1)

        database.personDao().insert(person1)
        database.personDao().insert(person2)
        database.personDao().insert(person3)
        database.personDao().insert(person4)

        database.personMediRelationDao().insert(relation1)
        database.personMediRelationDao().insert(relation2)
        database.personMediRelationDao().insert(relation3)
        database.personMediRelationDao().insert(relation4)
        database.personMediRelationDao().insert(relation5)
        database.personMediRelationDao().insert(relation6)
    }

    private fun removeData(database: AppDatabase) {
        database.medicineDao().delete(medicine1)
        database.medicineDao().delete(medicine2)
        database.medicineDao().delete(medicine3)
        database.medicineDao().delete(medicine4)
        database.medicineDao().delete(medicine5)
        database.medicineDao().delete(medicine6)

        database.medicineUnitDao().delete(medicineUnit1)

        database.personDao().delete(person1)
        database.personDao().delete(person2)
        database.personDao().delete(person3)
        database.personDao().delete(person4)

        database.personMediRelationDao().delete(relation1)
        database.personMediRelationDao().delete(relation2)
        database.personMediRelationDao().delete(relation3)
        database.personMediRelationDao().delete(relation4)
        database.personMediRelationDao().delete(relation5)
        database.personMediRelationDao().delete(relation6)
    }
}