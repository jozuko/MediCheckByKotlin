package com.studiojozu.medicheck.di

import android.app.Application
import com.studiojozu.medicheck.di.module.ApplicationModule
import com.studiojozu.medicheck.di.module.PersistenceModule
import com.studiojozu.medicheck.di.module.ServiceModule

class MediCheckApplication : Application() {

    lateinit var mComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()
        initComponent()
    }

    private fun initComponent() {
        mComponent = DaggerAppComponent.builder()
                .applicationModule(ApplicationModule(this))
                .persistenceModule(PersistenceModule())
                .serviceModule(ServiceModule())
                .build()
    }
}
