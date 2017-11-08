package com.studiojozu.medicheck.infrastructure.persistence.dao

import android.arch.persistence.room.*
import com.studiojozu.medicheck.infrastructure.persistence.entity.SqliteMediTimeRelation
import com.studiojozu.medicheck.infrastructure.persistence.entity.SqliteTimetable

@Dao
interface SqliteMediTimeRelationRepository {
    @Query("select * from medi_time_relation")
    fun findAll(): Array<SqliteMediTimeRelation>

    @Query("select" +
            " timetable.*" +
            " from medi_time_relation inner join timetable on medi_time_relation.timetable_id=timetable.timetable_id" +
            " where medi_time_relation.medicine_id=:medicineId" +
            " order by timetable_display_order,timetable_time")
    fun findTimetableByMedicineId(medicineId: String): Array<SqliteTimetable>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(sqliteMediTimeRelation: SqliteMediTimeRelation)

    @Delete
    fun delete(sqliteMediTimeRelation: SqliteMediTimeRelation)

    @Query("delete from medi_time_relation where medi_time_relation.medicine_id=:medicineId")
    fun deleteByMedicineId(medicineId: String)
}