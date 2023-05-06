package com.mangbaam.mvitemplate.sample.newssearch.list

import android.os.Parcelable
import com.mangbaam.mvitemplate.sample.newssearch.Article
import kotlinx.parcelize.Parcelize

@Parcelize
data class SearchState(
    val loading: Boolean = false,
    val latestKeyword: String = "",
    val result: List<Article> = emptyList(),
    val error: String? = null,
) : Parcelable {
    val empty get() = result.isEmpty()
}
