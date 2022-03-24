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

    private var cache: List<ArticlesEntity>? = null

    override suspend fun load(refresh: Boolean, period: Int, apiKey: String): List<ArticlesEntity> {
        return withContext(ioDispatcher) {
            if (!refresh && cache != null && cache?.isNotEmpty() == true) {
                return@withContext cache ?: emptyList()
            } else {
                val response = articleApi.getArticleList(
                    period = period,
                    apiKey = apiKey,
                )

                if (response is Call.Success) {
                    val successResult = response.result.list
                    cache = successResult
                    return@withContext successResult.also {
                        articleListDao.insertAll(
                            mapToDbEntity(it, period)
                        )
                    }
                } else {
                    return@withContext emptyList()
                }
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