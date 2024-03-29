package com.dr.drillinstructor.di

import android.media.MediaPlayer
import androidx.preference.PreferenceManager
import com.dr.drillinstructor.ui.usecases.ResetTimerUseCase
import com.dr.drillinstructor.ui.vm.InTrainingFragmentViewModel
import com.dr.drillinstructor.ui.vm.MainActivityViewModel
import com.dr.drillinstructor.ui.vm.MainFragmentViewModel
import com.dr.drillinstructor.util.*
import com.dr.drillinstructor.wrapper.AlarmHelper
import com.dr.drillinstructor.wrapper.NotificationHelper
import com.dr.drillinstructor.wrapper.SoundPlayer
import com.dr.drillinstructor.wrapper.VibrationHelper
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {
    single { MediaPlayer() }
    single {
        SoundPlayer(
            androidContext().assets,
            get(), androidContext()
        )
    }
    single { AlarmHelper(androidContext()) }
    single { NotificationHelper(androidContext()) }
    single { VibrationHelper(androidContext()) }
    single { TrainingStateProviderImpl(get()) as TrainingStateProvider } // do not delete cast
    single { PreferenceRepositoryImpl(PreferenceManager.getDefaultSharedPreferences(androidContext())) as PreferenceRepository }
    single { TrainingManager(get(), get(), get(), get(), get(), get()) }
    factory { ResetTimerUseCase(get()) }
    viewModel { MainActivityViewModel() }
    viewModel { MainFragmentViewModel(androidApplication()) }
    viewModel { InTrainingFragmentViewModel(androidApplication(), get(), get()) }
}
