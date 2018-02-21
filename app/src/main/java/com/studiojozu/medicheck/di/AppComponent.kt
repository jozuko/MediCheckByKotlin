package com.studiojozu.medicheck.di

import com.studiojozu.medicheck.application.*
import com.studiojozu.medicheck.di.module.ApplicationModule
import com.studiojozu.medicheck.di.module.PersistenceModule
import com.studiojozu.medicheck.di.module.ServiceModule
import com.studiojozu.medicheck.resource.MainActivity
import com.studiojozu.medicheck.resource.activity.SettingActivity
import com.studiojozu.medicheck.resource.alarm.AlarmBroadcastReceiver
import com.studiojozu.medicheck.resource.alarm.MedicineAlarm
import com.studiojozu.medicheck.resource.fragment.setting.AlarmFragment
import javax.inject.Singleton

@Singleton
@dagger.Component(modules = arrayOf(ApplicationModule::class, PersistenceModule::class, ServiceModule::class))
interface AppComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(settingActivity: SettingActivity)
    fun inject(alarmFragment: AlarmFragment)
    fun inject(alarmBroadcastReceiver: AlarmBroadcastReceiver)
    fun inject(medicineAlarm: MedicineAlarm)
    fun inject(alarmScheduleService: AlarmScheduleService)
    fun inject(medicineFinderService: MedicineFinderService)
    fun inject(medicineDeleteService: MedicineDeleteService)
    fun inject(medicineRegisterService: MedicineRegisterService)
    fun inject(settingFinderService: SettingFinderService)
}