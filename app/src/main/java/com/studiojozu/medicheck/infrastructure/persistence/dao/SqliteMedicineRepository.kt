package com.studiojozu.medicheck.infrastructure.persistence.dao

import android.arch.persistence.room.*

import com.studiojozu.medicheck.infrastructure.persistence.entity.SqliteMedicine

@Dao
abstract class SqliteMedicineRepository {
    @Query("select * from medicine")
    abstract fun findAll(): Array<SqliteMedicine>

    @Query("select * from medicine where medicine_id = :medicineId")
    abstract fun findById(medicineId: String): SqliteMedicine?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(sqliteMedicine: SqliteMedicine)

    @Delete
    abstract fun delete(sqliteMedicine: SqliteMedicine)

    @Transaction
    open fun deleteMedicine(sqliteMedicine: SqliteMedicine,
                            sqlitePersonMediRelationRepository: SqlitePersonMediRelationRepository,
                            sqliteMediTimeRelationRepository: SqliteMediTimeRelationRepository,
                            sqliteScheduleRepository: SqliteScheduleRepository) {
        val medicineId = sqliteMedicine.mMedicineId.dbValue

        delete(sqliteMedicine)
        sqlitePersonMediRelationRepository.deleteByMedicineId(medicineId)
        sqliteMediTimeRelationRepository.deleteByMedicineId(medicineId)
        sqliteScheduleRepository.deleteAllByMedicineId(medicineId)
    }
}
