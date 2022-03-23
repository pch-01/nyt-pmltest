package com.peerapon.data.api

import com.agoda.ninjato.Api
import com.agoda.ninjato.converter.GsonBodyConverterFactory
import com.agoda.ninjato.extension.call.Call
import com.agoda.ninjato.extension.call.call
import com.agoda.ninjato.http.HttpClient
import com.peerapon.data.api.entity.ArticleDetailEntity

class ArticleDetailApiImpl(
    client: HttpClient
) : Api(client), ArticleDetailApi {

    override val baseUrl: String
        get() = ""

    override fun getArticleDetail(id: String): Call<ArticleDetailEntity> =
        call<ArticleDetailEntity> {
            this.get {
                fullUrl = id
                converterFactories += GsonBodyConverterFactory()
            }
        }
}