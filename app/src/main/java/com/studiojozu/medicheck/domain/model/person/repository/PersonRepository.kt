package com.studiojozu.medicheck.domain.model.person.repository

import android.app.Application
import com.studiojozu.medicheck.R
import com.studiojozu.medicheck.domain.model.person.*
import com.studiojozu.medicheck.infrastructure.persistence.dao.SqlitePersonRepository
import com.studiojozu.medicheck.infrastructure.persistence.entity.SqlitePerson
import com.studiojozu.medicheck.infrastructure.persistence.preference.PreferencePersonRepository

class PersonRepository(
        private val application: Application,
        private val sqlitePersonRepository: SqlitePersonRepository,
        private val preferencePersonRepository: PreferencePersonRepository) {

    val latestUsedPerson: Person
        get() {
            val personIdType = preferencePersonRepository.latestUsedPersonId
            if (personIdType != null) {
                findById(personIdType = personIdType)?.let { return it }
            }

            var person = sqlitePersonRepository.findById(SqlitePerson.DEFAULT_PERSON_ID)?.toPerson()
            if (person == null)
                person = defaultPerson

            preferencePersonRepository.latestUsedPersonId = person.mPersonId
            return person
        }

    private val defaultPerson: Person
        get() {
            val displayOrder = sqlitePersonRepository.maxDisplayOrder() + 1
            val person = Person(
                    mPersonId = PersonIdType(SqlitePerson.DEFAULT_PERSON_ID),
                    mPersonName = PersonNameType(application.applicationContext.resources.getString(R.string.person_self)),
                    mPersonPhoto = PersonPhotoType(""),
                    mPersonDisplayOrder = PersonDisplayOrderType(displayOrder))
            insert(person)

            return person
        }

    val size: Int
        get() = findAll().size

    fun findById(personIdType: PersonIdType): Person? {
        val person = sqlitePersonRepository.findById(personId = personIdType.dbValue) ?: return null
        return person.toPerson()
    }

    fun findAll(): List<Person> =
            sqlitePersonRepository.findAll().map { it.toPerson() }

    fun existPersonById(personIdType: PersonIdType): Boolean =
            sqlitePersonRepository.findById(personId = personIdType.dbValue) != null

    fun insert(person: Person) {
        sqlitePersonRepository.insert(SqlitePerson.build { mPerson = person })
    }

    fun delete(person: Person) {
        sqlitePersonRepository.delete(SqlitePerson.build { mPerson = person })
    }
}
