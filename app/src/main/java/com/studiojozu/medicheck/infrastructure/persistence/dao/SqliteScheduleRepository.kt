package com.studiojozu.medicheck.infrastructure.persistence.dao

import android.arch.persistence.room.*
import com.studiojozu.medicheck.infrastructure.persistence.entity.SqliteSchedule

@Dao
interface SqliteScheduleRepository {
    @Query("select * from schedule")
    fun findAll(): Array<SqliteSchedule>

    @Query("select * from schedule where schedule_need_alarm=1 and schedule_is_take=0")
    fun findAlertAll(): Array<SqliteSchedule>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(sqliteSchedule: SqliteSchedule)

    @Delete
    fun delete(sqliteSchedule: SqliteSchedule)
}