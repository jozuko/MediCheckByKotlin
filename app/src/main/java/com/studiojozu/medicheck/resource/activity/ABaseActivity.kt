package com.studiojozu.medicheck.resource.activity

import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.view.Window
import android.view.WindowManager
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

abstract class ABaseActivity : AppCompatActivity() {
    companion object {
        const val RESULT_BACK_TO_MENU = 1
    }

    override fun attachBaseContext(newBase: Context) = super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
    }
}
