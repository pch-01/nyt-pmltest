package com.peerapon.app.di

import com.peerapon.app.ArticleDetailFragment
import com.peerapon.app.ArticleListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {
        @ContributesAndroidInjector(modules = [ArticleListFragmentProvideModule::class])
        abstract fun contributeArticleListFragment(): ArticleListFragment

        @ContributesAndroidInjector
        abstract fun contributeArticleDetailFragment(): ArticleDetailFragment
}