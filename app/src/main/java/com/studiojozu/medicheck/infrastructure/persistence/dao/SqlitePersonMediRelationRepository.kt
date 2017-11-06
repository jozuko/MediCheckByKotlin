package com.studiojozu.medicheck.infrastructure.persistence.dao

import android.arch.persistence.room.*
import com.studiojozu.medicheck.infrastructure.persistence.entity.SqlitePersonMediRelation

@Dao
interface SqlitePersonMediRelationRepository {
    @Query("select * from person_medi_relation")
    fun findAll(): Array<SqlitePersonMediRelation>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(sqlitePersonMediRelation: SqlitePersonMediRelation)

    @Delete
    fun delete(sqlitePersonMediRelation: SqlitePersonMediRelation)
}