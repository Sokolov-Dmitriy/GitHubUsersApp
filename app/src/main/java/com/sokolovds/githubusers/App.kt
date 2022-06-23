package com.sokolovds.githubusers

import android.app.Application
import com.sokolovds.data.di.cloudModule
import com.sokolovds.data.di.repositoriesModule
import com.sokolovds.githubusers.di.appModule
import com.sokolovds.githubusers.di.domainModule
import org.koin.android.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            if (BuildConfig.DEBUG) {
                androidLogger(Level.ERROR)
            }
            androidContext(this@App)
            modules(listOf(appModule, domainModule, cloudModule, repositoriesModule))
        }
    }
}