package com.android.newsspace

import com.android.newsspace.data.NewsRepository
import com.android.newsspace.data.NewsRepositoryImpl
import com.android.newsspace.data.service.NewsService
import com.android.newsspace.domain.GetLatesteNewsUseCase
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

fun configureDomainModuleForTest() = module {
    factory<GetLatesteNewsUseCase> { GetLatesteNewsUseCase(get()) }
}

fun configureDataModuleForTest(baseUrl: String) = module {

    //instanciar API
    single {
        val factory = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create(factory))
            .build()
            .create(NewsService::class.java)
    }

    //instanciar repository
    single<NewsRepository> { NewsRepositoryImpl(get()) }



}