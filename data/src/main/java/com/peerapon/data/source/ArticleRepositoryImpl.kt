package com.peerapon.data.source

import com.agoda.ninjato.extension.call.Call
import com.peerapon.data.api.ArticleApi
import com.peerapon.data.api.entity.ArticlesEntity
import com.peerapon.data.db.dao.ArticleListDao
import com.peerapon.data.db.entity.ArticleInfoEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class ArticleRepositoryImpl(
    private val articleApi: ArticleApi,
    private val articleListDao: ArticleListDao,
    private val ioDispatcher: CoroutineDispatcher
) : ArticleRepository {

    override suspend fun load(period: Int, apiKey: String): List<ArticlesEntity> {
        return withContext(ioDispatcher) {
            val reponse = articleApi.getArticleList(
                period = 1,
                apiKey = apiKey,
            )

            if (reponse is Call.Success) {
                val successResult = reponse.result.list
                return@withContext successResult.apply {
                    successResult.also {
                        articleListDao.insertAll(
                            mapToDbEntity(it, period)
                        )
                    }
                }
            } else {
                val exceptionMessage = (reponse as? Call.Failure)?.throwable?.message
                throw Exception("failure: $exceptionMessage")
            }
        }
    }

    private fun mapToDbEntity(it: List<ArticlesEntity>, period: Int) =
        it.map {
            ArticleInfoEntity(
                id = it.id,
                period = period,
                title = it.title,
                abstractText = it.abstract,
                bigThumbnailUrl = it.media.firstOrNull { it.type == "image" }?.mediaMetadata?.lastOrNull()?.mediaUrl
                    ?: ""
            )
        }
}