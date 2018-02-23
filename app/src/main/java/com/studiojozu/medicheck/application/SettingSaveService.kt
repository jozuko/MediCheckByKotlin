package com.studiojozu.medicheck.application

import com.studiojozu.medicheck.di.MediCheckApplication
import com.studiojozu.medicheck.domain.model.setting.RemindIntervalType
import com.studiojozu.medicheck.domain.model.setting.RemindTimeoutType
import com.studiojozu.medicheck.domain.model.setting.Setting
import com.studiojozu.medicheck.domain.model.setting.UseReminderType
import com.studiojozu.medicheck.domain.model.setting.repository.SettingRepository
import javax.inject.Inject

class SettingSaveService(application: MediCheckApplication) {

    @Inject
    lateinit var settingRepository: SettingRepository

    init {
        application.component.inject(this)
    }

    fun save(oldSetting: Setting, useReminder: UseReminderType): Setting {
        val newSetting = Setting(useReminder = useReminder, remindInterval = oldSetting.remindInterval, remindTimeout = oldSetting.remindTimeout)
        updateSetting(newSetting)
        return newSetting
    }

    fun save(oldSetting: Setting, remindInterval: RemindIntervalType): Setting {
        val newSetting = Setting(useReminder = oldSetting.useReminder, remindInterval = remindInterval, remindTimeout = oldSetting.remindTimeout)
        updateSetting(newSetting)
        return newSetting
    }

    fun save(oldSetting: Setting, remindTimeout: RemindTimeoutType): Setting {
        val newSetting = Setting(useReminder = oldSetting.useReminder, remindInterval = oldSetting.remindInterval, remindTimeout = remindTimeout)
        updateSetting(newSetting)
        return newSetting
    }

    private fun updateSetting(setting: Setting) =
            settingRepository.updateData(setting)
}