package com.studiojozu.medicheck.di

import com.studiojozu.medicheck.di.module.ApplicationModule
import com.studiojozu.medicheck.di.module.PersistenceModule
import com.studiojozu.medicheck.di.module.ServiceModule

class MediCheckTestApplication : MediCheckApplication() {

    lateinit var mAppTestComponent: AppTestComponent
        private set

    override fun onCreate() {
        super.onCreate()
        initComponent()
    }

    override fun initComponent() {
        super.initComponent()
        mAppTestComponent = DaggerAppTestComponent.builder()
                .applicationModule(ApplicationModule(this))
                .persistenceModule(PersistenceModule())
                .serviceModule(ServiceModule())
                .build()
    }
}
