package com.studiojozu.medicheck.di

import android.app.Application
import com.studiojozu.medicheck.di.module.ApplicationModule
import com.studiojozu.medicheck.di.module.PersistenceModule
import com.studiojozu.medicheck.di.module.ServiceModule

open class MediCheckApplication : Application() {

    lateinit var mComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()
        initComponent()
    }

    open fun initComponent() {
        mComponent = DaggerAppComponent.builder()
                .applicationModule(getApplicationModule())
                .persistenceModule(getPersistenceModule())
                .serviceModule(getServiceModule())
                .build()
    }

    open fun getApplicationModule() = ApplicationModule(this)
    open fun getPersistenceModule() = PersistenceModule()
    open fun getServiceModule() = ServiceModule()
}
