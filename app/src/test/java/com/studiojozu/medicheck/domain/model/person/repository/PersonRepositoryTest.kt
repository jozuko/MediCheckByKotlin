package com.studiojozu.medicheck.domain.model.person.repository

import com.studiojozu.medicheck.di.MediCheckTestApplication
import com.studiojozu.medicheck.domain.model.person.*
import com.studiojozu.medicheck.domain.model.setting.ATestParent
import com.studiojozu.medicheck.infrastructure.persistence.entity.SqlitePerson
import org.junit.Assert
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
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
class PersonRepositoryTest : ATestParent() {

    @Inject
    lateinit var personRepository: PersonRepository

    @Before
    fun setUp() = (RuntimeEnvironment.application as MediCheckTestApplication).mAppTestComponent.inject(this)

    @Test
    @Throws(Exception::class)
    fun crud() {
        val defaultPerson = Person(
                personName = PersonNameType("Myself"),
                personPhoto = PersonPhotoType(""),
                personDisplayOrder = PersonDisplayOrderType(1))

        // select no data
        var persons = personRepository.findAll()
        Assert.assertNotNull(persons)
        Assert.assertEquals(1, persons.size)
        assertIgnoreId(defaultPerson, persons[0])

        // insert
        val insertEntity = Person(personDisplayOrder = PersonDisplayOrderType(2))
        personRepository.insert(insertEntity)
        persons = personRepository.findAll()
        Assert.assertEquals(2, persons.size)
        assertIgnoreId(defaultPerson, persons[0])
        assert(insertEntity, persons[1])

        // update
        val updateEntity = insertEntity.copy(personName = PersonNameType("Jozuko Dev"))
        personRepository.insert(updateEntity)
        persons = personRepository.findAll()
        Assert.assertEquals(2, persons.size)
        assertIgnoreId(defaultPerson, persons[0])
        assert(updateEntity, persons[1])

        // delete
        val deleteEntity = insertEntity.copy()
        personRepository.delete(deleteEntity)
        persons = personRepository.findAll()
        Assert.assertEquals(1, persons.size)
        assertIgnoreId(defaultPerson, persons[0])
    }

    @Test
    @Throws(Exception::class)
    fun findById() {
        // insert
        val insertEntity = Person(personDisplayOrder = PersonDisplayOrderType(2))
        personRepository.insert(insertEntity)

        // findById
        val entity1 = personRepository.findById(insertEntity.personId)!!
        assert(insertEntity, entity1)

        // findById
        val entity2 = personRepository.findById(PersonIdType("unknown id"))
        Assert.assertNull(entity2)

        // delete
        val deleteMedicineEntity = insertEntity.copy()
        personRepository.delete(deleteMedicineEntity)
    }

    @Test
    @Throws(Exception::class)
    fun existPersonById() {
        // insert
        val insertEntity = Person(personDisplayOrder = PersonDisplayOrderType(2))
        personRepository.insert(insertEntity)

        // findById
        assertTrue(personRepository.existPersonById(insertEntity.personId))

        // findById
        assertFalse(personRepository.existPersonById(PersonIdType("unknown id")))

        // delete
        personRepository.delete(insertEntity)
    }

    @Test
    @Throws(Exception::class)
    fun maxDisplayOrder() {

        personRepository.delete(Person(personId = PersonIdType(SqlitePerson.DEFAULT_PERSON_ID)))

        // maxDisplayOrder
        Assert.assertEquals(0L, personRepository.maxDisplayOrder())

        // insert
        val insertEntity = Person(personDisplayOrder = PersonDisplayOrderType(2))
        personRepository.insert(insertEntity)

        // maxDisplayOrder
        Assert.assertEquals(2L, personRepository.maxDisplayOrder())

        // delete
        val deleteMedicineEntity = insertEntity.copy()
        personRepository.delete(deleteMedicineEntity)
    }

    private fun assert(expect: Person, actual: Person) {
        Assert.assertEquals(expect.personId, actual.personId)
        Assert.assertEquals(expect.personName, actual.personName)
        Assert.assertEquals(expect.personPhoto, actual.personPhoto)
        Assert.assertEquals(expect.personDisplayOrder, actual.personDisplayOrder)
    }

    private fun assertIgnoreId(expect: Person, actual: Person) {
        Assert.assertEquals(expect.personName, actual.personName)
        Assert.assertEquals(expect.personPhoto, actual.personPhoto)
        Assert.assertEquals(expect.personDisplayOrder, actual.personDisplayOrder)
    }

}