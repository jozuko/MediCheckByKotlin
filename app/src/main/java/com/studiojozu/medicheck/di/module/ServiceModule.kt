package com.studiojozu.medicheck.di.module

import android.app.Application
import com.studiojozu.medicheck.application.AlarmScheduleService
import com.studiojozu.medicheck.application.AlarmService
import com.studiojozu.medicheck.application.MedicineDeleteService
import com.studiojozu.medicheck.application.MedicineFinderService
import com.studiojozu.medicheck.di.MediCheckApplication
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
    open fun providerAlarmScheduleService(application: Application) =
            AlarmScheduleService(application as MediCheckApplication)

    @Singleton
    @Provides
    open fun providerAlarmService(application: Application) =
            AlarmService(application as MediCheckApplication)

}