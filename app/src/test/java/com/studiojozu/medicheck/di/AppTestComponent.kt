package com.studiojozu.medicheck.di

import com.studiojozu.medicheck.di.module.ApplicationModule
import com.studiojozu.medicheck.di.module.PersistenceModule
import com.studiojozu.medicheck.di.module.ServiceModule
import com.studiojozu.medicheck.domain.model.medicine.repository.MediTimeRelationRepositoryTest
import com.studiojozu.medicheck.domain.model.medicine.repository.MedicineUnitRepositoryTest
import com.studiojozu.medicheck.domain.model.medicine.repository.MedicineViewRepositoryTest
import com.studiojozu.medicheck.domain.model.person.repository.PersonMediRelationRepositoryTest
import com.studiojozu.medicheck.domain.model.person.repository.PersonRepositoryTest
import com.studiojozu.medicheck.domain.model.schedule.repository.ScheduleRepositoryTest
import com.studiojozu.medicheck.domain.model.setting.repository.SettingRepositoryTest
import com.studiojozu.medicheck.domain.model.setting.repository.TimetableRepositoryTest
import javax.inject.Singleton

@Singleton
@dagger.Component(modules = arrayOf(ApplicationModule::class, PersistenceModule::class, ServiceModule::class))
interface AppTestComponent : AppComponent {
    fun inject(settingRepositoryTest: SettingRepositoryTest)
    fun inject(timetableRepositoryTest: TimetableRepositoryTest)
    fun inject(scheduleRepositoryTest: ScheduleRepositoryTest)
    fun inject(personRepositoryTest: PersonRepositoryTest)
    fun inject(medicineUnitRepositoryTest: MedicineUnitRepositoryTest)
    fun inject(medicineViewRepositoryTest: MedicineViewRepositoryTest)
    fun inject(mediTimeRelationRepositoryTest: MediTimeRelationRepositoryTest)
    fun inject(personMediRelationRepositoryTest: PersonMediRelationRepositoryTest)
}