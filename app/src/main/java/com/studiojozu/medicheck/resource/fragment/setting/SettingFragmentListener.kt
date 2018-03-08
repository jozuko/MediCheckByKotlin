package com.studiojozu.medicheck.resource.fragment.setting

import android.support.v4.app.Fragment

interface SettingFragmentListener {
    enum class SettingFragmentType {
        MENU,
        PERSON,
        ALARM,
        TAKE_TIME;

        fun getFragment(): Fragment
                = when (this) {
            ALARM -> AlarmFragment.newInstance()
            PERSON -> PersonFragment.newInstance()
            TAKE_TIME -> TimetableFragment.newInstance()
            else -> SettingMenuFragment.newInstance()
        }

    }

    fun goNext(fragmentType: SettingFragmentType)

    fun goBack()
}