package com.peerapon.domain.interactor

import com.peerapon.domain.Result
import com.peerapon.domain.contract.ArticleDetail

interface ArticleDetailInteractor {
    suspend fun getArticleDetail(id: String): Result<ArticleDetail>
}