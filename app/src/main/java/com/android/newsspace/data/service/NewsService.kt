package com.android.newsspace.data.service

import com.android.newsspace.data.model.News
import retrofit2.http.GET
import retrofit2.http.Path

interface NewsService {

//    @GET("articles")
//    suspend fun getNewsApi() : List<News>

    @GET("{type}")
    suspend fun getNewsApi(@Path("type") type: String) : List<News>

}