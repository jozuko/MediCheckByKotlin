package com.studiojozu.medicheck.di.module

import android.app.Application
import com.studiojozu.medicheck.application.*
import com.studiojozu.medicheck.domain.model.schedule.ScheduleService

class ServiceTestModule : ServiceModule() {

    @Suppress("MemberVisibilityCanPrivate")
    var mMedicineFinderService: MedicineFinderService? = null

    @Suppress("MemberVisibilityCanPrivate")
    var mMedicineDeleteService: MedicineDeleteService? = null

    @Suppress("MemberVisibilityCanPrivate")
    var mMedicineRegisterService: MedicineRegisterService? = null

    @Suppress("MemberVisibilityCanPrivate")
    var mAlarmScheduleService: AlarmScheduleService? = null

    @Suppress("MemberVisibilityCanPrivate")
    var mAlarmService: AlarmService? = null

    @Suppress("MemberVisibilityCanPrivate")
    var mScheduleService: ScheduleService? = null

    override fun providerMedicineFinderService(application: Application) =
            mMedicineFinderService ?: super.providerMedicineFinderService(application)

    override fun providerMedicineDeleteService(application: Application) =
            mMedicineDeleteService ?: super.providerMedicineDeleteService(application)

    override fun providerMedicineRegisterService(application: Application) =
            mMedicineRegisterService ?: super.providerMedicineRegisterService(application)

    override fun providerAlarmScheduleService(application: Application) =
            mAlarmScheduleService ?: super.providerAlarmScheduleService(application)

    override fun providerAlarmService(application: Application) =
            mAlarmService ?: super.providerAlarmService(application)

    override fun providerScheduleService() =
            mScheduleService ?: super.providerScheduleService()

}