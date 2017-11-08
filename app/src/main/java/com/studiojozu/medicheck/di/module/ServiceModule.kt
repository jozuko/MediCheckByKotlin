package com.studiojozu.medicheck.di.module

import android.app.Application
import com.studiojozu.medicheck.application.MedicineFinderService
import com.studiojozu.medicheck.di.MediCheckApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ServiceModule {

    @Singleton
    @Provides
    fun providerMedicineFinderService(application: Application) =
            MedicineFinderService(application as MediCheckApplication)
}