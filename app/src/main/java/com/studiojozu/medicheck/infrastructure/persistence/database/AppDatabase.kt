package com.studiojozu.medicheck.infrastructure.persistence.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import com.studiojozu.medicheck.infrastructure.persistence.converter.*
import com.studiojozu.medicheck.infrastructure.persistence.dao.*
import com.studiojozu.medicheck.infrastructure.persistence.entity.*

@Database(
        entities = arrayOf(
                SqliteMedicine::class,
                SqliteMedicineUnit::class,
                SqliteMediTimeRelation::class,
                SqlitePersonMediRelation::class,
                SqlitePerson::class,
                SqliteSchedule::class,
                SqliteSetting::class,
                SqliteTimetable::class),
        version = 1,
        exportSchema = false)
@TypeConverters(
        SqliteMedicineConverters::class,
        SqliteMedicineUnitConverters::class,
        SqliteScheduleConverters::class,
        SqliteTimetableConverters::class,
        SqlitePersonConverters::class,
        SqliteSettingConverters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun medicineDao(): SqliteMedicineRepository
    abstract fun medicineUnitDao(): SqliteMedicineUnitRepository
    abstract fun medicineViewDao(): SqliteMedicineMedicineUnitRepository
    abstract fun mediTimeRelationDao(): SqliteMediTimeRelationRepository
    abstract fun personMediRelationDao(): SqlitePersonMediRelationRepository
    abstract fun personDao(): SqlitePersonRepository
    abstract fun scheduleDao(): SqliteScheduleRepository
    abstract fun settingDao(): SqliteSettingRepository
    abstract fun timetableDao(): SqliteTimetableRepository

    companion object {
        private var instance: AppDatabase? = null

        fun getAppDatabase(context: Context): AppDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(context.applicationContext,
                        AppDatabase::class.java,
                        "medicheck.db")
                        .allowMainThreadQueries()
                        .addCallback(DatabaseCallback(context))
                        .build()
            }
            return instance!!
        }
    }
}
