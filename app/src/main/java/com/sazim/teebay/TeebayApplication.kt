package com.sazim.teebay

import android.app.Application
import com.sazim.teebay.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TeebayApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@TeebayApplication)
            modules(appModule)
        }
    }
}