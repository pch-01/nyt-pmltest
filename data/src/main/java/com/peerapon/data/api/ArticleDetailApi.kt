package com.peerapon.data.api

import com.agoda.ninjato.extension.call.Call
import com.peerapon.data.api.entity.ArticleDetailEntity

interface ArticleDetailApi {
    fun getArticleDetail(id: String): Call<ArticleDetailEntity>
}