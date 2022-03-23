package com.peerapon.app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peerapon.app.BuildConfig
import com.peerapon.data.source.ArticleRepositoryImpl
import com.peerapon.domain.contract.ArticleListViewState
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ArticleListViewModel @Inject constructor(
    private val repositoryImpl: ArticleRepositoryImpl
) : ViewModel() {

    private val _showError = MutableLiveData<Boolean>()
    val showError: LiveData<Boolean> = _showError

    private val _showLoading = MutableLiveData<Boolean>()
    val showLoading: LiveData<Boolean> = _showLoading

    private val _showContent = MutableLiveData<Boolean>()

    private val _uiState = MutableStateFlow<List<ArticleListViewState>>(emptyList())
    val content: StateFlow<List<ArticleListViewState>> = _uiState

    init {
        viewModelScope.launch {
           val deferred = async {
               repositoryImpl.load(1, BuildConfig.API_KEY).map {
                   ArticleListViewState(
                       title = it.title,
                       thumbnailUri = it.uri,
                       id = it.id,
                       url = it.url,
                   )
               }
           }
            _uiState.value = deferred.await()
        }
    }

}