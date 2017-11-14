package com.studiojozu.medicheck.di.module

import android.app.Application
import com.studiojozu.medicheck.application.AlarmScheduleService
import com.studiojozu.medicheck.application.AlarmService
import com.studiojozu.medicheck.application.MedicineFinderService

class ServiceTestModule : ServiceModule() {

    @Suppress("MemberVisibilityCanPrivate")
    var mMedicineFinderService: MedicineFinderService? = null
    @Suppress("MemberVisibilityCanPrivate")
    var mAlarmScheduleService: AlarmScheduleService? = null
    @Suppress("MemberVisibilityCanPrivate")
    var mAlarmService: AlarmService? = null

    override fun providerMedicineFinderService(application: Application) =
            mMedicineFinderService ?: super.providerMedicineFinderService(application)

    override fun providerAlarmScheduleService(application: Application) =
            mAlarmScheduleService ?: super.providerAlarmScheduleService(application)

    override fun providerAlarmService(application: Application) =
            mAlarmService ?: super.providerAlarmService(application)
}