package com.peerapon.nytarticle.screen

import android.view.View
import com.agoda.kakao.recycler.KRecyclerItem
import com.agoda.kakao.recycler.KRecyclerView
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KTextView
import com.peerapon.app.R
import org.hamcrest.Matcher

open class ArticleListScreen : Screen<ArticleListScreen>() {
    val recycler: KRecyclerView = KRecyclerView({
        withId(R.id.recyclerView)
    }, itemTypeBuilder = {
        itemType(::ListItem)
    })

    class ListItem(parent: Matcher<View>) : KRecyclerItem<ListItem>(parent) {
        val title: KTextView = KTextView(parent) { withId(R.id.itemTitle) }
    }
}