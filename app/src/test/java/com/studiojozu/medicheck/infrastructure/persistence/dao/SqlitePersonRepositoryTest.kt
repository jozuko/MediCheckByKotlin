package com.studiojozu.medicheck.infrastructure.persistence.dao

import com.studiojozu.medicheck.domain.model.person.*
import com.studiojozu.medicheck.domain.model.setting.ATestParent
import com.studiojozu.medicheck.infrastructure.persistence.database.AppDatabase
import com.studiojozu.medicheck.infrastructure.persistence.entity.SqlitePerson
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = "src/main/AndroidManifest.xml")
class SqlitePersonRepositoryTest : ATestParent() {

    @Test
    @Throws(Exception::class)
    fun crud() {
        val database = AppDatabase.getAppDatabase(RuntimeEnvironment.application.applicationContext)
        val dao = database.personDao()
        val defaultPerson = Person(mPersonName = PersonNameType("Myself"), mPersonPhoto = PersonPhotoType(""), mPersonDisplayOrder = PersonDisplayOrderType(1))

        // select no data
        var persons = dao.findAll()
        assertNotNull(persons)
        assertEquals(1, persons.size)
        assertIgnoreId(defaultPerson, persons[0])

        // insert
        val insertEntity = Person(mPersonDisplayOrder = PersonDisplayOrderType(2))
        dao.insert(setSqlitePerson(insertEntity))
        persons = dao.findAll()
        assertEquals(2, persons.size)
        assertIgnoreId(defaultPerson, persons[0])
        assert(insertEntity, persons[1])

        // update
        val updateEntity = insertEntity.copy(mPersonName = PersonNameType("Jozuko Dev"))
        dao.insert(setSqlitePerson(updateEntity))
        persons = dao.findAll()
        assertEquals(2, persons.size)
        assertIgnoreId(defaultPerson, persons[0])
        assert(updateEntity, persons[1])

        // delete
        val deleteEntity = insertEntity.copy()
        dao.delete(setSqlitePerson(deleteEntity))
        persons = dao.findAll()
        assertEquals(1, persons.size)
        assertIgnoreId(defaultPerson, persons[0])
    }

    @Test
    @Throws(Exception::class)
    fun findById() {
        val database = AppDatabase.getAppDatabase(RuntimeEnvironment.application.applicationContext)
        val dao = database.personDao()

        // insert
        val insertEntity = Person(mPersonDisplayOrder = PersonDisplayOrderType(2))
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

    @Test
    @Throws(Exception::class)
    fun maxDisplayOrder() {
        val database = AppDatabase.getAppDatabase(RuntimeEnvironment.application.applicationContext)
        val dao = database.personDao()

        dao.delete(SqlitePerson.build { mPerson = Person(mPersonId = PersonIdType(SqlitePerson.DEFAULT_PERSON_ID)) })

        // maxDisplayOrder
        assertEquals(0L, dao.maxDisplayOrder())

        // insert
        val insertEntity = Person(mPersonDisplayOrder = PersonDisplayOrderType(2))
        dao.insert(setSqlitePerson(insertEntity))

        // maxDisplayOrder
        assertEquals(2L, dao.maxDisplayOrder())

        // delete
        val deleteMedicineEntity = insertEntity.copy()
        dao.delete(setSqlitePerson(deleteMedicineEntity))
    }

    private fun setSqlitePerson(entity: Person): SqlitePerson =
            SqlitePerson.build { mPerson = entity }

    private fun assert(expect: Person, actual: SqlitePerson) {
        assertEquals(expect.mPersonId, actual.mPersonId)
        assertEquals(expect.mPersonName, actual.mPersonName)
        assertEquals(expect.mPersonPhoto, actual.mPersonPhoto)
        assertEquals(expect.mPersonDisplayOrder, actual.mPersonDisplayOrder)
    }

    private fun assertIgnoreId(expect: Person, actual: SqlitePerson) {
        assertEquals(expect.mPersonName, actual.mPersonName)
        assertEquals(expect.mPersonPhoto, actual.mPersonPhoto)
        assertEquals(expect.mPersonDisplayOrder, actual.mPersonDisplayOrder)
    }
}