package com.studiojozu.medicheck.di.module

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.studiojozu.medicheck.domain.model.MediTimeRelationRepository
import com.studiojozu.medicheck.domain.model.PersonMediRelationRepository
import com.studiojozu.medicheck.domain.model.medicine.repository.MedicineUnitRepository
import com.studiojozu.medicheck.domain.model.medicine.repository.MedicineViewRepository
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
class PersistenceModule {

    @Singleton
    @Provides
    fun provideSharedPreferences(application: Application): SharedPreferences =
            application.getSharedPreferences("medicheck", Context.MODE_PRIVATE)

    @Singleton
    @Provides
    fun providePersonPreference(sharedPreferences: SharedPreferences): PreferencePersonRepository =
            PreferencePersonRepository(sharedPreferences)

    @Singleton
    @Provides
    fun provideDatabase(application: Application): AppDatabase =
            AppDatabase.getAppDatabase(application)

    @Singleton
    @Provides
    fun provideMedicineViewRepository(database: AppDatabase): MedicineViewRepository =
            MedicineViewRepository(database.medicineDao(), database.medicineUnitDao(), database.medicineViewDao())

    @Singleton
    @Provides
    fun provideMedicineUnitRepository(database: AppDatabase): MedicineUnitRepository =
            MedicineUnitRepository(database.medicineUnitDao())

    @Singleton
    @Provides
    fun provideTimetableRepository(database: AppDatabase): TimetableRepository =
            TimetableRepository(database.timetableDao())

    @Singleton
    @Provides
    fun providePersonRepository(database: AppDatabase): PersonRepository =
            PersonRepository(database.personDao())

    @Singleton
    @Provides
    fun provideScheduleRepository(database: AppDatabase): ScheduleRepository =
            ScheduleRepository(database.scheduleDao())

    @Singleton
    @Provides
    fun provideSettingRepository(database: AppDatabase): SettingRepository =
            SettingRepository(database.settingDao())

    @Singleton
    @Provides
    fun provideMediTimeRelationRepository(database: AppDatabase): MediTimeRelationRepository =
            MediTimeRelationRepository(database.mediTimeRelationDao())

    @Singleton
    @Provides
    fun providePersonMediRelationRepository(database: AppDatabase): PersonMediRelationRepository =
            PersonMediRelationRepository(database.personMediRelationDao())
}