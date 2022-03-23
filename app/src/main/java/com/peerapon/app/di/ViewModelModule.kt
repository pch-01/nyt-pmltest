package com.peerapon.app.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.peerapon.app.viewmodel.ArticleDetailViewModel
import com.peerapon.app.viewmodel.ArticleListViewModel
import com.peerapon.app.viewmodel.factory.BaseViewModelFactory
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import kotlin.reflect.KClass

@Module
abstract class ViewModelModule {
    @Binds
    abstract fun bindViewModelFactory(factory: BaseViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ModelKey(ArticleListViewModel::class)
    abstract fun bindListViewModel(viewModel: ArticleListViewModel): ViewModel

    @Binds
    @IntoMap
    @ModelKey(ArticleDetailViewModel::class)
    abstract fun bindDetailViewModel(viewModel: ArticleDetailViewModel): ViewModel

}

@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ModelKey(val value: KClass<out ViewModel>)
