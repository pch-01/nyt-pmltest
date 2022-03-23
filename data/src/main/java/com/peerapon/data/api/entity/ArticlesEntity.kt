package com.peerapon.data.api.entity

data class ArticlesEntity(
    val id: String,
    val title: String,
    val url: String,
    val uri: String,
    val media: List<MediaEntity>,
    val abstract: String
)