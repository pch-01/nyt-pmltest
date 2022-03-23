package com.peerapon.domain.contract

data class ArticleListViewState(
    val title: String,
    val thumbnailUri: String,
    val id: String,
    val url: String
)