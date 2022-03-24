package com.peerapon.data.source

import com.agoda.ninjato.extension.call.Call
import com.peerapon.data.api.ArticleApi
import com.peerapon.data.api.entity.ArticlesEntity
import com.peerapon.data.api.entity.MediaEntity
import com.peerapon.data.api.entity.MediaMetadataEntity
import com.peerapon.data.api.entity.ResultEntity
import com.peerapon.data.db.dao.ArticleListDao
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ArticleRepositoryImplTest {

    @Mock
    lateinit var articleApi: ArticleApi

    @Mock
    lateinit var articleListDao: ArticleListDao

    lateinit var repository: ArticleRepository


    private val testCoroutineDispatcher = StandardTestDispatcher()
    private val testCoroutineScope = TestScope(testCoroutineDispatcher)

    @Before
    fun setUp() {
        repository = ArticleRepositoryImpl(
            articleApi,
            articleListDao,
            testCoroutineDispatcher
        )
    }

    @Test
    fun loadError() {
        Mockito.`when`(articleApi.getArticleList(1, "key")).thenReturn(
            Call.Failure(
                Exception("HTTP 429")
            )
        )

        testCoroutineScope.runTest {
            val response = repository.load(true, 1, "key")
            assertEquals(response.isEmpty(), true)
        }
    }

    @Test
    fun loadSuccess() {
        Mockito.`when`(articleApi.getArticleList(1, "key")).thenReturn(
            Call.Success(
                    ResultEntity(listOf(
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
                    ), status = "ok")
        ))

        testCoroutineScope.runTest {
            val response = repository.load(true, 1, "key")
            assertEquals(response.isNotEmpty(), true)
            assertEquals(response[0].abstract, "abstractTest")
        }
    }

    @Test
    fun loadSuccessWithCache() {
        Mockito.`when`(articleApi.getArticleList(1, "key")).thenReturn(
            Call.Success(
                ResultEntity(listOf(
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
                ), status = "ok")
            ))

        testCoroutineScope.runTest {
            val response = repository.load(true, 1, "key")
            assertEquals(response.isNotEmpty(), true)
            assertEquals(response[0].abstract, "abstractTest")

            val responseCache = repository.load(false, 1, "key")
            assertEquals(responseCache.isNotEmpty(), true)
            assertEquals(responseCache[0].abstract, "abstractTest")
        }
    }
}