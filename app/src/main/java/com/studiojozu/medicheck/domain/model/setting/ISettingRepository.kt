package com.studiojozu.medicheck.domain.model.setting

import android.content.Context

interface ISettingRepository {
    fun getSetting(context: Context): Setting

    fun save(context: Context, useReminder: UseReminderType, remindIntervalType: RemindIntervalType, remindTimeout: RemindTimeoutType)
}
