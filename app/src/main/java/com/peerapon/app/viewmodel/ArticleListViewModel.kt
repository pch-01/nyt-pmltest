package com.peerapon.app.viewmodel

import androidx.hilt.Assisted
import androidx.lifecycle.*
import com.peerapon.app.BuildConfig
import com.peerapon.data.source.ArticleRepositoryImpl
import com.peerapon.domain.contract.ArticleListViewState
import com.peerapon.domain.contract.Period
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleListViewModel @Inject constructor(
    private val repositoryImpl: ArticleRepositoryImpl,
    @Assisted private val state: SavedStateHandle
) : ViewModel() {

    companion object {
        const val SEARCH_KEY = "query"
    }

    private var _uiState = MutableLiveData<List<ArticleListViewState>>(emptyList())
    val queryState = state.getLiveData(SEARCH_KEY, "")

    val filteredFlow = combine(
        queryState.asFlow(),
        _uiState.asFlow()
    ) { query, currentState ->
        if (query.isNotBlank() && query.isNotEmpty() && currentState != null) {
            currentState.filter { it.title.contains(query, ignoreCase = true) }
        } else {
            currentState
        }
    }
    val filteredContent: LiveData<List<ArticleListViewState>> = filteredFlow.asLiveData()

    init {
        viewModelScope.launch {
            load(Period.ONE_DAY, refresh = true)
        }
    }

    suspend fun load(period: Period, refresh: Boolean = true) {
        viewModelScope.launch {
            val deferred = async {
                kotlin.runCatching {
                    getListArticle(refresh, period)
                }
            }
            _uiState.value = deferred.await().getOrDefault(emptyList()).also {
            }
        }
    }

    private suspend fun getListArticle(
        refresh: Boolean,
        period: Period
    ) = repositoryImpl.load(refresh, period.days, BuildConfig.API_KEY).map {
        ArticleListViewState(
            title = it.title,
            thumbnailUri = it.uri,
            id = it.id,
            url = it.url,
        )
    }

    fun onPeriodUpdate(period: Period) = viewModelScope.launch {
        load(period, true)
    }
}
