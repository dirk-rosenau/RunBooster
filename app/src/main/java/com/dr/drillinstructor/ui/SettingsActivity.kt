package com.dr.drillinstructor.ui

import android.content.Intent
import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import com.dr.drillinstructor.R
import com.dr.drillinstructor.util.BUNDLE_HARD_MODE
import com.dr.drillinstructor.util.BUNDLE_LIGHT_MODE
import com.dr.drillinstructor.util.BUNDLE_START_MODE_VALUE
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : WearableActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        // Enables Always-on
        setAmbientEnabled()

        initListeners()
    }

    private fun initListeners() {
        clickBoxLightDuration.setOnClickListener {
            startActivityWithMode(BUNDLE_LIGHT_MODE)
        }

        clickBoxHardDuration.setOnClickListener {
            startActivityWithMode(BUNDLE_HARD_MODE)
        }
    }

    private fun startActivityWithMode(mode: String) {
        val intent = Intent(this, NumberPickerActivity::class.java)
        intent.putExtra(BUNDLE_START_MODE_VALUE, mode)
        startActivity(intent)
    }
}
