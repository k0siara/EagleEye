package com.patrykkosieradzki.eagleeye

import android.app.Application
import androidx.databinding.library.BuildConfig
import com.patrykkosieradzki.eagleeye.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class EagleEyeApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidLogger()
            androidContext(this@EagleEyeApplication)
            modules(networkModule, appModule)
        }
    }
}