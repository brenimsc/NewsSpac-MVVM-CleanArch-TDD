package com.android.newsspace.data.di

import android.util.Log
import com.android.newsspace.data.NewsRepository
import com.android.newsspace.data.NewsRepositoryImpl
import com.android.newsspace.data.service.NewsService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

// Organiza as dependencias da camada Data
object DataModule {

    private const val BASE_URL = "https://api.spaceflightnewsapi.net/v3/"


    fun load() {
        loadKoinModules(newsModule() + networkModule())
    }

    private fun newsModule(): Module {
        return module {
            single<NewsRepository> { NewsRepositoryImpl(get()) }
        }
    }

    private fun networkModule(): Module {
        return module {
            single<NewsService> { creteService(get(), get()) }
            single { Moshi.Builder().add(KotlinJsonAdapterFactory()).build() }
            single {

                val interceptor = HttpLoggingInterceptor {
                    Log.e("OKHTTP", it)
                }

                interceptor.level = HttpLoggingInterceptor.Level.BODY

                OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .build()
            }

        }
    }

    private inline fun <reified T> creteService(
        factory: Moshi,
        client: OkHttpClient
    ): T {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(factory))
            .client(client)
            .build()
            .create(T::class.java)
    }
}