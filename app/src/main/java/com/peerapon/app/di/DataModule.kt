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
import com.peerapon.data.source.ArticleRepository
import com.peerapon.data.source.ArticleRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DataModule {

    @Singleton
    @Provides
    fun provideNinjatoHttpClient(): HttpClient {
        val okHttpClient = OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }).build()
        return NinjatoOkHttpClient(okHttpClient)
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
    fun provideArticleRepository(api: ArticleApi, dao: ArticleListDao): ArticleRepository =
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
        @ApplicationContext applicationContext: Context
    ) = ArticleDatabase.getInstance(applicationContext)

    @Singleton
    @Provides
    fun provideArticleListDao(db: ArticleDatabase) = db.articleListDao()
}
