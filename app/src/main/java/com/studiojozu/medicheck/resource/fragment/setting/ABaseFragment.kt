package com.studiojozu.medicheck.resource.fragment.setting

import android.content.Context
import android.os.Build
import android.support.v4.app.Fragment
import com.studiojozu.medicheck.di.MediCheckApplication

abstract class ABaseFragment : Fragment() {
    protected val application: MediCheckApplication
        get() = (activity.application as MediCheckApplication)

    protected val applicationContext: Context
        get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            context
        } else {
            activity.applicationContext
        }

    protected val fragmentListener: SettingFragmentListener?
        get() = if (activity is SettingFragmentListener) (activity as SettingFragmentListener) else null
}

