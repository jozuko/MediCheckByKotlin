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
            ALARM -> AlarmFragment()
            PERSON -> PersonFragment()
            TAKE_TIME -> TimetableFragment()
            else -> SettingMenuFragment()
        }

    }

    fun goNext(fragmentType: SettingFragmentType)

    fun goBack()
}