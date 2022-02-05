package com.dr.drillinstructor.ui

import android.content.Intent
import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import com.dr.drillinstructor.databinding.ActivitySettingsBinding
import com.dr.drillinstructor.util.BUNDLE_HARD_MODE
import com.dr.drillinstructor.util.BUNDLE_LIGHT_MODE
import com.dr.drillinstructor.util.BUNDLE_START_MODE_VALUE
import com.dr.drillinstructor.util.PreferenceRepository
import org.koin.android.ext.android.inject

class SettingsActivity : WearableActivity() {

    private val preferenceRepository: PreferenceRepository by inject()
    lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySettingsBinding.inflate(layoutInflater)

        setContentView(binding.root)

        // Enables Always-on
        //setAmbientEnabled()
        initSoundSwitch()
        initListeners()
    }

    private fun initSoundSwitch() {
        binding.switchSound.isChecked = preferenceRepository.isSoundEnabled()
    }

    private fun initListeners() {
        binding.clickBoxLightDuration.setOnClickListener {
            startActivityWithMode(BUNDLE_LIGHT_MODE)
        }

        binding.clickBoxHardDuration.setOnClickListener {
            startActivityWithMode(BUNDLE_HARD_MODE)
        }

        binding.switchSound.setOnCheckedChangeListener { _, checked ->
            preferenceRepository.setSoundEnabled(checked)
        }

        binding.clickBoxSound.setOnClickListener {
            binding.switchSound.toggle()
        }
    }

    private fun startActivityWithMode(mode: String) {
        val intent = Intent(this, NumberPickerActivity::class.java)
        intent.putExtra(BUNDLE_START_MODE_VALUE, mode)
        startActivity(intent)
    }
}
