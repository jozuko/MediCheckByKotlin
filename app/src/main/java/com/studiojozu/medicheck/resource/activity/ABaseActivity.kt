package com.studiojozu.medicheck.resource.activity

import android.app.Activity
import android.content.Context
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

abstract class ABaseActivity : Activity() {
    override fun attachBaseContext(newBase: Context) = super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
}
