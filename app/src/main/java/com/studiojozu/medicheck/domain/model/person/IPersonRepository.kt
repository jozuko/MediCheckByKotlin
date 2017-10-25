package com.studiojozu.medicheck.domain.model.person

import android.content.Context

interface IPersonRepository {
    fun getDefaultPerson(context: Context): Person

    fun findPersonById(context: Context, personIdType: PersonIdType): Person

    fun findAll(context: Context): List<Person>

    fun existPersonById(context: Context, personIdType: PersonIdType): Boolean

    fun size(context: Context): Int

    fun add(context: Context, person: Person)

    fun remove(context: Context, personIdType: PersonIdType)
}
