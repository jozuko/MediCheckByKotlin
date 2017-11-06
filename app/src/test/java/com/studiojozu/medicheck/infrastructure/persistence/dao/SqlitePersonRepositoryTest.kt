package com.studiojozu.medicheck.infrastructure.persistence.dao

import com.studiojozu.medicheck.domain.model.person.Person
import com.studiojozu.medicheck.domain.model.person.PersonNameType
import com.studiojozu.medicheck.domain.model.setting.ATestParent
import com.studiojozu.medicheck.infrastructure.persistence.database.AppDatabase
import com.studiojozu.medicheck.infrastructure.persistence.entity.SqlitePerson
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
class SqlitePersonRepositoryTest : ATestParent() {

    @Test
    @Throws(Exception::class)
    fun crud() {
        val database = AppDatabase.getAppDatabase(RuntimeEnvironment.application.applicationContext)
        val dao = database.personDao()

        // select no data
        var persons = dao.findAll()
        assertNotNull(persons)
        assertEquals(0, persons.size)

        // insert
        val insertEntity = Person()
        dao.insert(setSqlitePerson(insertEntity))
        persons = dao.findAll()
        assertEquals(1, persons.size)
        assert(insertEntity, persons[0])

        // update
        val updateEntity = insertEntity.copy(mPersonName = PersonNameType("Jozuko Dev"))
        dao.insert(setSqlitePerson(updateEntity))
        persons = dao.findAll()
        assertEquals(1, persons.size)
        assert(updateEntity, persons[0])

        // delete
        val deleteEntity = insertEntity.copy()
        dao.delete(setSqlitePerson(deleteEntity))
        persons = dao.findAll()
        assertEquals(0, persons.size)
    }

    @Test
    @Throws(Exception::class)
    fun findById() {
        val database = AppDatabase.getAppDatabase(RuntimeEnvironment.application.applicationContext)
        val dao = database.personDao()

        // insert
        val insertEntity = Person()
        dao.insert(setSqlitePerson(insertEntity))

        // findById
        val medicine1 = dao.findById(insertEntity.mPersonId.dbValue)
        assert(insertEntity, medicine1!!)

        // findById
        val medicine2 = dao.findById("unknown id")
        assertNull(medicine2)

        // delete
        val deleteMedicineEntity = insertEntity.copy()
        dao.delete(setSqlitePerson(deleteMedicineEntity))
    }

    private fun setSqlitePerson(entity: Person): SqlitePerson {
        val medicine = SqlitePerson(personId = entity.mPersonId.dbValue)
        medicine.mPersonName = entity.mPersonName.dbValue
        medicine.mPersonPhoto = entity.mPersonPhoto.dbValue
        medicine.mPersonDisplayOrder = entity.mPersonDisplayOrder.dbValue

        return medicine
    }

    private fun assert(expect: Person, actual: SqlitePerson) {
        assertEquals(expect.mPersonId.dbValue, actual.mPersonId)
        assertEquals(expect.mPersonName.dbValue, actual.mPersonName)
        assertEquals(expect.mPersonPhoto.dbValue, actual.mPersonPhoto)
        assertEquals(expect.mPersonDisplayOrder.dbValue, actual.mPersonDisplayOrder)
    }
}