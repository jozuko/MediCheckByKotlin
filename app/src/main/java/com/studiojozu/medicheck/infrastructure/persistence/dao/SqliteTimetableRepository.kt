package com.studiojozu.medicheck.infrastructure.persistence.dao

import android.arch.persistence.room.*
import com.studiojozu.medicheck.infrastructure.persistence.entity.SqliteTimetable

@Dao
interface SqliteTimetableRepository {

    @Query("select * from timetable order by timetable_display_order")
    fun findAll(): Array<SqliteTimetable>

    @Query("select * from timetable where timetable_id=:timetableId")
    fun findById(timetableId: String): SqliteTimetable?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(sqliteTimetable: SqliteTimetable)

    @Delete
    fun delete(sqliteTimetable: SqliteTimetable)
}