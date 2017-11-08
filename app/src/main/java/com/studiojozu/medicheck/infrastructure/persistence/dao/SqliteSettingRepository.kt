package com.studiojozu.medicheck.infrastructure.persistence.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.studiojozu.medicheck.infrastructure.persistence.entity.SqliteSetting

@Dao
interface SqliteSettingRepository {

    @Query("select * from mSchedule limit 1")
    fun find(): SqliteSetting?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(sqliteSetting: SqliteSetting)

    @Query("delete from mSchedule")
    fun delete()
}