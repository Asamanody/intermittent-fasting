package com.el3asas.regym

import android.app.Application
import com.el3asas.regym.di.ClientsModules
import com.el3asas.regym.di.DataBaseModule
import com.el3asas.regym.di.RepositoriesModules
import com.el3asas.regym.di.ViewModelModules
import com.el3asas.regym.utils.appLaunched
import com.intuit.sdp.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import timber.log.Timber

class MyNewApp : Application() {
    override fun onCreate() {
        super.onCreate()
        appLaunched(BuildConfig.APPLICATION_ID)
        startKoin {
            androidLogger(if (BuildConfig.DEBUG) org.koin.core.logger.Level.ERROR else org.koin.core.logger.Level.NONE)
            androidContext(this@MyNewApp)
            modules(listOf(DataBaseModule, RepositoriesModules, ViewModelModules, ClientsModules))
        }
        Timber.plant(Timber.DebugTree())
    }
}