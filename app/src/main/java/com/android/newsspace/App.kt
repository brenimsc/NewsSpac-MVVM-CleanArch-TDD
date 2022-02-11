package com.android.newsspace

import android.app.Application
import com.android.newsspace.data.di.DataModule
import com.android.newsspace.domain.di.DomainModule
import com.android.newsspace.presentation.di.PresentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
        }

        //carregando modulos
        PresentationModule.load()
        DataModule.load()
        DomainModule.load()
    }
}