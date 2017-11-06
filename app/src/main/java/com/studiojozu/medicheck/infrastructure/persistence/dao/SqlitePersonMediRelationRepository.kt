package com.studiojozu.medicheck.infrastructure.persistence.dao

import android.arch.persistence.room.*
import com.studiojozu.medicheck.infrastructure.persistence.entity.SqliteMedicineMedicineUnit
import com.studiojozu.medicheck.infrastructure.persistence.entity.SqlitePerson
import com.studiojozu.medicheck.infrastructure.persistence.entity.SqlitePersonMediRelation

@Dao
interface SqlitePersonMediRelationRepository {
    @Query("select * from person_medi_relation")
    fun findAll(): Array<SqlitePersonMediRelation>

    @Query("select" +
            " person.*" +
            " from person_medi_relation inner join person on person_medi_relation.person_id=person.person_id" +
            " inner join medicine on person_medi_relation.medicine_id=medicine.medicine_id" +
            " where person_medi_relation.medicine_id=:medicineId" +
            " limit 1")
    fun findPersonByMedicineId(medicineId: String): SqlitePerson?

    @Query("select" +
            " medicine.*" +
            ",medicine_unit.medicine_unit_value as medicine_unit_value" +
            ",medicine_unit.medicine_unit_display_order as medicine_unit_display_order" +
            " from person_medi_relation inner join person on person_medi_relation.person_id=person.person_id" +
            " inner join medicine on person_medi_relation.medicine_id=medicine.medicine_id" +
            " left join medicine_unit on medicine.medicine_unit_id=medicine_unit.medicine_unit_id" +
            " where person_medi_relation.person_id=:personId")
    fun findMedicineByPersonId(personId: String): Array<SqliteMedicineMedicineUnit>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(sqlitePersonMediRelation: SqlitePersonMediRelation)

    @Delete
    fun delete(sqlitePersonMediRelation: SqlitePersonMediRelation)
}