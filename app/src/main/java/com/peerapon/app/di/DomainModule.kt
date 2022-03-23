package com.peerapon.app.di

import com.peerapon.data.repository.ArticleDetailRepository
import com.peerapon.domain.interactor.ArticleDetailInteractor
import com.peerapon.domain.interactor.ArticleDetailInteractorImpl
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers

@Module
class DomainModule {

    @Provides
    fun provideArticleDetailInteractor(
        articleDetailRepository: ArticleDetailRepository
    ): ArticleDetailInteractor {
        return ArticleDetailInteractorImpl(articleDetailRepository, Dispatchers.IO)
    }
}
