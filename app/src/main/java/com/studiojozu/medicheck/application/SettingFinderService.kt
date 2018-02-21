package com.studiojozu.medicheck.application

import com.studiojozu.medicheck.di.MediCheckApplication
import com.studiojozu.medicheck.domain.model.setting.Setting
import com.studiojozu.medicheck.domain.model.setting.repository.SettingRepository
import javax.inject.Inject

class SettingFinderService(application: MediCheckApplication) {

    @Inject
    lateinit var settingRepository: SettingRepository

    init {
        application.component.inject(this)
    }

    fun findSetting(): Setting = settingRepository.find()

}