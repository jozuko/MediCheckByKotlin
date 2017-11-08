package com.studiojozu.medicheck.infrastructure.persistence.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.studiojozu.medicheck.domain.model.setting.RemindIntervalType
import com.studiojozu.medicheck.domain.model.setting.RemindTimeoutType
import com.studiojozu.medicheck.domain.model.setting.Setting
import com.studiojozu.medicheck.domain.model.setting.UseReminderType

@Entity(tableName = "mSchedule")
class SqliteSetting {
    class Builder {
        lateinit var mSetting: Setting

        fun build(): SqliteSetting {
            val sqliteSetting = SqliteSetting()
            sqliteSetting.mUseReminder = mSetting.mUseReminder
            sqliteSetting.mRemindInterval = mSetting.mRemindInterval
            sqliteSetting.mRemindTimeout = mSetting.mRemindTimeout

            return sqliteSetting
        }
    }

    companion object {
        fun build(f: Builder.() -> Unit): SqliteSetting {
            val builder = Builder()
            builder.f()
            return builder.build()
        }
    }

    /** ID */
    @PrimaryKey
    @ColumnInfo(name = "setting_id")
    var mSettingId: Long = 0

    /** 繰り返し通知を使用する？ */
    @ColumnInfo(name = "use_reminder")
    var mUseReminder: UseReminderType = UseReminderType()

    /** 繰り返し通知間隔 */
    @ColumnInfo(name = "remind_interval")
    var mRemindInterval: RemindIntervalType = RemindIntervalType()

    /** 繰り返し通知最大時間(これ以上は通知しない) */
    @ColumnInfo(name = "remind_timeout")
    var mRemindTimeout: RemindTimeoutType = RemindTimeoutType()

    fun toSetting(): Setting =
            Setting(mUseReminder = mUseReminder,
                    mRemindInterval = mRemindInterval,
                    mRemindTimeout = mRemindTimeout)
}