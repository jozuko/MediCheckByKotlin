package com.studiojozu.medicheck.infrastructure.persistence.database

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.RoomDatabase
import android.content.ContentValues
import android.content.Context
import com.studiojozu.medicheck.R
import com.studiojozu.medicheck.domain.model.medicine.MedicineUnitIdType
import com.studiojozu.medicheck.domain.model.person.PersonIdType

class DatabaseCallback(private val mContext: Context) : RoomDatabase.Callback() {

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)

        // insert init data
        initMedicineUnit(db)
        initPerson(db)
    }

    private fun initMedicineUnit(db: SupportSQLiteDatabase) {
        val contentValues = ContentValues()
        contentValues.put("medicine_unit_id", MedicineUnitIdType().dbValue)
        contentValues.put("medicine_unit_value", mContext.resources.getString(R.string.medicine_unit_1))
        contentValues.put("medicine_unit_display_order", 1)

        db.insert("medicine_unit", OnConflictStrategy.IGNORE, contentValues)
    }

    private fun initPerson(db: SupportSQLiteDatabase) {
        val contentValues = ContentValues()
        contentValues.put("person_id", PersonIdType().dbValue)
        contentValues.put("person_name", mContext.resources.getString(R.string.person_self))
        contentValues.put("person_photo", "")
        contentValues.put("person_display_order", 1)

        db.insert("person", OnConflictStrategy.IGNORE, contentValues)
    }
}