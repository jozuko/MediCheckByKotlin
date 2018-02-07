package com.studiojozu.medicheck.resource.activity

import android.os.Bundle
import butterknife.BindView
import butterknife.ButterKnife
import com.studiojozu.medicheck.R
import com.studiojozu.medicheck.resource.custom.HeaderView
import com.studiojozu.medicheck.resource.fragment.setting.SettingFragmentListener

class SettingActivity : ABaseActivity(), SettingFragmentListener {
    companion object {
        const val TAG_CONTENTS = "tag_contents"
    }

    private var currentFragmentType = SettingFragmentListener.SettingFragmentType.MENU

    @BindView(R.id.header_view)
    lateinit var headerView: HeaderView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_setting)
        ButterKnife.bind(this)

        headerView.setParentActivity(this)
        showChildFragment(currentFragmentType)
    }

    override fun goNext(fragmentType: SettingFragmentListener.SettingFragmentType) {
        currentFragmentType = fragmentType
        showChildFragment(fragmentType)
    }

    override fun goBack() =
            supportFragmentManager.popBackStack()

    private fun showChildFragment(nextFragmentType: SettingFragmentListener.SettingFragmentType) {
        val fragment = nextFragmentType.getFragment()
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.child_fragment, fragment, TAG_CONTENTS)

        if (currentFragmentType != SettingFragmentListener.SettingFragmentType.MENU)
            fragmentTransaction.addToBackStack(null)

        fragmentTransaction.commit()
    }
}
