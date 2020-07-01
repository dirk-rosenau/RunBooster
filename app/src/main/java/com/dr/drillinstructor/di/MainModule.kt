package com.dr.drillinstructor.di

import android.media.MediaPlayer
import android.preference.PreferenceManager
import com.dr.drillinstructor.util.*
import com.dr.drillinstructor.wrapper.AlarmHelper
import com.dr.drillinstructor.wrapper.SoundPlayer
import com.dr.drillinstructor.wrapper.VibrationHelper
import org.koin.android.ext.koin.androidContext
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
    single { VibrationHelper(androidContext()) }
    single { TrainingStateProviderImpl(get()) as TrainingStateProvider } // do not delete cast
    single { PreferenceRepositoryImpl(PreferenceManager.getDefaultSharedPreferences(androidContext())) as PreferenceRepository }
    single { TrainingManager(get(), get(), get(), get(), get()) }
}
