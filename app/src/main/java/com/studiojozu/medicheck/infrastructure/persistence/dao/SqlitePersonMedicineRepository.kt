package com.studiojozu.medicheck.infrastructure.persistence.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.studiojozu.medicheck.infrastructure.persistence.entity.SqlitePersonMedicine

@Dao
interface SqlitePersonMedicineRepository {
    @Query("select" +
            " person.person_id as person_id" +
            ",person.person_name as person_name" +
            ",person.person_photo as person_photo" +
            ",person.person_display_order as person_display_order" +
            ",medicine.medicine_id as medicine_id" +
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
            " from person left join person_medi_relation on person.person_id=person_medi_relation.person_id" +
            " left join medicine on person_medi_relation.medicine_id=medicine.medicine_id" +
            " left join medicine_unit on medicine.medicine_unit_id=medicine_unit.medicine_unit_id" +
            " where medicine.medicine_id=:medicineId")
    fun findByMedicineId(medicineId: String): SqlitePersonMedicine?

    @Query("select" +
            " person.person_id as person_id" +
            ",person.person_name as person_name" +
            ",person.person_photo as person_photo" +
            ",person.person_display_order as person_display_order" +
            ",medicine.medicine_id as medicine_id" +
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
            " from person left join person_medi_relation on person.person_id=person_medi_relation.person_id" +
            " left join medicine on person_medi_relation.medicine_id=medicine.medicine_id" +
            " left join medicine_unit on medicine.medicine_unit_id=medicine_unit.medicine_unit_id" +
            " where person.person_id=:personId")
    fun findByPersonId(personId: String): Array<SqlitePersonMedicine>
}