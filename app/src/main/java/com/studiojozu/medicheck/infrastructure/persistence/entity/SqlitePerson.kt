package com.studiojozu.medicheck.infrastructure.persistence.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "person")
open class SqlitePerson(personId: String) {
    /** 飲む人ID */
    @PrimaryKey
    @ColumnInfo(name = "person_id")
    var mPersonId: String = personId

    /** 名前 */
    @ColumnInfo(name = "person_name")
    var mPersonName: String = ""

    /** 写真 */
    @ColumnInfo(name = "person_photo")
    var mPersonPhoto: String = ""

    /** 表示順 */
    @ColumnInfo(name = "person_display_order")
    var mPersonDisplayOrder: Long = 0
}