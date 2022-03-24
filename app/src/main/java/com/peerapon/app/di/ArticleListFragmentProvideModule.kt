package com.peerapon.app.di

import com.peerapon.app.adapter.ArticleListAdapter
import com.peerapon.app.adapter.DiffUtilCallBack
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
class ArticleListFragmentProvideModule {

    @Provides
    fun provideArticleListAdapter(): ArticleListAdapter =
        ArticleListAdapter(DiffUtilCallBack())
}