package com.studiojozu.medicheck.di

import android.annotation.SuppressLint
import com.studiojozu.medicheck.di.module.ApplicationModule
import com.studiojozu.medicheck.di.module.PersistenceModule
import com.studiojozu.medicheck.di.module.ServiceModule

@SuppressLint("Registered")
class MediCheckTestApplication : MediCheckApplication() {

    lateinit var mAppTestComponent: AppTestComponent
        private set

    @Suppress("MemberVisibilityCanPrivate")
    var mApplicationModule: ApplicationModule? = null
        set(applicationModule) {
            field = applicationModule
            field ?: return
            initComponent()
        }

    @Suppress("MemberVisibilityCanPrivate")
    var mPersistenceModule: PersistenceModule? = null
        set(persistenceModule) {
            field = persistenceModule
            field ?: return
            initComponent()
        }

    @Suppress("MemberVisibilityCanPrivate")
    var mServiceModule: ServiceModule? = null
        set(serviceModule) {
            field = serviceModule
            field ?: return
            initComponent()
        }

    override fun initComponent() {
        super.initComponent()
        mAppTestComponent = DaggerAppTestComponent.builder()
                .applicationModule(getApplicationModule())
                .persistenceModule(getPersistenceModule())
                .serviceModule(getServiceModule())
                .build()
    }

    override fun getApplicationModule(): ApplicationModule =
            mApplicationModule ?: super.getApplicationModule()

    override fun getPersistenceModule(): PersistenceModule =
            mPersistenceModule ?: super.getPersistenceModule()

    override fun getServiceModule(): ServiceModule =
            mServiceModule ?: super.getServiceModule()
}
