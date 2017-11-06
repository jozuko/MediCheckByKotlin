package com.studiojozu.medicheck.infrastructure.persistence.dao

import android.arch.persistence.room.*
import com.studiojozu.medicheck.infrastructure.persistence.entity.SqlitePerson

@Dao
interface SqlitePersonRepository {
    @Query("select * from person order by person_display_order")
    fun findAll(): Array<SqlitePerson>

    @Query("select * from person where person_id=:personId")
    fun findById(personId: String): SqlitePerson?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(sqlitePerson: SqlitePerson)

    @Delete
    fun delete(sqlitePerson: SqlitePerson)
}