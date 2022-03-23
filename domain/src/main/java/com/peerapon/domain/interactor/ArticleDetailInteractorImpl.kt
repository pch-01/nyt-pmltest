package com.peerapon.domain.interactor

import com.agoda.ninjato.extension.call.Call
import com.peerapon.data.repository.ArticleDetailRepository
import com.peerapon.domain.Result
import com.peerapon.domain.contract.ArticleDetail
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class ArticleDetailInteractorImpl(
    private val articleDetailRepository: ArticleDetailRepository,
    private val ioDispatcher: CoroutineDispatcher
) : ArticleDetailInteractor {
    override suspend fun getArticleDetail(id: String): Result<ArticleDetail> =
        withContext(ioDispatcher) {
            val result = articleDetailRepository.getArticleDetail(id)
            if (result is Call.Success) {
                val successResult = result.result
                Result.Success(
                    ArticleDetail(
                        title = successResult.title,
                        abstractText = successResult.abstractText,
                        thumbnailUrl = successResult.thumbnailUrl
                    )
                )
            } else {
                Result.Failure((result as Call.Failure).throwable)
            }
        }
}