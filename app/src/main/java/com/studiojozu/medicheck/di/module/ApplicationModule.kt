package com.studiojozu.medicheck.di.module

import android.app.Application
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
open class ApplicationModule(private var app: Application) {

    @Provides
    @Singleton
    fun provideApplication(): Application = app
}