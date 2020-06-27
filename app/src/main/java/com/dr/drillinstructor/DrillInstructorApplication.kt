package com.dr.drillinstructor

import android.app.Application
import com.dr.drillinstructor.di.mainModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class DrillInstructorApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // start Koin!
        startKoin {
            // declare used Android context
            androidContext(this@DrillInstructorApplication)
            // declare modules
            modules(mainModule)
        }
    }
}
