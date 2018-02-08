package com.studiojozu.medicheck.resource.alarm

import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import android.graphics.BitmapFactory
import android.os.Build
import com.studiojozu.medicheck.R
import com.studiojozu.medicheck.application.AlarmScheduleService
import com.studiojozu.medicheck.di.MediCheckApplication
import com.studiojozu.medicheck.domain.model.alarm.AlarmSchedule
import javax.inject.Inject

/**
 * アラームクラス
 */
class MedicineAlarm(context: Context) {
    companion object {
        private val NOTIFICATION_MEDICINE = 1
    }

    private val context: Context = context.applicationContext

    private val notificationManager: NotificationManager

    @Inject
    lateinit var alarmScheduleService: AlarmScheduleService

    init {
        notificationManager = this.context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        (this.context as MediCheckApplication).component.inject(this)
    }

    /**
     * データベースに登録されているスケジュールから、アラームが必要なスケジュールを抽出し、スケジュール設定する。
     */
    fun showNotification() {
        val alarmTargetSchedules = alarmScheduleService.getNeedAlarmSchedules()
        if (alarmTargetSchedules.isEmpty()) return

        // 通知を生成する
        val notification = createNotification(alarmTargetSchedules)
        notificationManager.cancelAll()
        notificationManager.notify(NOTIFICATION_MEDICINE, notification)
    }

    /**
     * 直ちに通知を行うNotificationを生成する。
     *
     * @param targetSchedules アラーム対象スケジュール
     * @return Notificationインスタンス
     */
    private fun createNotification(targetSchedules: List<AlarmSchedule>): Notification? {

        val notificationMessage = getNotificationMessage(targetSchedules)
        if (notificationMessage.isEmpty()) return null

        val largeIcon = BitmapFactory.decodeResource(context.resources, R.mipmap.notification_action_icon)

        @Suppress("DEPRECATION")
        with(Notification.Builder(context)) {
            setSmallIcon(R.mipmap.notification_icon)
            setLargeIcon(largeIcon)
            setTicker(context.resources.getString(R.string.notification_medicine_title))
            setContentTitle(context.resources.getString(R.string.notification_medicine_title))
            setContentText(notificationMessage)
            setWhen(System.currentTimeMillis())
            setDefaults(Notification.DEFAULT_LIGHTS or Notification.DEFAULT_SOUND or Notification.DEFAULT_VIBRATE)
            setAutoCancel(true)

            return if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
                notification
            } else build()
        }
    }

    /**
     * Notificationに表示するメッセージを作成する
     *
     * @param targetSchedules アラームが必要なスケジュール
     * @return Notificationに表示するメッセージ
     */
    private fun getNotificationMessage(targetSchedules: List<AlarmSchedule>): String =
            targetSchedules.joinToString("\n") { it.personName + " " + it.medicineName }
}
