package com.peerapon.domain.usecase

import com.peerapon.data.source.ArticleRepository
import com.peerapon.domain.contract.ArticleListViewState
import com.peerapon.domain.contract.Period
import javax.inject.Inject

class ArticleUseCaseImpl @Inject constructor(private val articleRepository: ArticleRepository) :
    ArticleUseCase {
    override suspend fun getListArticle(refresh: Boolean, period: Period, key: String) =
        kotlin.runCatching {
            articleRepository.load(refresh, period.days, key).map {
                ArticleListViewState(
                    title = it.title,
                    thumbnailUri = it.uri,
                    id = it.id,
                    url = it.url,
                )
            }
        }
}