package com.peerapon.app.viewmodel

import androidx.hilt.Assisted
import androidx.lifecycle.*
import com.peerapon.app.BuildConfig
import com.peerapon.domain.contract.ArticleListViewState
import com.peerapon.domain.contract.Period
import com.peerapon.domain.usecase.ArticleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleListViewModel @Inject constructor(
    private val useCase: ArticleUseCase,
    @Assisted private val state: SavedStateHandle
) : ViewModel() {

    companion object {
        const val SEARCH_KEY = "query"
    }

    private var _uiState = MutableLiveData<List<ArticleListViewState>>(emptyList())
    val queryState = state.getLiveData(SEARCH_KEY, "")
    private val filteredFlow = combine(
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
                useCase.getListArticle(refresh, period, BuildConfig.API_KEY)
            }
            _uiState.value = deferred.await().getOrDefault(emptyList())
        }
    }

    fun onPeriodUpdate(period: Period) = viewModelScope.launch {
        load(period, true)
    }
}
