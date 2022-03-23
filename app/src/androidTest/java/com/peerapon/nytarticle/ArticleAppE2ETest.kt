package com.peerapon.nytarticle

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.agoda.kakao.screen.Screen.Companion.onScreen
import com.peerapon.app.MainActivity
import com.peerapon.nytarticle.screen.ArticleDetailScreen
import com.peerapon.nytarticle.screen.ArticleListScreen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Thread.sleep

@RunWith(AndroidJUnit4ClassRunner::class)
class ArticleAppE2ETest {

    @Rule
    @JvmField
    val rule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testCheckArticleFlow() {
        onScreen<ArticleListScreen> {
            recycler {
                isDisplayed()
                sleep(5000L)
                firstChild<ArticleListScreen.ListItem> {
                    title {
                        isDisplayed()
                        hasAnyText()
                        click()
                    }
                }
            }
        }

        onScreen<ArticleDetailScreen> {
            titleTextView {
                hasAnyText()
                isDisplayed()
            }

            abstractTextView {
                hasAnyText()
                isDisplayed()
            }
        }
    }
}