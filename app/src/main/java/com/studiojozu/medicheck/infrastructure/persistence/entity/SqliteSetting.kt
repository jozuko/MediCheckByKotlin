package com.studiojozu.medicheck.infrastructure.persistence.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.studiojozu.medicheck.domain.model.setting.RemindIntervalType
import com.studiojozu.medicheck.domain.model.setting.RemindTimeoutType
import com.studiojozu.medicheck.domain.model.setting.Setting
import com.studiojozu.medicheck.domain.model.setting.UseReminderType

@Entity(tableName = "setting")
class SqliteSetting {
    class Builder {
        lateinit var setting: Setting

        fun build(): SqliteSetting {
            val sqliteSetting = SqliteSetting()
            sqliteSetting.useReminder = setting.useReminder
            sqliteSetting.remindInterval = setting.remindInterval
            sqliteSetting.remindTimeout = setting.remindTimeout

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
    var settingId: Long = 0

    /** 繰り返し通知を使用する？ */
    @ColumnInfo(name = "use_reminder")
    var useReminder: UseReminderType = UseReminderType()

    /** 繰り返し通知間隔 */
    @ColumnInfo(name = "remind_interval")
    var remindInterval: RemindIntervalType = RemindIntervalType()

    /** 繰り返し通知最大時間(これ以上は通知しない) */
    @ColumnInfo(name = "remind_timeout")
    var remindTimeout: RemindTimeoutType = RemindTimeoutType()

    fun toSetting(): Setting =
            Setting(useReminder = useReminder,
                    remindInterval = remindInterval,
                    remindTimeout = remindTimeout)
}