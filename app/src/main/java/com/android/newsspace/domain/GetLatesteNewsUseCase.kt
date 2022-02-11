package com.android.newsspace.domain

import com.android.newsspace.core.UseCase
import com.android.newsspace.data.NewsRepository
import com.android.newsspace.data.model.News
import kotlinx.coroutines.flow.Flow

class GetLatesteNewsUseCase(private val repository: NewsRepository) : UseCase<String, List<News>>() {

    override suspend fun execute(param: String): Flow<List<News>> = repository.getNews(param)

}