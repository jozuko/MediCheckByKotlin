package com.studiojozu.medicheck.domain.model.person.repository

import com.studiojozu.medicheck.domain.model.person.Person
import com.studiojozu.medicheck.domain.model.person.PersonIdType
import com.studiojozu.medicheck.infrastructure.persistence.dao.SqlitePersonRepository
import com.studiojozu.medicheck.infrastructure.persistence.entity.SqlitePerson

class PersonRepository(private val sqlitePersonRepository: SqlitePersonRepository) {

    fun findById(personIdType: PersonIdType): Person? {
        val person = sqlitePersonRepository.findById(personId = personIdType.dbValue) ?: return null
        return person.toPerson()
    }

    fun findAll(): List<Person> =
            sqlitePersonRepository.findAll().map { it.toPerson() }

    fun existPersonById(personIdType: PersonIdType): Boolean =
            sqlitePersonRepository.findById(personId = personIdType.dbValue) != null

    fun maxDisplayOrder() =
            sqlitePersonRepository.maxDisplayOrder()

    fun insert(person: Person) =
            sqlitePersonRepository.insert(SqlitePerson.build { this.person = person })

    fun delete(person: Person) =
            sqlitePersonRepository.delete(SqlitePerson.build { this.person = person })
}
