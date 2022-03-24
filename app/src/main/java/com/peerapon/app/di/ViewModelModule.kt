package com.peerapon.app.di

import com.peerapon.data.repository.ArticleDetailRepository
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
}