package com.studiojozu.medicheck.di

import com.studiojozu.medicheck.application.AlarmScheduleService
import com.studiojozu.medicheck.application.MedicineFinderService
import com.studiojozu.medicheck.di.module.ApplicationModule
import com.studiojozu.medicheck.di.module.PersistenceModule
import com.studiojozu.medicheck.di.module.ServiceModule
import com.studiojozu.medicheck.resource.MainActivity
import com.studiojozu.medicheck.resource.alarm.AlarmBroadcastReceiver
import com.studiojozu.medicheck.resource.alarm.MedicineAlarm
import javax.inject.Singleton

@Singleton
@dagger.Component(modules = arrayOf(ApplicationModule::class, PersistenceModule::class, ServiceModule::class))
interface AppComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(alarmBroadcastReceiver: AlarmBroadcastReceiver)
    fun inject(medicineFinderService: MedicineFinderService)
    fun inject(alarmScheduleService: AlarmScheduleService)
    fun inject(medicineAlarm: MedicineAlarm)
}