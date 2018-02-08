package com.studiojozu.medicheck.resource.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.studiojozu.medicheck.application.AlarmService
import com.studiojozu.medicheck.di.MediCheckApplication
import javax.inject.Inject

class AlarmBroadcastReceiver : BroadcastReceiver() {

    @Inject
    lateinit var alarmService: AlarmService

    override fun onReceive(p0: Context?, p1: Intent?) {
        val context = p0 ?: return
        (context.applicationContext as MediCheckApplication).component.inject(this)

        resetAlarm()
        showNotification(context)
    }

    private fun resetAlarm() =
            alarmService.resetAlarm()

    private fun showNotification(context: Context) =
            MedicineAlarm(context).showNotification()

}