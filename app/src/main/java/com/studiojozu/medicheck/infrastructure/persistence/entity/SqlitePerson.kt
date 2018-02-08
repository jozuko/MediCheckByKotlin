package com.studiojozu.medicheck.infrastructure.persistence.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import com.studiojozu.medicheck.domain.model.person.*

@Entity(tableName = "person")
open class SqlitePerson(personId: PersonIdType) {
    class Builder {
        lateinit var person: Person

        fun build(): SqlitePerson {
            val sqlitePerson = SqlitePerson(personId = person.personId)
            sqlitePerson.personName = person.personName
            sqlitePerson.personPhoto = person.personPhoto
            sqlitePerson.personDisplayOrder = person.personDisplayOrder

            return sqlitePerson
        }
    }

    companion object {
        const val DEFAULT_PERSON_ID = "00000000"
        fun build(f: Builder.() -> Unit): SqlitePerson {
            val builder = Builder()
            builder.f()
            return builder.build()
        }
    }

    /** 飲む人ID */
    @Suppress("CanBePrimaryConstructorProperty")
    @PrimaryKey
    @ColumnInfo(name = "person_id")
    var personId: PersonIdType = personId

    /** 名前 */
    @ColumnInfo(name = "person_name")
    var personName: PersonNameType = PersonNameType()

    /** 写真 */
    @ColumnInfo(name = "person_photo")
    var personPhoto: PersonPhotoType = PersonPhotoType()

    /** 表示順 */
    @ColumnInfo(name = "person_display_order")
    var personDisplayOrder: PersonDisplayOrderType = PersonDisplayOrderType()

    @Ignore
    fun toPerson() =
            Person(
                    personId = personId,
                    personName = personName,
                    personPhoto = personPhoto,
                    personDisplayOrder = personDisplayOrder)
}