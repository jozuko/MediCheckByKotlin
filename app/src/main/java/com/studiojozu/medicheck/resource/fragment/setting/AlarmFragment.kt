package com.studiojozu.medicheck.resource.fragment.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Spinner
import android.widget.Switch
import butterknife.BindView
import butterknife.ButterKnife
import com.studiojozu.medicheck.R
import com.studiojozu.medicheck.domain.model.setting.repository.SettingRepository
import javax.inject.Inject

class AlarmFragment : ABaseFragment() {
    @Inject
    lateinit var mSettingRepository: SettingRepository

    @BindView(R.id.setting_item_use_reminder_switch)
    lateinit var mUseReminderSwitch: Switch

    @BindView(R.id.setting_item_reminder_interval_spinner)
    lateinit var mReminderIntervalSpinner: Spinner

    @BindView(R.id.setting_item_reminder_timeout_spinner)
    lateinit var mReminderTimeoutSpinner: Spinner

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        application.component.inject(this)

        val view = getView(inflater, container)
        ButterKnife.bind(this, view)

        setInitialData()
        return view
    }

    private fun getView(inflater: LayoutInflater, container: ViewGroup?): View
            = inflater.inflate(R.layout.fragment_setting_alarm, container, false)

    private fun setInitialData() {
        val setting = mSettingRepository.find()
        mUseReminderSwitch.isChecked = setting.useReminder()
    }

}