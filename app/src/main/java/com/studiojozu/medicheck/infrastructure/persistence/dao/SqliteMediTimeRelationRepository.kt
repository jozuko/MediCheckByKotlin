package com.studiojozu.medicheck.infrastructure.persistence.dao

import android.arch.persistence.room.*
import com.studiojozu.medicheck.infrastructure.persistence.entity.SqliteMediTimeRelation
import com.studiojozu.medicheck.infrastructure.persistence.entity.SqliteMedicineMedicineUnit
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

    @Query("select" +
            " medicine.medicine_id as medicine_id" +
            ",medicine.medicine_name as medicine_name" +
            ",medicine.medicine_take_number as medicine_take_number" +
            ",medicine.medicine_unit_id as medicine_unit_id" +
            ",medicine.medicine_date_number as medicine_date_number" +
            ",medicine.medicine_start_datetime as medicine_start_datetime" +
            ",medicine.medicine_interval as medicine_interval" +
            ",medicine.medicine_interval_mode as medicine_interval_mode" +
            ",medicine.medicine_photo as medicine_photo" +
            ",medicine.medicine_need_alarm as medicine_need_alarm" +
            ",medicine.medicine_delete_flag as medicine_delete_flag" +
            ",medicine_unit.medicine_unit_value as medicine_unit_value" +
            ",medicine_unit.medicine_unit_display_order as medicine_unit_display_order" +
            " from medi_time_relation" +
            " inner join medicine on medi_time_relation.medicine_id=medicine.medicine_id" +
            " left join medicine_unit on medicine.medicine_unit_id=medicine_unit.medicine_unit_id" +
            " where medi_time_relation.timetable_id=:timetableId")
    fun findMedicineByTimetableId(timetableId: String): Array<SqliteMedicineMedicineUnit>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(sqliteMediTimeRelation: SqliteMediTimeRelation)

    @Delete
    fun delete(sqliteMediTimeRelation: SqliteMediTimeRelation)

    @Query("delete from medi_time_relation where medi_time_relation.medicine_id=:medicineId")
    fun deleteByMedicineId(medicineId: String)
}