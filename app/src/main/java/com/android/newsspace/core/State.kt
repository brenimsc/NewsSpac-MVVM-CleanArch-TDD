package com.android.newsspace.core

import com.android.newsspace.data.model.News

sealed class State<out T: Any>  {

    object Loading : State<Nothing>()

    data class Success<out T: Any>(val result: List<News>) : State<T>()

    data class Error(val error: Throwable) : State<Nothing>()
}