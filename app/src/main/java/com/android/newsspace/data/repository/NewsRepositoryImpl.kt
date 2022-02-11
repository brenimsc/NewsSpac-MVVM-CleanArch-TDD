package com.android.newsspace.data

import com.android.newsspace.core.RemoteException
import com.android.newsspace.data.model.Launch
import com.android.newsspace.data.model.News
import com.android.newsspace.data.service.NewsService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException



class NewsRepositoryImpl(private val service: NewsService) : NewsRepository {


    override suspend fun getNews(category: String) = flow {
        try {
            val newsList = service.getNewsApi(category)
            emit(newsList)
        } catch (e: HttpException) {
            throw RemoteException("Unable to connect Api")
        }
    }
}

