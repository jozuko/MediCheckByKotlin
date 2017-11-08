package com.studiojozu.medicheck.infrastructure.persistence.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import com.studiojozu.medicheck.domain.model.person.*

@Entity(tableName = "person")
open class SqlitePerson(personId: PersonIdType) {
    class Builder {
        lateinit var mPerson: Person

        fun build(): SqlitePerson {
            val sqlitePerson = SqlitePerson(personId = mPerson.mPersonId)
            sqlitePerson.mPersonName = mPerson.mPersonName
            sqlitePerson.mPersonPhoto = mPerson.mPersonPhoto
            sqlitePerson.mPersonDisplayOrder = mPerson.mPersonDisplayOrder

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
    @PrimaryKey
    @ColumnInfo(name = "person_id")
    var mPersonId: PersonIdType = personId

    /** 名前 */
    @ColumnInfo(name = "person_name")
    var mPersonName: PersonNameType = PersonNameType()

    /** 写真 */
    @ColumnInfo(name = "person_photo")
    var mPersonPhoto: PersonPhotoType = PersonPhotoType()

    /** 表示順 */
    @ColumnInfo(name = "person_display_order")
    var mPersonDisplayOrder: PersonDisplayOrderType = PersonDisplayOrderType()

    @Ignore
    fun toPerson() =
            Person(
                    mPersonId = mPersonId,
                    mPersonName = mPersonName,
                    mPersonPhoto = mPersonPhoto,
                    mPersonDisplayOrder = mPersonDisplayOrder)
}