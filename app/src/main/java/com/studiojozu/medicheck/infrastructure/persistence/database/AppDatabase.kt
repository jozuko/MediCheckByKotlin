package com.studiojozu.medicheck.infrastructure.persistence.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.studiojozu.medicheck.infrastructure.persistence.dao.*
import com.studiojozu.medicheck.infrastructure.persistence.entity.SqliteMediTimeRelation
import com.studiojozu.medicheck.infrastructure.persistence.entity.SqliteMedicine
import com.studiojozu.medicheck.infrastructure.persistence.entity.SqliteMedicineUnit
import com.studiojozu.medicheck.infrastructure.persistence.entity.SqlitePersonMediRelation

@Database(
        entities = arrayOf(
                SqliteMedicine::class,
                SqliteMedicineUnit::class,
                SqliteMediTimeRelation::class,
                SqlitePersonMediRelation::class),
        version = 1,
        exportSchema = false)
internal abstract class AppDatabase : RoomDatabase() {

    abstract fun medicineDao(): SqliteMedicineRepository
    abstract fun medicineUnitDao(): SqliteMedicineUnitRepository
    abstract fun medicineViewDao(): SqliteMedicineMedicineUnitRepository
    abstract fun mediTimeRelationDao(): SqliteMediTimeRelationRepository
    abstract fun personMediRelationDao(): SqlitePersonMediRelationRepository

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
