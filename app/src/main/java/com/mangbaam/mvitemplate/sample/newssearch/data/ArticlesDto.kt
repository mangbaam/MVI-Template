package com.mangbaam.mvitemplate.sample.newssearch.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArticlesDto(
    @SerialName("status") val status: String,
    @SerialName("totalResult") val resultCount: Int,
    @SerialName("articles") val articles: List<ArticleDto>,
)

@Serializable
data class ArticleDto(
    @SerialName("source") val id: String,
    @SerialName("author") val author: String,
    @SerialName("title") val title: String,
    @SerialName("description") val desc: String,
    @SerialName("url") val url: String,
    @SerialName("urlToImage") val urlToImage: String,
    @SerialName("content") val content: String,
)
