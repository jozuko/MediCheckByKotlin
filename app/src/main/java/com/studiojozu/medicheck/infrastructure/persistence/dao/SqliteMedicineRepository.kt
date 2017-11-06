package com.studiojozu.medicheck.infrastructure.persistence.dao

import android.arch.persistence.room.*

import com.studiojozu.medicheck.infrastructure.persistence.entity.SqliteMedicine

@Dao
interface SqliteMedicineRepository {
    @Query("select * from medicine")
    fun findAll(): Array<SqliteMedicine>

    @Query("select * from medicine where medicine_id = :medicineId")
    fun findById(medicineId: String): SqliteMedicine?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(sqliteMedicine: SqliteMedicine)

    @Delete
    fun delete(sqliteMedicine: SqliteMedicine)
}
