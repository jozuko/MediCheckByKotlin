package com.studiojozu.medicheck.resource.activity

import android.app.Activity
import android.os.Bundle
import com.studiojozu.medicheck.R

/**
 * メニュー画面
 */
class MenuActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
    }
}
