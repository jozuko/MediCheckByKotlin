package com.studiojozu.medicheck.resource.fragment.setting

import android.os.Bundle
import android.support.v7.widget.SwitchCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import butterknife.BindView
import butterknife.ButterKnife
import com.studiojozu.medicheck.R
import com.studiojozu.medicheck.application.SettingFinderService
import com.studiojozu.medicheck.application.SettingSaveService
import com.studiojozu.medicheck.domain.model.setting.RemindIntervalType
import com.studiojozu.medicheck.domain.model.setting.RemindTimeoutType
import com.studiojozu.medicheck.domain.model.setting.Setting
import com.studiojozu.medicheck.domain.model.setting.UseReminderType
import javax.inject.Inject

class AlarmFragment : ABaseFragment() {

    companion object {
        fun newInstance() = AlarmFragment()
    }

    @Inject
    lateinit var settingFinderService: SettingFinderService

    @Inject
    lateinit var settingSaveService: SettingSaveService

    @BindView(R.id.setting_item_use_reminder_switch)
    lateinit var useReminderSwitch: SwitchCompat

    @BindView(R.id.setting_item_reminder_interval_spinner)
    lateinit var reminderIntervalSpinner: Spinner

    @BindView(R.id.setting_item_reminder_timeout_spinner)
    lateinit var reminderTimeoutSpinner: Spinner

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

    private fun getView(inflater: LayoutInflater, container: ViewGroup?): View
            = inflater.inflate(R.layout.fragment_setting_alarm, container, false)

    private fun setInitialData() {
        settingSavedData = settingFinderService.findSetting()
        remindIntervalTypes = settingSavedData.getRemindIntervalMap(context)
        remindTimeoutTypes = settingSavedData.getRemindTimeoutMap(context)

        setInitialDataUseReminder()
        setInitialDataInterval()
        setInitialDataTimeout()

        setUseReminderSwitchItemSelectedListener()
        setReminderIntervalSpinnerItemSelectedListener()
        setReminderTimeoutSpinnerItemSelectedListener()
    }

    private fun setInitialDataUseReminder() {
        useReminderSwitch.isChecked = settingSavedData.useReminder()
    }

    private fun setInitialDataInterval() {
        val valueArray = remindIntervalTypes.values.toTypedArray()
        val defaultIndex = valueArray.indexOf(settingSavedData.remindInterval.getDisplayValue(resources))

        val adapter = createSpinnerAdapter(valueArray)
        reminderIntervalSpinner.adapter = adapter
        reminderIntervalSpinner.setSelection(defaultIndex)
    }

    private fun setInitialDataTimeout() {
        val valueArray = remindTimeoutTypes.values.toTypedArray()
        val defaultIndex = valueArray.indexOf(settingSavedData.remindTimeout.getDisplayValue(resources))

        val adapter = createSpinnerAdapter(valueArray)
        reminderTimeoutSpinner.adapter = adapter
        reminderTimeoutSpinner.setSelection(defaultIndex)
    }

    private fun createSpinnerAdapter(valueArray: Array<String>): ArrayAdapter<String> {
        val adapter = ArrayAdapter<String>(context, R.layout.spinner_item, valueArray)
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)

        return adapter
    }

    private fun setUseReminderSwitchItemSelectedListener() =
            useReminderSwitch.setOnCheckedChangeListener({ _, isChecked ->
                settingSavedData = settingSaveService.save(settingSavedData, UseReminderType(isChecked))
            })

    private fun setReminderIntervalSpinnerItemSelectedListener() {
        reminderIntervalSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, position: Int, id: Long) {
                settingSavedData = settingSaveService.save(settingSavedData, RemindIntervalType(RemindIntervalType.RemindIntervalPattern.typeOf(position)))
            }

            override fun onNothingSelected(adapterView: AdapterView<*>) = Unit
        }
    }

    private fun setReminderTimeoutSpinnerItemSelectedListener() {
        reminderTimeoutSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, position: Int, id: Long) {
                settingSavedData = settingSaveService.save(settingSavedData, RemindTimeoutType(RemindTimeoutType.RemindTimeoutPattern.typeOf(position)))
            }

            override fun onNothingSelected(adapterView: AdapterView<*>) = Unit
        }
    }
}