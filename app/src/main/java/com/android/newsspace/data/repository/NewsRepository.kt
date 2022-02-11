package com.android.newsspace.data

import com.android.newsspace.data.model.News
import kotlinx.coroutines.flow.Flow


interface NewsRepository {

    suspend fun getNews(category: String) : Flow<List<News>>

}