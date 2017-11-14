package com.studiojozu.medicheck.application

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.studiojozu.common.domain.model.CalendarNoSecond
import com.studiojozu.medicheck.resource.alarm.AlarmBroadcastReceiver
import java.util.*

class AlarmService(application: Application) {
    companion object {
        private const val REQUEST_CODE_MEDICINE_ALARM = 1
    }

    private val mContext: Context = application.applicationContext
    private val mAlarmManager: AlarmManager = application.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    fun resetAlarm() {
        clearAlarm()
        setAlarm()
    }

    private fun clearAlarm() {
        val pendingIntent = createAlarmIntent()
        pendingIntent.cancel()
        mAlarmManager.cancel(pendingIntent)
    }

    private fun setAlarm() {
        val pendingIntent = createAlarmIntent()
        val triggerAtMillis = getNowPlusOneMinute().timeInMillis

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mAlarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent)
            return
        }
        mAlarmManager.set(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent)
    }

    private fun createAlarmIntent(): PendingIntent {
        val intent = Intent(mContext, AlarmBroadcastReceiver::class.java)
        return PendingIntent.getBroadcast(mContext, REQUEST_CODE_MEDICINE_ALARM, intent, 0)
    }

    private fun getNowPlusOneMinute(): Calendar {
        val now = CalendarNoSecond().calendar
        now.add(Calendar.MINUTE, 1)
        return now
    }
}