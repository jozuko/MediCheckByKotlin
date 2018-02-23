package com.studiojozu.medicheck.resource.activity

import android.os.Bundle
import butterknife.BindView
import butterknife.ButterKnife
import com.studiojozu.medicheck.R
import com.studiojozu.medicheck.resource.custom.HeaderView
import com.studiojozu.medicheck.resource.fragment.setting.SettingFragmentListener

class SettingActivity : ABaseActivity(), SettingFragmentListener, HeaderView.OnClickBackListener {
    companion object {
        const val TAG_CONTENTS = "tag_contents"
    }

    @BindView(R.id.header_view)
    lateinit var headerView: HeaderView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_setting)
        ButterKnife.bind(this)

        headerView.setParentActivity(this)
        headerView.setOnClickBackListener(this)
        showChildFragment(SettingFragmentListener.SettingFragmentType.MENU)
    }

    override fun goNext(fragmentType: SettingFragmentListener.SettingFragmentType) =
            showChildFragment(fragmentType)

    override fun goBack() =
            supportFragmentManager.popBackStack()

    override fun onClickedHeaderBack() {
        if (supportFragmentManager.backStackEntryCount == 0) {
            setResult(ABaseActivity.RESULT_BACK_TO_MENU)
            finish()
            return
        }
        supportFragmentManager.popBackStack()
    }

    private fun showChildFragment(nextFragmentType: SettingFragmentListener.SettingFragmentType) {
        val fragment = nextFragmentType.getFragment()
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.child_fragment, fragment, TAG_CONTENTS)

        if (nextFragmentType != SettingFragmentListener.SettingFragmentType.MENU)
            fragmentTransaction.addToBackStack(null)

        fragmentTransaction.commit()
    }
}
