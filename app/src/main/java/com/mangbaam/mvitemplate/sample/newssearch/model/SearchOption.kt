package com.mangbaam.mvitemplate.sample.newssearch.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SearchOption(
    val keyword: String = "",
    val sortBy: SortBy = SortBy.Popularity,
    val searchIn: Set<SearchIn> = emptySet(),
    val page: Int = 1,
) : Parcelable

enum class SearchIn(val path: String) {
    Title("title"), Description("description"), Content("content")
}

enum class SortBy(val path: String) {
    Relevancy("relevancy"), Popularity("popularity"), PublishedAt("publishedAt")
}
