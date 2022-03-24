package com.peerapon.domain.contract

interface ArticleUseCase {
    suspend fun getListArticle(refresh: Boolean, period: Period, key: String): Result<List<ArticleListViewState>>
}