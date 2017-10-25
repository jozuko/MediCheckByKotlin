package com.studiojozu.medicheck.domain.model.setting

import com.studiojozu.common.domain.model.general.ABooleanType

class UseReminderType @JvmOverloads constructor(value: Any = true) : ABooleanType<UseReminderType>(value) {
    companion object {
        const val serialVersionUID = 7662891586709926954L
    }
}
