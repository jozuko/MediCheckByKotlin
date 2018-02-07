package com.studiojozu.medicheck.resource.activity

import android.os.Bundle
import android.widget.Spinner
import android.widget.Switch
import butterknife.BindView
import butterknife.ButterKnife
import com.studiojozu.medicheck.R
import com.studiojozu.medicheck.di.MediCheckApplication
import com.studiojozu.medicheck.domain.model.setting.repository.SettingRepository
import javax.inject.Inject

class SettingActivity : ABaseActivity() {

    @Inject
    lateinit var mSettingRepository: SettingRepository

    @BindView(R.id.setting_item_use_reminder_switch)
    lateinit var mUseReminderSwitch: Switch

    @BindView(R.id.setting_item_reminder_interval_spinner)
    lateinit var mReminderIntervalSpinner: Spinner

    @BindView(R.id.setting_item_reminder_timeout_spinner)
    lateinit var mReminderTimeoutSpinner: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as MediCheckApplication).mComponent.inject(this)

        setContentView(R.layout.activity_setting)
        ButterKnife.bind(this)

        setInitialData()
    }

    private fun setInitialData() {
        val setting = mSettingRepository.find()
        mUseReminderSwitch.isChecked = setting.useReminder()
    }



}
