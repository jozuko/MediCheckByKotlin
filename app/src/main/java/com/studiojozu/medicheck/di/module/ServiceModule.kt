package com.studiojozu.medicheck.di.module

import android.app.Application
import com.studiojozu.medicheck.application.*
import com.studiojozu.medicheck.di.MediCheckApplication
import com.studiojozu.medicheck.domain.model.schedule.ScheduleService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
open class ServiceModule {

    @Singleton
    @Provides
    open fun providerMedicineFinderService(application: Application) =
            MedicineFinderService(application as MediCheckApplication)

    @Singleton
    @Provides
    open fun providerMedicineDeleteService(application: Application) =
            MedicineDeleteService(application as MediCheckApplication)

    @Singleton
    @Provides
    open fun providerMedicineRegisterService(application: Application) =
            MedicineRegisterService(application as MediCheckApplication)

    @Singleton
    @Provides
    open fun providerAlarmScheduleService(application: Application) =
            AlarmScheduleService(application as MediCheckApplication)

    @Singleton
    @Provides
    open fun providerAlarmService(application: Application) =
            AlarmService(application as MediCheckApplication)

    @Singleton
    @Provides
    open fun providerScheduleService() =
            ScheduleService()

    @Singleton
    @Provides
    open fun providerSettingFinderService(application: Application) =
            SettingFinderService(application as MediCheckApplication)

    @Singleton
    @Provides
    open fun providerSettingSaveService(application: Application) =
            SettingSaveService(application as MediCheckApplication)

    @Singleton
    @Provides
    open fun providerTimetableFinderService(application: Application) =
            TimetableFinderService(application as MediCheckApplication)
}