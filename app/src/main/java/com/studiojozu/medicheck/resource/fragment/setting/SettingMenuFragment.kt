package com.studiojozu.medicheck.resource.fragment.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import butterknife.OnClick
import com.studiojozu.medicheck.R

class SettingMenuFragment : ABaseFragment() {

    companion object {
        fun newInstance() = SettingMenuFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = getView(inflater, container)
        ButterKnife.bind(this, view)

        return view
    }

    private fun getView(inflater: LayoutInflater, container: ViewGroup?): View
            = inflater.inflate(R.layout.fragment_setting_menu, container, false)

    @OnClick(R.id.menu_button_person)
    fun onClickPersonButton() {
        fragmentListener?.goNext(SettingFragmentListener.SettingFragmentType.PERSON)
    }

    @OnClick(R.id.menu_button_take_time)
    fun onClickTakeTimeButton() {
        fragmentListener?.goNext(SettingFragmentListener.SettingFragmentType.TAKE_TIME)
    }

    @OnClick(R.id.menu_button_alarm)
    fun onClickAlarmButton() {
        fragmentListener?.goNext(SettingFragmentListener.SettingFragmentType.ALARM)
    }
}