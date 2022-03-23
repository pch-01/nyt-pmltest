package com.peerapon.app.di

import com.peerapon.app.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module(
    includes = [AppModule::class, ViewModelModule::class,
        ArticleListFragmentProvideModule::class]
)
abstract class MainActivityArticleAndroidInjectorModule {

    @ContributesAndroidInjector(modules = [FragmentModule::class])
    abstract fun contributeMainActivity(): MainActivity
}
