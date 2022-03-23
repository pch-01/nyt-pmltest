package com.peerapon.data.repository

import com.agoda.ninjato.extension.call.Call
import com.peerapon.data.api.entity.ArticleDetailEntity

interface ArticleDetailRepository {
    fun getArticleDetail(id: String): Call<ArticleDetailEntity>
}