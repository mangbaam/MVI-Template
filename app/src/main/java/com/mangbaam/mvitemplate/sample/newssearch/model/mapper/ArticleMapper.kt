package com.mangbaam.mvitemplate.sample.newssearch.model.mapper

import com.mangbaam.mvitemplate.sample.newssearch.data.ArticleDto
import com.mangbaam.mvitemplate.sample.newssearch.model.Article

fun ArticleDto.toArticle() = Article(
    id = "",
    title = title,
    desc = desc,
    author = author,
    url = url,
    urlToImage = urlToImage,
    content = content,
)
