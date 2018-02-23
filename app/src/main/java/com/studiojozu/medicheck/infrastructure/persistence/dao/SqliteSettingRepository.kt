package com.studiojozu.medicheck.infrastructure.persistence.dao

import android.arch.persistence.room.*
import com.studiojozu.medicheck.infrastructure.persistence.entity.SqliteSetting

@Dao
abstract class SqliteSettingRepository {

    @Transaction
    open fun updateData(insertData: SqliteSetting) {
        delete()
        insert(insertData)
    }

    @Query("select * from setting limit 1")
    abstract fun find(): SqliteSetting?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(sqliteSetting: SqliteSetting)

    @Query("delete from setting")
    abstract fun delete()
}