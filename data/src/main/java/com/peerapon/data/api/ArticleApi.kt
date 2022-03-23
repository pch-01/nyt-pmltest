package com.peerapon.data.api

import com.agoda.ninjato.extension.call.Call
import com.peerapon.data.api.entity.ArticlesEntity
import com.peerapon.data.api.entity.ResultEntity

interface ArticleApi {
    fun getArticleList(period: Int, apiKey: String): Call<ResultEntity<ArticlesEntity>>
}