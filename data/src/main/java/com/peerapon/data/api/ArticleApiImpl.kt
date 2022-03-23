package com.peerapon.data.api

import com.agoda.ninjato.Api
import com.agoda.ninjato.converter.GsonBodyConverterFactory
import com.agoda.ninjato.extension.call.Call
import com.agoda.ninjato.extension.call.call
import com.agoda.ninjato.http.HttpClient
import com.peerapon.data.api.entity.ArticlesEntity
import com.peerapon.data.api.entity.ResultEntity

class ArticleApiImpl(
    client: HttpClient
) : Api(client), ArticleApi {
    override val baseUrl: String
        get() = "https://api.nytimes.com/svc/mostpopular/v2/viewed/"

    override fun getArticleList(
        period: Int,
        apiKey: String,
    ): Call<ResultEntity<ArticlesEntity>> =
        call {
            this.get {
                endpointUrl = "$period.json?api-key=$apiKey"
                converterFactories += GsonBodyConverterFactory()
            }
        }
}