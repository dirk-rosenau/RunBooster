package com.dr.drillinstructor.ui

import android.content.Intent
import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import com.dr.drillinstructor.R
import com.dr.drillinstructor.util.BUNDLE_HARD_MODE
import com.dr.drillinstructor.util.BUNDLE_LIGHT_MODE
import com.dr.drillinstructor.util.BUNDLE_START_MODE_VALUE
import com.dr.drillinstructor.util.PreferenceRepository
import kotlinx.android.synthetic.main.activity_settings.*
import org.koin.android.ext.android.inject

class SettingsActivity : WearableActivity() {

    private val preferenceRepository: PreferenceRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        // Enables Always-on
        //setAmbientEnabled()
        initSoundSwitch()
        initListeners()
    }

    private fun initSoundSwitch() {
        switch_sound.isChecked = preferenceRepository.isSoundEnabled()
    }

    private fun initListeners() {
        clickBoxLightDuration.setOnClickListener {
            startActivityWithMode(BUNDLE_LIGHT_MODE)
        }

        clickBoxHardDuration.setOnClickListener {
            startActivityWithMode(BUNDLE_HARD_MODE)
        }

        switch_sound.setOnCheckedChangeListener { _, checked ->
            preferenceRepository.setSoundEnabled(checked)
        }

        clickBoxSound.setOnClickListener {
            switch_sound.toggle()
        }
    }

    private fun startActivityWithMode(mode: String) {
        val intent = Intent(this, NumberPickerActivity::class.java)
        intent.putExtra(BUNDLE_START_MODE_VALUE, mode)
        startActivity(intent)
    }
}
