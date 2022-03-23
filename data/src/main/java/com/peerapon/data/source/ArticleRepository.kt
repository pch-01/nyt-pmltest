package com.peerapon.data.source

import com.peerapon.data.api.entity.ArticlesEntity

interface ArticleRepository {
    suspend fun load(period: Int, apiKey: String): List<ArticlesEntity>
}