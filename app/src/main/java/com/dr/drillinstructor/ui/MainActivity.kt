package com.dr.drillinstructor.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.wear.ambient.AmbientModeSupport
import com.dr.drillinstructor.R
import com.dr.drillinstructor.ui.events.MainEvent
import com.dr.drillinstructor.ui.events.OpenSettings
import com.dr.drillinstructor.ui.events.StartTraining
import com.dr.drillinstructor.ui.events.StopTraining
import com.dr.drillinstructor.ui.vm.MainActivityViewModel
import com.dr.drillinstructor.util.PreferenceRepository
import com.dr.drillinstructor.util.TrainingManager
import com.dr.drillinstructor.util.TrainingStateProvider
import com.dr.drillinstructor.wrapper.VibrationHelper
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : FragmentActivity(), AmbientModeSupport.AmbientCallbackProvider {

    private val vibrationHelper: VibrationHelper by inject()
    private val trainingManager: TrainingManager by inject()

    private lateinit var ambientController: AmbientModeSupport.AmbientController

    private val viewModel by viewModel<MainActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ambientController = AmbientModeSupport.attach(this)

        observeEvents()
    }

    private fun observeEvents() {
        viewModel.mainEventLiveData.observe(this, Observer(this::handleEvent))
    }

    override fun onResume() {
        super.onResume()
        val fragment =
            if (trainingManager.isTrainingStarted()) InTrainingFragment.newInstance() else MainFragment.newInstance()
        showFragment(fragment)
    }

    private fun showFragment(fragment: Fragment) {
        // find fragment by tag (das jeweils andere), dann add fragment by tag
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment)
            .commit()
    }

    private fun handleEvent(event: MainEvent) {
        when (event) {
            OpenSettings -> openSettings()
            StartTraining -> handlePlayPressed()
            StopTraining -> handleStopPressed()
        }
    }

    private fun handlePlayPressed() {
        showFragment(InTrainingFragment.newInstance())
        vibrationHelper.vibrateShort()
        trainingManager.startTraining()
    }

    private fun openSettings() {
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
    }

    override fun getAmbientCallback(): AmbientModeSupport.AmbientCallback = MyAmbientCallback()


    private fun handleStopPressed() {
        trainingManager.stopTrainng()
        vibrationHelper.vibrateShort()
        // TODO nicht neue instance sondern finden!!
        showFragment(MainFragment.newInstance())
    }


    private class MyAmbientCallback : AmbientModeSupport.AmbientCallback() {

        override fun onEnterAmbient(ambientDetails: Bundle?) {
            // Handle entering ambient mode
        }

        override fun onExitAmbient() {
            // Handle exiting ambient mode
        }

        override fun onUpdateAmbient() {
            // Update the content
        }
    }

}
