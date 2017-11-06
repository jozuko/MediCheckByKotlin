package com.studiojozu.medicheck.infrastructure.persistence.dao

import android.arch.persistence.room.*
import com.studiojozu.medicheck.infrastructure.persistence.entity.SqliteMediTimeRelation

@Dao
interface SqliteMediTimeRelationRepository {
    @Query("select * from medi_time_relation")
    fun findAll(): Array<SqliteMediTimeRelation>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(sqliteMediTimeRelation: SqliteMediTimeRelation)

    @Delete
    fun delete(sqliteMediTimeRelation: SqliteMediTimeRelation)
}