package com.studiojozu.medicheck.di.module

import android.app.Application
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

class PersistenceTestModule : PersistenceModule() {

    @Suppress("MemberVisibilityCanPrivate")
    var mSharedPreferences: SharedPreferences? = null
    @Suppress("MemberVisibilityCanPrivate")
    var mPreferencePersonRepository: PreferencePersonRepository? = null
    @Suppress("MemberVisibilityCanPrivate")
    var mAppDatabase: AppDatabase? = null
    @Suppress("MemberVisibilityCanPrivate")
    var mMedicineViewRepository: MedicineViewRepository? = null
    @Suppress("MemberVisibilityCanPrivate")
    var mMedicineUnitRepository: MedicineUnitRepository? = null
    @Suppress("MemberVisibilityCanPrivate")
    var mTimetableRepository: TimetableRepository? = null
    @Suppress("MemberVisibilityCanPrivate")
    var mPersonRepository: PersonRepository? = null
    @Suppress("MemberVisibilityCanPrivate")
    var mScheduleRepository: ScheduleRepository? = null
    @Suppress("MemberVisibilityCanPrivate")
    var mSettingRepository: SettingRepository? = null
    @Suppress("MemberVisibilityCanPrivate")
    var mMediTimeRelationRepository: MediTimeRelationRepository? = null
    @Suppress("MemberVisibilityCanPrivate")
    var mPersonMediRelationRepository: PersonMediRelationRepository? = null

    override fun provideSharedPreferences(application: Application): SharedPreferences =
            mSharedPreferences ?: super.provideSharedPreferences(application)

    override fun providePersonPreference(sharedPreferences: SharedPreferences): PreferencePersonRepository =
            mPreferencePersonRepository ?: super.providePersonPreference(sharedPreferences)

    override fun provideDatabase(application: Application): AppDatabase =
            mAppDatabase ?: super.provideDatabase(application)

    override fun provideMedicineViewRepository(database: AppDatabase): MedicineViewRepository =
            mMedicineViewRepository ?: super.provideMedicineViewRepository(database)

    override fun provideMedicineUnitRepository(database: AppDatabase): MedicineUnitRepository =
            mMedicineUnitRepository ?: super.provideMedicineUnitRepository(database)

    override fun provideTimetableRepository(database: AppDatabase): TimetableRepository =
            mTimetableRepository ?: super.provideTimetableRepository(database)

    override fun providePersonRepository(database: AppDatabase): PersonRepository =
            mPersonRepository ?: super.providePersonRepository(database)

    override fun provideScheduleRepository(database: AppDatabase): ScheduleRepository =
            mScheduleRepository ?: super.provideScheduleRepository(database)

    override fun provideSettingRepository(database: AppDatabase): SettingRepository =
            mSettingRepository ?: super.provideSettingRepository(database)

    override fun provideMediTimeRelationRepository(database: AppDatabase): MediTimeRelationRepository =
            mMediTimeRelationRepository ?: super.provideMediTimeRelationRepository(database)

    override fun providePersonMediRelationRepository(database: AppDatabase): PersonMediRelationRepository =
            mPersonMediRelationRepository ?: super.providePersonMediRelationRepository(database)
}