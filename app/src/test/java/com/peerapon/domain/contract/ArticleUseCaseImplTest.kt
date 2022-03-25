package com.peerapon.domain.contract

import com.peerapon.data.api.entity.ArticlesEntity
import com.peerapon.data.api.entity.MediaEntity
import com.peerapon.data.api.entity.MediaMetadataEntity
import com.peerapon.data.source.ArticleRepository
import com.peerapon.domain.usecase.ArticleUseCase
import com.peerapon.domain.usecase.ArticleUseCaseImpl
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ArticleUseCaseImplTest {

    @Mock
    private lateinit var repository: ArticleRepository
    lateinit var useCase: ArticleUseCase

    @Before
    fun setUp() {
        useCase = ArticleUseCaseImpl(repository)
    }

    @Test
    fun getListArticle() {
        runBlocking {
            Mockito.`when`(repository.load(false, 1, "key")).thenReturn(
                listOf(
                    ArticlesEntity(
                        id = "1",
                        title = "test",
                        abstract = "abstractTest",
                        url = "urlNews",
                        uri = "uri",
                        media = listOf(
                            MediaEntity(
                                type = "image",
                                mediaMetadata = listOf(
                                    MediaMetadataEntity(
                                        mediaUrl = "imageUrl"
                                    )
                                )
                            )
                        )
                    )
                )
            )

            val value = useCase.getListArticle(false, Period.ONE_DAY, "key")
            assertEquals(value.getOrNull()?.get(0)?.title, "test")
        }
    }
}