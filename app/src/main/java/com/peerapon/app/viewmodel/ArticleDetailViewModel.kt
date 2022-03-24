package com.peerapon.app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peerapon.domain.Result
import com.peerapon.domain.contract.ArticleDetail
import com.peerapon.domain.interactor.ArticleDetailInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleDetailViewModel @Inject constructor(
    private val articleDetailInteractor: ArticleDetailInteractor
) : ViewModel() {

    private val _showError = MutableLiveData<Boolean>()
    val showError: LiveData<Boolean> = _showError

    private val _showLoading = MutableLiveData<Boolean>()
    val showLoading: LiveData<Boolean> = _showLoading

    private val _showContent = MutableLiveData<Boolean>()

    private val _content = MutableLiveData<ArticleDetail?>()
    val content: LiveData<ArticleDetail?> = _content

    fun onLoad(id: String) {
        loadState()

        viewModelScope.launch {
            when (val user = articleDetailInteractor.getArticleDetail(id)) {
                is Result.Success -> {
                    val userValue = user.value
                    setContentState(userValue)
                }
                is Result.Failure -> errorState()
            }
        }
    }

    private fun setContentState(result: ArticleDetail?) {
        _showContent.postValue(true)
        _content.postValue(result)
        _showLoading.postValue(false)
        _showError.postValue(false)
    }

    private fun loadState() {
        _showLoading.postValue(true)
        _showError.postValue(false)
        _content.postValue(null)
        _showContent.postValue(false)
    }

    private fun errorState() {
        _showLoading.postValue(false)
        _showError.postValue(true)
        _content.postValue(null)
        _showContent.postValue(false)
    }
}