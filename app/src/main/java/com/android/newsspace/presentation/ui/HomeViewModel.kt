package com.android.newsspace.presentation.ui

import androidx.lifecycle.*
import com.android.newsspace.core.RemoteException
import com.android.newsspace.core.State
import com.android.newsspace.data.NewsCategory
import com.android.newsspace.data.NewsRepository
import com.android.newsspace.data.model.News
import com.android.newsspace.domain.GetLatesteNewsUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class HomeViewModel(private val getLatesteNewsUseCase: GetLatesteNewsUseCase) : ViewModel() {


    private val _progressBar = MutableLiveData<Boolean>(false)
    val progressBar: LiveData<Boolean> get() = _progressBar

    fun showProgressBar() {
        _progressBar.value = true
    }

    fun hideProgressBar() {
        _progressBar.value = false
    }


    private val _snackbar = MutableLiveData<String?>(null)
    val snackbar: LiveData<String?> get() = _snackbar

    fun onSnackBarShow() {
        _snackbar.value = null
    }



    private val _listNews = MutableLiveData<State<List<News>>>()
    val listNews: LiveData<State<List<News>>> get() = _listNews

    init {
        fetchPosts(NewsCategory.ARTICLE)
    }


    fun fetchPosts(category: NewsCategory) {
        viewModelScope.launch {
            getLatesteNewsUseCase.execute(category.value)
                .onStart {
                    _listNews.postValue(State.Loading)
                    delay(500)
                }
                .catch {
                    val exception =  RemoteException("Unable to conecct to Api")
                    _listNews.postValue(State.Error(exception))
                    _snackbar.value = exception.message
                }
                .collect {
                _listNews.postValue(State.Success(it))
            }
        }
    }

    val helloText = Transformations.map(listNews) {
        when (it) {
            is State.Loading -> {
                "Carregando news..."
            }
            is State.Error -> {
                "Oops! Ocorreu um problema!"
            }
            is State.Success -> {
                ""
            }
        }
    }
}