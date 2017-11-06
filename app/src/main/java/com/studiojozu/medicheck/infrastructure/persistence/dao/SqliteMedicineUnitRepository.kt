package com.studiojozu.medicheck.infrastructure.persistence.dao

import android.arch.persistence.room.*
import com.studiojozu.medicheck.infrastructure.persistence.entity.SqliteMedicineUnit

@Dao
interface SqliteMedicineUnitRepository {
    @Query("select * from medicine_unit order by medicine_unit_display_order")
    fun findAll(): Array<SqliteMedicineUnit>

    @Query("select * from medicine_unit where medicine_unit_id = :medicineUnitId")
    fun findById(medicineUnitId: String): SqliteMedicineUnit?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(sqliteMedicineUnit: SqliteMedicineUnit)

    @Delete
    fun delete(sqliteMedicineUnit: SqliteMedicineUnit)
}
