package com.studiojozu.medicheck.domain.model.setting.repository

import com.studiojozu.medicheck.domain.model.setting.Setting
import com.studiojozu.medicheck.infrastructure.persistence.dao.SqliteSettingRepository
import com.studiojozu.medicheck.infrastructure.persistence.entity.SqliteSetting

class SettingRepository(private val sqliteSettingRepository: SqliteSettingRepository) {

    fun find(): Setting {
        val sqliteSetting = sqliteSettingRepository.find() ?: return Setting()
        return sqliteSetting.toSetting()
    }

    fun updateData(newSetting: Setting) {
        sqliteSettingRepository.delete()
        sqliteSettingRepository.insert(SqliteSetting.build { setting = newSetting })
    }
}
