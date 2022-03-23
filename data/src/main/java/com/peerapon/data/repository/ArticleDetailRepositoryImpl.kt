package com.peerapon.data.repository

import com.agoda.ninjato.extension.call.Call
import com.peerapon.data.api.entity.ArticleDetailEntity
import com.peerapon.data.db.dao.ArticleListDao

class ArticleDetailRepositoryImpl(
    private val articleDetailDao: ArticleListDao
) : ArticleDetailRepository {
    override fun getArticleDetail(id: String): Call<ArticleDetailEntity> =
        articleDetailDao.getArticleDetail(id)?.let {
            Call.Success(ArticleDetailEntity(abstractText = it.abstractText, title = it.title, thumbnailUrl = it.bigThumbnailUrl))
        } ?: Call.Failure(Exception("Cannot find related article"))
}