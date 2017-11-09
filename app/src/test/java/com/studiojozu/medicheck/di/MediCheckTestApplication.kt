package com.studiojozu.medicheck.di

import android.app.Application
import com.studiojozu.medicheck.di.module.ApplicationModule
import com.studiojozu.medicheck.di.module.PersistenceModule
import com.studiojozu.medicheck.di.module.ServiceModule

class MediCheckTestApplication : Application() {

    lateinit var mAppTestComponent: AppTestComponent
        private set

    override fun onCreate() {
        super.onCreate()
        initComponent()
    }

    fun initComponent() {
        mAppTestComponent = DaggerAppTestComponent.builder()
                .applicationModule(ApplicationModule(this))
                .persistenceModule(PersistenceModule())
                .serviceModule(ServiceModule())
                .build()
    }
}
