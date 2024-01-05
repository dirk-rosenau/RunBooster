package com.dr.drillinstructor.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.wear.ambient.AmbientModeSupport
import com.dr.drillinstructor.R
import com.dr.drillinstructor.ui.events.*
import com.dr.drillinstructor.ui.vm.MainActivityViewModel
import com.dr.drillinstructor.util.TrainingManager
import com.dr.drillinstructor.wrapper.VibrationHelper
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : FragmentActivity(), AmbientModeSupport.AmbientCallbackProvider {

    private val vibrationHelper: VibrationHelper by inject()
    private val trainingManager: TrainingManager by inject()

    private lateinit var ambientController: AmbientModeSupport.AmbientController

    internal val viewModel by viewModel<MainActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ambientController = AmbientModeSupport.attach(this)
        observeEvents()
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
            && Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                1
            )
        }
    }

    private fun observeEvents() {
        viewModel.mainEventLiveData.observe(this, Observer(this::handleEvent))
    }

    override fun onResume() {
        super.onResume()
        val tag =
            if (trainingManager.isTrainingStarted()) TAG_TRAINING else TAG_MAIN
        showFragment(tag, false)
    }

    private fun showFragment(tag: String, animate: Boolean) {
        var fragment = supportFragmentManager.findFragmentByTag(tag)
        if (fragment == null) {
            fragment = when (tag) {
                TAG_MAIN -> MainFragment.newInstance()
                TAG_TRAINING -> InTrainingFragment.newInstance()
                else -> throw IllegalArgumentException("Wrong Fragment Tag!")
            }
        }

        supportFragmentManager.beginTransaction()
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .replace(R.id.fragment_container, fragment, tag)
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
        showFragment(TAG_TRAINING, true)
        vibrationHelper.vibrateShort()
        trainingManager.startTraining()
    }

    private fun openSettings() {
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
    }

    override fun getAmbientCallback(): AmbientModeSupport.AmbientCallback =
        MyAmbientCallback(viewModel)


    private fun handleStopPressed() {
        trainingManager.stopTrainng()
        vibrationHelper.vibrateShort()
        showFragment(TAG_MAIN, true)
    }

    private class MyAmbientCallback(private val viewModel: MainActivityViewModel) :
        AmbientModeSupport.AmbientCallback() {

        override fun onEnterAmbient(ambientDetails: Bundle?) {
            viewModel.ambient.value = EnterAmbient
        }

        override fun onExitAmbient() {
            viewModel.ambient.value = ExitAmbient
        }

        override fun onUpdateAmbient() {
            viewModel.ambient.value = UpdateAmbient
        }
    }

    companion object {
        const val TAG_MAIN = "main"
        const val TAG_TRAINING = "training"
    }

}
