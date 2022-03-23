package com.peerapon.nytarticle.screen

import com.agoda.kakao.common.views.KView
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KTextView
import com.peerapon.app.R

class ArticleDetailScreen : Screen<ArticleDetailScreen>() {
    val viewGroup = KView { withId(R.id.contentViewGroup) }

    val titleTextView = KTextView { withId(R.id.titleTextView) }
    val abstractTextView = KTextView { withId(R.id.abstractTextView) }

    init {
        rootView = viewGroup
    }
}