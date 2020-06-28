package com.dr.drillinstructor.di

import android.media.MediaPlayer
import com.dr.drillinstructor.SoundPlayer
import com.dr.drillinstructor.TrainingManager
import com.dr.drillinstructor.TrainingStateProvider
import com.dr.drillinstructor.TrainingStateProviderImpl
import com.dr.drillinstructor.wrapper.AlarmHelper
import com.dr.drillinstructor.wrapper.VibrationHelper
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val mainModule = module {
    single { MediaPlayer() }
    single { SoundPlayer(androidContext().assets, get()) }
    single { AlarmHelper(androidContext()) }
    single { VibrationHelper(androidContext()) }
    single { TrainingStateProviderImpl() as TrainingStateProvider } // do not delete cast
    single { TrainingManager(get(), get()) }
}
