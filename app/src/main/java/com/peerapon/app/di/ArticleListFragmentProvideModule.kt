package com.peerapon.app.di

import com.peerapon.app.ArticleListFragment
import com.peerapon.app.adapter.ArticleListAdapter
import com.peerapon.app.adapter.DiffUtilCallBack
import dagger.Module
import dagger.Provides


@Module
class ArticleListFragmentProvideModule {

    @Provides
    fun provideArticleListAdapter(fragment: ArticleListFragment): ArticleListAdapter =
        ArticleListAdapter(DiffUtilCallBack()) { fragment.goToDetailScreen(it) }
}