package com.studiojozu.medicheck.resource.fragment.setting

import android.os.Bundle
import android.support.v7.widget.SwitchCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Unbinder
import com.studiojozu.medicheck.R
import com.studiojozu.medicheck.application.SettingFinderService
import com.studiojozu.medicheck.domain.model.setting.Setting
import javax.inject.Inject

class AlarmFragment : ABaseFragment() {
    @Inject
    lateinit var settingFinderService: SettingFinderService

    @BindView(R.id.setting_item_use_reminder_switch)
    lateinit var mUseReminderSwitch: SwitchCompat

    @BindView(R.id.setting_item_reminder_interval_spinner)
    lateinit var mReminderIntervalSpinner: Spinner

    @BindView(R.id.setting_item_reminder_timeout_spinner)
    lateinit var mReminderTimeoutSpinner: Spinner

    private var unBinder: Unbinder? = null

    private lateinit var settingSavedData: Setting

    private lateinit var remindIntervalTypes: MutableMap<Int, String>

    private lateinit var remindTimeoutTypes: MutableMap<Int, String>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        application.component.inject(this)

        val view = getView(inflater, container)
        unBinder = ButterKnife.bind(this, view)

        setInitialData()
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        unBinder?.unbind()
    }

    private fun getView(inflater: LayoutInflater, container: ViewGroup?): View
            = inflater.inflate(R.layout.fragment_setting_alarm, container, false)

    private fun setInitialData() {
        settingSavedData = settingFinderService.findSetting()
        remindIntervalTypes = settingSavedData.getRemindIntervalMap(context)
        remindTimeoutTypes = settingSavedData.getRemindTimeoutMap(context)

        setInitialDataUseReminder()
        setInitialDataInterval()
        setInitialDataTimeout()
    }

    private fun setInitialDataUseReminder() {
        mUseReminderSwitch.isChecked = settingSavedData.useReminder()
    }

    private fun setInitialDataInterval() {
        val valueArray = remindIntervalTypes.values.toTypedArray()
        val defaultIndex = valueArray.indexOf(settingSavedData.remindInterval.getDisplayValue(resources))

        val adapter = createSpinnerAdapter(valueArray)
        mReminderIntervalSpinner.adapter = adapter
        mReminderIntervalSpinner.setSelection(defaultIndex)
    }

    private fun setInitialDataTimeout() {
        val valueArray = remindTimeoutTypes.values.toTypedArray()
        val defaultIndex = valueArray.indexOf(settingSavedData.remindTimeout.getDisplayValue(resources))

        val adapter = createSpinnerAdapter(valueArray)
        mReminderTimeoutSpinner.adapter = adapter
        mReminderTimeoutSpinner.setSelection(defaultIndex)
    }

    private fun createSpinnerAdapter(valueArray: Array<String>): ArrayAdapter<String> {
        val adapter = ArrayAdapter<String>(context, R.layout.spinner_item, valueArray)
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)

        return adapter
    }
}