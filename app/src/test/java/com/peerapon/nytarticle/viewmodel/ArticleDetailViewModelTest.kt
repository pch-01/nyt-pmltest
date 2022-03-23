package com.peerapon.nytarticle.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.peerapon.app.viewmodel.ArticleDetailViewModel
import com.peerapon.domain.contract.ArticleDetail
import com.peerapon.domain.interactor.ArticleDetailInteractor
import com.peerapon.nytarticle.rule.CoroutineTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.eq
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ArticleDetailViewModelTest {

    @Mock
    lateinit var articleDetailInteractor: ArticleDetailInteractor

    @Mock
    lateinit var errorObserver: Observer<Boolean>

    @Mock
    lateinit var loadingObserver: Observer<Boolean>

    @Mock
    lateinit var detailObserver: Observer<ArticleDetail?>

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = CoroutineTestRule()

    @Test
    fun `load state when init view model`() = runBlocking {
        val viewModel = ArticleDetailViewModel(articleDetailInteractor)

        viewModel.onLoad("")

        viewModel.showLoading.observeForever(loadingObserver)
        viewModel.showError.observeForever(errorObserver)
        viewModel.content.observeForever(detailObserver)

        verify(loadingObserver).onChanged(true)
        verify(errorObserver).onChanged(false)
        verify(detailObserver).onChanged(null)
    }

    @Test
    fun `load but detail got failed result`() = testCoroutineRule.runBlockingTest {
        `when`(articleDetailInteractor.getArticleDetail("")).thenReturn(
            com.peerapon.domain.Result.Failure(
                NullPointerException()
            )
        )
        val viewModel = ArticleDetailViewModel(articleDetailInteractor)

        viewModel.onLoad("")

        viewModel.showLoading.observeForever(loadingObserver)
        viewModel.showError.observeForever(errorObserver)
        viewModel.content.observeForever(detailObserver)

        verify(loadingObserver).onChanged(false)
        verify(errorObserver).onChanged(true)
        verify(detailObserver).onChanged(null)
    }

    @Test
    fun `load detail got successfully`() = testCoroutineRule.runBlockingTest {
        `when`(articleDetailInteractor.getArticleDetail("id1")).thenReturn(
            com.peerapon.domain.Result.Success<ArticleDetail>(
                ArticleDetail(
                    "abt", "title", "url:/image"
                )
            )
        )

        val viewModel = ArticleDetailViewModel(articleDetailInteractor)
        viewModel.onLoad("id1")

        viewModel.showLoading.observeForever(loadingObserver)
        viewModel.showError.observeForever(errorObserver)
        viewModel.content.observeForever(detailObserver)

        verify(loadingObserver).onChanged(false)
        verify(errorObserver).onChanged(false)
        verify(detailObserver).onChanged(
            eq(
                ArticleDetail(
                    "abt", "title", "url:/image"
                )
            )
        )
    }
}