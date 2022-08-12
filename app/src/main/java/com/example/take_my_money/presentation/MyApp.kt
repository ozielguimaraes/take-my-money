package com.example.take_my_money.presentation

import android.app.Application
import com.example.take_my_money.data.di.loadRepositoryModule
import com.example.take_my_money.data.di.loadWebService
import com.example.take_my_money.presentation.di.loadPresentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        loadKoin()
    }

    private fun loadKoin() {
        startKoin {
            androidLogger()
            androidContext(this@MyApp)
        }
        loadKoinModules(
            loadPresentationModule() +
                loadRepositoryModule() +
                loadWebService()
        )
    }
}
