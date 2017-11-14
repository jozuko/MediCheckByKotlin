package com.studiojozu.medicheck.di.module

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.studiojozu.medicheck.domain.model.medicine.repository.MediTimeRelationRepository
import com.studiojozu.medicheck.domain.model.medicine.repository.MedicineUnitRepository
import com.studiojozu.medicheck.domain.model.medicine.repository.MedicineViewRepository
import com.studiojozu.medicheck.domain.model.person.repository.PersonMediRelationRepository
import com.studiojozu.medicheck.domain.model.person.repository.PersonRepository
import com.studiojozu.medicheck.domain.model.schedule.repository.ScheduleRepository
import com.studiojozu.medicheck.domain.model.setting.repository.SettingRepository
import com.studiojozu.medicheck.domain.model.setting.repository.TimetableRepository
import com.studiojozu.medicheck.infrastructure.persistence.database.AppDatabase
import com.studiojozu.medicheck.infrastructure.persistence.preference.PreferencePersonRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
open class PersistenceModule {

    @Singleton
    @Provides
    open fun provideSharedPreferences(application: Application): SharedPreferences =
            application.getSharedPreferences("medicheck", Context.MODE_PRIVATE)

    @Singleton
    @Provides
    open fun providePersonPreference(sharedPreferences: SharedPreferences): PreferencePersonRepository =
            PreferencePersonRepository(sharedPreferences)

    @Singleton
    @Provides
    open fun provideDatabase(application: Application): AppDatabase =
            AppDatabase.getAppDatabase(application)

    @Singleton
    @Provides
    open fun provideMedicineViewRepository(database: AppDatabase): MedicineViewRepository =
            MedicineViewRepository(
                    sqliteMedicineRepository = database.medicineDao(),
                    sqliteMedicineUnitRepository = database.medicineUnitDao(),
                    sqliteMedicineMedicineUnitRepository = database.medicineViewDao(),
                    sqlitePersonMediRelationRepository = database.personMediRelationDao(),
                    sqliteMediTimeRelationRepository = database.mediTimeRelationDao(),
                    sqliteScheduleRepository = database.scheduleDao())

    @Singleton
    @Provides
    open fun provideMedicineUnitRepository(database: AppDatabase): MedicineUnitRepository =
            MedicineUnitRepository(database.medicineUnitDao())

    @Singleton
    @Provides
    open fun provideTimetableRepository(database: AppDatabase): TimetableRepository =
            TimetableRepository(database.timetableDao())

    @Singleton
    @Provides
    open fun providePersonRepository(database: AppDatabase): PersonRepository =
            PersonRepository(database.personDao())

    @Singleton
    @Provides
    open fun provideScheduleRepository(database: AppDatabase): ScheduleRepository =
            ScheduleRepository(database.scheduleDao())

    @Singleton
    @Provides
    open fun provideSettingRepository(database: AppDatabase): SettingRepository =
            SettingRepository(database.settingDao())

    @Singleton
    @Provides
    open fun provideMediTimeRelationRepository(database: AppDatabase): MediTimeRelationRepository =
            MediTimeRelationRepository(database.mediTimeRelationDao())

    @Singleton
    @Provides
    open fun providePersonMediRelationRepository(database: AppDatabase): PersonMediRelationRepository =
            PersonMediRelationRepository(database.personMediRelationDao())
}