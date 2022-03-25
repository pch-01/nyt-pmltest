package com.peerapon.domain.usecase

import com.peerapon.domain.contract.ArticleListViewState
import com.peerapon.domain.contract.Period

interface ArticleUseCase {
    suspend fun getListArticle(refresh: Boolean, period: Period, key: String): Result<List<ArticleListViewState>>
}