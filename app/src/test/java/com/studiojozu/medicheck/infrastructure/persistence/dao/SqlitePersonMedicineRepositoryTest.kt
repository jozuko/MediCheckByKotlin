package com.studiojozu.medicheck.infrastructure.persistence.dao

import com.studiojozu.medicheck.domain.model.medicine.*
import com.studiojozu.medicheck.domain.model.person.Person
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
class SqlitePersonMedicineRepositoryTest : ATestParent() {
    private val medicineUnit1 = setSqliteMedicineUnit(getMedicineUnit("錠"))

    private val medicine1 = setSqliteMedicine(getMedicine("メルカゾール", medicineUnit1.mMedicineUnitId))
    private val medicine2 = setSqliteMedicine(getMedicine("Previcox", medicineUnit1.mMedicineUnitId))
    private val medicine3 = setSqliteMedicine(getMedicine("ビオイムバスター", medicineUnit1.mMedicineUnitId))
    private val medicine4 = setSqliteMedicine(getMedicine("フィラリア-1", medicineUnit1.mMedicineUnitId))
    private val medicine5 = setSqliteMedicine(getMedicine("フィラリア-2", medicineUnit1.mMedicineUnitId))
    private val medicine6 = setSqliteMedicine(getMedicine("フィラリア-3", medicineUnit1.mMedicineUnitId))

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
    fun findByMedicineId() {
        val database = AppDatabase.getAppDatabase(RuntimeEnvironment.application.applicationContext)
        val dao = database.personMedicineDao()

        // select not data
        var personMedicine = dao.findByMedicineId("")
        assertNull(personMedicine)

        // addData
        addData(database)

        // findByMedicineId
        personMedicine = dao.findByMedicineId(medicine1.mMedicineId)
        assertNotNull(personMedicine)
        assert(medicine1, medicineUnit1, person1, personMedicine!!)

        // findByMedicineId
        personMedicine = dao.findByMedicineId(medicine2.mMedicineId)
        assertNotNull(personMedicine)
        assert(medicine2, medicineUnit1, person2, personMedicine!!)

        // delete data
        removeData(database)
    }

    @Test
    @Throws(Exception::class)
    fun findByPersonId() {
        val database = AppDatabase.getAppDatabase(RuntimeEnvironment.application.applicationContext)
        val dao = database.personMedicineDao()

        // select not data
        var personMedicineArray = dao.findByPersonId("")
        assertEquals(0, personMedicineArray.size)

        // addData
        addData(database)

        // findByPersonId
        personMedicineArray = dao.findByPersonId(person1.mPersonId)
        assertEquals(1, personMedicineArray.size)
        assert(medicine1, medicineUnit1, person1, personMedicineArray[0])

        // findByPersonId
        personMedicineArray = dao.findByPersonId(person2.mPersonId)
        assertEquals(3, personMedicineArray.size)
        assert(medicine2, medicineUnit1, person2, personMedicineArray[0])
        assert(medicine3, medicineUnit1, person2, personMedicineArray[1])
        assert(medicine4, medicineUnit1, person2, personMedicineArray[2])

        // delete data
        removeData(database)
    }

    private fun setSqliteMedicine(entity: Medicine): SqliteMedicine {
        val medicine = SqliteMedicine(medicineId = entity.mMedicineId.dbValue)
        medicine.mMedicineName = entity.mMedicineName.dbValue
        medicine.mMedicineTakeNumber = entity.mMedicineTakeNumber.dbValue
        medicine.mMedicineUnitId = entity.mMedicineUnit.mMedicineUnitId.dbValue
        medicine.mMedicineDateNumber = entity.mMedicineDateNumber.dbValue.toInt()
        medicine.mMedicineStartDatetime = entity.mMedicineStartDatetime.dbValue
        medicine.mMedicineInterval = entity.mMedicineInterval.dbValue.toInt()
        medicine.mMedicineIntervalMode = entity.mMedicineIntervalMode.dbValue
        medicine.mMedicinePhoto = entity.mMedicinePhoto.dbValue
        medicine.mMedicineNeedAlarm = entity.mMedicineNeedAlarm.isTrue
        medicine.mMedicineDeleteFlag = entity.mMedicineDeleteFlag.isTrue

        return medicine
    }

    private fun setSqliteMedicineUnit(entity: MedicineUnit): SqliteMedicineUnit {
        val sqliteMedicineUnit = SqliteMedicineUnit(medicineUnitId = entity.mMedicineUnitId.dbValue)
        sqliteMedicineUnit.mMedicineUnitValue = entity.mMedicineUnitValue.dbValue
        sqliteMedicineUnit.mMedicineUnitDisplayOrder = entity.mMedicineUnitDisplayOrder.dbValue

        return sqliteMedicineUnit
    }

    private fun setSqlitePerson(entity: Person): SqlitePerson {
        val medicine = SqlitePerson(personId = entity.mPersonId.dbValue)
        medicine.mPersonName = entity.mPersonName.dbValue
        medicine.mPersonPhoto = entity.mPersonPhoto.dbValue
        medicine.mPersonDisplayOrder = entity.mPersonDisplayOrder.dbValue

        return medicine
    }

    private fun setSqlitePersonMediRelation(person: SqlitePerson, medicine: SqliteMedicine): SqlitePersonMediRelation =
            SqlitePersonMediRelation(medicineId = medicine.mMedicineId, personId = person.mPersonId)

    private fun assert(expectMedicine: SqliteMedicine, expectMedicineUnit: SqliteMedicineUnit, expectPerson: SqlitePerson, actual: SqlitePersonMedicine) {
        assertEquals(expectPerson.mPersonId, actual.mPersonId)
        assertEquals(expectPerson.mPersonName, actual.mPersonName)
        assertEquals(expectPerson.mPersonPhoto, actual.mPersonPhoto)
        assertEquals(expectPerson.mPersonDisplayOrder, actual.mPersonDisplayOrder)

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