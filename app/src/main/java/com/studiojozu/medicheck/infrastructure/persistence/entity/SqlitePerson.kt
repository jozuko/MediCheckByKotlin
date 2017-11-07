package com.studiojozu.medicheck.infrastructure.persistence.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.studiojozu.medicheck.domain.model.person.PersonDisplayOrderType
import com.studiojozu.medicheck.domain.model.person.PersonIdType
import com.studiojozu.medicheck.domain.model.person.PersonNameType
import com.studiojozu.medicheck.domain.model.person.PersonPhotoType

@Entity(tableName = "person")
open class SqlitePerson(personId: PersonIdType) {
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
}