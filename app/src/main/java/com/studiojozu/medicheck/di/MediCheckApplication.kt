package com.studiojozu.medicheck.di

import android.app.Application
import com.studiojozu.medicheck.di.module.ApplicationModule
import com.studiojozu.medicheck.di.module.PersistenceModule
import com.studiojozu.medicheck.di.module.ServiceModule
import uk.co.chrisjenx.calligraphy.CalligraphyConfig

open class MediCheckApplication : Application() {

    lateinit var mComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()
        initComponent()
        settingFont()
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

    private fun settingFont() {
        val calligraphy = CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/rounded-mgenplus-1pp-regular.ttf")
                .setFontAttrId(uk.co.chrisjenx.calligraphy.R.attr.fontPath)
                .build()

        CalligraphyConfig.initDefault(calligraphy)
    }
}
