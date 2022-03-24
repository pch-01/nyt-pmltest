package com.peerapon.app.di

import com.peerapon.data.repository.ArticleDetailRepository
import com.peerapon.data.source.ArticleRepository
import com.peerapon.domain.contract.ArticleUseCase
import com.peerapon.domain.contract.ArticleUseCaseImpl
import com.peerapon.domain.interactor.ArticleDetailInteractor
import com.peerapon.domain.interactor.ArticleDetailInteractorImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.Dispatchers

@InstallIn(ViewModelComponent::class)
@Module
class ViewModelModule {

    @Provides
    fun provideArticleDetailInteractor(
        articleDetailRepository: ArticleDetailRepository
    ): ArticleDetailInteractor {
        return ArticleDetailInteractorImpl(articleDetailRepository, Dispatchers.IO)
    }

    @Provides
    fun provideArticleUseCase(
        articleRepository: ArticleRepository
    ): ArticleUseCase {
        return ArticleUseCaseImpl(articleRepository)
    }
}