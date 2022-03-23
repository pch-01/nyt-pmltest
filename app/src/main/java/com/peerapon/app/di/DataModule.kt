package com.peerapon.app.di

import android.content.Context
import com.agoda.ninjato.client.NinjatoOkHttpClient
import com.agoda.ninjato.http.HttpClient
import com.peerapon.app.db.ArticleDatabase
import com.peerapon.data.api.ArticleApi
import com.peerapon.data.api.ArticleApiImpl
import com.peerapon.data.api.ArticleDetailApi
import com.peerapon.data.api.ArticleDetailApiImpl
import com.peerapon.data.db.dao.ArticleListDao
import com.peerapon.data.repository.ArticleDetailRepository
import com.peerapon.data.repository.ArticleDetailRepositoryImpl
import com.peerapon.data.source.ArticleRepositoryImpl
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
class DataModule {

    @Singleton
    @Provides
    fun provideNinjatoHttpClient(): HttpClient {
        return NinjatoOkHttpClient(OkHttpClient())
    }

    @Singleton
    @Provides
    fun provideArticleApi(client: HttpClient): ArticleApi {
        return ArticleApiImpl(client)
    }

    @Singleton
    @Provides
    fun provideDetailApi(client: HttpClient): ArticleDetailApi {
        return ArticleDetailApiImpl(client)
    }

    @Provides
    @Singleton
    fun provideArticleRepository(api: ArticleApi, dao: ArticleListDao): ArticleRepositoryImpl =
        ArticleRepositoryImpl(api, dao, Dispatchers.IO)

    @Singleton
    @Provides
    fun provideArticleDetailRepository(
        articleListDao: ArticleListDao
    ): ArticleDetailRepository {
        return ArticleDetailRepositoryImpl(articleListDao)
    }

    @Singleton
    @Provides
    fun provideDatabase(
        applicationContext: Context
    ) = ArticleDatabase.getInstance(applicationContext)

    @Singleton
    @Provides
    fun provideArticleListDao(db: ArticleDatabase) = db.articleListDao()
}
