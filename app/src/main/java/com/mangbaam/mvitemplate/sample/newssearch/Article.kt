package com.mangbaam.mvitemplate.sample.newssearch

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Article(
    val id: String,
    val title: String,
    val desc: String,
) : Parcelable
