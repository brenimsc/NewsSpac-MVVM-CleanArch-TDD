package com.android.newsspace.domain.di

import com.android.newsspace.domain.GetLatesteNewsUseCase
import get
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

object DomainModule {

    fun load() {
        loadKoinModules(useCaseModule())
    }

    private fun useCaseModule(): Module {
        return module {
            factory { GetLatesteNewsUseCase(get()) }
        }
    }
}