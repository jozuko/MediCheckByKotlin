package com.studiojozu.medicheck.di

import com.studiojozu.medicheck.application.MedicineFinderService
import com.studiojozu.medicheck.di.module.ApplicationModule
import com.studiojozu.medicheck.di.module.PersistenceModule
import com.studiojozu.medicheck.di.module.ServiceModule
import com.studiojozu.medicheck.resource.MainActivity
import javax.inject.Singleton

@Singleton
@dagger.Component(modules = arrayOf(ApplicationModule::class, PersistenceModule::class, ServiceModule::class))
interface AppComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(medicineFinderService: MedicineFinderService)
}