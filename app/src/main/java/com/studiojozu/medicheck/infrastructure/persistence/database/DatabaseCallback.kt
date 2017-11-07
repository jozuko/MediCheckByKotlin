package com.studiojozu.medicheck.infrastructure.persistence.database

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.RoomDatabase
import android.content.ContentValues
import android.content.Context
import com.studiojozu.medicheck.R
import com.studiojozu.medicheck.domain.model.medicine.MedicineUnitIdType
import com.studiojozu.medicheck.domain.model.person.PersonIdType
import com.studiojozu.medicheck.domain.model.setting.RemindIntervalType
import com.studiojozu.medicheck.domain.model.setting.RemindTimeoutType
import com.studiojozu.medicheck.domain.model.setting.TimetableIdType
import com.studiojozu.medicheck.domain.model.setting.TimetableTimeType

class DatabaseCallback(private val mContext: Context) : RoomDatabase.Callback() {

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)

        // insert init data
        initMedicineUnit(db)
        initPerson(db)
        initTimetable(db)
        initSetting(db)
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

    private fun initSetting(db: SupportSQLiteDatabase) {
        val contentValues = ContentValues()
        contentValues.put("use_reminder", true)
        contentValues.put("remind_interval", RemindIntervalType(RemindIntervalType.RemindIntervalPattern.MINUTE_5).dbValue)
        contentValues.put("remind_timeout", RemindTimeoutType(RemindTimeoutType.RemindTimeoutPattern.HOUR_24).dbValue)

        db.insert("setting", OnConflictStrategy.IGNORE, contentValues)
    }

    private fun initTimetable(db: SupportSQLiteDatabase) {
        var displayOrder = 1L
        val contentValues = ContentValues()

        contentValues.put("timetable_id", TimetableIdType().dbValue)
        contentValues.put("timetable_name", mContext.resources.getString(R.string.timing_morning))
        contentValues.put("timetable_time", TimetableTimeType(7, 0).dbValue)
        contentValues.put("timetable_display_order", displayOrder++)
        db.insert("timetable", OnConflictStrategy.IGNORE, contentValues)

        contentValues.clear()
        contentValues.put("timetable_id", TimetableIdType().dbValue)
        contentValues.put("timetable_name", mContext.resources.getString(R.string.timing_noon))
        contentValues.put("timetable_time", TimetableTimeType(12, 0).dbValue)
        contentValues.put("timetable_display_order", displayOrder++)
        db.insert("timetable", OnConflictStrategy.IGNORE, contentValues)

        contentValues.clear()
        contentValues.put("timetable_id", TimetableIdType().dbValue)
        contentValues.put("timetable_name", mContext.resources.getString(R.string.timing_night))
        contentValues.put("timetable_time", TimetableTimeType(19, 0).dbValue)
        contentValues.put("timetable_display_order", displayOrder++)
        db.insert("timetable", OnConflictStrategy.IGNORE, contentValues)

        contentValues.clear()
        contentValues.put("timetable_id", TimetableIdType().dbValue)
        contentValues.put("timetable_name", mContext.resources.getString(R.string.timing_before_sleep))
        contentValues.put("timetable_time", TimetableTimeType(22, 0).dbValue)
        contentValues.put("timetable_display_order", displayOrder++)
        db.insert("timetable", OnConflictStrategy.IGNORE, contentValues)

        contentValues.clear()
        contentValues.put("timetable_id", TimetableIdType().dbValue)
        contentValues.put("timetable_name", mContext.resources.getString(R.string.timing_before_breakfast))
        contentValues.put("timetable_time", TimetableTimeType(6, 30).dbValue)
        contentValues.put("timetable_display_order", displayOrder++)
        db.insert("timetable", OnConflictStrategy.IGNORE, contentValues)

        contentValues.clear()
        contentValues.put("timetable_id", TimetableIdType().dbValue)
        contentValues.put("timetable_name", mContext.resources.getString(R.string.timing_before_lunch))
        contentValues.put("timetable_time", TimetableTimeType(11, 30).dbValue)
        contentValues.put("timetable_display_order", displayOrder++)
        db.insert("timetable", OnConflictStrategy.IGNORE, contentValues)

        contentValues.clear()
        contentValues.put("timetable_id", TimetableIdType().dbValue)
        contentValues.put("timetable_name", mContext.resources.getString(R.string.timing_before_dinner))
        contentValues.put("timetable_time", TimetableTimeType(18, 30).dbValue)
        contentValues.put("timetable_display_order", displayOrder++)
        db.insert("timetable", OnConflictStrategy.IGNORE, contentValues)

        contentValues.clear()
        contentValues.put("timetable_id", TimetableIdType().dbValue)
        contentValues.put("timetable_name", mContext.resources.getString(R.string.timing_after_breakfast))
        contentValues.put("timetable_time", TimetableTimeType(7, 30).dbValue)
        contentValues.put("timetable_display_order", displayOrder++)
        db.insert("timetable", OnConflictStrategy.IGNORE, contentValues)

        contentValues.clear()
        contentValues.put("timetable_id", TimetableIdType().dbValue)
        contentValues.put("timetable_name", mContext.resources.getString(R.string.timing_after_lunch))
        contentValues.put("timetable_time", TimetableTimeType(12, 30).dbValue)
        contentValues.put("timetable_display_order", displayOrder++)
        db.insert("timetable", OnConflictStrategy.IGNORE, contentValues)

        contentValues.clear()
        contentValues.put("timetable_id", TimetableIdType().dbValue)
        contentValues.put("timetable_name", mContext.resources.getString(R.string.timing_after_dinner))
        contentValues.put("timetable_time", TimetableTimeType(19, 30).dbValue)
        contentValues.put("timetable_display_order", displayOrder++)
        db.insert("timetable", OnConflictStrategy.IGNORE, contentValues)

        contentValues.clear()
        contentValues.put("timetable_id", TimetableIdType().dbValue)
        contentValues.put("timetable_name", mContext.resources.getString(R.string.timing_between_meals_morning))
        contentValues.put("timetable_time", TimetableTimeType(10, 0).dbValue)
        contentValues.put("timetable_display_order", displayOrder++)
        db.insert("timetable", OnConflictStrategy.IGNORE, contentValues)

        contentValues.clear()
        contentValues.put("timetable_id", TimetableIdType().dbValue)
        contentValues.put("timetable_name", mContext.resources.getString(R.string.timing_between_meals_afternoon))
        contentValues.put("timetable_time", TimetableTimeType(16, 0).dbValue)
        contentValues.put("timetable_display_order", displayOrder)
        db.insert("timetable", OnConflictStrategy.IGNORE, contentValues)
    }
}