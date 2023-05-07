package com.mangbaam.mvitemplate.sample.newssearch.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Article(
    val id: String,
    val title: String,
    val desc: String,
    val author: String,
    val url: String,
    val urlToImage: String,
    val content: String,
) : Parcelable
