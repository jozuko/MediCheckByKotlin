package com.studiojozu.medicheck.domain.model.setting

import android.content.Context
import com.studiojozu.common.domain.model.general.ADateType
import com.studiojozu.common.domain.model.general.ATimeType
import java.io.Serializable
import java.util.*

/**
 * 設定を画面から受信し、DBに保存する。また、DBのデータを画面表示用に加工する。
 *
 * @property mUseReminder リマンダ機能を使用するか？
 * @property mRemindInterval リマンダ機能のインターバル時間
 * @property mRemindTimeout リマンダ機能のタイムアウト時間
 */
data class Setting(private var mUseReminder: UseReminderType = UseReminderType(),
                   private var mRemindInterval: RemindIntervalType = RemindIntervalType(),
                   private var mRemindTimeout: RemindTimeoutType = RemindTimeoutType()) : Serializable {

    companion object {
        const val serialVersionUID = -8960841441881026848L
    }

    /**
     * リマンダ機能を使用するかを設定する
     *
     * @param useReminder リマンダ機能を使用する場合はtrueを設定する
     */
    fun setUseReminder(useReminder: Boolean) {
        mUseReminder = UseReminderType(useReminder)
    }

    /**
     * リマンダ機能のタイムアウト時間を設定する
     *
     * @param timeoutMinute タイムアウト時間（分）.
     * 選択できる時間は、[.getRemindTimeoutMap] で取得する
     */
    fun setRemindTimeout(timeoutMinute: Int) {
        mRemindTimeout = RemindTimeoutType(timeoutMinute)
    }

    /**
     * リマンダ機能のインターバルを設定する
     *
     * @param intervalMinute リマンダ機能のインターバル(分).
     * 選択できる時間は、[.getRemindIntervalMap] で取得する
     */
    fun setRemindInterval(intervalMinute: Int) {
        mRemindInterval = RemindIntervalType(intervalMinute)
    }

    fun useReminder(): Boolean = mUseReminder.isTrue

    /**
     * パラメータnowに指名した時刻が、リマインド機能の限界時間を超えているか？
     *
     * @param now          現在日時
     * @param scheduleDate アラーム予定日付
     * @param scheduleTime アラーム予定時刻
     * @return リマインド機能の限界時間を超えている場合はtrueを返却する
     */
    fun isRemindTimeout(now: Calendar, scheduleDate: ADateType<*>, scheduleTime: ATimeType<*>): Boolean {
        val reminderDatetimeType = ReminderDatetimeType(now.timeInMillis)
        return mRemindTimeout.isTimeout(reminderDatetimeType, scheduleDate, scheduleTime)
    }

    /**
     * パラメータnowに指名した時刻が、リマインド時刻であるか？
     *
     * @param now          現在日時
     * @param scheduleDate アラーム予定日付
     * @param scheduleTime アラーム予定時刻
     * @return リマインド時刻である場合はtrueを返却する
     */
    fun isRemindTiming(now: Calendar, scheduleDate: ADateType<*>, scheduleTime: ATimeType<*>): Boolean {
        val scheduleDatetime = ReminderDatetimeType(scheduleDate, scheduleTime)
        val currentDateTime = ReminderDatetimeType(now.timeInMillis)

        val diffMinutes = currentDateTime.diffMinutes(scheduleDatetime)
        return if (diffMinutes <= 0L) false else (diffMinutes % mRemindInterval.dbValue == 0L)
    }

    /**
     * リマインド機能のタイムアウト時間として、選択可能な時間(分)と対応する文言を返却する。
     *
     * @return リマインド機能のタイムアウト時間一覧
     */
    fun getRemindTimeoutMap(context: Context): TreeMap<Int, String> = RemindTimeoutType.getAllValues(context.applicationContext)

    /**
     * リマインド機能のインターバルとして、選択可能な時間(分)と対応する文言を返却する。
     *
     * @return リマインド機能のインターバル一覧
     */
    fun getRemindIntervalMap(context: Context): TreeMap<Int, String> = RemindIntervalType.getAllValues(context.applicationContext)
}
