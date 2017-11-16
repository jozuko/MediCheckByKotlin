package com.studiojozu.medicheck.infrastructure.persistence.dao

import android.arch.persistence.room.*
import com.studiojozu.medicheck.infrastructure.persistence.entity.*

@Dao
interface SqliteMedicineRepository {
    @Query("select * from medicine")
    fun findAll(): Array<SqliteMedicine>

    @Query("select * from medicine where medicine_id = :medicineId")
    fun findById(medicineId: String): SqliteMedicine?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(sqliteMedicine: SqliteMedicine)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMedicine(sqliteMedicine: SqliteMedicine,
                       sqliteMedicineUnit: SqliteMedicineUnit,
                       sqlitePersonMediRelationArray: Array<SqlitePersonMediRelation> = emptyArray(),
                       sqliteMediTimeRelationArray: Array<SqliteMediTimeRelation> = emptyArray(),
                       sqliteScheduleArray: Array<SqliteSchedule> = emptyArray())

    @Delete
    fun delete(sqliteMedicine: SqliteMedicine)

    @Delete
    fun deleteMedicine(sqliteMedicine: SqliteMedicine,
                       sqlitePersonMediRelationArray: Array<SqlitePersonMediRelation> = emptyArray(),
                       sqliteMediTimeRelationArray: Array<SqliteMediTimeRelation> = emptyArray(),
                       sqliteScheduleArray: Array<SqliteSchedule> = emptyArray())
}
