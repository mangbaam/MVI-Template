package com.mangbaam.mvitemplate.sample.newssearch.ui.list

import android.os.Parcelable
import com.mangbaam.mvitemplate.sample.newssearch.model.Article
import com.mangbaam.mvitemplate.sample.newssearch.model.SearchOption
import kotlinx.parcelize.Parcelize

@Parcelize
data class SearchState(
    val loading: Boolean = false,
    val searchOption: SearchOption = SearchOption(),
    val result: List<Article> = emptyList(),
    val error: String? = null,
) : Parcelable {
    val empty get() = result.isEmpty()
    val latestKeyword get() = searchOption.keyword
}
