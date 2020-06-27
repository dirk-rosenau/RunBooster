package com.dr.drillinstructor.di

import android.media.MediaPlayer
import com.dr.drillinstructor.AlarmHelper
import com.dr.drillinstructor.SoundPlayer
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val mainModule = module {
    single { MediaPlayer() }
    single { SoundPlayer(androidContext().assets, get()) }
    single { AlarmHelper(androidContext()) }
}
